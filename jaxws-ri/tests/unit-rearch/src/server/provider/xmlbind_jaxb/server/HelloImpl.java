/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.provider.xmlbind_jaxb.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.Provider;
import javax.xml.ws.Service;
import javax.xml.ws.Service.Mode;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.soap.*;
import org.w3c.dom.Node;
import javax.xml.transform.dom.DOMSource;
import javax.activation.DataSource;
import javax.xml.ws.ServiceMode;
import com.sun.xml.messaging.saaj.packaging.mime.internet.MimeBodyPart;
import com.sun.xml.messaging.saaj.packaging.mime.internet.MimeMultipart;
import com.sun.xml.messaging.saaj.packaging.mime.internet.InternetHeaders;
import java.io.*;
import junit.framework.*;
import java.util.*;
import javax.xml.ws.WebServiceProvider;
import javax.xml.transform.sax.SAXSource;
import org.jvnet.fastinfoset.FastInfosetSource;

@WebServiceProvider
@ServiceMode (value=Service.Mode.MESSAGE)
public class HelloImpl implements Provider<Source> {
    
    private static final JAXBContext jaxbContext = createJAXBContext ();
    private int bodyIndex;
    private String fooString;
    
    public javax.xml.bind.JAXBContext getJAXBContext (){
        return jaxbContext;
    }
    
    private static javax.xml.bind.JAXBContext createJAXBContext (){
        try{
            return javax.xml.bind.JAXBContext.newInstance (ObjectFactory.class);
        }catch(javax.xml.bind.JAXBException e){
            throw new WebServiceException (e.getMessage (), e);
        }
    }
    
    private Source sendSource () {
        System.out.println ("**** sendSource ******");
        String begin = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body>";
        String end = "</soapenv:Body></soapenv:Envelope>";
        String body  = "<HelloResponse xmlns=\"urn:test:types\"><argument xmlns=\"\">" + fooString + "</argument><extra xmlns=\"\">bar</extra></HelloResponse>";
        
        String content = body;
        return new StreamSource(new ByteArrayInputStream(content.getBytes()));
    }
    
    private void recvBean (Source source) throws Exception {
        System.out.println ("**** recvBean ******");
        
        Hello hello = (Hello)jaxbContext.createUnmarshaller().unmarshal(source);
//            hello = (Hello)jaxbContext.createUnmarshaller().unmarshal(((StreamSource)source).getInputStream());        
        
        String argument = hello.getArgument();
        if (argument.equals("Dispatch")) {
            fooString = "foo"; // normal case
        } else if (argument.equals("hellofromhandler")) {
            fooString = "hellotohandler"; // handler test case
        } else {
            throw new WebServiceException("hello.getArgument(): expected \"Dispatch\", got \"" + hello.getArgument() + "\"");
        }
        if (!"Test".equals(hello.getExtra())) {
            throw new WebServiceException("hello.getArgument(): expected \"Test\", got \"" + hello.getExtra() + "\"");
        }
    }
    
    public Source invoke(Source source) {
        try {
            recvBean(source);

            return sendSource();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
