/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sun.xml.ws.assembler;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.EndpointAddress;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.WSService;
import com.sun.xml.ws.api.client.WSPortInfo;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.pipe.ClientTubeAssemblerContext;
import com.sun.xml.ws.api.pipe.Codec;
import com.sun.xml.ws.api.server.Container;
import com.sun.xml.ws.assembler.dev.ClientTubelineAssemblyContext;
import com.sun.xml.ws.policy.PolicyMap;

/**
 * The context is a wrapper around the existing JAX-WS {@link ClientTubeAssemblerContext} with additional features
 * 
 * @author Marek Potociar (marek.potociar at sun.com)
 */
class DefaultClientTubelineAssemblyContext extends TubelineAssemblyContextImpl implements ClientTubelineAssemblyContext {

    private final @NotNull ClientTubeAssemblerContext wrappedContext;
    private final PolicyMap policyMap;
    private final WSPortInfo portInfo; // TODO: is this really needed?
    private final WSDLPort wsdlPort;
    // TODO: replace the PipeConfiguration

    public DefaultClientTubelineAssemblyContext(@NotNull ClientTubeAssemblerContext context) {
        this.wrappedContext = context;
        this.wsdlPort = context.getWsdlModel();
        this.portInfo = context.getPortInfo();
        this.policyMap = context.getPortInfo().getPolicyMap();
    }

    @Override
    public PolicyMap getPolicyMap() {
        return policyMap;
    }
    
    @Override
    public boolean isPolicyAvailable() {
        return policyMap != null && !policyMap.isEmpty();
    }

    /**
     * The created pipeline will be used to serve this port.
     * Null if the service isn't associated with any port definition in WSDL,
     * and otherwise non-null.
     * 
     * Replaces {@link com.sun.xml.ws.api.pipe.ClientTubeAssemblerContext#getWsdlModel()}
     */
    @Override
    public WSDLPort getWsdlPort() {
        return wsdlPort;
    }

    @Override
    public WSPortInfo getPortInfo() {
        return portInfo;
    }
    
    /**
     * The endpoint address. Always non-null. This parameter is taken separately
     * from {@link com.sun.xml.ws.api.model.wsdl.WSDLPort} (even though there's {@link com.sun.xml.ws.api.model.wsdl.WSDLPort#getAddress()})
     * because sometimes WSDL is not available.
     */
    @Override
    public @NotNull EndpointAddress getAddress() {
        return wrappedContext.getAddress();
    }

    /**
     * The pipeline is created for this {@link com.sun.xml.ws.api.WSService}.
     * Always non-null. (To be precise, the newly created pipeline
     * is owned by a proxy or a dispatch created from this {@link com.sun.xml.ws.api.WSService}.)
     */
    @Override
    public @NotNull WSService getService() {
        return wrappedContext.getService();
    }

    /**
     * The binding of the new pipeline to be created.
     */
    @Override
    public @NotNull WSBinding getBinding() {
        return wrappedContext.getBinding();
    }

    /**
     * The created pipeline will use seiModel to get java concepts for the endpoint
     *
     * @return Null if the service doesn't have SEI model e.g. Dispatch,
     *         and otherwise non-null.
     */
    @Override
    public @Nullable SEIModel getSEIModel() {
        return wrappedContext.getSEIModel();
    }

    /**
     * Returns the Container in which the client is running
     *
     * @return Container in which client is running
     */
    @Override
    public Container getContainer() {
        return wrappedContext.getContainer();
    }       

    /**
     * Gets the {@link Codec} that is set by {@link #setCodec} or the default codec
     * based on the binding.
     *
     * @return codec to be used for web service requests
     */
    @Override
    public @NotNull Codec getCodec() {
        return wrappedContext.getCodec();
    }

    /**
     * Interception point to change {@link Codec} during {@link com.sun.xml.ws.api.pipe.Tube}line assembly. The
     * new codec will be used by jax-ws client runtime for encoding/decoding web service
     * request/response messages. The new codec should be used by the transport tubes.
     *
     * <p>
     * the codec should correctly implement {@link Codec#copy} since it is used while
     * serving requests concurrently.
     *
     * @param codec codec to be used for web service requests
     */
    @Override
    public void setCodec(@NotNull Codec codec) {
        wrappedContext.setCodec(codec);
    }
    
    @Override
    public ClientTubeAssemblerContext getWrappedContext() {
        return wrappedContext;
    }
}
