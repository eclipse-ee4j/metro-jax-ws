/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
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

import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPFault;
import jakarta.xml.ws.*;
import jakarta.xml.ws.soap.AddressingFeature;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;


/**
 * @author Rama Pulavarthi
 */
public class DefaultResponsesTest extends TestCase {
    private static Endpoint responseProcessor;
    private static Exchanger<SOAPMessage> respMsgExchanger = new Exchanger<SOAPMessage>();
    
    private static QName PORT = new QName("http://server.responses.wsa.fromjava/", "DefaultResponsesEndpoint");
    private static final String action = "http://server.responses.wsa.fromjava/DefaultResponsesEndpoint/addNumbersRequest";
    private static final String endpointAddress = System.getProperty("defaultResponsesEndpointAddress");
    private static String nonAnonAddress;
    public static final Dispatch<SOAPMessage> createDispatchWithoutAddressing() {
        return new DefaultResponsesEndpointService().createDispatch(PORT, SOAPMessage.class, Service.Mode.MESSAGE, new AddressingFeature(false));
    }

    public static String getNonAnonymousClientAddress() {
        return "http://localhost:"+ PortAllocator.getFreePort()+ "/DefaultResponsesTest/nonanoymous";
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
     * @throws Exception
     */
    public void testAnonymousReplyTo1() throws Exception {
        DefaultResponsesEndpoint client =new DefaultResponsesEndpointService().getDefaultResponsesEndpoint();
        assertEquals(20, client.addNumbers(10,10));
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
     * @throws Exception
     */
    public void testNonAnonymousFaultTo1() throws Exception {
        SOAPMessage response = invoke(createDispatchWithoutAddressing(),
                TestMessages.NON_ANONYMOUS_FAULT_TO_COMPLETE_MESSAGE,
                S11_NS,
                nonAnonAddress,
                action,
                endpointAddress,
                "testNonAnonymousReplyTo");
        SOAPBody sb = response.getSOAPBody();
        Iterator itr = sb.getChildElements(new QName("http://server.responses.wsa.fromjava/", "addNumbersResponse"));
        assertTrue(itr.hasNext());
    }

    /**
     * Fault response case
     * @throws Exception
     */
    public void testNonAnonymousFaultTo2() throws Exception {
        invokeAsync(createDispatchWithoutAddressing(),
                TestMessages.NON_ANONYMOUS_FAULT_TO_COMPLETE_FAULTY_MESSAGE,
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
        assertEquals(fault.getFaultString(),"Negative numbers can't be added!");

    }

}
