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

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.*;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.stream.*;
import java.net.URL;
import java.io.*;


/**
 * @author Jitendra Kotamraju
 */
public class UnicodeServiceNameTester extends TestCase {

    public void testUnicodeAddress() throws Exception {
        int port = Util.getFreePort();
        String address = "http://localhost:"+port+"/"+java.net.URLEncoder.encode("Hello\u00EEService", "UTF-8");
        Endpoint endpoint = Endpoint.create(new MyEndpoint());
        endpoint.publish(address);
        URL pubUrl = new URL(address+"?wsdl");
        access(pubUrl.openStream());
        endpoint.stop();
    }

    private void access(InputStream in) throws Exception {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        XMLStreamReader reader = xif.createXMLStreamReader(in);
        while(reader.hasNext()) {
            reader.next();
        }
    }

    @WebService(name="Hello",serviceName="Hello\u00EEService", targetNamespace="http://example.com/Hello")
    @SOAPBinding(style=SOAPBinding.Style.RPC)
    public static class MyEndpoint {
        public int echo(int a) {
            return a;
        }
    }

}

