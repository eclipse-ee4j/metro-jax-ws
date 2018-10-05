/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package externalmetadata.fromjava.server;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Handler checking expected SOAPActions are coming from client
 *
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
public class CheckSOAPActionHandler implements SOAPHandler<SOAPMessageContext> {

    public static String expectedSOAPAction = "\"overridenInputAction\"";

    @SuppressWarnings("unchecked")
    public boolean handleMessage(SOAPMessageContext context) {

        if (!(Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY)) {

            Map<String, List<String>> map = (Map<String, List<String>>) context.get(MessageContext.HTTP_REQUEST_HEADERS);
            List<String> soapAction = getHTTPHeader(map, "SOAPAction");
            String soapAction0 = soapAction != null && soapAction.size() > 0 ? soapAction.get(0) : null;

            // both "" and "overridenInputAction" are ok ...
            if (!expectedSOAPAction.equals(soapAction0) && !"\"\"".equals(soapAction0)) {
                throw new IllegalStateException("Received unexpected SOAPAction - received: [" + soapAction0 +
                        "], expectedSOAPActions: [" + expectedSOAPAction + "]");
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
