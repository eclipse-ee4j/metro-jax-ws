/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.pipe;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.addressing.W3CWsaClientTube;
import com.sun.xml.ws.addressing.v200408.MemberSubmissionWsaClientTube;
import com.sun.xml.ws.api.EndpointAddress;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.WSService;
import com.sun.xml.ws.api.addressing.AddressingVersion;
import com.sun.xml.ws.api.client.ClientPipelineHook;
import com.sun.xml.ws.api.client.WSPortInfo;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.pipe.helper.PipeAdapter;
import com.sun.xml.ws.api.server.Container;
import com.sun.xml.ws.binding.BindingImpl;
import com.sun.xml.ws.client.ClientSchemaValidationTube;
import com.sun.xml.ws.developer.SchemaValidationFeature;
import com.sun.xml.ws.developer.WSBindingProvider;
import com.sun.xml.ws.handler.ClientLogicalHandlerTube;
import com.sun.xml.ws.handler.ClientMessageHandlerTube;
import com.sun.xml.ws.handler.ClientSOAPHandlerTube;
import com.sun.xml.ws.handler.HandlerTube;
import com.sun.xml.ws.protocol.soap.ClientMUTube;
import com.sun.xml.ws.transport.DeferredTransportPipe;
import com.sun.xml.ws.util.pipe.DumpTube;

import javax.xml.ws.soap.SOAPBinding;
import java.io.PrintStream;

/**
 * Factory for well-known {@link Tube} implementations
 * that the {@link TubelineAssembler} needs to use
 * to satisfy JAX-WS requirements.
 *
 * @author Jitendra Kotamraju
 */
public class ClientTubeAssemblerContext {

    private final @NotNull EndpointAddress address;
    private final @Nullable WSDLPort wsdlModel;
    private final @Nullable SEIModel seiModel;
    private final @Nullable Class    sei;
    private final @NotNull WSService rootOwner;
    private final @NotNull WSBinding binding;
    private final @NotNull Container container;
    private @NotNull Codec codec;

    //Nullable only to maintain comaptibility with old constructors of this class.
    private final @Nullable WSBindingProvider bindingProvider;

    /**
     * This constructor should be used only by JAX-WS Runtime and is not meant for external consumption.
     * @deprecated
     *      Use {@link #ClientTubeAssemblerContext(EndpointAddress, WSDLPort, WSService, WSBindingProvider, WSBinding, Container, Codec, SEIModel, Class)}
     */
    public ClientTubeAssemblerContext(@NotNull EndpointAddress address, @Nullable WSDLPort wsdlModel, @NotNull WSService rootOwner, @NotNull WSBinding binding) {
        this(address, wsdlModel, rootOwner, binding, Container.NONE);
    }

    /**
     * This constructor should be used only by JAX-WS Runtime and is not meant for external consumption.
     * @deprecated
     *      Use {@link #ClientTubeAssemblerContext(EndpointAddress, WSDLPort, WSService, WSBindingProvider, WSBinding, Container, Codec, SEIModel, Class)}
     */
    public ClientTubeAssemblerContext(@NotNull EndpointAddress address, @Nullable WSDLPort wsdlModel,
                                      @NotNull WSService rootOwner, @NotNull WSBinding binding,
                                      @NotNull Container container) {
        // WSBinding is actually BindingImpl
        this(address, wsdlModel, rootOwner, binding, container, ((BindingImpl)binding).createCodec() );
    }

    /**
     * This constructor should be used only by JAX-WS Runtime and is not meant for external consumption.
     * @deprecated
     *      Use {@link #ClientTubeAssemblerContext(EndpointAddress, WSDLPort, WSService, WSBindingProvider, WSBinding, Container, Codec, SEIModel, Class)}
     */
    public ClientTubeAssemblerContext(@NotNull EndpointAddress address, @Nullable WSDLPort wsdlModel,
                                      @NotNull WSService rootOwner, @NotNull WSBinding binding,
                                      @NotNull Container container, Codec codec) {
        this(address, wsdlModel, rootOwner, binding, container, codec, null, null);
    }

    /**
     * This constructor should be used only by JAX-WS Runtime and is not meant for external consumption.
     * @deprecated
     *      Use {@link #ClientTubeAssemblerContext(EndpointAddress, WSDLPort, WSService, WSBindingProvider, WSBinding, Container, Codec, SEIModel, Class)}
     */
    public ClientTubeAssemblerContext(@NotNull EndpointAddress address, @Nullable WSDLPort wsdlModel,
                                      @NotNull WSService rootOwner, @NotNull WSBinding binding,
                                      @NotNull Container container, Codec codec, SEIModel seiModel, Class sei) {
        this(address, wsdlModel, rootOwner, null/* no info on which port it is, so pass null*/, binding, container, codec, seiModel, sei);
    }

    /**
     * This constructor should be used only by JAX-WS Runtime and is not meant for external consumption.
     *
     * @since JAX-WS 2.2
     */
    public ClientTubeAssemblerContext(@NotNull EndpointAddress address, @Nullable WSDLPort wsdlModel,
                                      @NotNull WSBindingProvider bindingProvider, @NotNull WSBinding binding,
                                      @NotNull Container container, Codec codec, SEIModel seiModel, Class sei) {
        this(address, wsdlModel, (bindingProvider==null? null: bindingProvider.getPortInfo().getOwner()), bindingProvider, binding, container, codec, seiModel, sei);

    }

    //common constructor
    //WSService is null, when ClientTubeAssemblerContext is created for sending non-anonymous responses.
    private ClientTubeAssemblerContext(@NotNull EndpointAddress address, @Nullable WSDLPort wsdlModel,
                                       @Nullable WSService rootOwner, @Nullable WSBindingProvider bindingProvider, @NotNull WSBinding binding,
                                      @NotNull Container container, Codec codec, SEIModel seiModel, Class sei) {
        this.address = address;
        this.wsdlModel = wsdlModel;
        this.rootOwner = rootOwner;
        this.bindingProvider = bindingProvider;
        this.binding = binding;
        this.container = container;
        this.codec = codec;
        this.seiModel = seiModel;
        this.sei = sei;
    }

    /**
     * The endpoint address. Always non-null. This parameter is taken separately
     * from {@link com.sun.xml.ws.api.model.wsdl.WSDLPort} (even though there's {@link com.sun.xml.ws.api.model.wsdl.WSDLPort#getAddress()})
     * because sometimes WSDL is not available.
     */
    public @NotNull EndpointAddress getAddress() {
        return address;
    }

    /**
     * The created pipeline will be used to serve this port.
     * Null if the service isn't associated with any port definition in WSDL,
     * and otherwise non-null.
     */
    public @Nullable WSDLPort getWsdlModel() {
        return wsdlModel;
    }

    /**
     * The pipeline is created for this {@link com.sun.xml.ws.api.WSService}.
     * Always non-null. (To be precise, the newly created pipeline
     * is owned by a proxy or a dispatch created from thsi {@link com.sun.xml.ws.api.WSService}.)
     */
    public @NotNull WSService getService() {
        return rootOwner;
    }

    /**
     * The pipeline is created for this {@link com.sun.xml.ws.api.client.WSPortInfo}.
     * Nullable incase of backwards compatible usages of this class.
     */
    public @Nullable WSPortInfo getPortInfo() {
        return bindingProvider == null? null: bindingProvider.getPortInfo();
    }


    /**
     * The pipeline is created for this {@link WSBindingProvider}.
     * Nullable incase of backwards compatible usages of this class.
     */
    public @Nullable WSBindingProvider getBindingProvider() {
        return bindingProvider;
    }

    /**
     * The binding of the new pipeline to be created.
     */
    public @NotNull WSBinding getBinding() {
        return binding;
    }

    /**
     * The created pipeline will use seiModel to get java concepts for the endpoint
     *
     * @return Null if the service doesn't have SEI model e.g. Dispatch,
     *         and otherwise non-null.
     */
    public @Nullable SEIModel getSEIModel() {
        return seiModel;
    }
    
    /**
     * The SEI class for the endpoint
     *
     * @return Null if the service doesn't have SEI model e.g. Dispatch,
     *         and otherwise non-null.
     */
    public @Nullable Class getSEI() {
        return sei;
    }
    
    /**
     * Returns the Container in which the client is running
     *
     * @return Container in which client is running
     */
    public Container getContainer() {
        return container;
    }

    /**
     * creates a {@link Tube} that dumps messages that pass through.
     */
    public Tube createDumpTube(String name, PrintStream out, Tube next) {
        return new DumpTube(name, out, next);
    }

    /**
     * Creates a {@link Tube} that adds container specific security
     */
    public @NotNull Tube createSecurityTube(@NotNull Tube next) {
        ClientPipelineHook hook = container.getSPI(ClientPipelineHook.class);
        if (hook != null) {
            ClientPipeAssemblerContext ctxt = new ClientPipeAssemblerContext(address, wsdlModel,
                                      rootOwner, binding, container);
            return PipeAdapter.adapt(hook.createSecurityPipe(ctxt, PipeAdapter.adapt(next)));
        }
        return next;
    }

    /**
     * Creates a {@link Tube} that invokes protocol and logical handlers.
     */
    public Tube createWsaTube(Tube next) {
        if (binding instanceof SOAPBinding && AddressingVersion.isEnabled(binding) && wsdlModel!=null)
            if(AddressingVersion.fromBinding(binding) == AddressingVersion.MEMBER) {
                return new MemberSubmissionWsaClientTube(wsdlModel, binding, next);    
            } else {
                return new W3CWsaClientTube(wsdlModel, binding, next);
            }
        else
            return next;
    }

    /**
     * Creates a {@link Tube} that invokes protocol and logical handlers.
     */
    public Tube createHandlerTube(Tube next) {
        HandlerTube cousinHandlerTube = null;
        //XML/HTTP Binding can have only LogicalHandlerPipe
        if (binding instanceof SOAPBinding) {
            //Add MessageHandlerTube
            HandlerTube messageHandlerTube = new ClientMessageHandlerTube(seiModel, binding, wsdlModel, next);
            next = cousinHandlerTube = messageHandlerTube;

            //Add SOAPHandlerTuber
            HandlerTube soapHandlerTube = new ClientSOAPHandlerTube(binding, next, cousinHandlerTube);            
            next = cousinHandlerTube = soapHandlerTube;
        }
        return new ClientLogicalHandlerTube(binding, seiModel, next, cousinHandlerTube);
    }

    /**
     * Creates a {@link Tube} that performs SOAP mustUnderstand processing.
     * This pipe should be before HandlerPipes.
     */
    public Tube createClientMUTube(Tube next) {
        if(binding instanceof SOAPBinding)
            return new ClientMUTube(binding,next);
        else
            return next;
    }

    /**
     * creates a {@link Tube} that validates messages against schema
     */
    public Tube createValidationTube(Tube next) {
        if (binding instanceof SOAPBinding && binding.isFeatureEnabled(SchemaValidationFeature.class) && wsdlModel!=null)
            return new ClientSchemaValidationTube(binding, wsdlModel, next);
        else
            return next;
    }

    /**
     * Creates a transport pipe (for client), which becomes the terminal pipe.
     */
    public Tube createTransportTube() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        // The application may configure the endpoint address through request context
        // using {@link BindingProvider#ENDPOINT_ADDRESS_PROPERTY}. Let us
        // defer the creation of actual transport until the service invocation,
        // DeferredTransportPipe is used for this purpose.
        return new DeferredTransportPipe(cl,this);
    }

    /**
     * Gets the {@link Codec} that is set by {@link #setCodec} or the default codec
     * based on the binding.
     *
     * @return codec to be used for web service requests
     */
    public @NotNull Codec getCodec() {
        return codec;
    }

    /**
     * Interception point to change {@link Codec} during {@link Tube}line assembly. The
     * new codec will be used by jax-ws client runtime for encoding/decoding web service
     * request/response messages. The new codec should be used by the transport tubes.
     *
     * <p>
     * the codec should correctly implement {@link Codec#copy} since it is used while
     * serving requests concurrently.
     *
     * @param codec codec to be used for web service requests
     */
    public void setCodec(@NotNull Codec codec) {
        this.codec = codec;
    }

}
