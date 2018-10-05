/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.dispatch.wsdl_hello_lit_asynchandler.client;
import junit.framework.Assert;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Response;
import javax.xml.ws.WebServiceException;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by JAXRPC Development Team
 * <p/>
 * Date: Jul 15, 2004
 * Time: 3:38:43 PM
 */
public class XMLAsyncHandler implements javax.xml.ws.AsyncHandler{

        Source source;

        public XMLAsyncHandler(Source source){
            this.source = source;
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

        public void handleResponse(Response response) {
            try {
            System.out.println("--------------------------------------");
            System.out.println("Handling response xmlHandler");
            Source obj = (Source)response.get();
                Assert.assertTrue(obj != null);
                Assert.assertTrue(obj.getClass().isAssignableFrom(StreamSource.class)
                    || obj.getClass().isAssignableFrom(DOMSource.class)
                    || obj.getClass().isAssignableFrom(SAXSource.class));
                System.out.println("XML Assertion passes");
                System.out.println("--------------------------------------");
                System.out.println(" Source result ");
                System.out.println(sourceToXMLString(obj));

                System.out.println("--------------------------------------");

            } catch (Exception e){
                Assert.fail("XMLAsyncHandler threw exception " + e.getMessage());

            }
        }
    }

