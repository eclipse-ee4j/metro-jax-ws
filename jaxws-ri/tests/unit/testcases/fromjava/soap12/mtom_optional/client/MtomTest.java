/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.soap12.mtom_optional.client;

import junit.framework.TestCase;

import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.Source;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.MTOMFeature;
import javax.xml.ws.handler.MessageContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Rama Pulavarthi
 */
public class MtomTest extends TestCase {

    //test for WSIT 1069, makes sure Mtom is enabled and this happens when the WSDL has the necessary policy assertion.
    public void testMtom() throws Exception {
        MtomSample proxy = new MtomSampleService().getMtomSamplePort();
        Source output = proxy.echo(getSource("sample_doc.xml"));
        Map<String, List<String>> response_headers =
        (Map<String, List<String>>)((BindingProvider)proxy).getResponseContext().get(MessageContext.HTTP_RESPONSE_HEADERS);
        String s = response_headers.get("Content-Type").get(0);
        assertTrue(s.startsWith("multipart/related"));
        assertTrue(s.contains("type=\"application/xop+xml\""));
        
    }

    //test for WSIT 1062
    public void testMtomOptionality() throws Exception {
        MtomSample proxy = new MtomSampleService().getMtomSamplePort(new MTOMFeature(false));
        Source output = proxy.echo(getSource("sample_doc.xml"));
        Map<String, List<String>> response_headers =
        (Map<String, List<String>>)((BindingProvider)proxy).getResponseContext().get(MessageContext.HTTP_RESPONSE_HEADERS);
        String s = response_headers.get("Content-Type").get(0);
        System.out.println(s);
        assertTrue(s.startsWith("application/soap+xml"));
        assertTrue(!s.contains("type=\"application/xop+xml\""));
    }

    //test for WSIT 1062, testing wsp:Optional="true" in the wsdl
    public void testMtomPolicyOptionality() throws Exception {
        MtomSample proxy = new MtomSampleService().getMtomSamplePort(new MTOMFeature(false));
        String address = (String) ((BindingProvider) proxy).getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document wsdl = db.parse(address + "?wsdl");

        Element el = (Element) wsdl.getElementsByTagNameNS(
                "http://schemas.xmlsoap.org/ws/2004/09/policy/optimizedmimeserialization","OptimizedMimeSerialization").item(0);
        String optional = el.getAttributeNS("http://www.w3.org/ns/ws-policy","Optional");
        assertTrue(Boolean.valueOf(optional));



    }
    
    private StreamSource getSource(String file) throws Exception {
           InputStream is = getClass().getClassLoader().getResourceAsStream(file);
           return new StreamSource(is);
       }

       private InputStream getResource(String file) throws Exception {
           InputStream is = getClass().getClassLoader().getResourceAsStream(file);
           return is;
       }

}
