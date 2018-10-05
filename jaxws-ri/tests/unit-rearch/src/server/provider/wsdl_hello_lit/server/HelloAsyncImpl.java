/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
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

import javax.xml.bind.JAXBContext;
import javax.xml.ws.WebServiceException;
import com.sun.xml.ws.api.server.AsyncProvider;
import com.sun.xml.ws.api.server.AsyncProviderCallback;
import javax.xml.ws.Service;
import javax.xml.ws.Service.Mode;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.WebServiceContext;

/**
 * @author Jitendra Kotamraju
 */

@WebServiceProvider(
    wsdlLocation="WEB-INF/wsdl/hello_literal.wsdl",
    targetNamespace="urn:test",
    serviceName="Hello")

public class HelloAsyncImpl extends AbstractImpl implements AsyncProvider<Source> {

    public void invoke(Source source, AsyncProviderCallback cbak, WebServiceContext ctxt) {
        try {
            Hello hello = recvBean(source);
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
