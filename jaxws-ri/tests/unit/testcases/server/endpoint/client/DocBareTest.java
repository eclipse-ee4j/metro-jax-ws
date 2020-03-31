/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.endpoint.client;

import java.io.StringReader;
import java.net.URL;
 
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Endpoint;
import jakarta.xml.ws.Service;

import javax.xml.stream.*;
import java.io.*;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.Style;

import junit.framework.TestCase;
import testutil.PortAllocator;

/**
 * @author Jitendra Kotamraju
 */

public class DocBareTest extends TestCase
{
    private static final String NS = "http://echo.org/";

    public void testBare() throws Exception {
        int port = PortAllocator.getFreePort();
        String address = "http://localhost:"+port+"/bare";
        Endpoint endpoint = Endpoint.create(new BareService());
        endpoint.publish(address);
        endpoint.stop();
    }

    @WebService
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public class BareService {
     
	public String foo(int i) {
	    return "foo";
	}
     
	public String bar(int i) {
	    return "bar";
	}
     
    }
}
