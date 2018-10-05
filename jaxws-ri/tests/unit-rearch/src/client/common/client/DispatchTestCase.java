/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.common.client;

import junit.framework.TestCase;
import testutil.ClientServerTestUtil;

import javax.xml.ws.Service;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import java.net.URI;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.ArrayList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;

/**
 * User: kwalsh
 * Date: Apr 13, 2006
 * Time: 3:45:47 PM
 *
 */
public abstract class DispatchTestCase extends TestCase {

       public DispatchTestCase(String name){
           super(name);
       }




    protected void addPort( Service service, QName portQName, String bindingIdString, String endpointAddress) {
          service.addPort(portQName, bindingIdString, endpointAddress);
    }


    public Source makeSaxSource(String msg) {

            byte[] bytes = msg.getBytes();
            ByteArrayInputStream saxinputStream = new ByteArrayInputStream(bytes);
            InputSource inputSource = new InputSource(saxinputStream);
            return new SAXSource(inputSource);
        }

        public Source makeStreamSource(String msg) {

            byte[] bytes = msg.getBytes();
            ByteArrayInputStream sinputStream = new ByteArrayInputStream(bytes);
            return new StreamSource(sinputStream);
        }

        public Collection<Source> makeMsgSource(String msg) {
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

        public Source makeDOMSource(String msg) {

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
                fail("Error creating Dom Document");
            } catch (IOException e) {
                fail("Error creating Dom Document");
                fail("Error creating JABDispatch");
            }
        } catch (ParserConfigurationException pce) {
            fail("Error creating Dom Document");
            //IllegalArgumentException iae = new IllegalArgumentException(pce.getMessage());
            //iae.initCause(pce);
            //throw iae;
        }
        return null;
    }

        public SOAPMessage getSOAPMessage(Source msg) throws Exception {
          
           MessageFactory factory = MessageFactory.newInstance();
           SOAPMessage message = factory.createMessage();
           message.getSOAPPart().setContent((Source) msg);
           message.saveChanges();
           return message;
       }

        public SOAPMessage getSOAPMessage12(Source msg) throws Exception {

           MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
           SOAPMessage message = factory.createMessage();
           message.getSOAPPart().setContent((Source) msg);
           message.saveChanges();
           return message;
       }

       public String sourceToXMLString(Source result) {

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

    }




