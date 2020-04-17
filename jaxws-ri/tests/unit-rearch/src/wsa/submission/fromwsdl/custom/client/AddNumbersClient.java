/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.submission.fromwsdl.custom.client;

import com.sun.xml.ws.developer.MemberSubmissionAddressingFeature;
import org.custommonkey.xmlunit.XMLTestCase;
import testutil.ClientServerTestUtil;
import testutil.MemberSubmissionAddressingConstants;
import testutil.WsaUtils;

import javax.xml.namespace.QName;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.soap.SOAPBinding;
import jakarta.xml.ws.soap.SOAPFaultException;

/**
 * @author Arun Gupta
 */
public class AddNumbersClient extends XMLTestCase {
    private static final QName SERVICE_QNAME = new QName("http://example.com/", "AddNumbersService");
    private static final QName PORT_QNAME = new QName("http://example.com/", "AddNumbersPort");
    private static final String ENDPOINT_ADDRESS = "http://localhost:8080/jaxrpc-wsa_submission_fromwsdl_custom/hello";
    private static final String CORRECT_ACTION = "http://example.com/AddNumbersPortType/addNumbersRequest";
    private static final MemberSubmissionAddressingFeature ENABLED_ADDRESSING_FEATURE = new MemberSubmissionAddressingFeature(true);
    private static final MemberSubmissionAddressingFeature DISABLED_ADDRESSING_FEATURE = new MemberSubmissionAddressingFeature(false);

    public AddNumbersClient(String name) {
        super(name);
    }

    private String getAddress() {
        if(ClientServerTestUtil.useLocal())
            return ClientServerTestUtil.getLocalAddress(PORT_QNAME);
        else
            return ENDPOINT_ADDRESS;
    }

    private Dispatch<SOAPMessage> createDispatchWithoutWSDL() throws Exception {
        Service service = Service.create(SERVICE_QNAME);
        service.addPort(PORT_QNAME, SOAPBinding.SOAP11HTTP_BINDING, getAddress());
        return service.createDispatch(PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE, DISABLED_ADDRESSING_FEATURE);
    }

    private Dispatch<SOAPMessage> createDispatchWithWSDL() throws Exception {
        AddNumbersService service = new AddNumbersService();
        return service.createDispatch(PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE, ENABLED_ADDRESSING_FEATURE);
    }

    public void testBadAction() throws Exception {
        try {
            WsaUtils.invoke(createDispatchWithoutWSDL(), WsaUtils.BAD_ACTION_MESSAGE, WsaUtils.S11_NS, WsaUtils.MS_WSA_NS, getAddress(), MemberSubmissionAddressingConstants.WSA_ANONYMOUS_ADDRESS);
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException sfe) {
            assertFault(sfe, MemberSubmissionAddressingConstants.ACTION_NOT_SUPPORTED_QNAME);
        }
    }

    public void testMissingAction() throws Exception {
        try {
            WsaUtils.invoke(createDispatchWithoutWSDL(), WsaUtils.MISSING_ACTION_MESSAGE, WsaUtils.S11_NS, WsaUtils.MS_WSA_NS, getAddress(), MemberSubmissionAddressingConstants.WSA_ANONYMOUS_ADDRESS);
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException sfe) {
            assertFault(sfe, MemberSubmissionAddressingConstants.MAP_REQUIRED_QNAME);
        }
    }

    public void testReplyToRefps() throws Exception {
        WsaUtils.invoke(createDispatchWithoutWSDL(), WsaUtils.REPLY_TO_REFPS_MESSAGE, WsaUtils.S11_NS, WsaUtils.MS_WSA_NS, getAddress(), MemberSubmissionAddressingConstants.WSA_ANONYMOUS_ADDRESS, CORRECT_ACTION);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        response.writeTo(baos);
//        assertXpathExists(REPLY_TO_REFPS, baos.toString());
//        assertXpathEvaluatesTo("Key#123456789", REPLY_TO_REFPS_VALUE, baos.toString());
//        assertXpathExists(REPLY_TO_REFPS_ISREFP, baos.toString());
    }

    public void testFaultToRefps() throws Exception {
        try {
            WsaUtils.invoke(createDispatchWithoutWSDL(), WsaUtils.FAULT_TO_REFPS_MESSAGE, WsaUtils.S11_NS, WsaUtils.MS_WSA_NS, getAddress(), MemberSubmissionAddressingConstants.WSA_ANONYMOUS_ADDRESS, MemberSubmissionAddressingConstants.WSA_ANONYMOUS_ADDRESS, CORRECT_ACTION);
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException sfe) {
            assertTrue("Got SOAPFaultException", true);
        }
    }

    public void testDuplicateToHeader() throws Exception {
        try {
            WsaUtils.invoke(createDispatchWithWSDL(),
                    WsaUtils.DUPLICATE_TO_MESSAGE,
                    WsaUtils.S11_NS,
                    WsaUtils.MS_WSA_NS,
                    getAddress(),
                    getAddress());
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException sfe) {
            assertFault(sfe, MemberSubmissionAddressingConstants.INVALID_MAP_QNAME);
        }
    }

    public void testDuplicateReplyToHeader() throws Exception {
        try {
            WsaUtils.invoke(createDispatchWithWSDL(),
                    WsaUtils.DUPLICATE_REPLY_TO_MESSAGE,
                    WsaUtils.S11_NS,
                    WsaUtils.MS_WSA_NS,
                    MemberSubmissionAddressingConstants.WSA_ANONYMOUS_ADDRESS,
                    MemberSubmissionAddressingConstants.WSA_ANONYMOUS_ADDRESS);
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException sfe) {
            assertFault(sfe, MemberSubmissionAddressingConstants.INVALID_MAP_QNAME);
        }
    }


    public void testDuplicateFaultToHeader() throws Exception {
        try {
            WsaUtils.invoke(createDispatchWithWSDL(), WsaUtils.DUPLICATE_FAULT_TO_MESSAGE, WsaUtils.S11_NS, WsaUtils.MS_WSA_NS, MemberSubmissionAddressingConstants.WSA_ANONYMOUS_ADDRESS, MemberSubmissionAddressingConstants.WSA_ANONYMOUS_ADDRESS);
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException sfe) {
            assertFault(sfe, MemberSubmissionAddressingConstants.INVALID_MAP_QNAME);
        }
    }

    public void testDuplicateActionHeader() throws Exception {
        try {
            WsaUtils.invoke(createDispatchWithWSDL(), WsaUtils.DUPLICATE_ACTION_MESSAGE, WsaUtils.S11_NS, WsaUtils.MS_WSA_NS, getAddress(),CORRECT_ACTION, CORRECT_ACTION);
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException sfe) {
            assertFault(sfe, MemberSubmissionAddressingConstants.INVALID_MAP_QNAME);
        }
    }

    public void testDuplicateMessageIDHeader() throws Exception {
        try {
            WsaUtils.invoke(createDispatchWithWSDL(), WsaUtils.DUPLICATE_MESSAGE_ID_MESSAGE, WsaUtils.S11_NS, WsaUtils.MS_WSA_NS, getAddress());
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException sfe) {
            assertFault(sfe, MemberSubmissionAddressingConstants.INVALID_MAP_QNAME);
        }
    }

    public void testDuplicateMessageIDHeaderOneway() throws Exception {
        WsaUtils.invokeOneWay(createDispatchWithWSDL(), WsaUtils.DUPLICATE_MESSAGE_ID_MESSAGE_ONEWAY, WsaUtils.S11_NS, WsaUtils.W3C_WSA_NS, getAddress());
    }

    private void assertFault(SOAPFaultException sfe, QName expected) {
        assertNotNull("SOAPFaultException is null", sfe);
        assertNotNull("SOAPFault is null", sfe.getFault());
        assertEquals(expected, sfe.getFault().getFaultCodeAsQName());
    }

    private static final String REPLY_TO_REFPS =
            "//[namespace-uri()='http://schemas.xmlsoap.org/soap/envelope/' and local-name()='Envelope']/" +
            "[namespace-uri()='http://schemas.xmlsoap.org/soap/envelope/' and local-name()='Header']/" +
            "[namespace-uri()='http://example.org/customer' and local-name()='CustomerKey']";
    private static final String REPLY_TO_REFPS_VALUE =
            "string(//[namespace-uri()='http://schemas.xmlsoap.org/soap/envelope/' and local-name()='Envelope']/" +
            "[namespace-uri()='http://schemas.xmlsoap.org/soap/envelope/' and local-name()='Header']/" +
            "[namespace-uri()='http://example.org/customer' and local-name()='CustomerKey'])";
    private static final String REPLY_TO_REFPS_ISREFP =
            "//[namespace-uri()='http://schemas.xmlsoap.org/soap/envelope/' and local-name()='Envelope']/" +
            "[namespace-uri()='http://schemas.xmlsoap.org/soap/envelope/' and local-name()='Header']/" +
            "[namespace-uri()='http://example.org/customer' and local-name()='CustomerKey']";
}
