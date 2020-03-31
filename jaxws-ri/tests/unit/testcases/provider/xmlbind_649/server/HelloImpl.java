/*
 * Copyright (c) 2004, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package provider.xmlbind_649.server;

import javax.annotation.Resource;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import jakarta.xml.ws.*;
import jakarta.xml.ws.handler.MessageContext;
import java.util.List;
import java.util.Map;

/**
 * Client sends a specific Content-Type application/atom+xml
 *
 * @author Jitendra Kotamraju
 */
@WebServiceProvider(targetNamespace="urn:test", portName="HelloPort", serviceName="Hello")
@BindingType(value="http://www.w3.org/2004/08/wsdl/http")
public class HelloImpl implements Provider<Source> {
    @Resource
    WebServiceContext wsc;

    public Source invoke(Source source) {
        MessageContext ctxt = wsc.getMessageContext();
        String method = (String)ctxt.get(MessageContext.HTTP_REQUEST_METHOD);
        if (!method.equals("PUT")) {
            throw new WebServiceException("HTTP method expected=PUT got="+method);
        }
        Map<String, List<String>> hdrs = (Map<String, List<String>>)ctxt.get(MessageContext.HTTP_REQUEST_HEADERS);
        List<String> ctList = hdrs.get("Content-Type");
        if (ctList == null || ctList.size() != 1) {
            throw new WebServiceException("Invalid Content-Type header="+ctList);
        }
        String got = ctList.get(0);
        if (!got.equals("application/atom+xml")) {
            throw new WebServiceException("Expected=application/atom+xml"+" got="+got);
        }        
        return new SAXSource();
    }
    
}
