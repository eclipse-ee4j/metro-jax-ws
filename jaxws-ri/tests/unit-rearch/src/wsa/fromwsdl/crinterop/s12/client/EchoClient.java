/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromwsdl.crinterop.s12.client;

import testutil.W3CAddressingConstants;
import static testutil.WsaSOAPMessages.USER_FAULT_CODE;
import static testutil.WsaUtils.*;
import testutil.XMLTestCase;
import static wsa.fromwsdl.crinterop.s12.client.BindingProviderUtil.*;
import static wsa.fromwsdl.crinterop.s12.common.TestConstants.*;

import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.soap.SOAPFaultException;
import java.io.ByteArrayOutputStream;

/**
 * @author Arun Gupta
 */
public class EchoClient extends XMLTestCase {

    public EchoClient(String name) {
        super(name);
    }

    /**
     * SOAP 1.2 two-way message.
     */
    public void test1230() throws Exception {
        String result = createStub().echo("test1230");
        assertEquals("test1230", result);
    }

    /**
     * SOAP 1.2 two-way message with ReplyTo address of anonymous.
     */
    public void test1231() throws Exception {
        String result = createStub().echo("test1231");
        assertEquals("test1231", result);
    }

    /**
     * SOAP 1.2 two-way message with ReplyTo address containing Reference Parameters.
     */
    public void test1232() throws Exception {
        SOAPMessage response = invoke12(createDispatchWithWSDLWithoutAddressing(),
                                        MESSAGES.getReplyToRefpsEchoMessage(),
                                        S12_NS,
                                        getAddress(),
                                        ECHO_IN_ACTION,
                                        "test1232");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        response.writeTo(baos);
        assertXpathExists(ENVELOPE_HEADER, baos.toString());
        assertXpathExists(ACTION_HEADER, baos.toString());
        assertXpathEvaluatesTo(ACTION_HEADER, baos.toString(), ECHO_OUT_ACTION);
    }

    /**
     * SOAP 1.2 two-way message with fault. ReplyTo and FaultTo addresses containing Reference Parameters.
     */
    public void test1233() throws Exception {
        try {
            invoke12(createDispatchWithWSDLWithoutAddressing(),
                     MESSAGES.getReplyToFaultToRefpsEchoMessage(),
                     S12_NS,
                     getAddress(),
                     ECHO_IN_ACTION,
                     "fault-test1233");
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException e) {
            checkUserFaultCode(e);
        }
    }

    /**
     * SOAP 1.2 two-way message with fault. FaultTo is defaulted, ReplyTo address contains Reference Parameters.
     */
    public void test1234() throws Exception {
        try {
            invoke12(createDispatchWithWSDLWithoutAddressing(),
                     MESSAGES.getReplyToRefpsEchoMessage(),
                     S12_NS,
                     getAddress(),
                     ECHO_IN_ACTION,
                     "fault-test1234");
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException e) {
            checkUserFaultCode(e);
        }
    }

    /**
     * SOAP 1.2 two-way message with fault. FaultTo is anonymous, ReplyTo is non-anonymous.
     */
    public void test1235() throws Exception {
        try {
            invoke12(createDispatchWithWSDLWithoutAddressing(),
                     MESSAGES.getNonAnonymousReplyToAnonymousFaultToMessage(),
                     S12_NS,
                     getAddress(),
                     getNonAnonymousAddress(),
                     ECHO_IN_ACTION,
                     "fault-test1235");
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException e) {
            checkUserFaultCode(e);
        }
    }

    private void checkUserFaultCode(SOAPFaultException e) {
        assertNotNull(e.getFault());
        assertNotNull(e.getFault().getFaultSubcodes());
        assertTrue("No fault subcodes are found", e.getFault().getFaultSubcodes().hasNext());
        assertEquals(USER_FAULT_CODE, e.getFault().getFaultSubcodes().next());
    }

    /**
     * SOAP 1.2 two-way message with a ReplyTo address of none.
     */
    public void test1236() throws Exception {
        try {
            invoke12(createDispatchWithWSDLWithoutAddressing(),
                     MESSAGES.getNoneReplyToEchoMessage(),
                     S12_NS,
                     getAddress(),
                     ECHO_IN_ACTION,
                     "test1236");
            fail("1236: WebServiceException must be thrown");
        } catch (WebServiceException e) {
            assertEquals("No response returned.", e.getMessage());
        }
    }

    /**
     * SOAP 1.2 two-way message with a duplicate To header.
     */
    public void test1240() throws Exception {
        try {
            invoke12(createDispatchWithWSDLWithAddressing(),
                     MESSAGES.getDuplicateToMessage(),
                     S12_NS,
                     getAddress(),
                     getAddress(),
                     "test1240");
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            assertInvalidCardinalityCode12(e.getFault(), W3CAddressingConstants.WSA_NAMESPACE_NAME );
        }
    }

    /**
     * SOAP 1.2 two-way message with a duplicate Reply-To header.
     */
    public void test1241() throws Exception {
        try {
            invoke12(createDispatchWithWSDLWithAddressing(),
                     MESSAGES.getDuplicateReplyToMessage(),
                     S12_NS,
                     "test1241");
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            assertInvalidCardinalityCode12(e.getFault(), W3CAddressingConstants.WSA_NAMESPACE_NAME );
        }
    }

    /**
     * SOAP 1.2 two-way message with a duplicate Fault-To header.
     */
    public void test1242() throws Exception {
        try {
            invoke12(createDispatchWithWSDLWithAddressing(),
                     MESSAGES.getDuplicateFaultToMessage(),
                     S12_NS,
                     "test1242");
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            assertInvalidCardinalityCode12(e.getFault(), W3CAddressingConstants.WSA_NAMESPACE_NAME );
        }
    }

    /**
     * SOAP 1.2 two-way message with a duplicate action header.
     */
    public void test1243() throws Exception {
        try {
            invoke12(createDispatchWithWSDLWithAddressing(),
                     MESSAGES.getDuplicateActionMessage(),
                     S12_NS,getAddress(),ECHO_IN_ACTION,
                     ECHO_IN_ACTION,
                     "test1243");
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            assertInvalidCardinalityCode12(e.getFault(), W3CAddressingConstants.WSA_NAMESPACE_NAME );
        }
    }

    /**
     * SOAP 1.2 two-way message with a duplicate message ID header.
     */
    public void test1244() throws Exception {
        try {
            invoke12(createDispatchWithWSDLWithAddressing(),
                     MESSAGES.getDuplicateMessageIDMessage(),
                     S12_NS,
                     "test1244");
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            assertInvalidCardinalityCode12(e.getFault(), W3CAddressingConstants.WSA_NAMESPACE_NAME);
        }
    }

    /**
     * SOAP 1.2 two-way message with no Action header.
     */
    public void test1248() throws Exception {
        try {
            invoke12(createDispatchWithWSDLWithoutAddressing(),
                   MESSAGES.getNoActionEchoMessage(),
                   S12_NS,
                   getAddress(),
                   "test1248");
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            assertHeaderRequiredFaultCode12(e.getFault());
        }
    }

//    /**
//     * SOAP 1.2 two-way message with a duplicate To header and a ReplyTo of none.
//     */
//    public void test1249() throws Exception {
//        try {
//            invoke12(createDispatchWithWSDLWithoutAddressing(),
//                   MESSAGES.getDuplicateToNoneReplyToMessage(),
//                   S12_NS,
//                   getAddress(),
//                   getAddress(),
//                   ECHO_IN_ACTION,
//                   "test1249");
//            fail("SOAPFaultException must be thrown");
//        } catch (SOAPFaultException e) {
//            assertNotNull(e.getFault());
//            assertHeaderRequiredFaultCode12(e.getFault());
//        }
//    }

    /**
     * SOAP 1.2 two-way message with a non-anonymous ReplyTo address.
     */
    public void test1250() throws Exception {
        try {
            invoke12(createDispatchWithWSDLWithoutAddressing(),
                     MESSAGES.getNonAnonymousReplyToMessage(),
                     S12_NS,
                     getAddress(),
                     getNonAnonymousAddress(),
                     ECHO_IN_ACTION,
                     "test1250");
            fail("1236: WebServiceException must be thrown");
        } catch (WebServiceException e) {
            assertEquals("No response returned.", e.getMessage());
        }
    }

    /**
     * SOAP 1.2 two-way message with a non-anonymous ReplyTo address and a Reply targeted to none.
     */
    public void test1251() throws Exception {
        try {
            invoke12(createDispatchWithWSDLWithoutAddressing(),
                     MESSAGES.getNoneTargetedNonAnonymousReplyToMessage(),
                     S12_NS,
                     getAddress(),
                     getNonAnonymousAddress(),
                     getNonAnonymousAddress(),
                     ECHO_IN_ACTION,
                     "test1251");
            fail("1236: WebServiceException must be thrown");
        } catch (WebServiceException e) {
            assertEquals("No response returned.", e.getMessage());
        }
    }

    /**
     * SOAP 1.2 two-way message with a non-anonymous ReplyTo address and a none FaultTo.
     */
    public void test1252() throws Exception {
        try {
            invoke12(createDispatchWithWSDLWithoutAddressing(),
                     MESSAGES.getNonAnonymousReplyToNoneFaultToMessage(),
                     S12_NS,
                     getAddress(),
                     getNonAnonymousAddress(),
                     ECHO_IN_ACTION,
                     "fault-test1252");
            fail("1236: WebServiceException must be thrown");
        } catch (WebServiceException e) {
            assertEquals("No response returned.", e.getMessage());
        }
    }

    /**
     * SOAP 1.1 two-way message with wsa:From.
     */
    public void test1270() throws Exception {
        SOAPMessage response = invoke12(createDispatchWithWSDLWithoutAddressing(),
               MESSAGES.getFromMustUnderstandEchoMessage(),
               S12_NS,
               getAddress(),
               ECHO_IN_ACTION,
               "test1270");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        response.writeTo(baos);
        assertXpathExists(ENVELOPE_HEADER, baos.toString());
        assertXpathExists(ACTION_HEADER, baos.toString());
        assertXpathEvaluatesTo(ACTION_HEADER, baos.toString(), ECHO_OUT_ACTION);
    }

    private static final String ENVELOPE_HEADER = "//S12:Envelope";
    private static final String ACTION_HEADER = "//S12:Envelope/S12:Header/wsa:Action";
}
