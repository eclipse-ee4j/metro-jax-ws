/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/*
 * $Id: HeadTest.java,v 1.2 2009-09-29 21:58:00 jitu Exp $
 */

/*
 * Copyright 2004 Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package server.http_head_677.client;

import java.lang.reflect.Proxy;
import java.io.*;
import junit.framework.*;
import testutil.HTTPResponseInfo;
import jakarta.xml.soap.*;
import javax.xml.namespace.QName;
import java.net.*;
import jakarta.xml.ws.*;


/**
 * Tests HTTP HEAD requests
 *
 * @author Jitendra Kotamraju
 */
public class HeadTest extends TestCase {

    public HeadTest(String name) throws Exception {
        super(name);
    }

    Hello getStub() throws Exception {
        return new Hello_Service().getHelloPort();
    }

    /*
     * Tests HTTP HEAD requests
     */
    public void testHead() throws Exception {
        BindingProvider bp = (BindingProvider) getStub();
        String address =
            (String) bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);

        assertEquals(200, head(address+"?wsdl"));
        assertEquals(200, head(address+"?xsd=1"));
        assertEquals(404, head(address+"?wsdl=1"));
        assertEquals(404, head(address+"?xsd=2"));
    }

    private int head(String address) throws Exception {
        // create connection
        HttpURLConnection conn =
            (HttpURLConnection) new URL(address).openConnection();
        conn.setRequestMethod("HEAD");
        // lwhs is not working with keep-alive. Issue: 6886723
        conn.setRequestProperty("Connection", "close");
        return conn.getResponseCode();
    }

}
