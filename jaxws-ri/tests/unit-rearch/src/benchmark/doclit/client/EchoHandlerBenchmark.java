/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package benchmark.doclit.client;

/**
 * @author JAX-RPC RI Development Team
 */
public class EchoHandlerBenchmark extends DoclitTest {
    public EchoHandlerBenchmark(String name) {
        super(name);
    }

    public void testOnce() throws Exception {
    }

//    public EchoPortType getStub() {
//        EchoPortType stub = null;
///*
//        try {
//            ServiceFactory20 factory = new ServiceFactory20();
//            QName serviceQName = new QName("http://soapinterop.org/", "EchoService");
//            Class serviceClazz = Class.forName("benchmark.doclit.client.EchoService");
//            Service service = factory.loadService(serviceClazz);
//
//
//            QName portQname = new QName("http://soapinterop.org/", "EchoPort");
//            HandlerRegistry handlerRegistry = service.getHandlerRegistry();
//            List handlerChain = handlerRegistry.getHandlerChain(portQname);
//            HandlerInfo handlerInfo = new HandlerInfo();
//            handlerInfo.setHandlerClass(SimpleHandler.class);
//            handlerChain.add(handlerInfo);
//
//            Class portIF = Class.forName("benchmark.doclit.client.EchoPortType");
//            stub = (EchoPortType) service.getPort(new QName("http://soapinterop.org/", "EchoPort"), portIF);
//
//            // set transport
//            ClientServerTestUtil util = new ClientServerTestUtil();
//            util.setTransport(stub,
//                    "benchmark.doclit.server.EchoPortTypeImpl", System.out);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//*/
//        return stub;
//    }
}
