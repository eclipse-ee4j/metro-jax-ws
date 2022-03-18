/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromwsdl_api.binding.wsa_required_true.client;

import com.sun.xml.ws.addressing.W3CAddressingConstants;
import com.sun.xml.ws.developer.MemberSubmissionAddressingFeature;
import com.sun.xml.ws.api.addressing.AddressingVersion;
import com.sun.xml.ws.binding.BindingImpl;
import testutil.ClientServerTestUtil;
import testutil.WsaUtils;
import testutil.XMLTestCase;

import javax.xml.namespace.QName;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceFeature;
import jakarta.xml.ws.RespectBindingFeature;
import jakarta.xml.ws.soap.SOAPBinding;
import jakarta.xml.ws.soap.SOAPFaultException;
import jakarta.xml.ws.soap.AddressingFeature;
import java.io.ByteArrayOutputStream;


/**
 * @author Arun Gupta
 */
public class AddNumbersClient extends XMLTestCase {
    private static final QName SERVICE_QNAME = new QName("http://example.com/", "AddNumbersService");
    private static final QName PORT_QNAME = new QName("http://example.com/", "AddNumbersPort");
    private static final String ENDPOINT_ADDRESS = "http://localhost:8080/jaxrpc-wsa_fromwsdl_api_binding_wsa_required_true/hello";
    private static final String CORRECT_ACTION = "http://example.com/AddNumbersPortType/addNumbersRequest";

    public AddNumbersClient(String name) {
        super(name);
    }

    private String getAddress() {
        if (ClientServerTestUtil.useLocal())
            return ClientServerTestUtil.getLocalAddress(PORT_QNAME);
        else
            return ENDPOINT_ADDRESS;
    }

    private Dispatch<SOAPMessage> createDispatchWithoutWSDL() throws Exception {
        Service service = Service.create(SERVICE_QNAME);
        service.addPort(PORT_QNAME, SOAPBinding.SOAP11HTTP_BINDING, getAddress());

//        MemberSubmissionAddressingFeature msAddressingFeature = new MemberSubmissionAddressingFeature(true);
//        WebServiceFeature[] features = new WebServiceFeature[] {msAddressingFeature};
WebServiceFeature[] features = null;

        Dispatch<SOAPMessage> dispatch = service.createDispatch(PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE, features);
        return dispatch;
    }

    private Dispatch<SOAPMessage> createDispatchWithWSDL() throws Exception {

        MemberSubmissionAddressingFeature msAddressingFeature = new MemberSubmissionAddressingFeature(true);
        RespectBindingFeature respectBindingFeature = new RespectBindingFeature(true);
        WebServiceFeature[] features = new WebServiceFeature[] {msAddressingFeature, respectBindingFeature};
        AddNumbersService service = new AddNumbersService();

        return service.createDispatch(PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE );
    }

    private Dispatch<SOAPMessage> createDispatchWithWSDL(WebServiceFeature[] wsFeatures) throws Exception {
        AddNumbersService service = new AddNumbersService();
        return service.createDispatch(PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE, wsFeatures);
    }

    public void testBadAction() throws Exception {
        try {
            WsaUtils.invoke(createDispatchWithoutWSDL(), WsaUtils.BAD_ACTION_MESSAGE, WsaUtils.S11_NS, WsaUtils.W3C_WSA_NS, getAddress(), W3CAddressingConstants.WSA_ANONYMOUS_ADDRESS);
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException sfe) {
            assertFault(sfe, W3CAddressingConstants.ACTION_NOT_SUPPORTED_QNAME);
        }
    }

    public void testMissingAction() throws Exception {
        try {
            WsaUtils.invoke(createDispatchWithoutWSDL(), WsaUtils.MISSING_ACTION_MESSAGE, WsaUtils.S11_NS, WsaUtils.W3C_WSA_NS, getAddress(), W3CAddressingConstants.WSA_ANONYMOUS_ADDRESS);
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException sfe) {
            assertFault(sfe, W3CAddressingConstants.MAP_REQUIRED_QNAME);
        }
    }


    public void testFaultToRefps() throws Exception {
        try {
            WsaUtils.invoke(createDispatchWithoutWSDL(), WsaUtils.FAULT_TO_REFPS_MESSAGE, WsaUtils.S11_NS, WsaUtils.W3C_WSA_NS, getAddress(), W3CAddressingConstants.WSA_ANONYMOUS_ADDRESS, W3CAddressingConstants.WSA_ANONYMOUS_ADDRESS, CORRECT_ACTION);
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException sfe) {
            assertTrue("Got SOAPFaultException", true);
        }
    }

    public void testBadActionWithWSDL() throws Exception {
        try {
            RespectBindingFeature respectBindingFeature = new RespectBindingFeature(false);
            WebServiceFeature[] features = new WebServiceFeature[]{respectBindingFeature};
            Dispatch dispatch = createDispatchWithWSDL(features);
            BindingImpl binding = (BindingImpl)dispatch.getBinding();
            assertTrue(AddressingVersion.isEnabled(binding));

            WsaUtils.invoke(dispatch, WsaUtils.BAD_ACTION_MESSAGE, WsaUtils.S11_NS, WsaUtils.W3C_WSA_NS, getAddress(), W3CAddressingConstants.WSA_ANONYMOUS_ADDRESS);
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException sfe) {
            assertFault(sfe, W3CAddressingConstants.ACTION_NOT_SUPPORTED_QNAME);
        }
    }


    public void testMissingActionWithWSDL() throws Exception {
        try {

            WebServiceFeature[] features = new WebServiceFeature[]{new AddressingFeature(false)};
            Dispatch dispatch = createDispatchWithWSDL(features);
            BindingImpl binding = (BindingImpl)dispatch.getBinding();
            AddressingVersion addressingVersion = AddressingVersion.fromBinding(binding);
            System.out.println("Addressing version is " + addressingVersion);
            assertFalse(AddressingVersion.isEnabled(binding));

            WsaUtils.invoke(dispatch, WsaUtils.MISSING_ACTION_MESSAGE, WsaUtils.S11_NS, WsaUtils.W3C_WSA_NS, getAddress(), W3CAddressingConstants.WSA_ANONYMOUS_ADDRESS);
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException sfe) {
            assertFault(sfe, W3CAddressingConstants.MAP_REQUIRED_QNAME);
        }
    }

    public void testReplyToRefpsWithWSDL() throws Exception {
        MemberSubmissionAddressingFeature addressingFeature = new MemberSubmissionAddressingFeature(false);

        WebServiceFeature[] features = new WebServiceFeature[]{addressingFeature};
        Dispatch dispatch = createDispatchWithWSDL(features);
        BindingImpl binding = (BindingImpl)dispatch.getBinding();
        boolean enabled = AddressingVersion.isEnabled(binding);
        assertTrue(enabled);

        SOAPMessage response = WsaUtils.invoke(dispatch, WsaUtils.REPLY_TO_REFPS_MESSAGE, WsaUtils.S11_NS, WsaUtils.W3C_WSA_NS, getAddress(), W3CAddressingConstants.WSA_ANONYMOUS_ADDRESS, CORRECT_ACTION);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        response.writeTo(baos);
        assertXpathExists(REPLY_TO_REFPS, baos.toString());
        assertXpathEvaluatesTo(REPLY_TO_REFPS_VALUE, baos.toString(),"Key#123456789");
        assertXpathExists(REPLY_TO_REFPS_ISREFP, baos.toString());
    }

    //bug RespectBindingFeature will turn off enabled features in the wsdl
    //but not features that have been explicitly set by the client
    public void testFaultToRefpsWithWSDL() throws Exception {
        try {
            MemberSubmissionAddressingFeature addressingFeature = new MemberSubmissionAddressingFeature(true);
            RespectBindingFeature bindingFeature = new RespectBindingFeature(false);

            WebServiceFeature[] features = new WebServiceFeature[]{addressingFeature, bindingFeature};

            Dispatch dispatch = createDispatchWithWSDL(features);
            BindingImpl binding = (BindingImpl)dispatch.getBinding();
            boolean enabled = AddressingVersion.isEnabled(binding);
            if (enabled){
                System.out.println("Addressing is Enabled");
            } else {
                System.out.println("Addressing is disabled");
            }

            assertTrue (enabled == true);

            WsaUtils.invoke(dispatch, WsaUtils.FAULT_TO_REFPS_MESSAGE, WsaUtils.S11_NS, WsaUtils.W3C_WSA_NS, getAddress(), W3CAddressingConstants.WSA_ANONYMOUS_ADDRESS, W3CAddressingConstants.WSA_ANONYMOUS_ADDRESS, CORRECT_ACTION);
            fail("SOAPFaultException must be thrown");
        } catch (SOAPFaultException sfe) {
            assertTrue("Got SOAPFaultException", true);
        }
    }



    private void assertFault(SOAPFaultException sfe, QName expected) {
        assertNotNull("SOAPFaultException is null", sfe);
        assertNotNull("SOAPFault is null", sfe.getFault());
        assertEquals(expected, sfe.getFault().getFaultCodeAsQName());
    }

    private static final String REPLY_TO_REFPS =
            "//*[namespace-uri()='http://schemas.xmlsoap.org/soap/envelope/' and local-name()='Envelope']/*" +
                    "[namespace-uri()='http://schemas.xmlsoap.org/soap/envelope/' and local-name()='Header']/*" +
                    "[namespace-uri()='http://example.org/customer' and local-name()='CustomerKey']";
    private static final String REPLY_TO_REFPS_VALUE =
            "string(//*[namespace-uri()='http://schemas.xmlsoap.org/soap/envelope/' and local-name()='Envelope']/*" +
                                "[namespace-uri()='http://schemas.xmlsoap.org/soap/envelope/' and local-name()='Header']/*" +
                                "[namespace-uri()='http://example.org/customer' and local-name()='CustomerKey']/text())";
    private static final String REPLY_TO_REFPS_ISREFP =
            "//*[namespace-uri()='http://schemas.xmlsoap.org/soap/envelope/' and local-name()='Envelope']/*" +
                    "[namespace-uri()='http://schemas.xmlsoap.org/soap/envelope/' and local-name()='Header']/*" +
                    "[namespace-uri()='http://example.org/customer' and local-name()='CustomerKey']" +
                    "[@*[namespace-uri()='" + W3CAddressingConstants.WSA_NAMESPACE_NAME + "' and local-name()='IsReferenceParameter']]";
}                    
