/*
 * Copyright (c) 2004, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package provider.xmlbinding.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.Provider;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.Service.Mode;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.soap.*;
import org.w3c.dom.Node;
import javax.xml.transform.dom.DOMSource;
import jakarta.xml.ws.http.HTTPException;
import jakarta.xml.ws.WebServiceProvider;
import jakarta.xml.ws.BindingType;

/**
 * @author Jitendra Kotamraju
 */
@WebServiceProvider
@BindingType(value="http://www.w3.org/2004/08/wsdl/http")

public class HelloImpl implements Provider<Source> {

    private static final JAXBContext jaxbContext = createJAXBContext();
    private int combo;
    private int bodyIndex;

    public jakarta.xml.bind.JAXBContext getJAXBContext(){
        return jaxbContext;
    }
    
    private static jakarta.xml.bind.JAXBContext createJAXBContext(){
        try{
            return jakarta.xml.bind.JAXBContext.newInstance(ObjectFactory.class);
        }catch(jakarta.xml.bind.JAXBException e){
            throw new WebServiceException(e.getMessage(), e);
        }
    }

    private Source sendSource() {
        System.out.println("**** sendSource ******");
        String begin = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body>";
        String end = "</soapenv:Body></soapenv:Envelope>";

        String[] body  = {
            "<HelloResponse xmlns=\"urn:test:types\"><argument xmlns=\"\">foo</argument><extra xmlns=\"\">bar</extra></HelloResponse>",
            "<ans1:HelloResponse xmlns:ans1=\"urn:test:types\"><argument>foo</argument><extra>bar</extra></ans1:HelloResponse>"
        };
        int i = (++bodyIndex)%body.length;
        String content = begin+body[i]+end;
System.out.println("SEnding content**** ="+content);
        Source source = new StreamSource(
            new ByteArrayInputStream(content.getBytes()));
        return source;
    }

    private void recvBean(Source source) throws Exception {
        System.out.println("**** recvBean ******");
        Hello_Type hello = (Hello_Type)jaxbContext.createUnmarshaller().unmarshal(source);
        if (!hello.getArgument().equals("foo")) {
            throw new HTTPException(501);
        }
        if (!hello.getExtra().equals("bar")) {
            throw new HTTPException(502);
        }
    }

    private Source sendBean() throws Exception {
        System.out.println("**** sendBean ******");
        HelloResponse resp = new HelloResponse();
        resp.setArgument("foo");
        resp.setExtra("bar");
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        jaxbContext.createMarshaller().marshal(resp, bout);
        return new StreamSource(new ByteArrayInputStream(bout.toByteArray()));
    }

    public Source invoke(Source source) {
        try {
            System.out.println("**** Received in Provider Impl ******");
            SOAPMessage msg = MessageFactory.newInstance().createMessage();
            msg.getSOAPPart().setContent(source);
            Node node = msg.getSOAPBody().getFirstChild();
            recvBean(new DOMSource(node));
            return sendSource();
        } catch(HTTPException e) {
            throw e;
        } catch(Exception e) {
            throw new HTTPException(503);
        }
    }
}
