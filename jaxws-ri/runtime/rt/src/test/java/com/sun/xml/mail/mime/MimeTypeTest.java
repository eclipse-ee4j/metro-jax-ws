/*
 * Copyright (c) 2016, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.mail.mime;

import java.io.IOException;
import java.util.Properties;
import jakarta.mail.internet.MimeMessage;
import jakarta.activation.CommandMap;
import jakarta.activation.MailcapCommandMap;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetHeaders;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMultipart;
import junit.framework.TestCase;

/**
 * Test MIME types.
 */
public class MimeTypeTest extends TestCase {

    /**
     * Test mail message MIME type processing with lower case mime type.
     */
    public void testApplicationXMLMimeLowerCase() throws MessagingException, IOException {
        checkApplicationXMLMime("application/xml");
    }

    /**
     * Test mail message MIME type processing with upper case mime type.
     */
    public void testApplicationXMLMimeUpperCase() throws MessagingException, IOException {
        checkApplicationXMLMime("APPLICATION/XML");
    }

    /**
     * Test mail message MIME type processing with mixed case mime type.
     */
    public void testApplicationXMLMimeMixedCase() throws MessagingException, IOException {
        checkApplicationXMLMime("application/XML");
    }

    /**
     * Test mail message MIME type processing with provided MIME type.
     * @param mimeType MIME type to be tested.
     * @throws IOException {@code Cannot convert DataSource with content type "application/XML" to object in XmlDataContentHandler}
     *         when MIME type is not recognized.
     */
    private void checkApplicationXMLMime(final String mimeType) throws MessagingException, IOException {
        MailcapCommandMap mailcapCommandMap = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mailcapCommandMap.addMailcap("application/XML;; x-java-content-handler=com.sun.xml.ws.encoding.XmlDataContentHandler");
        mailcapCommandMap.addMailcap("application/xml;; x-java-content-handler=com.sun.xml.ws.encoding.XmlDataContentHandler");
        MimeMessage message;
        MimeMultipart mimeMultipart;
        final byte[] bodyConent = "<test/>".getBytes();
        InternetHeaders headers = new InternetHeaders();
        headers.setHeader("Content-Transfer-Encoding", "binary");
        headers.setHeader("Content-Disposition", "attachment; filename=test.xml");
        message = new MimeMessage(Session.getDefaultInstance(new Properties()));
        mimeMultipart = new MimeMultipart();
        headers.setHeader("Content-Type", mimeType);
        mimeMultipart.addBodyPart(new MimeBodyPart(headers, bodyConent));
        message.setContent(mimeMultipart);
        message.saveChanges();
        try {
            ((MimeMultipart) message.getContent()).getBodyPart(0).getContent();
        } catch (IOException ex) {
            throw ex;
        }
    }

}
