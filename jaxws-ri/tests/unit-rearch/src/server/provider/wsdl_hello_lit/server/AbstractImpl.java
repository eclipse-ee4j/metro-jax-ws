/*
 * Copyright (c) 2004, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.provider.wsdl_hello_lit.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.ws.WebServiceException;
import com.sun.xml.ws.api.server.AsyncProvider;
import com.sun.xml.ws.api.server.AsyncProviderCallback;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.Service.Mode;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.WebServiceProvider;
import jakarta.xml.ws.WebServiceContext;

/**
 * @author Jitendra Kotamraju
 */
public abstract class AbstractImpl {

    private static final JAXBContext jaxbContext = createJAXBContext();
    protected int combo;
    private int i;

    private jakarta.xml.bind.JAXBContext getJAXBContext(){
        return jaxbContext;
    }
    
    private static jakarta.xml.bind.JAXBContext createJAXBContext(){
        try{
            return jakarta.xml.bind.JAXBContext.newInstance(ObjectFactory.class);
        }catch(jakarta.xml.bind.JAXBException e){
            throw new WebServiceException(e.getMessage(), e);
        }
    }

    protected Source sendSource(Hello hello) {
        String arg = hello.getArgument();
        String extra = hello.getExtra();
        String body = ((++i%2) == 0)
            ? "<HelloResponse xmlns='urn:test:types'><argument xmlns=''>"+arg+"</argument><extra xmlns=''>"+extra+"</extra></HelloResponse>"
            : "<ans1:HelloResponse xmlns:ans1='urn:test:types'><argument>"+arg+"</argument><extra>"+extra+"</extra></ans1:HelloResponse>";
        Source source = new StreamSource(new ByteArrayInputStream(body.getBytes()));
        return source;
    }

    protected Source sendFaultSource() {
        String body  = 
            "<soap:Fault xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><faultcode>soap:Server</faultcode><faultstring>Server was unable to process request. ---> Not a valid accountnumber.</faultstring><detail /></soap:Fault>";

        Source source = new StreamSource(
            new ByteArrayInputStream(body.getBytes()));
        return source;
    }

    protected Hello recvBean(Source source) throws Exception {
        Hello hello = (Hello)jaxbContext.createUnmarshaller().unmarshal(source);
        return hello;
    }

    protected Source sendBean(Hello req) throws Exception {
        HelloResponse resp = new HelloResponse();
        resp.setArgument(req.getArgument());
        resp.setExtra(req.getExtra());
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        jaxbContext.createMarshaller().marshal(resp, bout);
        return new StreamSource(new ByteArrayInputStream(bout.toByteArray()));
    }

}
