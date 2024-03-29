/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.message;

import java.io.ByteArrayInputStream;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPConstants;
import jakarta.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.message.saaj.SAAJMessage;
import com.sun.xml.ws.api.addressing.AddressingVersion;

import junit.framework.TestCase;

public class ReplaceAddressingHeaderTest extends TestCase {


  public static final String TEST_NS = "http://jaxws.dev.java.net/";
  private HeaderList testInstance;

  public ReplaceAddressingHeaderTest(String name) {
      super(name);
  }

  @Override
  protected void setUp() throws Exception {
      super.setUp();

      testInstance = new HeaderList(SOAPVersion.SOAP_11);
  }

  @Override
  protected void tearDown() throws Exception {
      super.tearDown();

      testInstance = null;
  }

  public void testReplaceBehavior() throws Exception {

      String reqMsgStr ="<env:Envelope xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\">" +
      "<env:Header>" +
      "<r:AckRequested xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:r=\"http://docs.oasis-open.org/ws-rx/wsrm/200702\" xmlns:a=\"http://www.w3.org/2005/08/addressing\">" +
      "<r:Identifier>uuid:WLS2:store:WseeJaxwsFileStore:dece97a1d44772e7:-3fbed9f:13b4b7da0a6:-7fb6</r:Identifier>" +
      "</r:AckRequested>" +
      "<a:Action xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:r=\"http://docs.oasis-open.org/ws-rx/wsrm/200702\" " +
      "xmlns:a=\"http://www.w3.org/2005/08/addressing\" s:mustUnderstand=\"1\">http://docs.oasis-open.org/ws-rx/wsrm/200702/AckRequested</a:Action>" +
      "<a:To xmlns:s=\"http://www.w3.org/2003/05/soap-envelop\" " +
      "xmlns:r=\"http://docs.oasis-open.org/ws-rx/wsrm/200702\" xmlns:a=\"http://www.w3.org/2005/08/addressing\" " +
      "s:mustUnderstand=\"1\">http://10.245.29.191:9902/ReliableMessaging_Service_WSAddressing10_Indigo/OneWay.svc/Reliable_Addressable_Soap12_WSAddressing10_RM11</a:To>" +
      "</env:Header>" +
      "<env:Body/>" +
      "</env:Envelope>";

      String respMsgStr = "<env:Envelope xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\">" +
      "<env:Header>" +
      "<Action xmlns=\"http://www.w3.org/2005/08/addressing\">http://docs.oasis-open.org/ws-rx/wsrm/200702/SequenceAcknowledgement</Action>" +
      "<wsrm11:SequenceAcknowledgement xmlns:wsrm11=\"http://docs.oasis-open.org/ws-rx/wsrm/200702\">" +
      "<wsrm11:Identifier>uuid:WLS2:store:WseeJaxwsFileStore:dece97a1d44772e7:-3fbed9f:13b4b7da0a6:-7fb6</wsrm11:Identifier>" +
      "<wsrm11:AcknowledgementRange Lower=\"1\" Upper=\"3\"/>" +
      "</wsrm11:SequenceAcknowledgement>" +
      "<To xmlns=\"http://www.w3.org/2005/08/addressing\">" +
      "http://10.244.13.245:8000/bcabf5e4-d888-403c-a93e-99ed5e7f4a40/fe5c31e3-a8af-40ed-8066-e41c3ba9f742</To>" +
      "<ns0:ReplyTo xmlns:ns0=\"http://www.w3.org/2005/08/addressing\">" +
      "<ns0:Address>" +
      "http://10.245.29.191:9902/ReliableMessaging_Service_WSAddressing10_Indigo/OneWay.svc/Reliable_Addressable_Soap12_WSAddressing10_RM11" +
      "</ns0:Address>" +
      "<ns0:Metadata xmlns:ns1=\"http://www.w3.org/ns/wsdl-instance\" " +
      "ns1:wsdlLocation=\"http://tempuri.org/http://10.245.29.191:9902/ReliableMessaging_Service_WSAddressing10_Indigo/OneWay.svc/Reliable_Addressable_Soap12_WSAddressing10_RM11?wsdl\">" +
      "<wsam:InterfaceName xmlns:wsam=\"http://www.w3.org/2007/05/addressing/metadata\" xmlns:wsns=\"http://tempuri.org/\">" +
      "wsns:IPing</wsam:InterfaceName>" +
      "<wsam:ServiceName xmlns:wsam=\"http://www.w3.org/2007/05/addressing/metadata\" xmlns:wsns=\"http://tempuri.org/\" EndpointName=\"CustomBinding_IPing10\">" +
      "wsns:PingService</wsam:ServiceName>" +
      "</ns0:Metadata>" +
      "</ns0:ReplyTo>" +
      "</env:Header>" +
      "<env:Body/></env:Envelope>";

      AddressingVersion av = AddressingVersion.W3C;
      SOAPVersion sv = SOAPVersion.SOAP_12;
      String action = "http://docs.oasis-open.org/ws-rx/wsrm/200702/SequenceAcknowledgement";

      SAAJMessage reqMsg = new SAAJMessage(makeSOAPMessage(reqMsgStr));
      SAAJMessage respMsg = new SAAJMessage(makeSOAPMessage(respMsgStr));
      HeaderList hdrs = (HeaderList) respMsg.getHeaders();
      String originToHeader = hdrs.getTo(av, sv);

      Packet responsePacket=null;
      try{
          responsePacket = new Packet(reqMsg).createServerResponse(respMsg, av, sv, action);
      } catch (Exception e) {
          e.printStackTrace();
      }

      //check toHeader
      String toHeaderAfterProcessed = AddressingUtils.getTo(responsePacket.getHeaderList(), av, sv);
      assertEquals(toHeaderAfterProcessed, originToHeader);
  }
  private SOAPMessage makeSOAPMessage(String msg) throws Exception {
      MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
      SOAPMessage message = factory.createMessage();
      Source src = new StreamSource(new ByteArrayInputStream(msg.getBytes()));
      message.getSOAPPart().setContent(src);
      return message;
  }

}
