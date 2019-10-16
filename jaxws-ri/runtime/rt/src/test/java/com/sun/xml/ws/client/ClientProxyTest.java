/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client;

import java.net.URL;

import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceException;

import com.sun.xml.ws.api.BindingID;
import com.sun.xml.ws.api.ComponentFeature;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.pipe.ClientTubeAssemblerContext;
import com.sun.xml.ws.api.pipe.NextAction;
import com.sun.xml.ws.api.pipe.ServerTubeAssemblerContext;
import com.sun.xml.ws.api.pipe.TransportTubeFactory;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.pipe.TubeCloner;
import com.sun.xml.ws.api.pipe.TubelineAssembler;
import com.sun.xml.ws.api.pipe.TubelineAssemblerFactory;
import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
import com.sun.xml.ws.client.test.Echo;
import com.sun.xml.ws.client.test.EchoService;
import com.sun.xml.ws.client.test.NumbersRequest;

import junit.framework.TestCase;

public class ClientProxyTest extends TestCase {

    static class EchoTube extends AbstractTubeImpl {
        @Override
        public NextAction processRequest(Packet request) {
            NextAction na = new NextAction();
            na.returnWith(request);
            return na;
        }
        @Override
        public NextAction processResponse(Packet response) { return null; }
        @Override
        public NextAction processException(Throwable t) { return null; }
        @Override
        public void preDestroy() {}
        @Override
        public AbstractTubeImpl copy(TubeCloner cloner) { return null; }
    }

    @SuppressWarnings("unchecked")
    public void testNullResponseFromTransprt() throws Exception {
        URL wsdlURL = Thread.currentThread().getContextClassLoader().getResource("etc/EchoService.wsdl");
        EchoService srv = new EchoService(wsdlURL, new ComponentFeature( new com.sun.xml.ws.api.Component() {
            public <S> S getSPI(Class<S> spiType) {
                if (TransportTubeFactory.class.equals(spiType)) return (S) new TransportTubeFactory() {
                    public Tube doCreate( ClientTubeAssemblerContext context) {
                        return new EchoTube() {
                            public NextAction processRequest(Packet request) {
                                NextAction na = new NextAction();
                                na.returnWith(new Packet());
                                return na;
                            }
                        };
                    }
                };
                return null;
            }
        }));
        Echo echo = srv.getEchoPort();
        try {
            int res = echo.add(new NumbersRequest());
            fail();
        } catch (Exception e) {
            assertFalse(e instanceof NullPointerException);
            assertTrue(e instanceof WebServiceException);
        }
        try {
            echo.echoString(new Holder<String>(wsdlURL.toString()));
            fail();
        } catch (Exception e) {
            assertFalse(e instanceof NullPointerException);
            assertTrue(e instanceof WebServiceException);
        }
    }

    @SuppressWarnings("unchecked")
    public void testNullResponseFromTube() throws Exception {
        URL wsdlURL = Thread.currentThread().getContextClassLoader().getResource("etc/EchoService.wsdl");
        EchoService srv = new EchoService(wsdlURL, new ComponentFeature(new com.sun.xml.ws.api.Component() {
            public <S> S getSPI(Class<S> spiType) {
                if (TransportTubeFactory.class.equals(spiType)) return (S) new TransportTubeFactory() {
                    public Tube doCreate(ClientTubeAssemblerContext context) {
                        return new EchoTube();
                    }
                };
                if (TubelineAssemblerFactory.class.equals(spiType)) return (S) new TubelineAssemblerFactory() {
                    public TubelineAssembler doCreate(BindingID bindingId) {
                        return new TubelineAssembler() {
                            public Tube createClient(ClientTubeAssemblerContext context) {
                                final Tube head = context.createTransportTube();
                                return new EchoTube() {
                                    public NextAction processRequest(Packet request) {
                                        NextAction na = new NextAction();
                                        na.invoke(head, request);
                                        return na;
                                    }
                                    public NextAction processResponse(Packet response) {
                                        NextAction na = new NextAction();
                                        na.returnWith(new Packet());
                                        return na;
                                    }
                                };
                            }
                            public Tube createServer(ServerTubeAssemblerContext context) { return null; }
                        };
                    }
                };
                return null;
            }
        }));
        Echo echo = srv.getEchoPort();
        try {
            int res = echo.add(new NumbersRequest());
            fail();
        } catch (Exception e) {
            assertFalse(e instanceof NullPointerException);
            assertTrue(e instanceof WebServiceException);
        }
        try {
            echo.echoString(new Holder(wsdlURL.toString()));
            fail();
        } catch (Exception e) {
            assertFalse(e instanceof NullPointerException);
            assertTrue(e instanceof WebServiceException);
        }
    }
}
