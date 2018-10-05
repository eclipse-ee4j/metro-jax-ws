/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.domutil.client;

import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.streaming.XMLStreamWriterFactory;
import com.sun.xml.ws.message.saaj.SAAJMessage;
import com.sun.xml.ws.util.DOMUtil;
import junit.framework.TestCase;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * @author Rama Pulavarthi
 */

public class DomUtilTester extends TestCase {
    private final XMLOutputFactory staxOut;
    final File folder = new File(System.getProperty("tempdir") + "/classes/soapmessages");

    public DomUtilTester(String name) {
        super(name);
        this.staxOut = XMLOutputFactory.newInstance();
        staxOut.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
    }

    public void testSOAPEnvelope1() throws Exception {
        for (File f : folder.listFiles()) {
            System.out.println("***********"+ f.getName() + "***********");
            DOMSource src = makeDomSource(f);
            Node node = src.getNode();
            XMLStreamWriter writer = staxOut.createXMLStreamWriter(new PrintStream(System.out));
            DOMUtil.serializeNode((Element) node.getFirstChild(), writer);
            writer.close();
            assert(true);
            System.out.println("*****************************************");
        }
    }

    public void testSOAPEnvelope1_1() throws Exception {
        for (File f : folder.listFiles()) {
            System.out.println("***********"+ f.getName() + "***********");
            DOMSource src = makeDomSource(f);
            Node node = src.getNode();
            XMLStreamWriter writer = XMLStreamWriterFactory.create(System.out);
            DOMUtil.serializeNode((Element) node.getFirstChild(), writer);
            writer.close();
            assert(true);
            System.out.println("*****************************************");
        }
    }

    public void testSOAPEnvelope2() throws Exception {
        for (File f : folder.listFiles()) {
            System.out.println("***********"+ f.getName() + "***********");
            SOAPMessage soapmsg = getSOAPMessage(f);
            SAAJMessage saajmessage = new SAAJMessage(soapmsg);
            XMLStreamWriter writer = XMLStreamWriterFactory.create(System.out);
            saajmessage.writeTo(writer);
            writer.close();
            assert(true);
            System.out.println("*****************************************");
        }
    }

    public void testSOAPEnvelope2_1() throws Exception {
        for (File f : folder.listFiles()) {
            System.out.println("***********"+ f.getName() + "***********");
            SOAPMessage soapmsg = getSOAPMessage(f);
            SAAJMessage saajmessage = new SAAJMessage(soapmsg);
            XMLStreamWriter writer = staxOut.createXMLStreamWriter(new PrintStream(System.out));
            saajmessage.writeTo(writer);
            writer.close();
            assert(true);
            System.out.println("*****************************************");
        }
    }

    public void testSOAPEnvelope3() throws Exception {
        for (File f : folder.listFiles()) {
            System.out.println("***********"+ f.getName() + "***********");
            SOAPMessage soapmsg = getSOAPMessage(f);
            soapmsg.writeTo(System.out);
            assert(true);
            System.out.println("*****************************************");
        }
    }


    public static DOMSource makeDomSource(File f) throws Exception {
        InputStream is = new FileInputStream(f);
        DOMSource domSource = new DOMSource(createDOMNode(is));
        return domSource;
    }

    public static void printNode(Node node) {
        DOMSource source = new DOMSource(node);
        String msgString = null;
        try {
            Transformer xFormer = TransformerFactory.newInstance().newTransformer();
            xFormer.setOutputProperty("omit-xml-declaration", "yes");
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            Result result = new StreamResult(outStream);
            xFormer.transform(source, result);
            outStream.writeTo(System.out);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Node createDOMNode(InputStream inputStream) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setValidating(false);
        try {
            DocumentBuilder builder = dbf.newDocumentBuilder();
            try {
                return builder.parse(inputStream);
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ParserConfigurationException pce) {
            IllegalArgumentException iae = new IllegalArgumentException(pce.getMessage());
            iae.initCause(pce);
            throw iae;
        }
        return null;
    }

    public SOAPMessage getSOAPMessage(SOAPVersion version, Source msg) throws Exception {
        MessageFactory factory = version.saajMessageFactory;
        SOAPMessage message = factory.createMessage();
        message.getSOAPPart().setContent((Source) msg);
        message.saveChanges();
        return message;
    }

    public SOAPMessage getSOAPMessage(File f) throws Exception {
        SOAPVersion version = SOAPVersion.SOAP_11;
        if (f.getName().endsWith("_12.xml")) {
            version = SOAPVersion.SOAP_12;
        }
        MessageFactory mf = version.saajMessageFactory;
        SOAPMessage sm = mf.createMessage(null, new FileInputStream(f));
        return sm;
    }
}
