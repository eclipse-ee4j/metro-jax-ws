/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws1040.fromjava.client;

import com.sun.xml.ws.developer.MemberSubmissionAddressingFeature;
import junit.framework.TestCase;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.PortInfo;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.*;

/**
 * Simple WS just to verify MemberSubmissionAddressing configurationTODO: Write some description here ...
 *
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
public class WSAActionFromJavaTest extends TestCase {

    public void test() {
        WSAActionFromJavaService service = new WSAActionFromJavaService();
        setupCheckingHanlder(service);

        WSAActionFromJava port = service.getWSAActionFromJavaPort(new MemberSubmissionAddressingFeature(true));
        port.echo("test");
    }

    private void setupCheckingHanlder(WSAActionFromJavaService service) {
        service.setHandlerResolver(new HandlerResolver() {
            @Override
            @SuppressWarnings("unchecked")
            public List<Handler> getHandlerChain(PortInfo portInfo) {
                List<Handler> list = new ArrayList<Handler>();
                list.add(new CheckingHandler());
                return list;
            }
        });
    }

    private static class CheckingHandler implements SOAPHandler<SOAPMessageContext> {
        @Override
        public boolean handleMessage(SOAPMessageContext ctx) {
            QName qname = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "Action");
            try {
                Iterator elems = ctx.getMessage().getSOAPPart().getEnvelope().getHeader().getChildElements(qname);
                if (!elems.hasNext()) {
                    throw new RuntimeException("Required addressing header not found!");
                }
            } catch (SOAPException e) {
                e.printStackTrace();
                throw new RuntimeException("Required addressing header not found!");
            }
            return true;
        }

        @Override
        public Set<QName> getHeaders() {
            return null;
        }

        @Override
        public boolean handleFault(SOAPMessageContext context) {
            return false;
        }

        @Override
        public void close(MessageContext context) {
        }
    }
}
