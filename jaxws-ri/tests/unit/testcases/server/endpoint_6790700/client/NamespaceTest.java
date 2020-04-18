/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.endpoint_6790700.client;

import junit.framework.TestCase;

import jakarta.xml.ws.Endpoint;
import testutil.*;

/**
 * @author Jitendra Kotamraju
 */
public class NamespaceTest extends TestCase {

    public void testNamespace() throws Exception {
        int port = PortAllocator.getFreePort();
        String address = "http://localhost:"+port+"/hello";
        Endpoint e = Endpoint.create(new MyProvider());
        e.publish(address);

        String message = 
"<s:Envelope xmlns:s='http://schemas.xmlsoap.org/soap/envelope/'>"+
"  <s:Body xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema'>"+
"<ping xmlns='urn:com:dassault_systemes:webservice'><iSentence xsi:nil='true'/></ping>"+
"</s:Body>"+
"</s:Envelope>";
        HTTPResponseInfo rInfo = ClientServerTestUtil.sendPOSTRequest(address,message);
        assertEquals(200, rInfo.getResponseCode());

        e.stop();
    }

}
