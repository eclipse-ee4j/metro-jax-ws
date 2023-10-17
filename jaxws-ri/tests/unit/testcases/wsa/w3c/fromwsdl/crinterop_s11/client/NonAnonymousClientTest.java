/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.w3c.fromwsdl.crinterop_s11.client;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.Exchanger;
import java.util.Iterator;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPBody;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.Endpoint;
import javax.xml.namespace.QName;

import static testutil.W3CWsaUtils.invoke;
import static testutil.W3CWsaUtils.invokeAsync;
import static testutil.W3CWsaUtils.S11_NS;
import testutil.XMLTestCase;
import static testutil.WsaW3CSOAPMessages.USER_FAULT_CODE;
import static wsa.w3c.fromwsdl.crinterop_s11.client.BindingProviderUtil.*;
import static wsa.w3c.fromwsdl.crinterop_s11.client.TestConstants.*;

/**
 * @author Rama Pulavarthi
 */
public class NonAnonymousClientTest extends XMLTestCase {
    private static Endpoint responseProcessor;
    private static Exchanger<SOAPMessage> respMsgExchanger = new Exchanger<SOAPMessage>();
    private String clientAddress;

    public NonAnonymousClientTest(String name) {
        super(name);
    }

    /**
     * Sets up a Endpoint for listenign to non-anonymous responses,
     * which uses the Exchanger to pass the request message.
     * This in unncessaery for anonymous tests.
     *
     * @throws Exception
     */
    protected void setUp() throws Exception {
        super.setUp();
        clientAddress = getNonAnonymousClientAddress();
        responseProcessor = Endpoint.create(new NonAnonymousRespProcessor(respMsgExchanger));
        responseProcessor.publish(clientAddress);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        if (responseProcessor != null)
            responseProcessor.stop();
    }

    /**
     * SOAP 1.1 two-way message with a non-anonymous ReplyTo address.
     */
    public void Xtest1150() throws Exception {
        //requires fix in harness to work on SE 21+
        invokeAsync(createDispatchForNonAnonymousWithWSDLWithoutAddressing(),
                MESSAGES.getNonAnonymousReplyToMessage(),
                S11_NS,
                getNonAnonymousEndpointAddress(),
                clientAddress,
                ECHO_IN_ACTION,
                "test1150");
        //Lets see we get a response in 60 s
        SOAPMessage m = respMsgExchanger.exchange(null, TestConstants.CLIENT_MAX_TIMEOUT, TimeUnit.SECONDS);
//        System.out.println("****************************");
//        m.writeTo(System.out);
//        System.out.println("\n****************************");
        SOAPBody sb = m.getSOAPBody();
        Iterator itr = sb.getChildElements(new QName("http://example.org/echo","echoOut"));
        assertTrue(itr.hasNext());
    }

    /**
     * SOAP 1.1 two-way message with a non-anonymous ReplyTo address and a none FaultTo.
     */
    public void test1152() throws Exception {
        try {
            invoke(createDispatchForNonAnonymousWithWSDLWithoutAddressing(),
                    MESSAGES.getNonAnonymousReplyToNoneFaultToMessage(),
                    S11_NS,
                    getNonAnonymousEndpointAddress(),
                    clientAddress,
                    ECHO_IN_ACTION,
                    "fault-test1152");
            fail("WebServiceException must be thrown");
        } catch (WebServiceException e) {
            assertEquals("No response returned.", e.getMessage());
        }
    }

    /**
     * SOAP 1.1 two-way message with a non-anonymous ReplyTo and FaultTo address.
     */
    public void Xtest1194() throws Exception {
        //requires fix in harness to work on SE 21+
        invokeAsync(createDispatchForNonAnonymousWithWSDLWithoutAddressing(),
                    MESSAGES.getNonAnonymousReplyToMessage(),
                    S11_NS,
                    getNonAnonymousEndpointAddress(),
                    clientAddress,
                    ECHO_IN_ACTION,
                    "fault-test1194");
        //Lets see we get a response in 60 s
        SOAPMessage m = respMsgExchanger.exchange(null, TestConstants.CLIENT_MAX_TIMEOUT, TimeUnit.SECONDS);
//        System.out.println("****************************");
//        m.writeTo(System.out);
//        System.out.println("\n****************************");
        SOAPFault f = m.getSOAPBody().getFault();
        assertNotNull(f);
        assertEquals(USER_FAULT_CODE, f.getFaultCodeAsQName());

    }

}
