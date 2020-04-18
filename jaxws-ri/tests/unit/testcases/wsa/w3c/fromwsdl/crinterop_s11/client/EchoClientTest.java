/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.w3c.fromwsdl.crinterop_s11.client;

import java.io.ByteArrayOutputStream;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.SOAPFault;
import jakarta.xml.ws.soap.SOAPFaultException;
import jakarta.xml.ws.WebServiceException;
import javax.xml.namespace.QName;
import static testutil.W3CWsaUtils.*;
import static testutil.WsaW3CSOAPMessages.USER_FAULT_CODE;
import testutil.XMLTestCase;
import static wsa.w3c.fromwsdl.crinterop_s11.client.BindingProviderUtil.*;
import static wsa.w3c.fromwsdl.crinterop_s11.client.TestConstants.*;

public class EchoClientTest extends XMLTestCase {


    public EchoClientTest(String name) {
        super(name);
    }


    /**
     * SOAP 1.1 two-way message.
     */
    public void test1130() throws Exception {
            
        String result = createStub().echo("test1130");
        assertEquals("test1130", result);
    }

    /**
     * SOAP 1.1 two-way message with ReplyTo address of anonymous.
     */
    public void test1131() throws Exception {
        String result = createStub().echo("test1131");
        assertEquals("test1131", result);
    }

    /**
     * SOAP 1.1 two-way message with ReplyTo address containing Reference Parameters.
     */
    public void test1132() throws Exception {
        SOAPMessage response = invoke(createDispatchWithWSDLWithoutAddressing(),
                                      MESSAGES.getReplyToRefpsEchoMessage(),
                                      S11_NS,
                                      getAddress(),
                                      ECHO_IN_ACTION,
                                      "test1132");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        response.writeTo(baos);
        assertXpathExists(ENVELOPE_HEADER, baos.toString());
        assertXpathExists(ACTION_HEADER, baos.toString());
        assertXpathEvaluatesTo(ACTION_HEADER, baos.toString(), ECHO_OUT_ACTION);
    }

    /**
     * SOAP 1.2 two-way message with fault. ReplyTo and FaultTo addresses containing Reference Parameters.
     */
    public void test1133() throws Exception {
        try {
            invoke(createDispatchWithWSDLWithoutAddressing(),
                   MESSAGES.getReplyToFaultToRefpsEchoMessage(),
                   S11_NS,
                   getAddress(),
                   ECHO_IN_ACTION,
                   "fault-test1133");
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            assertEquals(USER_FAULT_CODE, e.getFault().getFaultCodeAsQName());
        }
    }

    /**
     * SOAP 1.1 two-way message with fault. FaultTo is defaulted, ReplyTo address contains Reference Parameters.
     */
    public void test1134() throws Exception {
        try {
            invoke(createDispatchWithWSDLWithoutAddressing(),
                   MESSAGES.getReplyToRefpsEchoMessage(),
                   S11_NS,
                   getAddress(),
                   ECHO_IN_ACTION,
                   "fault-test1134");
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            assertEquals(USER_FAULT_CODE, e.getFault().getFaultCodeAsQName());
        }
    }

    /**
     * SOAP 1.1 two-way message with fault. FaultTo is anonymous, ReplyTo is non-anonymous.
     */
    public void test1135() throws Exception {
        try {
            invoke(createDispatchWithWSDLWithoutAddressing(),
                   MESSAGES.getNonAnonymousReplyToAnonymousFaultToMessage(),
                   S11_NS,
                   getAddress(),
                   getNonAnonymousClientAddress(),
                   ECHO_IN_ACTION,
                   "fault-test1135");
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException  e) {
			assertNotNull(e.getFault());
            assertEquals(USER_FAULT_CODE, e.getFault().getFaultCodeAsQName());
        }
    }

    /**
     * SOAP 1.2 two-way message with a ReplyTo address of none.
     */
    public void test1136() throws Exception {
        try {
            invoke(createDispatchWithWSDLWithoutAddressing(),
                     MESSAGES.getNoneReplyToEchoMessage(),
                     S11_NS,
                     getAddress(),
                     ECHO_IN_ACTION,
                     "test1136");
            fail("WebServiceException must be thrown");
        } catch (WebServiceException e) {
            assertTrue(true);
            //assertEquals("No response returned.", e.getMessage());
        }
    }

    /**
     * SOAP 1.1 two-way message with a duplicate To header.
     */
    public void test1140() throws Exception {
        try {
            invoke(createDispatchWithWSDLWithAddressing(),
                   MESSAGES.getDuplicateToMessage(),
                   S11_NS,
                   getAddress(),
                   getAddress(),
                   "test1140");
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            SOAPFault f = e.getFault();
            assertInvalidHeaderFaultCode(f.getFaultCodeAsQName(), ADDRESSING_VERSION);
        }
    }

    /**
     * SOAP 1.1 two-way message with a duplicate Reply-To header.
     */
    public void test1141() throws Exception {
        try {
            invoke(createDispatchWithWSDLWithAddressing(),
                   MESSAGES.getDuplicateReplyToMessage(),
                   S11_NS,
                   getAddress(),
                   "test1141");
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            SOAPFault f = e.getFault();
            assertInvalidHeaderFaultCode(f.getFaultCodeAsQName(), ADDRESSING_VERSION);
        }
    }

    /**
     * SOAP 1.1 two-way message with a duplicate Fault-To header.
     */
    public void test1142() throws Exception {
        try {
            invoke(createDispatchWithWSDLWithAddressing(),
                   MESSAGES.getDuplicateFaultToMessage(),
                   S11_NS,
                   "test1142");
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            SOAPFault f = e.getFault();
            assertInvalidHeaderFaultCode(f.getFaultCodeAsQName(), ADDRESSING_VERSION);
        }
    }

    /**
     * SOAP 1.1 two-way message with a duplicate action header.
     */
    public void test1143() throws Exception {
        try {
            invoke(createDispatchWithWSDLWithoutAddressing(),
                   MESSAGES.getDuplicateActionMessage(),
                   S11_NS,getAddress(),ECHO_IN_ACTION,
                   ECHO_IN_ACTION,
                   "test1143");
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            SOAPFault f = e.getFault();
            assertInvalidHeaderFaultCode(f.getFaultCodeAsQName(), ADDRESSING_VERSION);
        }
    }

    /**
     * SOAP 1.1 two-way message with a duplicate MessageID header.
     */
    public void test1144() throws Exception {
        try {
            invoke(createDispatchWithWSDLWithAddressing(),
                   MESSAGES.getDuplicateMessageIDMessage(),
                   S11_NS,
                   getAddress(),
                   "test1144");
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            SOAPFault f = e.getFault();
            assertInvalidHeaderFaultCode(f.getFaultCodeAsQName(), ADDRESSING_VERSION);
        }
    }

    /**
     * SOAP 1.1 two-way message with no Action header.
     */
    public void test1147() throws Exception {
        try {
            invoke(createDispatchWithWSDLWithoutAddressing(),
                   MESSAGES.getNoActionEchoMessage(),
                   S11_NS,
                   getAddress(),
                   "test1147");
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            SOAPFault f = e.getFault();
            assertHeaderRequiredFaultCode(f.getFaultCodeAsQName());
        }
    }

    /**
     * SOAP 1.1 two-way message with no wsa:MessageId header.
     */
    public void test1191() throws Exception {
        try {
            invoke(createDispatchWithWSDLWithoutAddressing(),
                   MESSAGES.getNoMessageIdEchoMessage(),
                   S11_NS,
                   getAddress(),
                    ECHO_IN_ACTION,
                   "test1191");
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            SOAPFault f = e.getFault();
            assertHeaderRequiredFaultCode(f.getFaultCodeAsQName());
        }
    }

    public void test1193() throws Exception {
        try {
            SOAPMessage response = invoke12(createDispatchWithWSDLWithoutAddressing(),
                                        MESSAGES.getReplyToRefpsEchoMessage(),
                                        S12_NS,
                                        getAddress(),
                                        ECHO_IN_ACTION,
                                        "test1193");
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            SOAPFault f = e.getFault();
            assertEquals(new QName("http://schemas.xmlsoap.org/soap/envelope/","VersionMismatch"), f.getFaultCodeAsQName());

        }
    }

    /**
     * SOAP 1.1 two-way message with wsa:From.
     */
    public void test1170() throws Exception {
        SOAPMessage response = invoke(createDispatchWithWSDLWithoutAddressing(),
               MESSAGES.getFromMustUnderstandEchoMessage(),
               S11_NS,
               getAddress(),
               ECHO_IN_ACTION,
               "test1170");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        response.writeTo(baos);
        assertXpathExists(ENVELOPE_HEADER, baos.toString());
        assertXpathExists(ACTION_HEADER, baos.toString());
        assertXpathEvaluatesTo(ACTION_HEADER, baos.toString(), ECHO_OUT_ACTION);
    }

    private static final String ENVELOPE_HEADER = "//S11:Envelope";
    private static final String ACTION_HEADER = "//S11:Envelope/S11:Header/wsa:Action";

    }
