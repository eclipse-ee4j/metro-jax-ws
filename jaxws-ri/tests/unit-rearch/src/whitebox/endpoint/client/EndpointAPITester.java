/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.endpoint.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import testutil.ClientServerTestUtil;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.Holder;
import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.Endpoint;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;


/**
 * @author Jitendra Kotamraju
 */
public class EndpointAPITester extends TestCase {

    public EndpointAPITester(String name) {
        super(name);
    }

    public void testEndpoint() {
        int port = Util.getFreePort();
        String address = "http://localhost:"+port+"/hello";
        Endpoint endpoint = Endpoint.publish(address, new RpcLitEndpoint());

        doAPItesting(endpoint);
    }

    public void testHttpsAddress() {
        int port = Util.getFreePort();
        String address = "https://localhost:"+port+"/hello";
        // Doesn't support https scheme, so expect IllegalArgumentException
        try {
            Endpoint endpoint = Endpoint.publish(address, new RpcLitEndpoint());
            assertTrue(false);
        } catch(IllegalArgumentException e) {
        }
    }

    /**
     * @param endpoint - Already published endpoint
     */
    private void doAPItesting(Endpoint endpoint) {

        // Since it is already published, isPublished should return true
        if (!endpoint.isPublished()) {
            assertTrue(false);
        }

        // Binding shouldn't be null
        assertTrue(endpoint.getBinding() != null);

        // Since it is already published, cannot set metadata
        // Expect IllegalStateException
        try {
            endpoint.setMetadata(null);
            assertTrue(false);
        } catch(IllegalStateException e) {
        }

        // Since it is already published, cannot publish again.
        // Expect IllegalStateException
        try {
            endpoint.publish("http://localhost");
            assertTrue(false);
        } catch(IllegalStateException e) {
        }

        endpoint.stop();

        // Since it is stopped, isPublished should return false
        if (endpoint.isPublished()) {
            assertTrue(false);
        }

        // Since it is stopped, cannot publish again.
        // Expect IllegalStateException
        try {
            endpoint.publish("http://localhost");
            assertTrue(false);
        } catch(IllegalStateException e) {
        }
    }

    public void testEndpoint1() {
        int port = Util.getFreePort();
        String address = "http://localhost:"+port+"/hello";
        Endpoint endpoint = Endpoint.create(new RpcLitEndpoint());
        endpoint.publish(address);
        doAPItesting(endpoint);
    }

    public void testNoPath() throws Exception {
        int port = Util.getFreePort();
        String address = "http://localhost:"+port;
        Endpoint endpoint = Endpoint.create(new RpcLitEndpoint());
        try {
            endpoint.publish(address);
            assert(false);
        } catch(IllegalArgumentException ie) {
            // Intentionally left empty
        }
    }

    public void testSlashPath() throws Exception {
        int port = Util.getFreePort();
        String address = "http://localhost:"+port+"/";
        Endpoint endpoint = Endpoint.create(new RpcLitEndpoint());
        endpoint.publish(address);
        assertTrue(isWSDL(address));
        endpoint.stop();
    }

    private boolean isWSDL(String address) throws Exception {
        URL url = new URL(address+"?wsdl");
        InputStream in = url.openStream();
        BufferedReader rdr = new BufferedReader(new InputStreamReader(in));
        String str;
        while ((str=rdr.readLine()) != null) {
            if (str.indexOf("definitions") != -1) {
                return true;
            }
        }
        return false;
    }


    public void testStopBeforePublish() {
        Endpoint endpoint = Endpoint.create(new RpcLitEndpoint());
        // Stop before publish() shouldn't have any effect
        endpoint.stop();
    }

}

