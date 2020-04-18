/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package provider.attachment_595.client;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import javax.xml.namespace.QName;
import jakarta.xml.soap.AttachmentPart;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

/**
 * Handler adds an attachment on the outbound direction.
 *
 * @author Jitendra Kotamraju
 */
public class SOAPTestHandler implements SOAPHandler<SOAPMessageContext> {

    public Set<QName> getHeaders() {
        return null;
    }

    public boolean handleMessage(SOAPMessageContext smc) {
        if (!(Boolean)smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY))
            return true;
        try {
            SOAPMessage msg = smc.getMessage();
            AttachmentPart part = msg.createAttachmentPart(getDataHandler("gpsXml.xml"));
            part.setContentId("SOAPTestHandler@example.jaxws.sun.com");
            msg.addAttachmentPart(part);
            msg.saveChanges();
            smc.setMessage(msg);
        } catch (Exception e) {
            throw new WebServiceException(e);
        }
        return true;
    }

    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    public void close(MessageContext context) {}
    
    private DataHandler getDataHandler(final String file) throws Exception {
        return new DataHandler(new DataSource() {
            public String getContentType() {
                return "text/xml";
            }

            public InputStream getInputStream() {
                try {
                    return getClass().getModule().getResourceAsStream(file);
                } catch (java.io.IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String getName() {
                return null;
            }

            public OutputStream getOutputStream() {
                throw new UnsupportedOperationException();
            }
        });
    }

}
