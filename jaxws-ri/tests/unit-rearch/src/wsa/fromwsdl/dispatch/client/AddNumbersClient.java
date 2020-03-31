/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromwsdl.dispatch.client;

import com.sun.xml.ws.addressing.W3CAddressingConstants;
import org.custommonkey.xmlunit.XMLTestCase;
import testutil.ClientServerTestUtil;
import testutil.WsaUtils;

import javax.xml.namespace.QName;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.SOAPConstants;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.soap.AddressingFeature;
import jakarta.xml.ws.soap.SOAPBinding;
import jakarta.xml.ws.soap.SOAPFaultException;
import jakarta.jws.HandlerChain;
import java.util.Set;
import java.util.List;

/**
 * @author Arun Gupta
 */
public class AddNumbersClient extends XMLTestCase {
    private static final QName SERVICE_QNAME = new QName("http://example.com/", "AddNumbersService");
    private static final QName PORT_QNAME = new QName("http://example.com/", "AddNumbersPort");
    private static final String ENDPOINT_ADDRESS = "http://localhost:8080/jaxrpc-wsa_fromwsdl_dispatch/hello";
    private static final String ADD_NUMBERS_ACTION = "http://example.com/AddNumbersPortType/addNumbers1Request";
    private static final String ADD_NUMBERS2_ACTION = "add2InAction";
    private static final String ADD_NUMBERS3_ACTION = "http://example.com/AddNumbersPortType/addNumbers3Request";
    private static final String ADD_NUMBERS4_ACTION = "http://example.com/AddNumbersPortType/addNumbers4Request";

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
        AddNumbersService service  = new AddNumbersService();
        Dispatch<SOAPMessage> dispatch = service.createDispatch(PORT_QNAME,
                                                                SOAPMessage.class,
                                                                Service.Mode.MESSAGE,
                                                                new AddressingFeature(false, false));
        return dispatch;
    }

    public void testActionMismatch() throws Exception {
        Dispatch<SOAPMessage> dispatch = createDispatchWithoutWSDL();
        dispatch.getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY,"FakeSOAPAction");
        try {
            WsaUtils.invoke(dispatch,
                    WsaUtils.ACTION_DISPATCH_MESSAGE1,
                    WsaUtils.S11_NS,
                    WsaUtils.W3C_WSA_NS,
                    getAddress(),
                    W3CAddressingConstants.WSA_ANONYMOUS_ADDRESS,
                    ADD_NUMBERS_ACTION);
        } catch (SOAPFaultException sfe) {
            assertFault(sfe, W3CAddressingConstants.ACTION_MISMATCH);
        }
    }

    public void testAddNumbersDefaultActionDispatch() throws Exception {
        WsaUtils.invoke(createDispatchWithoutWSDL(),
                              WsaUtils.ACTION_DISPATCH_MESSAGE1,
                              WsaUtils.S11_NS,
                              WsaUtils.W3C_WSA_NS,
                              getAddress(),
            W3CAddressingConstants.WSA_ANONYMOUS_ADDRESS,
                              ADD_NUMBERS_ACTION);
    }

    public void testAddNumbers2ActionDispatch() throws Exception {
        WsaUtils.invoke(createDispatchWithoutWSDL(),
                              WsaUtils.ACTION_DISPATCH_MESSAGE1,
                              WsaUtils.S11_NS,
                              WsaUtils.W3C_WSA_NS,
                              getAddress(),
            W3CAddressingConstants.WSA_ANONYMOUS_ADDRESS,
                              ADD_NUMBERS2_ACTION);
    }

    public void testAddNumbers3ActionDispatch() throws Exception {
        WsaUtils.invoke(createDispatchWithoutWSDL(),
                              WsaUtils.ACTION_DISPATCH_MESSAGE1,
                              WsaUtils.S11_NS,
                              WsaUtils.W3C_WSA_NS,
                              getAddress(),
            W3CAddressingConstants.WSA_ANONYMOUS_ADDRESS,
                              ADD_NUMBERS3_ACTION);
    }

    public void testAddNumbers4ActionDispatch() throws Exception {
        WsaUtils.invoke(createDispatchWithoutWSDL(),
                              WsaUtils.ACTION_DISPATCH_MESSAGE1,
                              WsaUtils.S11_NS,
                              WsaUtils.W3C_WSA_NS,
                              getAddress(),
            W3CAddressingConstants.WSA_ANONYMOUS_ADDRESS,
                              ADD_NUMBERS4_ACTION);
    }

    public void testAddressingWithNoWSDL() throws Exception {
        Service service = Service.create(SERVICE_QNAME);
        service.addPort(PORT_QNAME, SOAPBinding.SOAP11HTTP_BINDING, getAddress());
        Dispatch<SOAPMessage> dispatch = service.createDispatch(PORT_QNAME,
                                                                SOAPMessage.class,
                                                                Service.Mode.MESSAGE,
                                                                new AddressingFeature());
        dispatch.getRequestContext().put(BindingProvider.SOAPACTION_USE_PROPERTY,true);
        dispatch.getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY,ADD_NUMBERS_ACTION);
        String message = 	"<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body>" +
            "<addNumbers xmlns=\"http://example.com/\">" +
             "<number1>10</number1>" +
            "<number2>10</number2>" +
            "</addNumbers>" +
            "</S:Body></S:Envelope>";
        WsaUtils.invoke(dispatch,message);
    }

    public void testAddressingWithNoWSDLwithHandler() throws Exception {
        Service service = Service.create(SERVICE_QNAME);
        service.addPort(PORT_QNAME, SOAPBinding.SOAP11HTTP_BINDING, getAddress());
        Dispatch<SOAPMessage> dispatch = service.createDispatch(PORT_QNAME,
                                                                SOAPMessage.class,
                                                                Service.Mode.MESSAGE,
                                                                new AddressingFeature());
        dispatch.getRequestContext().put(BindingProvider.SOAPACTION_USE_PROPERTY,true);
        dispatch.getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY,ADD_NUMBERS_ACTION);
        String message = 	"<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body>" +
            "<addNumbers xmlns=\"http://example.com/\">" +
             "<number1>10</number1>" +
            "<number2>10</number2>" +
            "</addNumbers>" +
                "</S:Body></S:Envelope>";
        List<Handler> handlerChain = dispatch.getBinding().getHandlerChain();
        handlerChain.add(new MyHandler());
        dispatch.getBinding().setHandlerChain(handlerChain);
        WsaUtils.invoke(dispatch,message);
    }

    private void assertFault(SOAPFaultException sfe, QName expected) {
        assertNotNull("SOAPFaultException is null", sfe);
        assertNotNull("SOAPFault is null", sfe.getFault());
        assertEquals(expected, sfe.getFault().getFaultCodeAsQName());
    }

    private static class MyHandler implements SOAPHandler<SOAPMessageContext> {

    public Set<QName> getHeaders() {
        return null;
    }

    public boolean handleMessage(SOAPMessageContext smc) {
        SOAPMessage message = smc.getMessage();

        return true;
    }

    public boolean handleFault(SOAPMessageContext smc) {
        return true;
    }

    // nothing to clean up
    public void close(MessageContext messageContext) {
    }
}

}
