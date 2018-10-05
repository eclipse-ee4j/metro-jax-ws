/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.endpoint1.client;

import junit.framework.TestCase;

import javax.xml.ws.Endpoint;
import javax.xml.transform.stream.StreamSource;
import java.net.URL;
import java.io.*;
import javax.xml.ws.Provider;
import javax.xml.transform.Source;
import javax.xml.ws.WebServiceProvider;
import java.io.StringReader;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.WebServiceException;

import com.sun.xml.ws.developer.JAXWSProperties;
import com.sun.net.httpserver.HttpExchange;
import testutil.PortAllocator;


/**
 * @author Jitendra Kotamraju
 */
public class MessageContextTest extends TestCase {

    @WebServiceProvider
    static class MessageContextProvider implements Provider<Source> {
        @Resource
        WebServiceContext ctxt;

        public Source invoke(Source source) {
            MessageContext msgCtxt = ctxt.getMessageContext();
            
            HttpExchange exchange = (HttpExchange)msgCtxt.get(JAXWSProperties.HTTP_EXCHANGE);
            if (exchange == null ) {
                throw new WebServiceException("HttpExchange object is not populated");
            }

            String replyElement = new String("<p>hello world</p>");
            StreamSource reply = new StreamSource(new StringReader (replyElement));
            return reply;
        }
    }

    public void testHttpProperties() throws Exception {
        int port = PortAllocator.getFreePort();
        String address = "http://localhost:"+port+"/hello";
        Endpoint e = Endpoint.create(HTTPBinding.HTTP_BINDING, new MessageContextProvider());
        e.publish(address);

        URL url = new URL(address+"/a/b?a=b");
        InputStream in = url.openStream();
        BufferedReader rdr = new BufferedReader(new InputStreamReader(in));
        String str;
        while ((str=rdr.readLine()) != null);

        e.stop();
    }

}

