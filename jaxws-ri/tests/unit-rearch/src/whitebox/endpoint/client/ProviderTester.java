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

import jakarta.xml.ws.Endpoint;
import jakarta.xml.ws.http.HTTPBinding;
import jakarta.xml.ws.Provider;
import jakarta.activation.DataSource;
import jakarta.xml.ws.WebServiceProvider;
import jakarta.xml.ws.ServiceMode;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.Service.Mode;
import jakarta.xml.ws.BindingType;

/**
 * @author Jitendra Kotamraju
 */
public class ProviderTester extends TestCase {

    public void testProviderEndpoint() {
        int port = Util.getFreePort();
        String address = "http://127.0.0.1:"+port+"/";
        Endpoint e = Endpoint.create(HTTPBinding.HTTP_BINDING, new MyProvider());
        e.publish(address);
        // TODO add dispatch client to access this endpoint
        e.stop();
    }

    public void testHttpProviderEndpoint() {
        int port = Util.getFreePort();
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

