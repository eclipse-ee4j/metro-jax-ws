/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.wsa.responses.client;

import testutil.WsaUtils;
import testutil.PortAllocator;
import static testutil.W3CWsaUtils.invokeAsync;
import static testutil.W3CWsaUtils.S11_NS;
import static testutil.W3CWsaUtils.invoke;

import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.*;
import javax.xml.ws.soap.AddressingFeature;
import javax.xml.ws.soap.SOAPFaultException;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;

import com.sun.xml.ws.addressing.W3CAddressingConstants;


/**
 * @author Rama Pulavarthi
 */
public class AnonymousResponsesTest extends TestCase {

    private static QName PORT = new QName("http://server.responses.wsa.fromjava/", "AnonymousResponsesEndpoint");
    private static final String action = "http://server.responses.wsa.fromjava/AnonymousResponsesEndpoint/addNumbersRequest";
    private static final String endpointAddress = System.getProperty("anonymousResponsesEndpointAddress");
    private static String nonAnonAddress;

    public static final Dispatch<SOAPMessage> createDispatchWithoutAddressing() {
        return new AnonymousResponsesEndpointService().createDispatch(PORT, SOAPMessage.class, Service.Mode.MESSAGE, new AddressingFeature(false));
    }

    public static String getNonAnonymousClientAddress() {
        return "http://localhost:" + PortAllocator.getFreePort() + "/AnonymousResponsesTest/nonanoymous";
    }


    /**
     * SEI based invocation
     *
     * @throws Exception
     */
    public void testAnonymousReplyTo1() throws Exception {
        AnonymousResponsesEndpoint client = new AnonymousResponsesEndpointService().getAnonymousResponsesEndpoint();
        assertEquals(20, client.addNumbers(10, 10));
    }

    /**
     * Using Dispatch
     */
    public void testAnonymousReplyTo2() throws Exception {
        WsaUtils.invoke(createDispatchWithoutAddressing(),
                TestMessages.ANONYMOUS_REPLY_TO_COMPLETE_MESSAGE,
                WsaUtils.S11_NS,
                action,
                endpointAddress,
                "testAnonymousReplyTo");

    }

    public void testNonAnonymousReplyTo() throws Exception {
        try {
            invoke(createDispatchWithoutAddressing(),
                    TestMessages.NON_ANONYMOUS_REPLY_TO_COMPLETE_MESSAGE,
                    S11_NS,
                    nonAnonAddress,
                    action,
                    endpointAddress,
                    "testNonAnonymousReplyTo");
            fail("MUST throw SOAPFaultException with ONLY_ANONYMOUS_ADDRESS_SUPPORTED fault code");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            assertEquals(W3CAddressingConstants.ONLY_ANONYMOUS_ADDRESS_SUPPORTED, e.getFault().getFaultCodeAsQName());
        }
    }

    /**
     * Normal response case,just tests if the endpoint accepts non-anon FaultTo
     *
     * @throws Exception
     */
    public void testNonAnonymousFaultTo1() throws Exception {
        try {
            SOAPMessage response = invoke(createDispatchWithoutAddressing(),
                    TestMessages.NON_ANONYMOUS_FAULT_TO_COMPLETE_MESSAGE,
                    S11_NS,
                    nonAnonAddress,
                    action,
                    endpointAddress,
                    "testNonAnonymousReplyTo");
            fail("MUST throw SOAPFaultException with ONLY_ANONYMOUS_ADDRESS_SUPPORTED fault code");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            assertEquals(W3CAddressingConstants.ONLY_ANONYMOUS_ADDRESS_SUPPORTED, e.getFault().getFaultCodeAsQName());
        }


    }

    /**
     * Fault response case
     *
     * @throws Exception
     */
    public void testNonAnonymousFaultTo2() throws Exception {
        try {
            invoke(createDispatchWithoutAddressing(),
                    TestMessages.NON_ANONYMOUS_FAULT_TO_COMPLETE_FAULTY_MESSAGE,
                    S11_NS,
                    nonAnonAddress,
                    action,
                    endpointAddress,
                    "testNonAnonymousReplyTo");
            fail("MUST throw SOAPFaultException with ONLY_ANONYMOUS_ADDRESS_SUPPORTED fault code");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            assertEquals(W3CAddressingConstants.ONLY_ANONYMOUS_ADDRESS_SUPPORTED, e.getFault().getFaultCodeAsQName());
        }

    }

}
