/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.jaxws991.client;

import com.sun.xml.ws.api.addressing.AddressingVersion;
import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.developer.WSBindingProvider;
import com.sun.xml.ws.message.StringHeader;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple test reproducing bug JAXWS-991 (duplicate WS-A headers when added manually by user)
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
public class WSATest extends TestCase {

    Echo port;

    protected void setUp() throws Exception {
        port = new EchoService().getEchoPort();
    }

    public void testEcho() {

        // setting user defined MessageID:
        List<Header> headers = new ArrayList<Header>();
        headers.add(new StringHeader(AddressingVersion.W3C.messageIDTag, "userDefinedMessageID"));
        ((WSBindingProvider) port).setOutboundHeaders(headers);

        port.echoString("String to be echoed");

        // without fix, there is duplicit header wsa:MessageID so the WS call crashes with
        //  SOAPFaultException: A header representing a Message Addressing Property is
        //  not valid and the message cannot be processed ...
    }

}
