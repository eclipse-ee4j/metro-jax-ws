/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package provider.wsdl_hello_lit.server;

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
import javax.xml.ws.WebServiceProvider;

/**
 * @author Jitendra Kotamraju
 */
@WebServiceProvider(
    wsdlLocation="WEB-INF/wsdl/hello_literal.wsdl",
    targetNamespace="urn:test",
    serviceName="Hello",
    portName="HelloPort")

public class HelloImpl extends AbstractImpl implements Provider<Source> {

    public Source invoke(Source source) {
        try {
            Hello_Type hello = recvBean(source);
            if (!hello.getExtra().startsWith("extra")) {
                throw new WebServiceException("Expected=extra*,got="+hello.getExtra());
            }
            if (hello.getArgument().equals("fault")) {
                return sendFaultSource();
            }
            return (++combo%2 == 0) ? sendSource(hello) : sendBean(hello);
        } catch(Exception e) {
            throw new WebServiceException("Endpoint failed", e);
        }
    }

}
