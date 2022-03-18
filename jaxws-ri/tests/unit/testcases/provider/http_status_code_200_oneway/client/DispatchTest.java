/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package provider.http_status_code_200_oneway.client;

import junit.framework.TestCase;

import jakarta.activation.DataSource;
import javax.xml.namespace.QName;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.MimeHeaders;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.soap.SOAPBinding;
import java.io.ByteArrayInputStream;
import java.util.Map;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Issue WSIT:1354
 *
 * @author Jitendra Kotamraju
 */
public class DispatchTest extends TestCase {

    public DispatchTest(String name) throws Exception {
        super(name);
    }

    /*
     * Service sends status code 200+no envelope
     */
    public void testNoContentType() throws Exception {
        BindingProvider bp = (BindingProvider)new Hello_Service().getHelloPort();
        String address = (String) bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);

        Service service = Service.create(new QName("", ""));
        service.addPort(new QName("",""), SOAPBinding.SOAP11HTTP_BINDING, address);
        Dispatch<SOAPMessage> d = service.createDispatch(new QName("",""), SOAPMessage.class, Service.Mode.MESSAGE);
        String str = "<S:Envelope xmlns:S='http://schemas.xmlsoap.org/soap/envelope/'><S:Body/></S:Envelope>";
        MessageFactory fact = MessageFactory.newInstance();
        MimeHeaders headers = new MimeHeaders();
        SOAPMessage req = fact.createMessage(headers, new ByteArrayInputStream(str.getBytes("UTF-8")));
        d.invokeOneWay(req);
    }

}
