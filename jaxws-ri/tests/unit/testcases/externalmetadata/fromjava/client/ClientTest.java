/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package externalmetadata.fromjava.client;

import junit.framework.TestCase;
import com.oracle.webservices.api.databinding.ExternalMetadataFeature;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.handler.MessageContext;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.xml.ws.BindingProvider.SOAPACTION_USE_PROPERTY;

/**
 * Test for JAX_WS-1049: problem with filling packet from {@link com.sun.xml.ws.client.RequestContext} - setting different properties to
 * {@link com.sun.xml.ws.client.RequestContext} and checking on server side that those were applied correctly (specifically SOAPAction +
 * custom HTTP headers)
 *
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
public class ClientTest extends TestCase {

    public void test() throws SOAPException, IOException {

        // -- TEST different SOAPACTION properties in RequestContext
        File file = new File("testcases/externalmetadata/fromjava/client/external-wsdl-customization-client.xml");
        WebServiceFeature feature = ExternalMetadataFeature.builder().addFiles(file).build();
        FAKENAME port = new EchoImplService().getFAKENAMEPort(feature);
        Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();

        //THIS next line is causing the SOAPACTION http header to go blank, if fix JAX_WS-1049 not applied ...
        requestContext.keySet();

        port.doSomething();

        // -- TEST dispatch: http://java.net/jira/browse/JAX_WS-1014 : Bug <12883765>
        String jaxwsMsg = "<?xml version='1.0' encoding='UTF-8'?>" +
                "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "<S:Body><doSomething xmlns=\"overriden-target-namespace\"/></S:Body></S:Envelope>";

        EchoImplService service = new EchoImplService();
        Dispatch<SOAPMessage> dispatch = service.createDispatch(new QName("overriden-target-namespace", "FAKE-NAMEPort"), SOAPMessage.class, Service.Mode.MESSAGE);

        Map<String, List> headersMap = new HashMap<String, List>();
        headersMap.put("X-ExampleHeader2", Collections.singletonList("Value"));
        Map<String, Object> context = dispatch.getRequestContext();

        context.put(MessageContext.HTTP_REQUEST_HEADERS, headersMap);
        context.put(SOAPACTION_USE_PROPERTY, Boolean.TRUE);

        MimeHeaders mhs = new MimeHeaders();

        mhs.addHeader("My-Content-Type", "text/xml");
        mhs.addHeader("SOAPAction", "overridenInputAction");

        SOAPMessage msg = MessageFactory.newInstance().createMessage(mhs, new ByteArrayInputStream(jaxwsMsg.getBytes()));
        dispatch.invoke(msg);
    }

}
