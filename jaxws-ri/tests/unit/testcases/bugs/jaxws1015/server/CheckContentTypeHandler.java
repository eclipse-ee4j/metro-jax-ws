/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws1015.server;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Simple SOAPHandler checking incoming HTTP header Content-Type on the server side. If different from expected, throws an Exception
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
public class CheckContentTypeHandler implements SOAPHandler<SOAPMessageContext> {

    private static final String EXPECTED_CONTENT_TYPE = "application/soap+xml; charset=utf-8";

    public CheckContentTypeHandler() {
    }

    public boolean handleMessage(SOAPMessageContext context) {

        if (!(Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY)) {
            Map<String, List<String>> map = (Map<String, List<String>>) context.get(MessageContext.HTTP_REQUEST_HEADERS);
            List<String> contentType = getHTTPHeader(map, "Content-Type");
            if (contentType != null) {
                StringBuffer strBuf = new StringBuffer();
                for (String type : contentType) {
                    strBuf.append(type);
                }
                String actualContentType = strBuf.toString();
                if (!EXPECTED_CONTENT_TYPE.equals(actualContentType)) {
                    throw new RuntimeException("Unexpected Content-Type! " +
                            "expected: [" + EXPECTED_CONTENT_TYPE + "], " +
                            "actual: [" + actualContentType + "]"
                    );
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
