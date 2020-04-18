/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws1049.client;

import com.sun.xml.ws.client.RequestContext;
import junit.framework.TestCase;

import javax.xml.namespace.QName;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.MimeHeaders;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.handler.MessageContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jakarta.xml.ws.BindingProvider.SOAPACTION_URI_PROPERTY;
import static jakarta.xml.ws.BindingProvider.SOAPACTION_USE_PROPERTY;

/**
 * Test for JAX_WS-1049: problem with filling packet from {@link RequestContext} - setting different properties to
 * {@link RequestContext} and checking on server side that those were applied correctly (specifically SOAPAction +
 * custom HTTP headers)
 *
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
public class ClientTest extends TestCase {

    public void test() throws SOAPException, IOException {

        // -- TEST different SOAPACTION properties in RequestContext
        EchoImpl port = new EchoImplService().getEchoImplPort();
        Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();

        //THIS next line is causing the SOAPACTION http header to go blank, if fix JAX_WS-1049 not applied ...
        requestContext.keySet();

        port.doSomething();

        requestContext.put(SOAPACTION_USE_PROPERTY, Boolean.TRUE);
        requestContext.put(SOAPACTION_URI_PROPERTY, "customSOAPAction");
        port.doSomething();

        requestContext.put(SOAPACTION_USE_PROPERTY, false);
        port.doSomething();

        requestContext.put(SOAPACTION_USE_PROPERTY, null);
        requestContext.put(SOAPACTION_URI_PROPERTY, "");
        port.doSomething();

        // -- TEST dispatch: http://java.net/jira/browse/JAX_WS-1014 : Bug <12883765>
        String jaxwsMsg = "<?xml version='1.0' encoding='UTF-8'?>" +
                "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "<S:Body><doSomething xmlns=\"http://server.jaxws1049.bugs/\"/></S:Body></S:Envelope>";

        EchoImplService service = new EchoImplService();
        Dispatch<SOAPMessage> dispatch = service.createDispatch(new QName("http://server.jaxws1049.bugs/", "EchoImplPort"), SOAPMessage.class, Service.Mode.MESSAGE);

        Map<String, List> headersMap = new HashMap<String, List>();
        headersMap.put("X-ExampleHeader2", Collections.singletonList("Value"));
        dispatch.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, headersMap);

        MimeHeaders mhs = new MimeHeaders();

        mhs.addHeader("My-Content-Type", "text/xml");

        SOAPMessage msg = MessageFactory.newInstance().createMessage(mhs, new ByteArrayInputStream(jaxwsMsg.getBytes()));
        dispatch.invoke(msg);
    }

}
