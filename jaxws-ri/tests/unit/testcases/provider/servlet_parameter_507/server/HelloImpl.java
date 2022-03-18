/*
 * Copyright (c) 2004, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package provider.servlet_parameter_507.server;

import jakarta.annotation.Resource;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.*;
import jakarta.xml.ws.handler.MessageContext;
import java.io.ByteArrayInputStream;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Client sends form request in the POST request.
 *
 * @author Jitendra Kotamraju
 */
@WebServiceProvider
@BindingType(value="http://www.w3.org/2004/08/wsdl/http")
public class HelloImpl implements Provider<Source> {

    @Resource
    WebServiceContext ctxt;

    private Source sendSource() {
        String msg = "<S:Envelope xmlns:S='http://schemas.xmlsoap.org/soap/envelope/'><S:Body>"+
            "<HelloResponse xmlns='urn:test:types'><argument xmlns=''>foo</argument><extra xmlns=''>bar</extra></HelloResponse>"+
            "</S:Body></S:Envelope>";
        return new StreamSource(new ByteArrayInputStream(msg.getBytes()));
    }

    public Source invoke(Source source) {
        MessageContext msgCtxt = ctxt.getMessageContext();
        HttpServletRequest request =
          (HttpServletRequest)msgCtxt.get(MessageContext.SERVLET_REQUEST);
        String val = request.getParameter("a");
        if (val == null || !val.equals("b")) {
            throw new WebServiceException("Unexpected parameter value for a. Expected: "+"b"+" Got: "+val);
        }
        return sendSource();
    }
}
