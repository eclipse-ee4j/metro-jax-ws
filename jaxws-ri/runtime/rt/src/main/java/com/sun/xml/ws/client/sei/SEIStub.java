/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client.sei;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.client.WSPortInfo;
import com.sun.xml.ws.api.databinding.Databinding;
import com.sun.xml.ws.api.addressing.WSEndpointReference;
import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.api.message.Headers;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.model.MEP;
import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
import com.sun.xml.ws.api.pipe.Fiber;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.server.Container;
import com.sun.xml.ws.api.server.ContainerResolver;
import com.sun.xml.ws.binding.BindingImpl;
import com.sun.xml.ws.client.AsyncResponseImpl;
import com.sun.xml.ws.client.RequestContext;
import com.sun.xml.ws.client.ResponseContextReceiver;
import com.sun.xml.ws.client.Stub;
import com.sun.xml.ws.client.WSServiceDelegate;
import com.sun.xml.ws.model.JavaMethodImpl;
import com.sun.xml.ws.model.SOAPSEIModel;
import com.sun.xml.ws.wsdl.OperationDispatcher;

import javax.xml.namespace.QName;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link Stub} that handles method invocations
 * through a strongly-typed endpoint interface.
 *
 * @author Kohsuke Kawaguchi
 */
public final class SEIStub extends Stub implements InvocationHandler {

	Databinding databinding;
	
    @Deprecated
    public SEIStub(WSServiceDelegate owner, BindingImpl binding, SOAPSEIModel seiModel, Tube master, WSEndpointReference epr) {
        super(owner, master, binding, seiModel.getPort(), seiModel.getPort().getAddress(), epr);
        this.seiModel = seiModel;
        this.soapVersion = binding.getSOAPVersion();
        databinding = seiModel.getDatabinding();
        initMethodHandlers();
    }

    // added portInterface to the constructor, otherwise AsyncHandler won't work
    public SEIStub(WSPortInfo portInfo, BindingImpl binding, SOAPSEIModel seiModel, WSEndpointReference epr) {
        super(portInfo, binding, seiModel.getPort().getAddress(),epr);
        this.seiModel = seiModel;
        this.soapVersion = binding.getSOAPVersion();
        databinding = seiModel.getDatabinding();
        initMethodHandlers();
    }

    private void initMethodHandlers() {
        Map<WSDLBoundOperation, JavaMethodImpl> syncs = new HashMap<WSDLBoundOperation, JavaMethodImpl>();

        // fill in methodHandlers.
        // first fill in sychronized versions
        for (JavaMethodImpl m : seiModel.getJavaMethods()) {
            if (!m.getMEP().isAsync) {
                SyncMethodHandler handler = new SyncMethodHandler(this, m);
                syncs.put(m.getOperation(), m);
                methodHandlers.put(m.getMethod(), handler);
            }
        }

        for (JavaMethodImpl jm : seiModel.getJavaMethods()) {
            JavaMethodImpl sync = syncs.get(jm.getOperation());
            if (jm.getMEP() == MEP.ASYNC_CALLBACK) {
                Method m = jm.getMethod();
                CallbackMethodHandler handler = new CallbackMethodHandler(
                        this, m, m.getParameterTypes().length - 1);
                methodHandlers.put(m, handler);
            }
            if (jm.getMEP() == MEP.ASYNC_POLL) {
                Method m = jm.getMethod();
                PollingMethodHandler handler = new PollingMethodHandler(this, m);
                methodHandlers.put(m, handler);
            }
        }
    }

    public final SOAPSEIModel seiModel;

    public final SOAPVersion soapVersion;

    /**
     * Nullable when there is no associated WSDL Model
     * @return
     */
    public @Nullable
    OperationDispatcher getOperationDispatcher() {
        if(operationDispatcher == null && wsdlPort != null)
            operationDispatcher = new OperationDispatcher(wsdlPort,binding,seiModel);
        return operationDispatcher;
    }

    /**
     * For each method on the port interface we have
     * a {@link MethodHandler} that processes it.
     */
    private final Map<Method, MethodHandler> methodHandlers = new HashMap<Method, MethodHandler>();

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        validateInputs(proxy, method);
        Container old = ContainerResolver.getDefault().enterContainer(owner.getContainer());
        try {
            MethodHandler handler = methodHandlers.get(method);
            if (handler != null) {
                return handler.invoke(proxy, args);
            } else {
                // we handle the other method invocations by ourselves
                try {
                    return method.invoke(this, args);
                } catch (IllegalAccessException e) {
                    // impossible
                    throw new AssertionError(e);
                } catch (IllegalArgumentException e) {
                    throw new AssertionError(e);
                } catch (InvocationTargetException e) {
                    throw e.getCause();
                }
            }
        } finally {
            ContainerResolver.getDefault().exitContainer(old);
        }
    }

    private void validateInputs(Object proxy, Method method) {
        if (proxy == null || !Proxy.isProxyClass(proxy.getClass())) {
            throw new IllegalStateException("Passed object is not proxy!");
        }
        if (method == null || method.getDeclaringClass() == null
                || Modifier.isStatic(method.getModifiers())) {
            throw new IllegalStateException("Invoking static method is not allowed!");
        }
    }

    public final Packet doProcess(Packet request, RequestContext rc, ResponseContextReceiver receiver) {
        return super.process(request, rc, receiver);
    }

    public final void doProcessAsync(AsyncResponseImpl<?> receiver, Packet request, RequestContext rc, Fiber.CompletionCallback callback) {
        super.processAsync(receiver, request, rc, callback);
    }

    protected final @NotNull QName getPortName() {
        return wsdlPort.getName();
    }


    public void setOutboundHeaders(Object... headers) {
        if(headers==null)
            throw new IllegalArgumentException();
        Header[] hl = new Header[headers.length];
        for( int i=0; i<hl.length; i++ ) {
            if(headers[i]==null)
                throw new IllegalArgumentException();
            hl[i] = Headers.create(seiModel.getBindingContext(),headers[i]);
        }
        super.setOutboundHeaders(hl);
    }
}
