/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.endpoint1.client;

import junit.framework.TestCase;
import testutil.ClientServerTestUtil;
import testutil.HTTPResponseInfo;
import testutil.PortAllocator;

import javax.annotation.Resource;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.Endpoint;
import jakarta.xml.ws.Provider;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.WebServiceProvider;
import java.io.StringReader;


/**
 * @author Jitendra Kotamraju
 */
public class FaultToEndpointTest extends TestCase {

    @WebServiceProvider
    static class MyProvider implements Provider<Source> {
        @Resource
        WebServiceContext ctxt;

        public Source invoke(Source source) {
            String replyElement = new String("<p>hello world</p>");
            StreamSource reply = new StreamSource(new StringReader (replyElement));
            return reply;
        }
    }

    public void testFaultToEndpoint() throws Exception {
        int port = PortAllocator.getFreePort();
        String address = "http://localhost:"+port+"/hello";
        Endpoint e = Endpoint.create(new MyProvider());
        e.publish(address);

        String message =
"<s:Envelope xmlns:s='http://schemas.xmlsoap.org/soap/envelope/'><s:Body><s:Fault><faultcode>s:Client</faultcode><faultstring>sending fault to endpoint</faultstring></s:Fault></s:Body></s:Envelope>";

        HTTPResponseInfo rInfo =
            ClientServerTestUtil.sendPOSTRequest(address, message, "text/xml");
        assertEquals(200, rInfo.getResponseCode());

        e.stop();
    }

}
