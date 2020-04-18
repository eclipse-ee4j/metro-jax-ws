/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.provider.xmlbind_datasource.client;

import junit.framework.TestCase;
import org.w3c.dom.Node;
import testutil.ClientServerTestUtil;

import jakarta.activation.DataSource;
import jakarta.xml.bind.JAXBContext;
import javax.xml.namespace.QName;
import jakarta.xml.soap.AttachmentPart;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.http.HTTPBinding;
import java.awt.*;
import java.io.*;
import java.net.URI;

/**
 *
 * @author JAX-RPC RI Development Team
 */
public class HelloLiteralTest extends TestCase {

    private String helloSM= "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body><tns:Hello xmlns:tns=\"urn:test:types\"><argument>Dispatch</argument><extra>Test</extra></tns:Hello></soapenv:Body></soapenv:Envelope>";

    private QName serviceQName = new QName("urn:test", "Hello");
    private QName portQName = new QName("urn:test", "HelloPort");;
    private String endpointAddress;

    private Service service;
    private Dispatch<DataSource> dispatchDataSource;
    private ClientServerTestUtil util = new ClientServerTestUtil();
    
    private static final JAXBContext jaxbContext = createJAXBContext();
    
    
    public static void main(String[] args) {
        try {
            System.setProperty("uselocal", "true");
            HelloLiteralTest test = new HelloLiteralTest("HelloLiteralTest");
            test.testHelloRequestResponseDataSource();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public jakarta.xml.bind.JAXBContext getJAXBContext(){
        return jaxbContext;
    }
    
    private static jakarta.xml.bind.JAXBContext createJAXBContext(){
        try{
            return JAXBContext.newInstance(ObjectFactory.class);
        }catch(jakarta.xml.bind.JAXBException e){
            throw new WebServiceException(e.getMessage(), e);
        }
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

    public HelloLiteralTest(String name) {
        super(name);

        if(ClientServerTestUtil.useLocal())
            endpointAddress = "local://"+new File(System.getProperty("tempdir")).getAbsolutePath().replace('\\','/')+'?'+portQName.getLocalPart();
        else endpointAddress =
		   "http://localhost:8080/jaxrpc-provider_tests_xmlbind_datasource/hello";

    }
    
    Service createService () {
        Service service = Service.create(serviceQName);
        
        return service;
    }

  
    private Dispatch<DataSource> createDispatchDataSource() throws Exception {
        service = createService();
        service.addPort(portQName, HTTPBinding.HTTP_BINDING, setTransport(endpointAddress));
        dispatchDataSource = service.createDispatch(portQName, DataSource.class,
            Service.Mode.MESSAGE);
        //util.setTransport(dispatchDataSource, null);
        return dispatchDataSource;
    }    

    public void testHelloRequestResponseDataSource() throws Exception {
        Dispatch<DataSource> dispatch = createDispatchDataSource();
        DataSource ds = getDataSource();
        DataSource result = null;
        try {
            result = dispatch.invoke (ds);
        } catch (Exception ex){
            throw ex;
        }
        assertTrue (result instanceof DataSource);
        // verify the results

        // Create source according to type
        String contentType = result.getContentType();
        Source source = contentType.equals("application/fastinfoset") ?
            new org.jvnet.fastinfoset.FastInfosetSource(result.getInputStream())
            : new StreamSource(result.getInputStream());

        SOAPMessage msg = MessageFactory.newInstance().createMessage();
        msg.getSOAPPart().setContent(source);
        Node node = msg.getSOAPBody().getFirstChild();
        HelloResponse response = (HelloResponse)jaxbContext.createUnmarshaller().unmarshal(node);

        assertTrue("foo".equals(response.getArgument()));
        assertTrue("bar".equals(response.getExtra()));
    }
    
    private SOAPMessage getSOAPMessage() throws Exception {
        byte[] bytes = helloSM.getBytes();
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage();
        message.getSOAPPart().setContent(new StreamSource(bis));
        AttachmentPart ap = message.createAttachmentPart(getImage("java.jpg"), "image/jpeg");
        message.addAttachmentPart(ap);

        message.saveChanges();
        return message;
    }

     private Image getImage(String imageName) throws Exception {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(imageName);
        return javax.imageio.ImageIO.read(is);
    }
    
    private DataSource getDataSource() throws Exception {
        final SOAPMessage sm = getSOAPMessage();
        
        return new DataSource() {
            public InputStream getInputStream() {
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    sm.writeTo(baos);

                    ByteArrayInputStream bis = new ByteArrayInputStream(baos.toByteArray());

                    return bis;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }
            
            public OutputStream getOutputStream() {
                return null;
            }
            
            public String getContentType() {
                return sm.getMimeHeaders().getHeader("Content-Type")[0];
            }
            
            public String getName() {
                return "";
            }
        };
    }
    

}
