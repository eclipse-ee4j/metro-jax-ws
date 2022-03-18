/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.message.saaj_986.client;

import com.sun.xml.ws.message.saaj.SAAJMessage;
import junit.framework.TestCase;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;

/**
 * Tests the SAAJMessage implementation for issue: 986
 *
 * @author Iaroslav Savytskyi
 */
public class SaajMessageTest extends TestCase {

    private static final String MESSAGE = "<S:Envelope xmlns:S='http://schemas.xmlsoap.org/soap/envelope/'>" +
            "<S:Header>" +
            "<wsa:Action xmlns:wsa='http://www.w3.org/2005/08/addressing'>http://example.com/addNumbers</wsa:Action>" +
            "</S:Header>" +
            "<S:Body attr='value'>" +
            "<addNumbers xmlns='http://example.com/'/>" +
            "</S:Body></S:Envelope>";

    /**
     * Test for body attribute after SAAJMessage.copy()
     *
     * @throws Exception - error
     */
    public void testBodyAttr() throws Exception {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage soapMessage = factory.createMessage();
        Source src = new StreamSource(new ByteArrayInputStream(MESSAGE.getBytes()));
        soapMessage.getSOAPPart().setContent(src);

        SAAJMessage saajMsg = new SAAJMessage(soapMessage);
        saajMsg.hasHeaders(); // breaks the underlying SOAPMessage
        saajMsg = (SAAJMessage) saajMsg.copy();
        SOAPBody soapBody = saajMsg.readAsSOAPMessage().getSOAPBody();
        assertEquals("value", soapBody.getAttribute("attr"));
    }
}
