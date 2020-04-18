/*
 * Copyright (c) 2004, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package provider.wsdl_hello_lit_asyncprovider.server;

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

@WebServiceProvider(
    wsdlLocation="WEB-INF/wsdl/hello_literal.wsdl",
    targetNamespace="urn:test",
    serviceName="Hello",
    portName="HelloAsyncPort")

public class HelloAsyncImpl extends AbstractImpl implements AsyncProvider<Source> {

    public void invoke(Source source, AsyncProviderCallback cbak, WebServiceContext ctxt) {
        try {
            Hello_Type hello = recvBean(source);
            if (!hello.getExtra().startsWith("extra")) {
                cbak.send(new WebServiceException("Expected=extra*,got="+hello.getExtra()));
                return;
            }
            if (hello.getArgument().equals("fault")) {
                cbak.send(sendFaultSource());
                return;
            }
            cbak.send((++combo%2 == 0) ? sendSource(hello) : sendBean(hello));
        } catch(Exception e) {
            throw new WebServiceException("Endpoint failed", e);
        }
    }

}
