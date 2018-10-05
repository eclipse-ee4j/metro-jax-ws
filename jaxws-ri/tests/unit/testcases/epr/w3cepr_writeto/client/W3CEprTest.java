/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package epr.w3cepr_writeto.client;

import junit.framework.TestCase;

import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author Rama Pulavarthi
 */
public class W3CEprTest extends TestCase {
    public final String epr = "<EndpointReference xmlns=\"http://www.w3.org/2005/08/addressing\">" +
            "<Address>in-vm://epr.epr_webparam.server/?AddNumbersPort</Address><ReferenceParameters/>" +
            "<Metadata xmlns:wsdli=\"http://www.w3.org/ns/wsdl-instance\" wsdli:wsdlLocation=\"http://foobar.org/ AddNumbersService.wsdl\">" +
            "<wsam:InterfaceName xmlns:wsam=\"http://www.w3.org/2007/05/addressing/metadata\" xmlns:wsaw=\"http://www.w3.org/2006/05/addressing/wsdl\" xmlns:wsns=\"http://foobar.org/\">wsns:AddNumbers</wsam:InterfaceName>" +
            "<wsam:ServiceName xmlns:wsam=\"http://www.w3.org/2007/05/addressing/metadata\" xmlns:wsaw=\"http://www.w3.org/2006/05/addressing/wsdl\" xmlns:wsns=\"http://foobar.org/\" EndpointName=\"AddNumbersPort\">wsns:AddNumbersService</wsam:ServiceName>" +
            "</Metadata></EndpointReference>";
    public void testWriteTo() {
        StreamSource source = new StreamSource(new ByteArrayInputStream(epr.getBytes()));
        W3CEndpointReference epr = new W3CEndpointReference(source);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        epr.writeTo(new StreamResult(baos));
        System.out.println(baos.toString());
        //dummy assertion
        assertTrue(baos.toString().contains("xmlns:wsns=\"http://foobar.org/\""));

    }
}
