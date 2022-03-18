/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.provider.xmlbind_ds.client;

import junit.framework.*;
import testutil.ClientServerTestUtil;
import jakarta.xml.ws.Service;
import javax.xml.namespace.QName;
import java.io.PrintStream;
import jakarta.xml.ws.*;
import jakarta.xml.ws.soap.*;
import jakarta.xml.ws.http.*;
import jakarta.xml.soap.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.awt.Toolkit;
import java.awt.Image;
import java.net.URI;
import jakarta.activation.DataSource;

/**
 *
 * @author JAX-RPC RI Development Team
 */
public class HelloLiteralTest extends TestCase {

    private String helloSM= "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body><tns:Hello xmlns:tns=\"urn:test:types\"><argument>Dispatch </argument><extra>Test </extra></tns:Hello></soapenv:Body></soapenv:Envelope>";

    private QName serviceQName = new QName("urn:test", "Hello");
    private QName portQName = new QName("urn:test", "HelloPort");;
    private String endpointAddress;

    private Service service;
    private Dispatch<SOAPMessage> dispatch;

    public HelloLiteralTest(String name) {
        super(name);

        if (ClientServerTestUtil.useLocal())
            endpointAddress = "local://" + new File(System.getProperty("tempdir")).getAbsolutePath().replace('\\', '/') + '?' + portQName.getLocalPart();
        else
            endpointAddress = "http://localhost:8080/jaxrpc-provider_tests_xmlbind_ds/hello";
    }
    protected String setTransport(String endpoint) {
           try {

               if (ClientServerTestUtil.useLocal()) {
                  URI uri = new URI(endpoint);
                  return uri.resolve(new URI("local", uri.getPath(), uri.getFragment())).toString();
               }

           } catch (Exception ex) {
               ex.printStackTrace();
           }
        return endpoint;
       }
    private Dispatch<SOAPMessage> createDispatchSOAPMessage() throws Exception {
        Service service = Service.create(serviceQName);
        service.addPort(portQName, SOAPBinding.SOAP11HTTP_BINDING, setTransport(endpointAddress));
        dispatch = service.createDispatch(portQName, SOAPMessage.class,
            Service.Mode.MESSAGE);
        ClientServerTestUtil util = new ClientServerTestUtil();
        return dispatch;
    }

    public void testDataSource() throws Exception {
        Service service = Service.create(serviceQName);
        service.addPort(portQName, HTTPBinding.HTTP_BINDING, endpointAddress);
        Dispatch<DataSource> dispatch = service.createDispatch(portQName, DataSource.class, Service.Mode.MESSAGE);
        final SOAPMessage message = getSOAPMessage();
        DataSource ds = new DataSource() {
            public InputStream getInputStream() {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                     message.writeTo(bos);
                    bos.close();
                    return new ByteArrayInputStream(bos.toByteArray());
                } catch(Exception ioe) {
                    throw new RuntimeException(ioe);
                }
            }

            public OutputStream getOutputStream() {
                return null;
            }

            public String getContentType() {
                return message.getMimeHeaders().getHeader("Content-Type")[0];
            }

            public String getName() {
                return "";
            }
        };

        DataSource result = dispatch.invoke(ds);
    }

    public void testHelloRequestResponseSOAPMessage() throws Exception {
        Dispatch<SOAPMessage> dispatch = createDispatchSOAPMessage();
        SOAPMessage message = getSOAPMessage();
        SOAPMessage result = dispatch.invoke(message);
        assertTrue(result instanceof SOAPMessage);
    }

    private SOAPMessage getSOAPMessage() throws Exception {
        byte[] bytes = helloSM.getBytes();
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage();
        message.getSOAPPart().setContent(new StreamSource(bis));

        String userDir = System.getProperty("user.dir");
        String sepChar = System.getProperty("file.separator");
        String imageFile = userDir+sepChar
            +"src/server/provider/xmlbind_ds/common_resources/WEB-INF/"
            +"java.jpg";

        // Attach Image        
        Image img = Toolkit.getDefaultToolkit().getImage(imageFile);
        AttachmentPart ap = message.createAttachmentPart(img, "image/jpeg");
        message.addAttachmentPart(ap);

        message.saveChanges();
        return message;
    }

}
