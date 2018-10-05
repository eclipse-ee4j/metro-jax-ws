/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.encoding.serversf.server;

import com.sun.xml.ws.developer.Serialization;

import javax.annotation.Resource;
import javax.jws.*;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;

import java.util.List;
import java.util.Map;

/**
 * @author Jitendra Kotamraju
 */
@WebService(name = "Echo", serviceName = "echoService", targetNamespace = "http://echo.org/")
@Serialization(encoding="UTF-16")
public class EchoImpl {
    @Resource
    WebServiceContext wsc;

    public String echoString(String str) throws Exception1, Fault1, WSDLBarException {
        MessageContext ctxt = wsc.getMessageContext();
        Map<String, List<String>> hdrs = (Map<String, List<String>>)ctxt.get(MessageContext.HTTP_REQUEST_HEADERS);
        
        if (str == null) {
            return null;
        } else if (str.equals("Exception1")) {
            throw new Exception1("my exception1");
        } else if (str.equals("Fault1")) {
            FooException fooException = new FooException();
            fooException.setVarString("foo");
            fooException.setVarInt(33);
            fooException.setVarFloat(44F);
            throw new Fault1("fault1", fooException);
        } else if (str.equals("WSDLBarException")) {
            throw new WSDLBarException("my barException", new Bar(33));
        } else {
            List<String> ctList = hdrs.get("Content-Type");
            if (ctList == null || ctList.size() != 1) {
                throw new WebServiceException("Invalid Content-Type header="+ctList);
            }
            String ct = ctList.get(0).toLowerCase();
            if (!(ct.contains(str.toLowerCase()))) {
                throw new WebServiceException("Invalid Encoding for request. Expected="+str+" Got Content-Type header="+ctList);
            }
        }
        return str;
    }
}
