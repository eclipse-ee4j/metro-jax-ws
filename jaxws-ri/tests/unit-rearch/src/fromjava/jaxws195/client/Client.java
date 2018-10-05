/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.jaxws195.client;

import junit.framework.TestCase;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.Dispatch;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;

import com.sun.xml.ws.api.SOAPVersion;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Client extends TestCase {

    public Client(String name) {
        super(name);
    }

    private static final String jaxwsMsg="<?xml version=\"1.0\" ?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body><ns2:echoDate xmlns:ns2=\"http://server.jaxws195.fromjava/\"><arg0>0006-05-01T10:00:00.000Z</arg0></ns2:echoDate></S:Body></S:Envelope>";
    private static final String axisMsg="<?xml version=\"1.0\" ?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body><ns2:echoDate xmlns:ns2=\"http://server.jaxws195.fromjava/\"><arg0 xsi:type=\"xs:dateTime\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">0006-05-01T10:00:00.000Z</arg0></ns2:echoDate></S:Body></S:Envelope>";
    public void testUsingJaxwsMsg() throws IOException, SOAPException {
        TestServiceService service = new TestServiceService();
        Dispatch<SOAPMessage> disp = service.createDispatch(new QName("http://server.jaxws195.fromjava/", "TestServicePort"), SOAPMessage.class, Service.Mode.MESSAGE);
        MimeHeaders mhs = new MimeHeaders();
        mhs.addHeader("Content-Type", "text/xml");
        SOAPMessage msg = SOAPVersion.SOAP_11.saajMessageFactory.createMessage(mhs, new ByteArrayInputStream(jaxwsMsg.getBytes()));
        disp.invoke(msg);
    }

    public void testUsingAxisMsg() throws IOException, SOAPException {
        TestServiceService service = new TestServiceService();
        Dispatch<SOAPMessage> disp = service.createDispatch(new QName("http://server.jaxws195.fromjava/", "TestServicePort"), SOAPMessage.class, Service.Mode.MESSAGE);
        MimeHeaders mhs = new MimeHeaders();
        mhs.addHeader("Content-Type", "text/xml");
        SOAPMessage msg = SOAPVersion.SOAP_11.saajMessageFactory.createMessage(mhs, new ByteArrayInputStream(axisMsg.getBytes()));
        disp.invoke(msg);
    }




}
