/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package mtom.issue_671.client;

import junit.framework.TestCase;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.soap.MTOMFeature;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.http.HTTPBinding;
import jakarta.xml.soap.*;
import javax.xml.namespace.QName;
import java.io.*;
import java.util.*;

/**
 * Test that the messages are XOPed when MTOM is enabled.
 * Tests issue JAX-WS-671
 *
 * @author Rama Pulavarthi
 */
public class Issue671Test extends TestCase {

    private Hello mtom_proxy;
    private Hello no_mtom_proxy;
    private Dispatch<DataSource> dispatch;
    private Hello provider_proxy;

    public Issue671Test(String name) throws Exception {
        super(name);
    }

    protected void setUp() throws Exception {
        mtom_proxy = new HelloService().getHelloPort(new MTOMFeature());
        no_mtom_proxy = new HelloService().getHelloPort();
        provider_proxy = new HelloService().getHelloProviderPort(new MTOMFeature());
        String helloPortAddress = (String) ((BindingProvider) mtom_proxy).getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
        QName serviceName = new QName("http://example.org/mtom", "Hello");
        QName portName = new QName("http://example.org/mtom", "HelloPort");
        Service service = Service.create(serviceName);
        service.addPort(portName, HTTPBinding.HTTP_BINDING, helloPortAddress);
        dispatch = service.createDispatch(portName, DataSource.class, Service.Mode.MESSAGE);
    }

    /**
     * Normal MTOMEnabled SEI case, (useful for capturing sample messages).
     *
     * @throws Exception
     */
    public void testMtomEnabled() throws Exception {
        XDoc xdoc = new XDoc();
        xdoc.setDoc1(getSource("gpsXml.xml"));
        XDoc out = mtom_proxy.xDoc(xdoc);
    }

    /**
     * Server is Provider<DataSource>, verifies if the request is XOPed.
     *
     * @throws Exception if the request is not XOPed
     */
    public void testVerifyMtomEnabled() throws Exception {
        XDoc xdoc = new XDoc();
        xdoc.setDoc1(getSource("gpsXml.xml"));
        XDoc out = provider_proxy.xDoc(xdoc);
        assertNotNull(out.getDoc1());
    }

    /**
     * MTOMFeature not enabled on this proxy.
     * The messages should not be XOPed though MTOM is enabled on endpoint.
     *
     * @throws Exception
     */
    public void testNoMTOM() throws Exception {
        XDoc xdoc = new XDoc();
        xdoc.setDoc1(getSource("gpsXml.xml"));
        XDoc out = no_mtom_proxy.xDoc(xdoc);
    }

    /**
     * Request is normal SOAPMessage,
     * Verfies that the response is normal message from a MTOM enabled endpoint.
     *
     * @throws Exception
     */
    public void testVerifyNoMtom() throws Exception {
        // Create a request SOAPMessage
        SOAPMessage in = getSOAPMessage();
        DataSource ds = getDataSource(in);

        ds = dispatch.invoke(ds);
        assertFalse(ds.getContentType().contains("application/xop+xml"));
        // Create a SOAPMessage from response
        MimeHeaders headers = new MimeHeaders();
        headers.addHeader("Content-Type", ds.getContentType());
        SOAPMessage out = MessageFactory.newInstance().createMessage(headers, ds.getInputStream());

        // verify S:Body
        SOAPBody body = out.getSOAPBody();
        Iterator it = body.getChildElements();
        SOAPElement elem = (SOAPElement) it.next();
        assertEquals(new QName("http://example.org/mtom", "XDocOut"), elem.getElementQName());
        SOAPElement docit = (SOAPElement) elem.getChildElements().next();
        assertEquals(new QName("http://example.org/mtom", "doc1"), docit.getElementQName());

        //assert that there is no xop include
        assertFalse(docit.getChildElements(new QName("http://www.w3.org/2004/08/xop/include", "Include")).hasNext());

    }


    /**
     * Send a MTOM request, ContentType of request is application/xop+xml
     * Verfies that the response is MTOM message.
     *
     * @throws Exception
     */
    public void testMtomWithContentTypeHeader() throws Exception {
        // Create a request SOAPMessage
        SOAPMessage in = getXOPedSOAPMessage();
        DataSource ds = getDataSource(in);
        ds = dispatch.invoke(ds);

        // Create a SOAPMessage from response
        MimeHeaders headers = new MimeHeaders();
        headers.addHeader("Content-Type", ds.getContentType());
        SOAPMessage out = MessageFactory.newInstance().createMessage(headers, ds.getInputStream());

        // verify S:Body
        SOAPBody body = out.getSOAPBody();
        Iterator it = body.getChildElements();
        SOAPElement elem = (SOAPElement) it.next();
        assertEquals(new QName("http://example.org/mtom", "XDocOut"), elem.getElementQName());
        SOAPElement docit = (SOAPElement) elem.getChildElements().next();
        assertEquals(new QName("http://example.org/mtom", "doc1"), docit.getElementQName());

        // verify <doc1> contains xop:Include
        SOAPElement xopit = (SOAPElement) docit.getChildElements().next();
        assertEquals(new QName("http://www.w3.org/2004/08/xop/include", "Include"), xopit.getElementQName());
        String href = xopit.getAttributeValue(new QName("", "href"));
        assertEquals("cid:", href.substring(0, 4));
    }

    /**
     * Request is normal SOAPMessage, but Accept header is set to application/xop+xml
     * Verfies that the response is MTOM
     *
     * @throws Exception
     */
    public void testMtomWithAcceptHeader() throws Exception {
        // Create a request SOAPMessage
        SOAPMessage in = getSOAPMessage();
        DataSource ds = getDataSource(in);


        Map<String, java.util.List<String>> httpheaders = new HashMap<String, java.util.List<String>>();
        httpheaders.put("Accept", Arrays.asList("multipart/related; type=application/xop+xml"));
        ((BindingProvider) dispatch).getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, httpheaders);

        ds = dispatch.invoke(ds);

        // Create a SOAPMessage from response
        MimeHeaders headers = new MimeHeaders();
        headers.addHeader("Content-Type", ds.getContentType());
        SOAPMessage out = MessageFactory.newInstance().createMessage(headers, ds.getInputStream());

        // verify S:Body
        SOAPBody body = out.getSOAPBody();
        Iterator it = body.getChildElements();
        SOAPElement elem = (SOAPElement) it.next();
        assertEquals(new QName("http://example.org/mtom", "XDocOut"), elem.getElementQName());
        SOAPElement docit = (SOAPElement) elem.getChildElements().next();
        assertEquals(new QName("http://example.org/mtom", "doc1"), docit.getElementQName());

        // verify <doc1> contains xop:Include
        SOAPElement xopit = (SOAPElement) docit.getChildElements().next();
        assertEquals(new QName("http://www.w3.org/2004/08/xop/include", "Include"), xopit.getElementQName());
        String href = xopit.getAttributeValue(new QName("", "href"));
        assertEquals("cid:", href.substring(0, 4));
    }

    private void validate(DataHandler exp, DataHandler got) throws Exception {
        InputStream inExp = exp.getInputStream();
        InputStream inGot = got.getInputStream();
        int ch;
        while ((ch = inExp.read()) != -1) {
            assertEquals(ch, inGot.read());
        }
        assertEquals(-1, inGot.read());
        inExp.close();
        inGot.close();
    }

    private StreamSource getSource(String file) throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream(file);
        return new StreamSource(is);
    }

    private InputStream getResource(String file) throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream(file);
        return is;
    }

    private DataHandler getDataHandler(final String file) throws Exception {
        return new DataHandler(new DataSource() {
            public String getContentType() {
                return "text/html";
            }

            public InputStream getInputStream() {
                return getClass().getClassLoader().getResourceAsStream(file);
            }

            public String getName() {
                return null;
            }

            public OutputStream getOutputStream() {
                throw new UnsupportedOperationException();
            }
        });
    }

    private DataSource getDataSource(final SOAPMessage msg) throws Exception {

        return new DataSource() {
            public String getContentType() {
                return msg.getMimeHeaders().getHeader("Content-Type")[0];
            }

            public InputStream getInputStream() throws IOException {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    msg.writeTo(baos);
                } catch (SOAPException e) {
                    throw new RuntimeException(e);
                }
                return new ByteArrayInputStream(baos.toByteArray());
            }

            public String getName() {
                return null;
            }

            public OutputStream getOutputStream() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private SOAPMessage getXOPedSOAPMessage() throws Exception {
        MimeHeaders headers = new MimeHeaders();
        headers.addHeader("Content-Type", "text/xml");
        SOAPMessage msg = MessageFactory.newInstance().createMessage(headers, getResource("xop.envelope"));

        AttachmentPart doc1 = msg.createAttachmentPart(getDataHandler("gpsXml.xml"));
        doc1.setContentId("2c5bbb50-5679-4fea-9982-666753769e20@example.jaxws.sun.com");
        msg.addAttachmentPart(doc1);

        MimeHeaders hdrs = msg.getMimeHeaders();
        String boundary = "BOUNDARY_123456789_BOUNDARY";
        String ct =
                "multipart/related;type=\"application/xop+xml\";boundary=" + boundary + ";start-info=\"text/xml\"";
        hdrs.setHeader("Content-Type", ct);

        msg.saveChanges();
        return msg;
    }

    private SOAPMessage getSOAPMessage() throws Exception {
        MimeHeaders headers = new MimeHeaders();
        headers.addHeader("Content-Type", "text/xml");
        SOAPMessage msg = MessageFactory.newInstance().createMessage(headers, getResource("normal.envelope"));
        msg.saveChanges();
        return msg;
    }
}
