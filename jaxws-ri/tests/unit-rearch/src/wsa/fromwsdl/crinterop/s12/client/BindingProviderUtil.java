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

import java.util.List;
import java.util.ArrayList;

import javax.xml.namespace.QName;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.RespectBindingFeature;
import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.soap.SOAPBinding;
import jakarta.xml.ws.soap.AddressingFeature;

import testutil.ClientServerTestUtil;

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
            return ClientServerTestUtil.getLocalAddress(NON_ANON_PORT_QNAME);
        else
            return NON_ANONYMOUS_ENDPOINT_ADDRESS;
    }

    public static WsaTestPortType createStub() {
        return new WsaTestService().getWsaTestPort();
    }

    public static  Dispatch<SOAPMessage> createDispatchWithWSDLWithAddressing() {
        return new WsaTestService().createDispatch(PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE, ENABLED_RESPECT_BINDING_FEATURE);
    }

    public static  Dispatch<SOAPMessage> createDispatchWithWSDLWithoutAddressing() {
        return new WsaTestService().createDispatch(PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE, DISABLED_ADDRESSING_FEATURE);
    }

    private static final String ENDPOINT_ADDRESS = "http://localhost:8080/jaxrpc-wsa_fromwsdl_crinterop_s12/cr";
    private static final String NON_ANONYMOUS_ENDPOINT_ADDRESS = "http://localhost:8080/jaxrpc-wsa_fromwsdl_crinterop_s12/nonanonymous";
    private static final String NAMESPACE_URI = "http://example.org/wsaTestService";
    private static final QName SERVICE_QNAME = new QName(NAMESPACE_URI, "wsaTestService");
    private static final QName PORT_QNAME = new QName(NAMESPACE_URI, "wsaTestPort");
    private static final QName NON_ANON_PORT_QNAME = new QName(NAMESPACE_URI, "NonAnonymousProvider");
    private static final AddressingFeature DISABLED_ADDRESSING_FEATURE = new AddressingFeature(false);
    private static final RespectBindingFeature ENABLED_RESPECT_BINDING_FEATURE = new RespectBindingFeature(true);

}
