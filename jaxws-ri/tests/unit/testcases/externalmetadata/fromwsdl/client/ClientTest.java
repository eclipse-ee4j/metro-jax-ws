/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package externalmetadata.fromwsdl.client;

import junit.framework.TestCase;
import com.oracle.webservices.api.databinding.ExternalMetadataFeature;

import javax.xml.namespace.QName;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.MimeHeaders;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.handler.MessageContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jakarta.xml.ws.BindingProvider.SOAPACTION_USE_PROPERTY;

/**
 * Simple WS client to test Service deployed with external metadata
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
public class ClientTest extends TestCase {

    public void test() throws SOAPException, IOException {

        ServiceName port = new ExternalMetadataServiceImplService().getServiceNamePort();
        port.doSomething();

        // -- TEST dispatch: http://java.net/jira/browse/JAX_WS-1014 : Bug <12883765>
        String jaxwsMsg = "<?xml version='1.0' encoding='UTF-8'?>" +
                "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "<S:Body><doSomething xmlns=\"my-target-namespace\"/></S:Body></S:Envelope>";

//        ExternalMetadataService service = new ExternalMetadataService();
        ExternalMetadataServiceImplService service = new ExternalMetadataServiceImplService();
        Dispatch<SOAPMessage> dispatch = service.createDispatch(new QName("my-target-namespace", "ServiceNamePort"), SOAPMessage.class, Service.Mode.MESSAGE);

        Map<String, List> headersMap = new HashMap<String, List>();
        headersMap.put("X-ExampleHeader2", Collections.singletonList("Value"));
        Map<String, Object> context = dispatch.getRequestContext();

        context.put(MessageContext.HTTP_REQUEST_HEADERS, headersMap);
        context.put(SOAPACTION_USE_PROPERTY, Boolean.TRUE);

        MimeHeaders mhs = new MimeHeaders();

        mhs.addHeader("My-Content-Type", "text/xml");
        mhs.addHeader("SOAPAction", "myInputAction");

        SOAPMessage msg = MessageFactory.newInstance().createMessage(mhs, new ByteArrayInputStream(jaxwsMsg.getBytes()));
        dispatch.invoke(msg);
    }

}
