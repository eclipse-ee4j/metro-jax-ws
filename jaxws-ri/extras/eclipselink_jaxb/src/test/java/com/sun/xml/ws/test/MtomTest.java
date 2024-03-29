/*
 * Copyright (c) 2013, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;

import javax.xml.namespace.QName;

import com.sun.xml.ws.db.toplink.JAXBContextFactory;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.jvnet.staxex.Base64Data;
import org.jvnet.staxex.XMLStreamReaderEx;

import com.oracle.webservices.api.databinding.Databinding;
import com.oracle.webservices.api.databinding.DatabindingFactory;
import com.oracle.webservices.api.databinding.DatabindingModeFeature;
import com.oracle.webservices.api.databinding.JavaCallInfo;
import com.oracle.webservices.api.message.ContentType;
import com.oracle.webservices.api.message.MessageContext;
import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
import com.sun.xml.stream.buffer.stax.StreamWriterBufferCreator;
import com.sun.xml.ws.api.message.Attachment;
import com.sun.xml.ws.api.message.AttachmentSet;
import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.api.message.MessageContextFactory;
import com.sun.xml.ws.api.message.Packet;

import junit.framework.TestCase;

public class MtomTest extends TestCase {

    static MessageFactory messageFactory;
    MessageContextFactory packetFactory = (MessageContextFactory) com.oracle.webservices.api.message.MessageContextFactory.createFactory();
    DatabindingFactory dbf = DatabindingFactory.newInstance();
    String ns = "http://example.com/test";

    Class<MtomSEI> sei = MtomSEI.class;
    Databinding dbE = dbf.createBuilder(sei, null).feature(new DatabindingModeFeature(JAXBContextFactory.ECLIPSELINK_JAXB)).serviceName(new QName(ns, "srv")).build();
    Databinding dbG = dbf.createBuilder(sei, null).feature(new DatabindingModeFeature(DatabindingModeFeature.GLASSFISH_JAXB)).serviceName(new QName(ns, "srv")).build();

    static MtomBean b = new MtomBean();
    static {
        b.setBinary1("fkldhafkdahfkdhfkhasdkfhldashfi9pweqryiweqyriqelhfklasdhfkldashflksdah".getBytes());
        b.setBinary2("epriepireroerupequreopruopweuqrowequrpoweqrupweqrweqrweuqrweqrwerwerer".getBytes());
        try {
            messageFactory = MessageFactory.newInstance();
        } catch (SOAPException e) { e.printStackTrace();
        }
    }
    Object[] args = { b, b };

    public void testOutboundJAXBMessageToOutput() throws Exception {
//        System.out.println("\r\n----------------- eclipselink.jaxb");
        testOutboundJAXBMessageToOutput(dbE);
//        System.out.println();System.out.println();System.out.println();
//        System.out.println("\r\n----------------- glassfish.jaxb");
        testOutboundJAXBMessageToOutput(dbG);
    }

    private void testOutboundJAXBMessageToOutput(Databinding db) throws Exception {
        JavaCallInfo jci = db.createJavaCallInfo(findMethod(sei, "header"), args);
        {
            MessageContext mc = db.serializeRequest(jci);   ;
            int count = 0;
            for ( Header h : ((Packet)mc).getInternalMessage().getHeaders().asList() ) {
//                System.out.println("~~~ header " + h);
                XMLStreamReader hr = h.readHeader();
                while(hr.hasNext()) {
                    int i = hr.next();
                    if (hr.isCharacters()) {
                        assertEquals(org.jvnet.staxex.Base64Data.class,((XMLStreamReaderEx)hr).getPCDATA().getClass());
                        Base64Data b64 = (Base64Data)((XMLStreamReaderEx)hr).getPCDATA();
//                        System.out.println("~~~~~~~~~~~~~~~ header " + b64.getDataHandler());
//                        assertEquals(org.jvnet.staxex.Base64Data.class,((XMLStreamReaderEx)xr).getPCDATA().getClass());
                        count++;
                    }
                }
            }
            XMLStreamReader xr = ((Packet)mc).getInternalMessage().readPayload();
            while(xr.hasNext()) {
                int i = xr.next();
                if (xr.isCharacters()) {
                    assertEquals(org.jvnet.staxex.Base64Data.class,((XMLStreamReaderEx)xr).getPCDATA().getClass());
                    Base64Data b64 = (Base64Data)((XMLStreamReaderEx)xr).getPCDATA();
//                    System.out.println("----------------- b64 " + b64.getDataHandler());
//                    assertEquals(org.jvnet.staxex.Base64Data.class,((XMLStreamReaderEx)xr).getPCDATA().getClass());
                    count++;
                }
            }
//            for (Attachment a : ((Packet)mc).getMessage().getAttachments()) count++;
            assertEquals(4, count);
        }
        {
            MessageContext mc = db.serializeRequest(jci);
//            mc.writeTo(System.out);
        }
//        System.out.println("\r\n----------------- saaj");
//        {
//            MessageContext mc = db.serializeRequest(jci);
//            mc.getAsSOAPMessage().writeTo(System.out);
//        }

    }

    public void XtestNamespaceAsLastItem1() throws Exception {
        MutableXMLStreamBuffer xsb = new MutableXMLStreamBuffer();
        XMLStreamWriter w = xsb.createFromXMLStreamWriter();//new StreamWriterBufferCreator(xsb);
        w.writeStartElement("p1","tag","ns1");
        w.writeAttribute("a","x");
        w.writeNamespace("p","ns");
        w.writeEndElement();
        XMLStreamReader xr = xsb.readAsXMLStreamReader();
        boolean tested = false;
        while(xr.hasNext()) {
            int i = xr.next();
//            if (xr.isStartElement() && xr.getLocalName().equals("tag")) {
//                assertEquals(1,xr.getAttributeCount());
//                assertEquals("ns",xr.getNamespaceURI("p"));
//                tested = true;
//            }
        }
//        assertTrue(tested);
    }


//    public void xtestInboundStreamMessageToJAXB() throws Exception {
//        MessageContext mc = db.serializeRequest(jci);
//        ByteArrayOutputStream bao = new ByteArrayOutputStream();
//        ContentType ct = mc.writeTo(bao);
//        String contentType = ct.getContentType();
//        byte[] bytes = bao.toByteArray();
//        System.out.println("testInboundStreamMessageToJAXB message\r\n " + new String(bytes));
//        MessageContext mc_ = packetFactory.createContext(new ByteArrayInputStream(bytes), contentType);
//        JavaCallInfo reqCall = db.deserializeRequest(mc_);
//        MtomBean b = (MtomBean)reqCall.getParameters()[0];
//        System.out.println("testInboundStreamMessageToJAXB message result " + b.getBinary1().length);
//        System.out.println("testInboundStreamMessageToJAXB message result " + b.getBinary2().length);
//    }

    static public Method findMethod(Class<?> c, String n) {
        for (Method m :c.getMethods()) if (m.getName().equals(n)) return m;
        return null;
    }
}
