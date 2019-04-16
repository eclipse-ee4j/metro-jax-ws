/*
 * Copyright (c) 2004, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package testutil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPFault;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Response;

import com.sun.xml.ws.addressing.W3CAddressingConstants;
import com.sun.xml.ws.addressing.v200408.MemberSubmissionAddressingConstants;
import com.sun.xml.ws.api.addressing.AddressingVersion;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

public class W3CWsaUtils {
    public static final String UUID = "uuid:" + java.util.UUID.randomUUID();
    public static final String W3C_WSA_NS = W3CAddressingConstants.WSA_NAMESPACE_NAME;
    public static final String MS_WSA_NS = MemberSubmissionAddressingConstants.WSA_NAMESPACE_NAME;
    public static final String S11_NS = SOAPConstants.URI_NS_SOAP_1_1_ENVELOPE;
    public static final String S12_NS = SOAPConstants.URI_NS_SOAP_1_2_ENVELOPE;

    private static final Source makeStreamSource(String msg) {
        byte[] bytes = msg.getBytes();
        ByteArrayInputStream sinputStream = new ByteArrayInputStream(bytes);
        return new StreamSource(sinputStream);
    }

    private static final SOAPMessage getSOAPMessage(Source msg) throws Exception {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage();
        message.getSOAPPart().setContent(msg);
        message.saveChanges();
        return message;
    }

    private static final SOAPMessage getSOAP12Message(Source msg) throws Exception {
        MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
        SOAPMessage message = factory.createMessage();
        message.getSOAPPart().setContent(msg);
        message.saveChanges();
        return message;
    }

    public static String fileToXMLString(String filename) {
        return sourceToXMLString(new StreamSource(new File(filename)));
    }

    public static String sourceToXMLString(Source result) {
        String xmlResult = null;
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            OutputStream out = new ByteArrayOutputStream();
            StreamResult streamResult = new StreamResult();
            streamResult.setOutputStream(out);
            transformer.transform(result, streamResult);
            xmlResult = streamResult.getOutputStream().toString();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return xmlResult;
    }

    public static final SOAPMessage invoke(Dispatch<SOAPMessage> dispatch, String request, String ... args) throws Exception {
        String fRequest = String.format(request, args);

        return dispatch.invoke(getSOAPMessage(makeStreamSource(fRequest)));
    }

    public static final Response<SOAPMessage> invokeAsync(Dispatch<SOAPMessage> dispatch, String request, String ... args) throws Exception {
        String fRequest = String.format(request, args);

        return dispatch.invokeAsync(getSOAPMessage(makeStreamSource(fRequest)));
    }

    public static final void invokeOneWay(Dispatch<SOAPMessage> dispatch, String request, String ... args) throws Exception {
        String fRequest = String.format(request, args);

        dispatch.invokeOneWay(getSOAPMessage(makeStreamSource(fRequest)));
    }

    public static final SOAPMessage invoke12(Dispatch<SOAPMessage> dispatch, String request, String ... args) throws Exception {
        String fRequest = String.format(request, args);

        return dispatch.invoke(getSOAP12Message(makeStreamSource(fRequest)));
    }

    public static final void invokeOneWay12(Dispatch<SOAPMessage> dispatch, String request, String ... args) throws Exception {
        String fRequest = String.format(request, args);

        dispatch.invokeOneWay(getSOAP12Message(makeStreamSource(fRequest)));
    }
    public static final String BAD_ACTION_MESSAGE = "<S:Envelope xmlns:S=\"%s\" " +
            "xmlns:wsa=\"%s\">\n" +
            "<S:Header>\n" +
            "<wsa:To>%s</wsa:To>\n" +
            "<wsa:MessageID>" + UUID + "</wsa:MessageID>\n" +
            "<wsa:ReplyTo>\n" +
            "  <wsa:Address>%s</wsa:Address>\n" +
            "</wsa:ReplyTo>\n" +
            "<wsa:Action>badSOAPAction</wsa:Action>\n" +
            "</S:Header>\n" +
            "<S:Body>\n" +
            "<addNumbers xmlns=\"http://example.com/\">\n" +
            "  <number1>10</number1>\n" +
            "  <number2>10</number2>\n" +
            "</addNumbers>\n" +
            "</S:Body></S:Envelope>";

    public static final String MISSING_ACTION_MESSAGE = "<S:Envelope xmlns:S=\"%s\" " +
            "xmlns:wsa=\"%s\">\n" +
            "<S:Header>\n" +
            "<wsa:To>%s</wsa:To>\n" +
            "<wsa:MessageID>" + UUID + "</wsa:MessageID>\n" +
            "<wsa:ReplyTo>\n" +
            "  <wsa:Address>%s</wsa:Address>\n" +
            "</wsa:ReplyTo>\n" +
            "</S:Header>\n" +
            "<S:Body>\n" +
            "<addNumbers xmlns=\"http://example.com/\">\n" +
            "  <number1>10</number1>\n" +
            "  <number2>10</number2>\n" +
            "</addNumbers>\n" +
            "</S:Body></S:Envelope>";

    public static final String REPLY_TO_REFPS_MESSAGE = "<S:Envelope xmlns:S=\"%s\" " +
            "xmlns:wsa=\"%s\">\n" +
            "<S:Header>\n" +
            "<wsa:To>%s</wsa:To>\n" +
            "<wsa:MessageID>" + UUID + "</wsa:MessageID>\n" +
            "<wsa:ReplyTo>\n" +
            "  <wsa:Address>%s</wsa:Address>\n" +
            "  <wsa:ReferenceParameters>\n" +
            "    <ck:CustomerKey xmlns:ck=\"http://example.org/customer\">Key#123456789</ck:CustomerKey>\n" +
            "  </wsa:ReferenceParameters>" +
            "</wsa:ReplyTo>\n" +
            "<wsa:Action>%s</wsa:Action>\n" +
            "</S:Header>\n" +
            "<S:Body>\n" +
            "<addNumbers xmlns=\"http://example.com/\">\n" +
            "  <number1>10</number1>\n" +
            "  <number2>10</number2>\n" +
            "</addNumbers>\n" +
            "</S:Body></S:Envelope>";

    public static final String FAULT_TO_REFPS_MESSAGE = "<S:Envelope xmlns:S=\"%s\" " +
            "xmlns:wsa=\"%s\">\n" +
            "<S:Header>\n" +
            "<wsa:To>%s</wsa:To>\n" +
            "<wsa:MessageID>" + UUID + "</wsa:MessageID>\n" +
            "<wsa:ReplyTo>\n" +
            "  <wsa:Address>%s</wsa:Address>\n" +
            "  <wsa:ReferenceParameters>\n" +
            "    <ck:CustomerKey xmlns:ck=\"http://example.org/customer\">Key#123456789</ck:CustomerKey>\n" +
            "  </wsa:ReferenceParameters>" +
            "</wsa:ReplyTo>\n" +
            "<wsa:FaultTo>\n" +
            "  <wsa:Address>%s</wsa:Address>\n" +
            "  <wsa:ReferenceParameters>\n" +
            "    <ck:CustomerKey xmlns:ck=\"http://example.org/customer\">Fault#123456789</ck:CustomerKey>\n" +
            "  </wsa:ReferenceParameters>" +
            "</wsa:FaultTo>\n" +
            "<wsa:Action>%s</wsa:Action>\n" +
            "</S:Header>\n" +
            "<S:Body>\n" +
            "<addNumbers xmlns=\"http://example.com/\">\n" +
            "  <number1>-10</number1>\n" +
            "  <number2>10</number2>\n" +
            "</addNumbers>\n" +
            "</S:Body></S:Envelope>";

    public static final String DUPLICATE_TO_MESSAGE = "<S:Envelope xmlns:S=\"%s\" " +
            "xmlns:wsa=\"%s\">\n" +
            "<S:Header>\n" +
            "<wsa:To>%s</wsa:To>\n" +
            "</S:Header>\n" +
            "<S:Body>\n" +
            "<addNumbers xmlns=\"http://example.com/\">\n" +
            "  <number1>10</number1>\n" +
            "  <number2>10</number2>\n" +
            "</addNumbers>\n" +
            "</S:Body></S:Envelope>";

    public static final String DUPLICATE_REPLY_TO_MESSAGE = "<S:Envelope xmlns:S=\"%s\" " +
            "xmlns:wsa=\"%s\">\n" +
            "<S:Header>\n" +
            "<wsa:ReplyTo><wsa:Address>%s</wsa:Address></wsa:ReplyTo>" +
            "</S:Header>\n" +
            "<S:Body>\n" +
            "<addNumbers xmlns=\"http://example.com/\">\n" +
            "  <number1>10</number1>\n" +
            "  <number2>10</number2>\n" +
            "</addNumbers>\n" +
            "</S:Body></S:Envelope>";

    public static final String DUPLICATE_FAULT_TO_MESSAGE = "<S:Envelope xmlns:S=\"%s\" " +
            "xmlns:wsa=\"%s\">\n" +
            "<S:Header>\n" +
            "<wsa:FaultTo><wsa:Address>%s</wsa:Address></wsa:FaultTo>" +
            "<wsa:FaultTo><wsa:Address>%s</wsa:Address></wsa:FaultTo>" +
            "</S:Header>\n" +
            "<S:Body>\n" +
            "<addNumbers xmlns=\"http://example.com/\">\n" +
            "  <number1>10</number1>\n" +
            "  <number2>10</number2>\n" +
            "</addNumbers>\n" +
            "</S:Body></S:Envelope>";

    public static final String DUPLICATE_ACTION_MESSAGE = "<S:Envelope xmlns:S=\"%s\" " +
            "xmlns:wsa=\"%s\">\n" +
            "<S:Header>\n" +
            "<wsa:Action>%s</wsa:Action>" +
            "</S:Header>\n" +
            "<S:Body>\n" +
            "<addNumbers xmlns=\"http://example.com/\">\n" +
            "  <number1>10</number1>\n" +
            "  <number2>10</number2>\n" +
            "</addNumbers>\n" +
            "</S:Body></S:Envelope>";

    public static final String DUPLICATE_MESSAGE_ID_MESSAGE = "<S:Envelope xmlns:S=\"%s\" " +
            "xmlns:wsa=\"%s\">\n" +
            "<S:Header>\n" +
            "<wsa:MessageID>" + UUID + "</wsa:MessageID>" +
            "</S:Header>\n" +
            "<S:Body>\n" +
            "<addNumbers xmlns=\"http://example.com/\">\n" +
            "  <number1>10</number1>\n" +
            "  <number2>10</number2>\n" +
            "</addNumbers>\n" +
            "</S:Body></S:Envelope>";

    public static final String DUPLICATE_MESSAGE_ID_MESSAGE_ONEWAY = "<S:Envelope xmlns:S=\"%s\" " +
            "xmlns:wsa=\"%s\">\n" +
            "<S:Header>\n" +
            "<wsa:MessageID>" + UUID + "</wsa:MessageID>" +
            "<wsa:MessageID>" + UUID + "</wsa:MessageID>" +
            "</S:Header>\n" +
            "<S:Body>\n" +
            "<addNumbers5 xmlns=\"http://example.com/\">\n" +
            "  <number1>10</number1>\n" +
            "  <number2>10</number2>\n" +
            "</addNumbers5>\n" +
            "</S:Body></S:Envelope>";

    private static final String ADD_NUMBERS_HEADER = "<S:Envelope xmlns:S=\"%s\" " +
            "xmlns:wsa=\"%s\">\n" +
            "<S:Header>\n" +
            "<wsa:To>%s</wsa:To>\n" +
            "<wsa:MessageID>" + UUID + "</wsa:MessageID>\n" +
            "<wsa:ReplyTo>\n" +
            "  <wsa:Address>%s</wsa:Address>\n" +
            "</wsa:ReplyTo>\n" +
            "<wsa:Action>%s</wsa:Action>\n" +
            "</S:Header>\n";

    private static final String ADD_NUMBERS_PAYLOAD = "<S:Body>\n" +
            "<addNumbers xmlns=\"http://example.com/\">\n" +
            "  <number1>10</number1>\n" +
            "  <number2>10</number2>\n" +
            "</addNumbers>\n" +
            "</S:Body></S:Envelope>";

    private static final String ADD_NUMBERS2_PAYLOAD = "<S:Body>\n" +
            "<addNumbers2 xmlns=\"http://example.com/\">\n" +
            "  <number1>10</number1>\n" +
            "  <number2>10</number2>\n" +
            "</addNumbers2>\n" +
            "</S:Body></S:Envelope>";

    private static final String ADD_NUMBERS3_PAYLOAD = "<S:Body>\n" +
            "<addNumbers2 xmlns=\"http://example.com/\">\n" +
            "  <number1>10</number1>\n" +
            "  <number2>10</number2>\n" +
            "</addNumbers2>\n" +
            "</S:Body></S:Envelope>";

    private static final String ADD_NUMBERS4_PAYLOAD = "<S:Body>\n" +
            "<addNumbers2 xmlns=\"http://example.com/\">\n" +
            "  <number1>10</number1>\n" +
            "  <number2>10</number2>\n" +
            "</addNumbers2>\n" +
            "</S:Body></S:Envelope>";

    public static final String ACTION_DISPATCH_MESSAGE1 = ADD_NUMBERS_HEADER + ADD_NUMBERS_PAYLOAD;
    public static final String ACTION_DISPATCH_MESSAGE2 = ADD_NUMBERS_HEADER + ADD_NUMBERS2_PAYLOAD;
    public static final String ACTION_DISPATCH_MESSAGE3 = ADD_NUMBERS_HEADER + ADD_NUMBERS3_PAYLOAD;
    public static final String ACTION_DISPATCH_MESSAGE4 = ADD_NUMBERS_HEADER + ADD_NUMBERS4_PAYLOAD;

    public static final void assertInvalidHeaderFaultCode(QName got, AddressingVersion av) {
        try {
            TestCase.assertEquals(av.invalidCardinalityTag, got);
        } catch (AssertionFailedError e) {
            TestCase.assertEquals(av.invalidMapTag, got);
        }
    }

    public static final void assertHeaderRequiredFaultCode(QName got) {
        TestCase.assertEquals(W3CAddressingConstants.MAP_REQUIRED_QNAME, got);
    }

    public static final void assertHeaderRequiredFaultCode12(SOAPFault f) {
        TestCase.assertNotNull("Fault element is null", f);
        QName faultcode = f.getFaultCodeAsQName();
        TestCase.assertEquals(SOAPConstants.SOAP_SENDER_FAULT, faultcode);
        Iterator iter = f.getFaultSubcodes();
        TestCase.assertNotNull("Subcode iterator is null", iter);
        TestCase.assertTrue("Subcode iterator has no elements", iter.hasNext());
        TestCase.assertEquals(iter.next(), W3CAddressingConstants.MAP_REQUIRED_QNAME);
//        TestCase.assertTrue("No subsubcode on the fault", iter.hasNext());
//        TestCase.assertEquals(iter.next(), W3CAddressingConstants.INVALID_CARDINALITY);
    }

    public static final void assertInvalidCardinalityCode12(SOAPFault f, AddressingVersion av) {
        TestCase.assertNotNull("Fault element is null", f);
        QName faultcode = f.getFaultCodeAsQName();
        TestCase.assertEquals(SOAPConstants.SOAP_SENDER_FAULT, faultcode);
        Iterator iter = f.getFaultSubcodes();
        TestCase.assertNotNull("Subcode iterator is null", iter);
        TestCase.assertTrue("Subcode iterator has no elements", iter.hasNext());
        TestCase.assertEquals(iter.next(), av.invalidMapTag);
        TestCase.assertTrue("No subsubcode on the fault", iter.hasNext());
        TestCase.assertEquals(iter.next(), av.invalidCardinalityTag);
    }
}


