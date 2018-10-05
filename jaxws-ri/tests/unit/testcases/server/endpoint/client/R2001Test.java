/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.endpoint.client;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.xml.namespace.QName;
import javax.xml.ws.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.net.URL;
import java.io.*;

import testutil.PortAllocator;

/**
 * @author Jitendra Kotamraju
 */
public class R2001Test extends TestCase {

    public void testR2001() throws Exception {
        int port = PortAllocator.getFreePort();
        String address = "http://localhost:"+port+"/hello";
        Endpoint endpoint = Endpoint.create(new R2001Provider());
        List<Source> metadata = new ArrayList<Source>();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String[] docs = {
            "stockquoteservice.wsdl",
            "stockquote.wsdl",
            "stockquote.xsd"
        };
        for(String doc : docs) {
            URL url = cl.getResource(doc);
            metadata.add(new StreamSource(url.openStream(), url.toExternalForm()));
        }
        endpoint.setMetadata(metadata);

        Map<String, Object> props = new HashMap<String, Object>();
        props.put(Endpoint.WSDL_SERVICE, new QName("http://example.com/stockquote/service", "StockQuoteService"));
        props.put(Endpoint.WSDL_PORT, new QName("http://example.com/stockquote/service", "StockQuotePort"));
        endpoint.setProperties(props);

        endpoint.publish(address);
        URL serUrl = new URL(address+"?wsdl");
        isGenerated(serUrl.openStream());
        URL absUrl = new URL(address+"?wsdl=1");
        isGenerated(absUrl.openStream());
        URL xsdUrl = new URL(address+"?xsd=1");
        isGenerated(xsdUrl.openStream());
        endpoint.stop();
    }

    public void isGenerated(InputStream in) throws IOException {
        BufferedReader rdr = new BufferedReader(new InputStreamReader(in));
        String str;
        while ((str=rdr.readLine()) != null);
    }

    @WebServiceProvider
    public class R2001Provider implements Provider<Source> {
        public Source invoke(Source source) {
            String replyElement = new String("<p>hello world</p>");
            StreamSource reply = new StreamSource(new StringReader (replyElement));
            return reply;
        }
    }

}
