/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.message.saaj.client;

import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.api.message.HeaderList;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.streaming.XMLStreamWriterFactory;
import com.sun.xml.ws.message.saaj.SAAJMessage;
import com.sun.xml.ws.streaming.XMLStreamReaderUtil;
import com.sun.xml.ws.util.ByteArrayBuffer;
import com.sun.xml.ws.util.xml.XmlUtil;
import junit.framework.TestCase;
import org.w3c.dom.Node;
import whitebox.message.saaj.types.ValueType;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.MimeHeaders;
import jakarta.xml.soap.Name;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPBodyElement;
import jakarta.xml.soap.SOAPConstants;
import jakarta.xml.soap.SOAPEnvelope;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


/**
 * @author Vivek Pandey
 */
public class SAAJMessageTester extends TestCase{
    private String soap11Msg = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header><Header1 xmlns=\"urn:test:types\"><value>Its Header1</value></Header1><Header2 xmlns=\"urn:test:types\" soapenv:mustUnderstand='true'><value>Its Header2</value></Header2></soapenv:Header><soapenv:Body><Body xmlns=\"urn:test:types\"><value>Its Body</value></Body></soapenv:Body></soapenv:Envelope>";
    //private String soap12Msg = "<soapenv:Envelope xmlns:soapenv=\"http://www.w3.org/2003/05/soap-envelope\"><soapenv:Header><Header1 xmlns=\"urn:test:types\">Its Header1</Header1></soapenv:Header><soapenv:Header><Header2 xmlns=\"urn:test:types\">Its Header2</Header2></soapenv:Header><soapenv:Body><Body xmlns=\"urn:test:types\">Its Body</Body></soapenv:Body></soapenv:Envelope>";
    private String headers[]={"Header1", "Header2"};
    private Message msg;


    /**
     * Constructs a test case with the given name.
     */
    public SAAJMessageTester(String name) {
        super(name);
        init();
    }

    private void init() {
        MessageFactory mf = null;
        try {
            mf = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
            MimeHeaders mhs = new MimeHeaders();
            mhs.addHeader("Content-Type", "text/xml");
            mhs.addHeader("Content-Transfer-Encoding", "binary");
            SOAPMessage sm = mf.createMessage(mhs, new ByteArrayInputStream(soap11Msg.getBytes()));
            msg = new SAAJMessage(sm);
        } catch (SOAPException e) {
            e.printStackTrace();
            assertTrue(false);
        } catch (IOException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testWriteTo() throws Exception {
    	String infoset = "<soapenv:Envelope xmlns='default:ns' xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' a='b' tns:b='a' xmlns:tns='tns'><soapenv:Header><Header1 xmlns='urn:test:types' a='b' tns:b='a' xmlns:tns='tns'/></soapenv:Header><soapenv:Body><Body xmlns='urn:test:types'><value>Its Body</value></Body></soapenv:Body></soapenv:Envelope>";
		writeMessage(infoset);
    }

    public void testWriteTo1() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("msg.xml");
		writeMessage(in);
    }

    public void testWriteTo2() throws Exception {
    	String infoset = "<soapenv:Envelope xmlns='default:ns' xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' a='b' tns:b='a' xmlns:tns='tns'><soapenv:Header><Header1 xmlns='urn:test:types' a='b' tns:b='a' xmlns:tns='tns'><a xmlns='nested'><b xmlns='nested-nested'><c xmlns='' /></b></a></Header1><Header2 xmlns='nested'><a soapenv:b='b'/></Header2></soapenv:Header><soapenv:Body><Body xmlns='urn:test:types'><value>Its Body</value></Body></soapenv:Body></soapenv:Envelope>";
		writeMessage(infoset);
    }

	private void writeMessage(String infoset) throws Exception {
		writeMessage(new ByteArrayInputStream(infoset.getBytes()));
	}

	private void writeMessage(InputStream in) throws Exception {
        MessageFactory mf = MessageFactory.newInstance(
			SOAPConstants.SOAP_1_1_PROTOCOL);
        MimeHeaders mhs = new MimeHeaders();
        mhs.addHeader("Content-Type", "text/xml");
        mhs.addHeader("Content-Transfer-Encoding", "binary");
        SOAPMessage sm = mf.createMessage(mhs, in);
        SAAJMessage msg = new SAAJMessage(sm);
        ByteArrayBuffer baos = new ByteArrayBuffer();
        XMLStreamWriter writer =
			XMLStreamWriterFactory.create(baos);
		msg.writeTo(writer);
		writer.flush();
	}


    public void testDOMLevel1WriteTo() throws Exception {
    	DocumentBuilderFactory builderFactory = null;
    	DocumentBuilder builder = null;
    	
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
        soapEnvelope.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        soapEnvelope.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
        SOAPBody soapBody = soapEnvelope.getBody();
        Name elementName = soapEnvelope.createName("addNumbers", "", "http://duke.org");
        SOAPBodyElement bodyElement = soapBody.addBodyElement(elementName);
        SAAJMessage msg = new SAAJMessage(soapMessage);

        ByteArrayBuffer baos = new ByteArrayBuffer();
        XMLStreamWriter writer =
			XMLStreamWriterFactory.createXMLStreamWriter(baos);
		msg.writeTo(writer);
		writer.flush();
    }

    public void testSOAP11MessageAsJAXB(){
        try {
            HeaderList hl = msg.getHeaders();
            JAXBContext ctxt=null;
            Unmarshaller unmarshaller = null;
            try {
                ctxt = JAXBContext.newInstance("whitebox.message.saaj.types");
                unmarshaller = ctxt.createUnmarshaller();
            } catch (JAXBException e) {
                e.printStackTrace();
                assertTrue(false);
            }
            int i = 0;
            //validate the headers form unmarshalled jaxb bean
            for(Header h:hl){
                assertTrue(validateHeader(h, unmarshaller, headers[i++], SOAPVersion.SOAP_11));
            }

            //validate body
            assertTrue(msg.getPayloadLocalPart().equals("Body") && msg.getPayloadNamespaceURI().equals("urn:test:types"));
            JAXBElement<ValueType> body = msg.readPayloadAsJAXB(unmarshaller);
            assertTrue(body.getValue().getValue().equals("Its Body"));
        } catch (JAXBException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testSOAP11MessageAsXMLStreamReader(){
        //check the body using XMLStreamReader
        XMLStreamReader reader = null;
        try {
            reader = msg.readPayload();
        } catch (XMLStreamException e) {
            e.printStackTrace();
            assertTrue(false);
        }
        XMLStreamReaderUtil.verifyReaderState(reader, START_ELEMENT);

        QName envName = reader.getName();
        assertTrue(envName.equals(new QName("urn:test:types", "Body")));
        XMLStreamReaderUtil.nextElementContent(reader);

        XMLStreamReaderUtil.verifyReaderState(reader, START_ELEMENT);
        assertTrue(reader.getName().equals(new QName("urn:test:types", "value")));
        XMLStreamReaderUtil.nextContent(reader);
        assertTrue(reader.getText().equals("Its Body"));
    }

    public void testWriteSOAP11MessagePayloadToXMLStreamWriter(){
        ByteArrayBuffer baos = new ByteArrayBuffer();
        XMLStreamWriter writer = XMLStreamWriterFactory.create(baos);
        try {
            msg.writePayloadTo(writer);
            writer.flush();
            Transformer transformer = XmlUtil.newTransformer();
            StreamSource source = new StreamSource(baos.newInputStream());
            DOMResult domResult = new DOMResult();
            transformer.transform(source, domResult);
            Node n = domResult.getNode().getFirstChild();
            assertTrue(n.getLocalName().equals("Body") && n.getNamespaceURI().equals("urn:test:types"));
            Node c = n.getFirstChild();
            assertTrue(c.getLocalName().equals("value") && c.getNamespaceURI().equals("urn:test:types"));
            assertTrue(c.getFirstChild().getNodeValue().equals("Its Body"));
        } catch (XMLStreamException e) {
            e.printStackTrace();
            assertTrue(false);
        } catch (TransformerException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    private boolean validateHeader(Header h, Unmarshaller unm, String localName, SOAPVersion version) {
        if(!h.getLocalPart().equals(localName) || !h.getNamespaceURI().equals("urn:test:types"))
            return false;
        if(!h.getRole(version).equals("http://schemas.xmlsoap.org/soap/actor/next"))
            return false;
         Set<String> roles = new HashSet<String>();
        roles.add("http://schemas.xmlsoap.org/soap/actor/next");
        if(h.getLocalPart().equals("Header2") && h.isIgnorable(version, roles))
            return false;

        if(h.getLocalPart().equals("Header1") && !h.isIgnorable(version, roles))
                return false;
        try {
            JAXBElement<ValueType> header1Value = h.readAsJAXB(unm);
            if(!header1Value.getName().equals(new QName("urn:test:types", localName)))
                return false;

            if(!header1Value.getValue().getValue().equals("Its "+localName))
                return false;
        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
