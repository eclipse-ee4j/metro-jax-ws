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
import javax.xml.namespace.QName;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.RespectBindingFeature;
import jakarta.xml.ws.soap.AddressingFeature;
import testutil.PortAllocator;

/**
 * @author Rama Pulavarthi
 */
public class BindingProviderUtil {
    public static String getAddress() {
        WsaTestPortType stub = new WsaTestService().getWsaTestPort();
        return (String) ((BindingProvider)stub).getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
    }

    public static String getNonAnonymousEndpointAddress() {
        return getAddress();
    }

    public static String getNonAnonymousClientAddress() {
	return "http://localhost:"+PortAllocator.getFreePort()+"/jaxws-crinterop-s11/nonanonymous";
    }

    public static WsaTestPortType createStub() {
        WsaTestPortType stub = new WsaTestService().getWsaTestPort();
        return stub;
    }

    public static  Dispatch<SOAPMessage> createDispatchWithWSDLWithAddressing() {
        Dispatch<SOAPMessage> dispatch = new WsaTestService().createDispatch(PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE, ENABLED_RESPECT_BINDING_FEATURE);
        return dispatch;
    }

    public static  Dispatch<SOAPMessage> createDispatchWithWSDLWithoutAddressing() {
        Dispatch<SOAPMessage> dispatch = new WsaTestService().createDispatch(PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE, DISABLED_ADDRESSING_FEATURE);
        dispatch.getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, "http://example.org/action/echoIn");
        dispatch.getRequestContext().put(BindingProvider.SOAPACTION_USE_PROPERTY, Boolean.TRUE);
        return dispatch;
    }

    public static  Dispatch<SOAPMessage> createDispatchForNonAnonymousWithWSDLWithoutAddressing() {
        Dispatch<SOAPMessage> dispatch = new WsaTestService().createDispatch(PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE, DISABLED_ADDRESSING_FEATURE);
        dispatch.getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, "http://example.org/action/echoIn");
        dispatch.getRequestContext().put(BindingProvider.SOAPACTION_USE_PROPERTY, Boolean.TRUE);
        return dispatch;
    }

    public static  Dispatch<SOAPMessage> createNotifyDispatchWithWSDLWithoutAddressing() {
        Dispatch<SOAPMessage> dispatch = new WsaTestService().createDispatch(PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE, DISABLED_ADDRESSING_FEATURE);
        dispatch.getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, TestConstants.NOTIFY_ACTION);
        dispatch.getRequestContext().put(BindingProvider.SOAPACTION_USE_PROPERTY, Boolean.TRUE);
        return dispatch;
    }


    private static final String NAMESPACE_URI = "http://example.org";
    private static final QName PORT_QNAME = new QName(NAMESPACE_URI, "wsaTestPort");

    private static final AddressingFeature DISABLED_ADDRESSING_FEATURE = new AddressingFeature(false);
    private static final RespectBindingFeature ENABLED_RESPECT_BINDING_FEATURE = new RespectBindingFeature(true);

}
