/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.fault;

import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.message.Message;
import junit.framework.TestCase;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import jakarta.xml.soap.Detail;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPFault;
import jakarta.xml.soap.SOAPMessage;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author Vivek Pandey
 */
public class jaxws413Test extends TestCase {

    public void test1() throws SOAPException, IOException {
        SOAPFault fault = SOAPVersion.SOAP_11.getSOAPFactory().createFault();

        fault.setFaultCode(new QName("http://foo/bar", "mycode", "myprefix"));
        fault.setFaultString("Some exception");
        Detail detail = fault.addDetail();
        detail.addDetailEntry(new QName("http://foo/bar", "soap11detail"));

        Message faultMsg = SOAPFaultBuilder.createSOAPFaultMessage(SOAPVersion.SOAP_11, fault);

        SOAPMessage sm = faultMsg.readAsSOAPMessage();

        Detail det = sm.getSOAPBody().getFault().getDetail();
        Iterator iter = det.getDetailEntries();
        assertTrue(iter.hasNext());
        Element item = (Element) iter.next();
        assertEquals(item.getNamespaceURI(), "http://foo/bar");
        assertEquals(item.getLocalName(), "soap11detail");

    }

    public void test2() throws SOAPException, IOException {
        SOAPFault fault = SOAPVersion.SOAP_12.getSOAPFactory().createFault();

        fault.setFaultCode(new QName("http://www.w3.org/2003/05/soap-envelope", "Sender", "myprefix"));
        fault.setFaultString("Some exception");
        Detail detail = fault.addDetail();
        detail.addDetailEntry(new QName("http://foo/bar", "soap12detail"));

        Message faultMsg = SOAPFaultBuilder.createSOAPFaultMessage(SOAPVersion.SOAP_12, fault);

        SOAPMessage sm = faultMsg.readAsSOAPMessage();
        Detail det = sm.getSOAPBody().getFault().getDetail();
        Iterator iter = det.getDetailEntries();
        assertTrue(iter.hasNext());
        Element item = (Element) iter.next();
        assertEquals(item.getNamespaceURI(), "http://foo/bar");
        assertEquals(item.getLocalName(), "soap12detail");

    }
}
