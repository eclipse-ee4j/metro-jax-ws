/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.endpoint_context.client;

import junit.framework.TestCase;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import testutil.PortAllocator;


/**
 * Tests Endpoint.setEndpointContext()
 *
 * @author Jitendra Kotamraju
 */
public class EndpointContextTest extends TestCase {

    public void testEndpointContext() throws Exception {
        int port = PortAllocator.getFreePort();

        String address1 = "http://localhost:"+port+"/foo";
        Endpoint endpoint1 = getEndpoint(new FooService());

        String address2 = "http://localhost:"+port+"/bar";
        Endpoint endpoint2 = getEndpoint(new BarService());

        EndpointContext ctxt = new MyEndpointContext(endpoint1, endpoint2);
        endpoint1.setEndpointContext(ctxt);
        endpoint2.setEndpointContext(ctxt);

        endpoint1.publish(address1);
        endpoint2.publish(address2);

        URL pubUrl = new URL(address1+"?wsdl");
        boolean patched = isPatched(pubUrl.openStream(), address1, address2);
        assertTrue(patched);

        pubUrl = new URL(address2+"?wsdl");
        patched = isPatched(pubUrl.openStream(), address1, address2);
        assertTrue(patched);

        endpoint1.stop();
        endpoint2.stop();
    }

    private Endpoint getEndpoint(Object impl) throws IOException {
        Endpoint endpoint = Endpoint.create(impl);
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String[] docs = {
            "EchoService.wsdl",
        };
        List<Source> metadata = new ArrayList<Source>();
        for(String doc : docs) {
            URL url = cl.getResource(doc);
            metadata.add(new StreamSource(url.openStream(), url.toExternalForm()));
        }
        endpoint.setMetadata(metadata);
        return endpoint;
    }

    public boolean isPatched(InputStream in, String address1, String address2)
		throws IOException {
        boolean address1Patched = false;
        boolean address2Patched = false;

        BufferedReader rdr = new BufferedReader(new InputStreamReader(in));
        String str;
        while ((str=rdr.readLine()) != null) {
            //System.out.println(str);
            if (str.indexOf(address1) != -1) {
                address1Patched = true;
            }
            if (str.indexOf(address2) != -1) {
                address2Patched = true;
            }
        }
        return address1Patched && address2Patched;
    }

    @WebServiceProvider(serviceName="EchoService", portName="fooPort",
        targetNamespace="http://echo.org/")
    public class FooService implements Provider<Source> {
        public Source invoke(Source source) {
            throw new WebServiceException("Not testing the invocation");
        }
    }

    @WebServiceProvider(serviceName="EchoService", portName="barPort",
        targetNamespace="http://echo.org/")
    public class BarService implements Provider<Source> {
        public Source invoke(Source source) {
            throw new WebServiceException("Not testing the invocation");
        }
    }

    private static class MyEndpointContext extends EndpointContext {
        final Set<Endpoint> set = new HashSet<Endpoint>();

        public MyEndpointContext(Endpoint endpoint1, Endpoint endpoint2) {
            set.add(endpoint1);
            set.add(endpoint2);
        }

        public Set<Endpoint> getEndpoints() {
            return set;
        }
    }

}

