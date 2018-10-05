/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws670.server;

import com.sun.istack.NotNull;

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
public class CheckActionHandler implements SOAPHandler<SOAPMessageContext> {

    private static final String EXPECTED_CONTENT_TYPE_PART1 = "multipart/related;";
    private static final String EXPECTED_CONTENT_TYPE_PART2 = "start-info=\"application/soap+xml;action=\\\"http://server.jaxws670.bugs/Echo/echoRequest\\\"\"";
    private static final String UNEXPECTED_CONTENT_TYPE = ";action=\"http://server.jaxws670.bugs/Echo/echoRequest\"";

    public CheckActionHandler() {
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
                if (!actualContentType.contains(EXPECTED_CONTENT_TYPE_PART1) ||
                        !actualContentType.contains(EXPECTED_CONTENT_TYPE_PART2) ||
                        actualContentType.contains(UNEXPECTED_CONTENT_TYPE)) {

                    throw new RuntimeException("Unexpected Content-Type! " +
                            "expected one must contain parts: [" + EXPECTED_CONTENT_TYPE_PART1 + "] and [" + EXPECTED_CONTENT_TYPE_PART2 + "], " +
                            "mustn't contain part: [" + UNEXPECTED_CONTENT_TYPE + "], " +
                            "actual: [" + actualContentType + "]"
                    );
                }
            }
        }

        return true;
    }

    private List<String> getHTTPHeader(@NotNull Map<String, List<String>> headers, @NotNull String header) {
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
