/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package provider.attachment_595.server;

import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.*;
import java.io.ByteArrayInputStream;
import java.util.Iterator;

/**
 * Verifies if the endpoint receives the attachment added by the handler.
 *
 * @author Jitendra Kotamraju 
 */
@WebServiceProvider(targetNamespace="urn:test", portName="HelloPort", serviceName="Hello")
@ServiceMode(value=Service.Mode.MESSAGE)
public class SOAPMsgProvider implements Provider<SOAPMessage> {

    public SOAPMessage invoke(SOAPMessage msg) {
        Iterator it = msg.getAttachments();
        if (!it.hasNext()) {
            throw new WebServiceException("Service didn't receive any attachments.");
        }
        AttachmentPart part = (AttachmentPart)it.next();
        if (!part.getContentId().equals("<SOAPTestHandler@example.jaxws.sun.com>")) {
            throw new WebServiceException("Attachment Content-ID expected="
                +"SOAPTestHandler@example.jaxws.sun.com got="+part.getContentId());
        }
        try {
            // keeping white space in the string is intentional
            String content = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body>  <VoidTestResponse xmlns=\"urn:test:types\"></VoidTestResponse></soapenv:Body></soapenv:Envelope>";
            Source source = new StreamSource(new ByteArrayInputStream(content.getBytes()));
            MessageFactory fact = MessageFactory.newInstance();
            SOAPMessage soap = fact.createMessage();
            soap.getSOAPPart().setContent(source);
            soap.getMimeHeaders().addHeader("foo", "bar");
            return soap;
        } catch(Exception e) {
            throw new WebServiceException(e);
        }
    }

}
