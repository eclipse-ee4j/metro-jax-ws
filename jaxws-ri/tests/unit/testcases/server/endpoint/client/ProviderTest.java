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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import testutil.ClientServerTestUtil;

import javax.xml.ws.Endpoint;
import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.Provider;
import javax.activation.DataSource;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.Service;
import javax.xml.ws.Service.Mode;
import javax.xml.ws.BindingType;

import testutil.PortAllocator;

/**
 * @author Jitendra Kotamraju
 */
public class ProviderTest extends TestCase {

    public void testProviderEndpoint() {
        int port = PortAllocator.getFreePort();
        String address = "http://127.0.0.1:"+port+"/";
        Endpoint e = Endpoint.create(HTTPBinding.HTTP_BINDING, new MyProvider());
        e.publish(address);
        // TODO add dispatch client to access this endpoint
        e.stop();
    }

    public void testHttpProviderEndpoint() {
        int port = PortAllocator.getFreePort();
        String address = "http://127.0.0.1:"+port+"/";
        Endpoint e = Endpoint.create(new HttpProvider());
        e.publish(address);
        // TODO add dispatch client to access this endpoint
        e.stop();
    }

    @BindingType(HTTPBinding.HTTP_BINDING)
    @WebServiceProvider
    @ServiceMode(value=Service.Mode.MESSAGE)
    public static class HttpProvider implements Provider<DataSource> {
        public DataSource invoke(DataSource ds) {
            return ds;
        }
    }

}

