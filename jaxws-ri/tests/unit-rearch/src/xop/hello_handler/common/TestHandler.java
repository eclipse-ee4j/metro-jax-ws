/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package xop.hello_handler.common;

import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.soap.*;

public class TestHandler implements SOAPHandler<SOAPMessageContext> {

    boolean localTransport = false;

    public Set<QName> getHeaders() {
        return null;
    }

    public boolean handleMessage(SOAPMessageContext context) {

        //if local transport dont verify HTTP headers
        localTransport = context.get("LocalTransport") != null;

        //Set a property and verify in ApplicationContext to verify if handler has executed.
        context.put("MyHandler_Property", "foo");
        context.setScope("MyHandler_Property", MessageContext.Scope.APPLICATION);

        Map<String, List<String>> map = null;
        if (!(Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY)) {
            if(localTransport){
                System.out.println("Running in non-HTTP transport: not verifying HTTP headers!");
                return true;
            }
            
            map = (Map<String, List<String>>) context.get(MessageContext.HTTP_RESPONSE_HEADERS);
            if (!verifyContentTypeHttpHeader(map)) {
                throw new RuntimeException("In Client TestHandler :handleMessage: FAILED");
            }
        }
        return true;
    }

    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    public void close(MessageContext context) {
    }

    private boolean verifyContentTypeHttpHeader(Map<String, List<String>> m) {
        Map<String, List<String>> map = convertKeysToLowerCase(m);
        List<String> values = map.get("content-type");
        System.out.println("DEBUG: HTTP header Content-Type=" + values);
        if (values != null) {
            String sValues = values.toString().toLowerCase();
            if ((sValues.indexOf("multipart/related") >= 0) &&
                    (sValues.indexOf("text/xml") >= 0) &&
                    (sValues.indexOf("application/xop+xml") >= 0)&&
                    (sValues.indexOf("start=") >= 0)) {
                return true;
            } else {
                System.out.println("FAILED: : INVALID HTTP Content-type [" + sValues + "]\"");
                return false;
            }
        } else {
            System.out.println("FAILED: the HTTP header Content-Type was not found");
            return false;
        }
    }

    private static Map<String, List<String>> convertKeysToLowerCase(
            Map<String, List<String>> in) {

        Map<String, List<String>> out = new HashMap<String, List<String>>();
        if (in != null) {
            for (Map.Entry<String, List<String>> e : in.entrySet()) {
                if (e.getKey() != null)
                    out.put(e.getKey().toLowerCase(), e.getValue());
                else
                    out.put(e.getKey(), e.getValue());
            }
        }
        return out;
    }


}
