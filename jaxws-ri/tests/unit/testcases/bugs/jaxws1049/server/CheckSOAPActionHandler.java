/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws1049.server;

import javax.xml.namespace.QName;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Handler checking expected SOAPActions are coming from client
 *
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
public class CheckSOAPActionHandler implements SOAPHandler<SOAPMessageContext> {

    public static String[] expectedSOAPActions = {
            "\"http://server.jaxws1049.bugs/EchoImpl/doSomethingRequest\"",
            "\"customSOAPAction\"",
            "\"http://server.jaxws1049.bugs/EchoImpl/doSomethingRequest\"",
            "\"http://server.jaxws1049.bugs/EchoImpl/doSomethingRequest\"",
    };
    int i = 0;

    public boolean handleMessage(SOAPMessageContext context) {

        if (!(Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY)) {

            Map<String, List<String>> map = (Map<String, List<String>>) context.get(MessageContext.HTTP_REQUEST_HEADERS);
            List<String> soapAction = getHTTPHeader(map, "SOAPAction");
            String soapAction0 = soapAction != null && soapAction.size() > 0 ? soapAction.get(0) : null;


            if (i < expectedSOAPActions.length) {
                // soapAction tests

                if (!expectedSOAPActions[i].equals(soapAction0)) {
                    throw new IllegalStateException("Received unexpected SOAPAction - received: [" + soapAction0 +
                            "], expectedSOAPActions: [" + expectedSOAPActions[i] + "]");
                }
                i++;

            } else {

                // dispatch test
                List<String> headers;
                headers = getHTTPHeader(map, "X-ExampleHeader2");
                if (headers == null) {
                    throw new IllegalStateException("Missing http header X-ExampleHeader2");
                }
                headers = getHTTPHeader(map, "My-Content-Type");
                if (headers == null) {
                    throw new IllegalStateException("Missing http header My-Content-Type");
                }
            }


        }

        return true;
    }

    private List<String> getHTTPHeader(Map<String, List<String>> headers, String header) {
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            String name = entry.getKey();
            if (name.equalsIgnoreCase(header))
                return entry.getValue();
        }
        return null;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return false;
    }

    @Override
    public void close(MessageContext context) {
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }
}
