/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromwsdl.anonymous.client;

import testutil.ClientServerTestUtil;
import testutil.W3CAddressingConstants;

import javax.xml.namespace.QName;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.soap.AddressingFeature;
import java.util.UUID;

/**
 * @author Arun Gupta
 */
public class BindingProviderUtil {
    static final String getNoAnonymousAddress() {
        if(ClientServerTestUtil.useLocal())
            return ClientServerTestUtil.getLocalAddress(NO_ANONYMOUS_PORT_QNAME);
        else
            return NO_ANONYMOUS_ENDPOINT_ADDRESS;
    }

    static final String getOptionalAnonymousAddress() {
        if(ClientServerTestUtil.useLocal())
            return ClientServerTestUtil.getLocalAddress(OPTIONAL_ANONYMOUS_PORT_QNAME);
        else
            return OPTIONAL_ANONYMOUS_ENDPOINT_ADDRESS;
    }

    static final String getRequiredAnonymousAddress() {
        if(ClientServerTestUtil.useLocal())
            return ClientServerTestUtil.getLocalAddress(REQUIRED_ANONYMOUS_PORT_QNAME);
        else
            return REQUIRED_ANONYMOUS_ENDPOINT_ADDRESS;
    }

    static final String getProhibitedAnonymousAddress() {
        if(ClientServerTestUtil.useLocal())
            return ClientServerTestUtil.getLocalAddress(PROHIBITED_ANONYMOUS_PORT_QNAME);
        else
            return PROHIBITED_ANONYMOUS_ENDPOINT_ADDRESS;
    }

    static final AddNumbersPortType1 createNoAnonymousStub() {
        return new AddNumbersService().getAddNumbersPort1();
    }

    static final AddNumbersPortType2 createOptionalAnonymousStub() {
        return new AddNumbersService().getAddNumbersPort2();
    }

    static final AddNumbersPortType3 createRequiredAnonymousStub() {
        return new AddNumbersService().getAddNumbersPort3();
    }

    static final AddNumbersPortType4 createProhibitedAnonymousStub() {
        return new AddNumbersService().getAddNumbersPort4();
    }

    static final Dispatch<SOAPMessage> createNoAnonymousDispatch() {
        return new AddNumbersService().createDispatch(NO_ANONYMOUS_PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE, ENABLED_ADDRESSING_FEATURE);
    }

    static final Dispatch<SOAPMessage> createOptionalAnonymousDispatch() {
        return new AddNumbersService().createDispatch(OPTIONAL_ANONYMOUS_PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE, ENABLED_ADDRESSING_FEATURE);
    }

    static final Dispatch<SOAPMessage> createRequiredAnonymousDispatch() {
        return new AddNumbersService().createDispatch(REQUIRED_ANONYMOUS_PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE, ENABLED_ADDRESSING_FEATURE);
    }

    static final Dispatch<SOAPMessage> createProhibitedAnonymousDispatch() {
        return new AddNumbersService().createDispatch(PROHIBITED_ANONYMOUS_PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE, ENABLED_ADDRESSING_FEATURE);
    }

    static final Dispatch<SOAPMessage> createNoAnonymousDispatchWithoutAddressing() {
        return new AddNumbersService().createDispatch(NO_ANONYMOUS_PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE, DISABLED_ADDRESSING_FEATURE);
    }

    static final Dispatch<SOAPMessage> createOptionalAnonymousDispatchWithoutAddressing() {
        return new AddNumbersService().createDispatch(OPTIONAL_ANONYMOUS_PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE, DISABLED_ADDRESSING_FEATURE);
    }

    static final Dispatch<SOAPMessage> createRequiredAnonymousDispatchWithoutAddressing() {
        return new AddNumbersService().createDispatch(REQUIRED_ANONYMOUS_PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE, DISABLED_ADDRESSING_FEATURE);
    }

    static final Dispatch<SOAPMessage> createProhibitedAnonymousDispatchWithoutAddressing() {
        return new AddNumbersService().createDispatch(PROHIBITED_ANONYMOUS_PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE, DISABLED_ADDRESSING_FEATURE);
    }

    private static String ANONYMOUS_REPLY_TO_HEADER = "<ReplyTo xmlns=\"" + W3CAddressingConstants.WSA_NAMESPACE_NAME + "\">" +
            "<Address>" + W3CAddressingConstants.WSA_ANONYMOUS_ADDRESS + "</Address>" +
            "</ReplyTo>";
    private static String ANONYMOUS_FAULT_TO_HEADER = "<FaultTo xmlns=\"" + W3CAddressingConstants.WSA_NAMESPACE_NAME  + "\">" +
            "<Address>" + W3CAddressingConstants.WSA_ANONYMOUS_ADDRESS + "</Address>" +
            "</FaultTo>";
    private static String NON_ANONYMOUS_FAULT_TO_HEADER = "<wsa:FaultTo>" +
            "<wsa:Address>%s</wsa:Address>" +
            "</wsa:FaultTo>";
    private static String NON_ANONYMOUS_REPLY_TO_HEADER = "<wsa:ReplyTo>" +
            "<wsa:Address>%s</wsa:Address>" +
            "</wsa:ReplyTo>";

    static final String ANONYMOUS_FAULT_TO_MESSAGE = "<S:Envelope xmlns:S=\"%s\" " +
            "xmlns:wsa=\"" + W3CAddressingConstants.WSA_NAMESPACE_NAME  + "\">\n" +
            "<S:Header>\n" +
            ANONYMOUS_FAULT_TO_HEADER +
            "</S:Header>\n" +
            "<S:Body>\n" +
            "<addNumbers xmlns=\"http://example.com/\">\n" +
            "  <number1>10</number1>\n" +
            "  <number2>10</number2>\n" +
            "  <testname>%s</testname>\n" +
            "</addNumbers>\n" +
            "</S:Body></S:Envelope>";

    static final String ANONYMOUS_FAULT_TO_COMPLETE_MESSAGE = "<S:Envelope xmlns:S=\"%s\" " +
            "xmlns:wsa=\"" + W3CAddressingConstants.WSA_NAMESPACE_NAME  + "\">\n" +
            "<S:Header>\n" +
            "<wsa:Action>%s</wsa:Action>" +
            "<wsa:MessageID>uuid:" + UUID.randomUUID() + "</wsa:MessageID>" +
            "<wsa:To>%s</wsa:To>" +
            ANONYMOUS_FAULT_TO_HEADER +
            "</S:Header>\n" +
            "<S:Body>\n" +
            "<addNumbers xmlns=\"http://example.com/\">\n" +
            "  <number1>10</number1>\n" +
            "  <number2>10</number2>\n" +
            "  <testname>%s</testname>\n" +
            "</addNumbers>\n" +
            "</S:Body></S:Envelope>";

    static final String NON_ANONYMOUS_FAULT_TO_MESSAGE = "<S:Envelope xmlns:S=\"%s\" " +
            "xmlns:wsa=\"" + W3CAddressingConstants.WSA_NAMESPACE_NAME  + "\">\n" +
            "<S:Header>\n" +
            NON_ANONYMOUS_FAULT_TO_HEADER +
            "</S:Header>\n" +
            "<S:Body>\n" +
            "<addNumbers xmlns=\"http://example.com/\">\n" +
            "  <number1>10</number1>\n" +
            "  <number2>10</number2>\n" +
            "  <testname>%s</testname>\n" +
            "</addNumbers>\n" +
            "</S:Body></S:Envelope>";

    static final String NON_ANONYMOUS_FAULT_TO_COMPLETE_MESSAGE = "<S:Envelope xmlns:S=\"%s\" " +
            "xmlns:wsa=\"" + W3CAddressingConstants.WSA_NAMESPACE_NAME  + "\">\n" +
            "<S:Header>\n" +
            NON_ANONYMOUS_FAULT_TO_HEADER +
            "<wsa:Action>%s</wsa:Action>" +
            "<wsa:To>%s</wsa:To>" +
            "<wsa:MessageID>uuid:" + UUID.randomUUID() + "</wsa:MessageID>" +
            "</S:Header>\n" +
            "<S:Body>\n" +
            "<addNumbers xmlns=\"http://example.com/\">\n" +
            "  <number1>10</number1>\n" +
            "  <number2>10</number2>\n" +
            "  <testname>%s</testname>\n" +
            "</addNumbers>\n" +
            "</S:Body></S:Envelope>";

    static final String NON_ANONYMOUS_REPLY_TO_ANONYMOUS_FAULT_TO_COMPLETE_MESSAGE = "<S:Envelope xmlns:S=\"%s\" " +
            "xmlns:wsa=\"" + W3CAddressingConstants.WSA_NAMESPACE_NAME  + "\">\n" +
            "<S:Header>\n" +
            NON_ANONYMOUS_REPLY_TO_HEADER +
            ANONYMOUS_FAULT_TO_HEADER +
            "<wsa:Action>%s</wsa:Action>" +
            "<wsa:To>%s</wsa:To>" +
            "<wsa:MessageID>uuid:" + UUID.randomUUID() + "</wsa:MessageID>" +
            "</S:Header>\n" +
            "<S:Body>\n" +
            "<addNumbers xmlns=\"http://example.com/\">\n" +
            "  <number1>10</number1>\n" +
            "  <number2>10</number2>\n" +
            "  <testcase>%s</testcase>\n" +
            "</addNumbers>\n" +
            "</S:Body></S:Envelope>";

    static final String ANONYMOUS_REPLY_TO_COMPLETE_MESSAGE = "<S:Envelope xmlns:S=\"%s\" " +
            "xmlns:wsa=\"" + W3CAddressingConstants.WSA_NAMESPACE_NAME  + "\">\n" +
            "<S:Header>\n" +
            ANONYMOUS_REPLY_TO_HEADER +
            "<wsa:Action>%s</wsa:Action>" +
            "<wsa:To>%s</wsa:To>" +
            "<wsa:MessageID>uuid:" + UUID.randomUUID() + "</wsa:MessageID>" +
            "</S:Header>\n" +
            "<S:Body>\n" +
            "<addNumbers xmlns=\"http://example.com/\">\n" +
            "  <number1>10</number1>\n" +
            "  <number2>10</number2>\n" +
            "  <testname>%s</testname>\n" +
            "</addNumbers>\n" +
            "</S:Body></S:Envelope>";

    static final String NON_ANONYMOUS_REPLY_TO_COMPLETE_MESSAGE = "<S:Envelope xmlns:S=\"%s\" " +
            "xmlns:wsa=\"" + W3CAddressingConstants.WSA_NAMESPACE_NAME  + "\">\n" +
            "<S:Header>\n" +
            NON_ANONYMOUS_REPLY_TO_HEADER +
            "<wsa:Action>%s</wsa:Action>" +
            "<wsa:To>%s</wsa:To>" +
            "<wsa:MessageID>uuid:" + UUID.randomUUID() + "</wsa:MessageID>" +
            "</S:Header>\n" +
            "<S:Body>\n" +
            "<addNumbers xmlns=\"http://example.com/\">\n" +
            "  <number1>10</number1>\n" +
            "  <number2>10</number2>\n" +
            "  <testname>%s</testname>\n" +
            "</addNumbers>\n" +
            "</S:Body></S:Envelope>";

    private static final AddressingFeature ENABLED_ADDRESSING_FEATURE = new AddressingFeature(true, true);
    private static final AddressingFeature DISABLED_ADDRESSING_FEATURE = new AddressingFeature(false);
    private static final String TARGET_NAMESPACE = "http://example.com/";
    private static final QName NO_ANONYMOUS_PORT_QNAME = new QName(TARGET_NAMESPACE, "AddNumbersPort1");
    private static final QName OPTIONAL_ANONYMOUS_PORT_QNAME = new QName(TARGET_NAMESPACE, "AddNumbersPort2");
    private static final QName REQUIRED_ANONYMOUS_PORT_QNAME = new QName(TARGET_NAMESPACE, "AddNumbersPort3");
    private static final QName PROHIBITED_ANONYMOUS_PORT_QNAME = new QName(TARGET_NAMESPACE, "AddNumbersPort4");
    private static final String NO_ANONYMOUS_ENDPOINT_ADDRESS = "http://localhost:8080/jaxrpc-wsa_fromwsdl_anonymous/hello1";
    private static final String OPTIONAL_ANONYMOUS_ENDPOINT_ADDRESS = "http://localhost:8080/jaxrpc-wsa_fromwsdl_anonymous/hello2";
    private static final String REQUIRED_ANONYMOUS_ENDPOINT_ADDRESS = "http://localhost:8080/jaxrpc-wsa_fromwsdl_anonymous/hello3";
    private static final String PROHIBITED_ANONYMOUS_ENDPOINT_ADDRESS = "http://localhost:8080/jaxrpc-wsa_fromwsdl_anonymous/hello4";
    static final String NO_IN_ACTION = "http://example.com/AddNumbersPortType1/add";
    static final String OPTIONAL_IN_ACTION = "http://example.com/AddNumbersPortType2/add";
    static final String REQUIRED_IN_ACTION = "http://example.com/AddNumbersPortType3/add";
    static final String PROHIBITED_IN_ACTION = "http://example.com/AddNumbersPortType4/add";
    static final String NON_ANONYMOUS_ADDRESS = "http://example.com/non-anonymous";
}
