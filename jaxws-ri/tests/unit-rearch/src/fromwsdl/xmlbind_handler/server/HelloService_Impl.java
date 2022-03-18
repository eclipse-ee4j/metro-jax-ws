/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.xmlbind_handler.server;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.soap.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.http.HTTPException;
import jakarta.xml.ws.Provider;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.Service.Mode;
import jakarta.xml.ws.WebServiceException;
                                                                                
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.rmi.RemoteException;
import java.util.Map;
                                                                                
import org.w3c.dom.Node;
import jakarta.xml.ws.WebServiceProvider;


/**
 */
@WebServiceProvider
public class HelloService_Impl implements Provider<Source> {
    private static final JAXBContext jaxbContext = createJAXBContext();

    public JAXBContext getJAXBContext(){
        return jaxbContext;
    }
                                                                                    
    private static jakarta.xml.bind.JAXBContext createJAXBContext(){
        try {
            return JAXBContext.newInstance(ObjectFactory.class);
        } catch(JAXBException e) {
            throw new WebServiceException(e.getMessage(), e);
        }
    }

    private Source sendSource(byte[] body) {
        String begin = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body>";
        String end = "</soapenv:Body></soapenv:Envelope>";
                                                                                                                        
        String content = begin + new String(body) + end;
        return new StreamSource(new ByteArrayInputStream(content.getBytes()));
    }

    private int recvBean(Source source) throws Exception {
	SOAPMessage msg = MessageFactory.newInstance().createMessage();
	msg.getSOAPPart().setContent(source);
	Node node = msg.getSOAPBody().getFirstChild();
	DOMSource dom = new DOMSource(node);
        Hello hello = (Hello) jaxbContext.createUnmarshaller().unmarshal(dom);
        return hello.getIntin();
    }


    private byte[] sendBean(int x) throws Exception {
        HelloResponse resp = new HelloResponse();
        resp.setIntout(x);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        Marshaller marshaller = jaxbContext.createMarshaller();
	marshaller.setProperty("jaxb.fragment", Boolean.TRUE);
        marshaller.marshal(resp, bout);

        bout.close();
        return bout.toByteArray();
    }
    
    public Source invoke(Source source) {
        try {
            int x = recvBean(source);
            byte[] body = sendBean(x);
            return sendSource(body);
        } catch(Exception e) {
            e.printStackTrace();
            throw new HTTPException(501);
        }

    }
    
}
