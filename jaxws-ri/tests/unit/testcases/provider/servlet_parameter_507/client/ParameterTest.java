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
 * $Id: ParameterTest.java,v 1.1 2009-11-02 18:33:46 jitu Exp $
 */

/*
 * Copyright 2004 Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package provider.servlet_parameter_507.client;

import java.lang.reflect.Proxy;
import java.io.*;
import junit.framework.*;
import testutil.ClientServerTestUtil;
import testutil.HTTPResponseInfo;
import jakarta.xml.soap.*;
import javax.xml.namespace.QName;

/**
 * Tests form request as POST request
 *
 * @author Jitendra Kotamraju
 */
public class ParameterTest extends TestCase {

    public ParameterTest(String name) throws Exception {
        super(name);
    }

    /*
     * Check for service's response code. It shouldn't be 202 since service
     * sets a http status code even for oneway
     */
    public void testStatusCode() throws Exception {
        Hello hello = new Hello_Service().getHelloPort();
        String message = "a=b&c=d";
        HTTPResponseInfo rInfo = ClientServerTestUtil.sendPOSTRequest(
                hello, message, "application/x-www-form-urlencoded");
        assertEquals(200, rInfo.getResponseCode());
    }

}
