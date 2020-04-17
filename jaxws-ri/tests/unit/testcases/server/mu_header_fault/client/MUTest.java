/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.mu_header_fault.client;

import junit.framework.TestCase;

import javax.xml.namespace.QName;
import jakarta.xml.soap.*;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.soap.SOAPFaultException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import testutil.HTTPResponseInfo;
import testutil.ClientServerTestUtil;

/**
 * @author Rama Pulavarthi
 */
public class MUTest extends TestCase {
    private static String s11_request = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">\n" +
            "          <S:Header>\n" +
            "              <wsa:Action S:mustUnderstand=\"1\">http://www.example.org/mustunderstand_action</wsa:Action>\n" +
            "          </S:Header>\n" +
            "          <S:Body>\n" +
            "              <ns2:echo xmlns:ns2=\"http://server.mu_header_fault.server/\">\n" +
            "                  <arg0>Hello</arg0>\n" +
            "              </ns2:echo>\n" +
            "          </S:Body>\n" +
            "      </S:Envelope>";
    private static String s12_request="<S:Envelope xmlns:S=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">" +
            "<S:Header>\n" +
            "     <wsa:Action S:mustUnderstand=\"1\">http://www.example.org/mustunderstand_action</wsa:Action>\n" +
            "</S:Header>\n" +
            "<S:Body><ns2:echo xmlns:ns2=\"http://server.mu_header_fault.server/\"><arg0>Hello</arg0></ns2:echo>" +
            "</S:Body></S:Envelope>";
    

    public void testMU_SOAP11() throws Exception {
        QName portQName = new QName("http://server.mu_header_fault.server/", "TestEndpointPort");
        Dispatch<SOAPMessage> dispatch = new TestEndpointService().createDispatch(portQName, SOAPMessage.class, Service.Mode.MESSAGE);
        SOAPMessage message = getSOAPMessage(makeStreamSource(s11_request));
        try {
            Object result = dispatch.invoke(message);
        } catch (SOAPFaultException e) {
            SOAPFault sf = e.getFault();
            assertTrue(sf.getFaultCode().endsWith(":MustUnderstand"));
            NodeList nl = ((Element)sf).getChildNodes();
            int codeIndex = indexOf(new QName("","faultcode"),nl);
            int reasonIndex = indexOf(new QName("","faultstring"),nl);
            assertTrue("<faultcode> and <faultstring> are not in proper order",codeIndex < reasonIndex);

        }
    }

    public void testMU_SOAP11_direct() throws Exception {

        TestEndpoint port = new TestEndpointService().getTestEndpointPort();
        HTTPResponseInfo rInfo =
            ClientServerTestUtil.sendPOSTRequest( port, s11_request);
        assertEquals(500, rInfo.getResponseCode());
        String resp = rInfo.getResponseBody();
        SOAPMessage respMesg = getSOAPMessage(makeStreamSource(resp));
        SOAPBody body = respMesg.getSOAPPart().getEnvelope().getBody();
        if (!body.hasFault()) {
            fail("MU Fault not thrown");
        }
        SOAPFault sf = body.getFault();
        assertTrue(sf.getFaultCode().endsWith(":MustUnderstand"));
        NodeList nl = ((Element)sf).getChildNodes();
        int codeIndex = indexOf(new QName("", "faultcode"), nl);
        int reasonIndex = indexOf(new QName("", "faultstring"), nl);
        assertTrue("<faultcode> and <faultstring> are not in proper order", codeIndex < reasonIndex);

    }

    public void testMU_SOAP12() throws Exception {
        QName portQName = new QName("http://server.mu_header_fault.server/", "TestEndpoint12Port");
        Dispatch<SOAPMessage> dispatch = new TestEndpoint12Service().createDispatch(portQName, SOAPMessage.class, Service.Mode.MESSAGE);
        SOAPMessage message = getSOAP12Message(makeStreamSource(s12_request));
        try {
            Object result = dispatch.invoke(message);
        } catch (SOAPFaultException e) {
            SOAPFault sf = e.getFault();
            assertTrue(sf.getFaultCode().endsWith(":MustUnderstand"));
            NodeList nl = ((Element)sf).getChildNodes();
            int codeIndex = indexOf(new QName("http://www.w3.org/2003/05/soap-envelope","Code"),nl);
            int reasonIndex = indexOf(new QName("http://www.w3.org/2003/05/soap-envelope","Reason"),nl);
            assertTrue("<env:Code> and <env:Reason> are not in proper order",codeIndex < reasonIndex);

        }


    }

    public void testMU_SOAP12_direct() throws Exception {

        TestEndpoint12 port = new TestEndpoint12Service().getTestEndpoint12Port();
        HTTPResponseInfo rInfo =
            ClientServerTestUtil.sendPOSTRequest( port, s12_request,"application/soap+xml" );
        assertEquals(500, rInfo.getResponseCode());
        String resp = rInfo.getResponseBody();
        SOAPMessage respMesg = getSOAP12Message(makeStreamSource(resp));
        SOAPBody body = respMesg.getSOAPPart().getEnvelope().getBody();
        if (!body.hasFault()) {
            fail("MU Fault not thrown");
        }
        SOAPFault sf = body.getFault();
        assertTrue(sf.getFaultCode().endsWith(":MustUnderstand"));
        NodeList nl = ((Element) sf).getChildNodes();
        int codeIndex = indexOf(new QName("http://www.w3.org/2003/05/soap-envelope", "Code"), nl);
        int reasonIndex = indexOf(new QName("http://www.w3.org/2003/05/soap-envelope", "Reason"), nl);
        assertTrue("<env:Code> and <env:Reason> are not in proper order",codeIndex < reasonIndex);

    }
    private int indexOf(QName q, NodeList nl) {
        for(int i=0;i<nl.getLength();i++) {
            Node n = nl.item(i);
            if(n.getLocalName().equals(q.getLocalPart()) && fixNull(n.getNamespaceURI()).equals(q.getNamespaceURI())) {
                return i;
            }
        }
        return -1;

    }

    private String fixNull(String s) {
        if (s== null)
            return "";
        else
            return s;
    }

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
}
