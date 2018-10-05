/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.soapaction_dispatch.client;

import junit.framework.TestCase;
import testutil.HTTPResponseInfo;
import testutil.ClientServerTestUtil;

import javax.xml.ws.BindingProvider;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.MessageFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;

import org.w3c.dom.Element;

/**
 * @author Rama Pulavarthi
 */
public class SOAPActionTest extends TestCase {
    private static String s11_request = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "          <S:Body>\n" +
            "              <ns2:input xmlns:ns2=\"http://server.soapaction_dispatch.server/\">\n" +
            "                  <arg0>Duke</arg0>\n" +
            "              </ns2:input>\n" +
            "          </S:Body>\n" +
            "      </S:Envelope>";

    public void testUnquotedSOAPAction1() throws Exception {
       TestEndpoint port = new TestEndpointService().getTestEndpointPort1();
        String address = (String) ((BindingProvider)port).getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
        HTTPResponseInfo rInfo =
            ClientServerTestUtil.sendPOSTRequest( address, s11_request,"text/xml","http://example.com/action/echo");
        String resp = rInfo.getResponseBody();
        SOAPMessage respMesg = getSOAPMessage(makeStreamSource(resp));
        SOAPBody body = respMesg.getSOAPPart().getEnvelope().getBody();
        Element e = (Element)body.getElementsByTagName("return").item(0);
        //make sure it is dispatched to echo() using SoapAction
        assertEquals("Hello Duke", e.getTextContent());
    }

    public void testUnquotedSOAPAction2() throws Exception {
       TestEndpoint port = new TestEndpointService().getTestEndpointPort1();
        String address = (String) ((BindingProvider)port).getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
        HTTPResponseInfo rInfo =
            ClientServerTestUtil.sendPOSTRequest( address, s11_request,"text/xml","http://example.com/action/echo1");
        String resp = rInfo.getResponseBody();
        SOAPMessage respMesg = getSOAPMessage(makeStreamSource(resp));
        SOAPBody body = respMesg.getSOAPPart().getEnvelope().getBody();
        Element e = (Element)body.getElementsByTagName("return").item(0);
        //make sure it is dispatched to echo1() using SoapAction
        assertEquals("Hello1 Duke", e.getTextContent());
    }

    public void testSOAP12Action() {
        TestEndpoint port = new TestEndpointService().getTestEndpointPort2();
        Echo input = new Echo();
        input.setArg0("Duke");
        EchoResponse response = port.echo(input);
        assertEquals("Hello Duke",response.getReturn()); 

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

}
