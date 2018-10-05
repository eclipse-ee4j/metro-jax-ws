/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.dispatch.xmlhttp_datasource.client;


import junit.framework.TestCase;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import testutil.ClientServerTestUtil;

import javax.activation.DataSource;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * @author JAX-RPC RI Development Team
 */
public class XMLHttp_DataSource extends TestCase {


    public XMLHttp_DataSource(String name) {
        super(name);
    }

    void setTransport(Dispatch dispatch) {
        try {
            // create helper class
            ClientServerTestUtil util = new ClientServerTestUtil();
            // set transport
            OutputStream log = null;
            log = System.out;
            util.setTransport(dispatch, (OutputStream) log);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private Source makeSaxSource(String msg) {

        byte[] bytes = msg.getBytes();
        ByteArrayInputStream saxinputStream = new ByteArrayInputStream(bytes);
        InputSource inputSource = new InputSource(saxinputStream);
        return new SAXSource(inputSource);
    }

    private Source makeStreamSource(String msg) {

        byte[] bytes = msg.getBytes();
        ByteArrayInputStream sinputStream = new ByteArrayInputStream(bytes);
        return new StreamSource(sinputStream);
    }

    private Collection<Source> makeMsgSource(String msg) {
        Collection<Source> sourceList = new ArrayList();

        byte[] bytes = msg.getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ByteArrayInputStream saxinputStream = new ByteArrayInputStream(bytes);
        InputSource inputSource = new InputSource(saxinputStream);

        ByteArrayInputStream sinputStream = new ByteArrayInputStream(bytes);

        DOMSource domSource = new DOMSource(createDOMNode(inputStream));
        sourceList.add(domSource);
        SAXSource saxSource = new SAXSource(inputSource);
        sourceList.add(saxSource);
        StreamSource streamSource = new StreamSource(sinputStream);
        sourceList.add(streamSource);

        return sourceList;
    }

   private Source makeDOMSource(String msg) {

        byte[] bytes = msg.getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

        return new DOMSource(createDOMNode(inputStream));
    }
    public Node createDOMNode(InputStream inputStream) {

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
    

    SOAPMessage getSOAPMessage(Source msg) throws Exception {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage();
        message.getSOAPPart().setContent((Source) msg);
        message.saveChanges();
        return message;
    }

    private String sourceToXMLString(Source result) {

        String xmlResult = null;
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            OutputStream out = new ByteArrayOutputStream();
            StreamResult streamResult = new StreamResult();
            streamResult.setOutputStream(out);
            transformer.transform(result, streamResult);
            xmlResult = streamResult.getOutputStream().toString();
        } catch (TransformerException e) {
            e.printStackTrace();            
        }
        return xmlResult;
    }


    public void testXMLHttpDataSource() {

        if (ClientServerTestUtil.useLocal())
             return;

        URI nsURI = null;
         try {
            nsURI = new URI("urn:yahoo:yn");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        QName serviceName = new QName("yahoo", nsURI.toString());
        QName portName = new QName("yahoo_port", nsURI.toString());
        Service s = Service.create(serviceName);
        URI address = null;
        try {
           
            address = new URI("http", null, "api.search.yahoo.com", 80,
                "/ImageSearchService/V1/imageSearch",
                "appid=YahooDemo&format=jpeg&query=dog",
                null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        s.addPort(portName, HTTPBinding.HTTP_BINDING, address.toString());


        Dispatch<DataSource> d = s.createDispatch(portName, DataSource.class, Service.Mode.MESSAGE);
        Map<String, Object> requestContext = d.getRequestContext();
        requestContext.put(MessageContext.HTTP_REQUEST_METHOD, new String("GET"));

        // Execute the search iterate through the results
        Object rs =  d.invoke(null);
        assertTrue(rs != null);
        assertTrue(rs instanceof DataSource);

    }


    /*
    * for debugging
    */
    public static void main(String [] args) {
        try {
            //System.setProperty("uselocal", "true");
            System.setProperty("com.sun.xml.ws.client.ContentNegotiation", "optimistic");
            XMLHttp_DataSource testor = new XMLHttp_DataSource("TestClient");
            testor.testXMLHttpDataSource();
            //testor.testDelay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
