/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package benchmark.rpclit.client;

/**
 * @author JAX-RPC RI Development Team
 */
public class EchoHandlerBenchmark extends RpclitTest {
    public EchoHandlerBenchmark(String name) throws Exception {
        super(name);
    }

    public void testOnce() throws Exception {
    }

    public EchoPortType getStub() {
        EchoPortType stub = null;
/*
        try {
            ServiceFactory20 factory = new ServiceFactory20();
            QName serviceQName = new QName("http://soapinterop.org/", "EchoService");
            QName portQname = new QName("http://soapinterop.org/", "EchoPort");
            
            Class serviceClazz = Class.forName("benchmark.rpclit.client.EchoService");
            Service service = factory.loadService(serviceClazz);
            
            HandlerRegistry handlerRegistry = service.getHandlerRegistry();
            List handlerChain = handlerRegistry.getHandlerChain(portQname);
            HandlerInfo handlerInfo = new HandlerInfo();
            handlerInfo.setHandlerClass(SimpleHandler.class);
            handlerChain.add(handlerInfo);

            Class portIF = Class.forName("benchmark.rpclit.client.EchoPortType");
            stub = (EchoPortType) service.getPort(portQname, portIF);
            
//            stub = (EchoPortType) service.getEchoPort();
            // set transport
            ClientServerTestUtil util = new ClientServerTestUtil();
//            util.setTransport(stub,
//                    "benchmark.rpclit112.server.EchoPortType_Tie",
//                    "benchmark.rpclit112.server.EchoPortTypeImpl");
            util.setTransport(stub,
                    "benchmark.rpclit.server.EchoPortTypeImpl", System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        return stub;
    }
}
