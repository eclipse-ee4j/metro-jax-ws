/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.submission.fromwsdl.crinterop.s12.client;

import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.AddressingFeature;
import javax.xml.namespace.QName;

import testutil.ClientServerTestUtil;
import com.sun.xml.ws.developer.MemberSubmissionAddressingFeature;


/**
 * @author Arun Gupta
 */
public class BindingProviderUtil {
    public static String getAddress() {
        if(ClientServerTestUtil.useLocal())
            return ClientServerTestUtil.getLocalAddress(PORT_QNAME);
        else
            return ENDPOINT_ADDRESS;
    }

    public static String getNonAnonymousAddress() {
        if(ClientServerTestUtil.useLocal())
            return ClientServerTestUtil.getLocalAddress(PORT_QNAME);
        else
            return NON_ANONYMOUS_ENDPOINT_ADDRESS;
    }

    public static WsaTestPortType createStub() {
        return new WsaTestService().getPort(WsaTestPortType.class, ENABLED_ADDRESSING_FEATURE);
    }

    public static  Dispatch<SOAPMessage> createDispatchWithWSDLWithAddressing() {
        return new WsaTestService().createDispatch(BindingProviderUtil.PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE, ENABLED_ADDRESSING_FEATURE);
    }

    public static  Dispatch<SOAPMessage> createDispatchWithWSDLWithoutAddressing() {
        return new WsaTestService().createDispatch(PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE, DISABLED_ADDRESSING_FEATURE);
    }

    private static final String ENDPOINT_ADDRESS = "http://localhost:8080/jaxrpc-wsa_submission_fromwsdl_crinterop_s12/member";
    private static final String NON_ANONYMOUS_ENDPOINT_ADDRESS = "http://localhost:8080/jaxrpc-wsa_fromwsdl_crinterop_s12/nonanonymous";
    private static final String NAMESPACE_URI = "http://example.org/wsaTestService";
    private static final QName PORT_QNAME = new QName(BindingProviderUtil.NAMESPACE_URI, "wsaTestPort");
    private static final MemberSubmissionAddressingFeature ENABLED_ADDRESSING_FEATURE = new MemberSubmissionAddressingFeature(true);
    private static final MemberSubmissionAddressingFeature DISABLED_ADDRESSING_FEATURE = new MemberSubmissionAddressingFeature(false);
}
