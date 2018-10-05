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
public class NonAnonymousResponsesTest extends TestCase {
    private static Endpoint responseProcessor;
    private static Exchanger<SOAPMessage> respMsgExchanger = new Exchanger<SOAPMessage>();

    private static QName PORT = new QName("http://server.responses.wsa.fromjava/", "NonAnonymousResponsesEndpoint");
    private static final String action = "http://server.responses.wsa.fromjava/NonAnonymousResponsesEndpoint/addNumbersRequest";
    private static final String endpointAddress = System.getProperty("nonAnonymousResponsesEndpointAddress");
    private static String nonAnonAddress;

    public static final Dispatch<SOAPMessage> createDispatchWithoutAddressing() {
        return new NonAnonymousResponsesEndpointService().createDispatch(PORT, SOAPMessage.class, Service.Mode.MESSAGE, new AddressingFeature(false));
    }

    public static String getNonAnonymousClientAddress() {
        return "http://localhost:" + PortAllocator.getFreePort() + "/NonAnonymousResponsesTest/nonanoymous";
    }

    /**
     * Sets up a Endpoint for listenign to non-anonymous responses,
     * which uses the Exchanger to pass the request message.
     * This in unnecessary for anonymous tests.
     *
     * @throws Exception
     */
    protected void setUp() throws Exception {
        super.setUp();
        nonAnonAddress = getNonAnonymousClientAddress();
        responseProcessor = Endpoint.create(new NonAnonymousRespProcessor(respMsgExchanger));
        responseProcessor.publish(nonAnonAddress);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        // Sleep to give endpoint some time to process the non-anonymous request
        // this is needed in case the object is exchanged between client and non-anonymous response endpoint,
        // which leads to test tearDown(), yet the ack to non-anonymous response is not sent back to server yet.
        Thread.sleep(5000);
        if (responseProcessor != null)
            responseProcessor.stop();
    }

    /**
     * SEI based invocation
     *
     * @throws Exception
     */
    public void testAnonymousReplyTo1() throws Exception {
        try {
            NonAnonymousResponsesEndpoint client = new NonAnonymousResponsesEndpointService().getNonAnonymousResponsesEndpoint();
            assertEquals(20, client.addNumbers(10, 10));
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            assertEquals(W3CAddressingConstants.ONLY_NON_ANONYMOUS_ADDRESS_SUPPORTED,
                    e.getFault().getFaultCodeAsQName());
        }
    }

    /**
     * Using Dispatch
     */
    public void testAnonymousReplyTo2() throws Exception {
        try {
            WsaUtils.invoke(createDispatchWithoutAddressing(),
                    TestMessages.ANONYMOUS_REPLY_TO_COMPLETE_MESSAGE,
                    WsaUtils.S11_NS,
                    action,
                    endpointAddress,
                    "testAnonymousReplyTo");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            assertEquals(W3CAddressingConstants.ONLY_NON_ANONYMOUS_ADDRESS_SUPPORTED,
                    e.getFault().getFaultCodeAsQName());
        }

    }

    public void testNonAnonymousReplyTo() throws Exception {
        invokeAsync(createDispatchWithoutAddressing(),
                TestMessages.NON_ANONYMOUS_REPLY_TO_COMPLETE_MESSAGE,
                S11_NS,
                nonAnonAddress,
                action,
                endpointAddress,
                "testNonAnonymousReplyTo");

        //Lets see we get a response in 60 s
        SOAPMessage m = respMsgExchanger.exchange(null, TestMessages.CLIENT_MAX_TIMEOUT, TimeUnit.SECONDS);
        // System.out.println("****************************");
        // m.writeTo(System.out);
        // System.out.println("\n****************************");
        SOAPBody sb = m.getSOAPBody();
        Iterator itr = sb.getChildElements(new QName("http://server.responses.wsa.fromjava/", "addNumbersResponse"));
        assertTrue(itr.hasNext());

    }

    /**
     * Normal response case,just tests if the endpoint accepts non-anon FaultTo
     *
     * @throws Exception
     */
    public void testNonAnonymousFaultTo1() throws Exception {

        invokeAsync(createDispatchWithoutAddressing(),
                TestMessages.NON_ANONYMOUS_FAULT_TO_COMPLETE_MESSAGE,
                S11_NS,
                nonAnonAddress,
                action,
                endpointAddress,
                "testNonAnonymousReplyTo");
        //Lets see we get a response in 60 s
        SOAPMessage m = respMsgExchanger.exchange(null, TestMessages.CLIENT_MAX_TIMEOUT, TimeUnit.SECONDS);
        // System.out.println("****************************");
        // m.writeTo(System.out);
        // System.out.println("\n****************************");
        SOAPBody sb = m.getSOAPBody();
        assertTrue(sb.hasFault());
        SOAPFault fault = sb.getFault();
        assertEquals(W3CAddressingConstants.ONLY_NON_ANONYMOUS_ADDRESS_SUPPORTED,fault.getFaultCodeAsQName());
    }

    /**
     * Fault response case
     *
     * @throws Exception
     */
    public void testNonAnonymousFaultTo2() throws Exception {
        invokeAsync(createDispatchWithoutAddressing(),
                TestMessages.NON_ANONYMOUS_REPLY_TO_NON_ANONYMOUS_FAULT_TO_COMPLETE_FAULTY_MESSAGE,
                S11_NS,
                nonAnonAddress,
                nonAnonAddress,
                action,
                endpointAddress,
                "testNonAnonymousReplyTo");
        //Lets see we get a response in 60 s
        SOAPMessage m = respMsgExchanger.exchange(null, TestMessages.CLIENT_MAX_TIMEOUT, TimeUnit.SECONDS);
        // System.out.println("****************************");
        // m.writeTo(System.out);
        // System.out.println("\n****************************");
        SOAPBody sb = m.getSOAPBody();
        assertTrue(sb.hasFault());
        SOAPFault fault = sb.getFault();
        assertEquals(fault.getFaultString(), "Negative numbers can't be added!");

    }

}
