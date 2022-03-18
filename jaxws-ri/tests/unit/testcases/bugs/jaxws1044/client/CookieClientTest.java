/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws1044.client;

import junit.framework.TestCase;

import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.handler.MessageContext;
import java.util.*;
import java.util.logging.Logger;

/**
 * TODO: Write some description here ...
 *
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
public class CookieClientTest extends TestCase {

    static final Logger logger = Logger.getLogger(CookieClientTest.class.getName());

    CookieAService portA = new CookieAServiceService().getA();
    int call;

    String[][] expectedCookies = {
            {"C=C0", "S0=S0"},
            {"C=C0", "S0=S0", "S1=S1"},
            {"C=C0", "S0=S0", "S1=S1", "S2=S2"},

            {"C=C1"},
            {"C=C1"},
            {"C=C1"},

            {"C=C2"},
            {"C=C2"},
            {"C=C2"},
    };

    public void test() {

        logger.fine("CookieClientTest.test");
        ((BindingProvider) portA).getRequestContext().put(BindingProvider.SESSION_MAINTAIN_PROPERTY, true);

        invokeA();
        invokeA();
        invokeA();

        invokeA();
        invokeA();
        invokeA();

        invokeA();
        invokeA();
        invokeA();
    }

    @SuppressWarnings("unchecked")
    private void invokeA() {

        // set cookie once per 3 calls:
        if (call % 3 == 0) {
            String cookie = "C" + "=C" + call / 3;
            logger.fine(" >> Client: " + cookie);
            setCookie((BindingProvider) portA, cookie);
        }

        portA.operation();

        // list cookies
        List<String> cookies = (List<String>) ((Map) ((BindingProvider) portA).getResponseContext().get(MessageContext.HTTP_RESPONSE_HEADERS)).get("Set-Cookie");
        logger.fine(" << Client: " + cookies + "\n\n");
        checkExpectedCookies(cookies);

        call++;
    }

    private void checkExpectedCookies(List<String> cookies) {
        List<String> expected = Arrays.asList(expectedCookies[call]);
        assertTrue("Didn't get all the expected cookies from the service (call #" + call + ").\n" +
                "  expected: " + expected + "\n" +
                "  received: " + cookies,
                cookies.containsAll(expected));
    }

    private void setCookie(BindingProvider port, String cookieHeader) {
        Map<String, List<String>> reqHeaders = new HashMap<String, List<String>>();
        reqHeaders.put("Cookie", Collections.singletonList(cookieHeader));
        port.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, reqHeaders);
    }
}
