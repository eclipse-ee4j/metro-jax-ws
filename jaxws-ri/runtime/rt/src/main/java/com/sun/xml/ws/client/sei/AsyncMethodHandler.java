/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client.sei;

//import com.sun.tools.ws.wsdl.document.soap.SOAPBinding;

import com.oracle.webservices.api.databinding.JavaCallInfo;
import com.sun.istack.NotNull;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.pipe.Fiber;
import com.sun.xml.ws.client.AsyncInvoker;
import com.sun.xml.ws.client.AsyncResponseImpl;
import com.sun.xml.ws.client.RequestContext;
import com.sun.xml.ws.client.ResponseContext;

import jakarta.xml.ws.AsyncHandler;
import jakarta.xml.ws.Response;
import jakarta.xml.ws.WebServiceException;

import java.lang.reflect.Method;

/**
 * Common part between {@link CallbackMethodHandler} and {@link PollingMethodHandler}.
 *
 * @author Kohsuke Kawaguchi
 * @author Jitendra Kotamraju
 */
abstract class AsyncMethodHandler extends MethodHandler {

    AsyncMethodHandler(SEIStub owner, Method m) {
    	super(owner, m);
    }

//    private final ResponseBuilder responseBuilder;
//    /**
//     * Async bean class that has setters for all out parameters
//     */
//    private final @Nullable Class asyncBeanClass;
//
//    AsyncMethodHandler(SEIStub owner, JavaMethodImpl jm, JavaMethodImpl sync) {
//        super(owner, sync);
//
//        List<ParameterImpl> rp = sync.getResponseParameters();
//        int size = 0;
//        for( ParameterImpl param : rp ) {
//            if (param.isWrapperStyle()) {
//                WrapperParameter wrapParam = (WrapperParameter)param;
//                size += wrapParam.getWrapperChildren().size();
//                if (sync.getBinding().getStyle() == Style.DOCUMENT) {
//                    // doc/asyncBeanClass - asyncBeanClass bean is in async signature
//                    // Add 2 or more so that it is considered as async bean case
//                    size += 2;
//                }
//            } else {
//                ++size;
//            }
//        }
//
//        Class tempWrap = null;
//        if (size > 1) {
//            rp = jm.getResponseParameters();
//            for(ParameterImpl param : rp) {
//                if (param.isWrapperStyle()) {
//                    WrapperParameter wrapParam = (WrapperParameter)param;
//                    if (sync.getBinding().getStyle() == Style.DOCUMENT) {
//                        // doc/asyncBeanClass style
//                        tempWrap = (Class)wrapParam.getTypeReference().type;
//                        break;
//                    }
//                    for(ParameterImpl p : wrapParam.getWrapperChildren()) {
//                        if (p.getIndex() == -1) {
//                            tempWrap = (Class)p.getTypeReference().type;
//                            break;
//                        }
//                    }
//                    if (tempWrap != null) {
//                        break;
//                    }
//                } else {
//                    if (param.getIndex() == -1) {
//                        tempWrap = (Class)param.getTypeReference().type;
//                        break;
//                    }
//                }
//            }
//        }
//        asyncBeanClass = tempWrap;
//        
//        switch(size) {
//            case 0 :
//                responseBuilder = buildResponseBuilder(sync, ValueSetterFactory.NONE);
//                break;
//            case 1 :
//                responseBuilder = buildResponseBuilder(sync, ValueSetterFactory.SINGLE);
//                break;
//            default :
//                responseBuilder = buildResponseBuilder(sync, new ValueSetterFactory.AsyncBeanValueSetterFactory(asyncBeanClass));
//        }
//       
//    }

    protected final Response<Object> doInvoke(Object proxy, Object[] args, AsyncHandler handler) {
        
        AsyncInvoker invoker = new SEIAsyncInvoker(proxy, args);
        invoker.setNonNullAsyncHandlerGiven(handler != null);
        AsyncResponseImpl<Object> ft = new AsyncResponseImpl<Object>(invoker,handler);
        invoker.setReceiver(ft);
        ft.run();
        return ft;
    }

    private class SEIAsyncInvoker extends AsyncInvoker {
        // snapshot the context now. this is necessary to avoid concurrency issue,
        // and is required by the spec
        private final RequestContext rc = owner.requestContext.copy();
        private final Object[] args;

        SEIAsyncInvoker(Object proxy, Object[] args) {
            this.args = args;
        }

        @Override
        public void do_run () {
        	JavaCallInfo call = owner.databinding.createJavaCallInfo(method, args);
            Packet req = (Packet)owner.databinding.serializeRequest(call);

            Fiber.CompletionCallback callback = new Fiber.CompletionCallback() {

                @Override
                public void onCompletion(@NotNull Packet response) {
                    responseImpl.setResponseContext(new ResponseContext(response));
                    Message msg = response.getMessage();
                    if (msg == null) {
                        return;
                    }
                    try {
                    	Object[] rargs = new Object[1];
                    	JavaCallInfo call = owner.databinding.createJavaCallInfo(method, rargs);
                        call = owner.databinding.deserializeResponse(response, call);
                        if (call.getException() != null) {
                            throw call.getException();
                        } else {
                            responseImpl.set(rargs[0], null);
                        }
//                    	dbHandler.readResponse(response, call);
//                    	responseImpl.set(rargs[0], null);
//                        if(msg.isFault()) {
//                            SOAPFaultBuilder faultBuilder = SOAPFaultBuilder.create(msg);
//                            throw faultBuilder.createException(checkedExceptions);
//                        } else {
//                            Object[] rargs = new Object[1];
//                            if (asyncBeanClass != null) {
//                                rargs[0] = asyncBeanClass.newInstance();
//                            }
//                            responseBuilder.readResponse(msg, rargs);
//                            responseImpl.set(rargs[0], null);
//                        }
                   } catch (Throwable t) {
                        if (t instanceof RuntimeException) {
                            if (t instanceof WebServiceException) {
                                responseImpl.set(null, t);
                                return;
                            }
                        }  else if (t instanceof Exception) {
                            responseImpl.set(null, t);
                            return;
                        }
                        //its RuntimeException or some other exception resulting from user error, wrap it in
                        // WebServiceException
                        responseImpl.set(null, new WebServiceException(t));
                    }
                }
                

                @Override
                public void onCompletion(@NotNull Throwable error) {
                    if (error instanceof WebServiceException) {
                        responseImpl.set(null, error);
                    } else {
                        //its RuntimeException or some other exception resulting from user error, wrap it in
                        // WebServiceException
                        responseImpl.set(null, new WebServiceException(error));
                    }
                }
            };
            owner.doProcessAsync(responseImpl, req, rc, callback);
        }
    }

    ValueGetterFactory getValueGetterFactory() {
        return ValueGetterFactory.ASYNC;
    }

}
