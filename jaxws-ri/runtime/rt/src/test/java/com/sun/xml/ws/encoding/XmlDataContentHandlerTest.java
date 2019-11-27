/*
 * Copyright (c) 2016, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.encoding;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import javax.activation.DataSource;
import junit.framework.TestCase;

/**
 * Test JAF data handler for XML content.
 */
public class XmlDataContentHandlerTest extends TestCase {

    /**
     * Data source mock-up for {@link XmlDataContentHandler} class test.
     */
    private static class DataSourceMockUp implements DataSource {

        /** Content type. */
        private final String contentType;

        /** Data source name. */
        private final String name;

        // ByteArrayInputStream does not need to be closed.
        /** Simple input stream. */
        private final ByteArrayInputStream is;

        /**
         * Creates an instance of dummy data source.
         * @param name Data source name.
         * @param contentType Content type.
         */
        private DataSourceMockUp(final String name, final String contentType) {
            this.name = name;
            this.contentType = contentType;
            final byte[] buffIn = ("Content type: " + contentType).getBytes(Charset.forName("UTF-8"));
            is = new ByteArrayInputStream(buffIn);
        }

        /**
         * Returns simple input stream.
         * @return simple input stream.
         */
        @Override
        public InputStream getInputStream() throws IOException {
            return is;
        }

        /**
         * Not supported.
         * @return nothing.
         * @throws IOException is never thrown.
         * @throws UnsupportedOperationException on every call.
         */
        @Override
        public OutputStream getOutputStream() throws IOException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        /**
         * Returns stored content type.
         * @return stored content type.
         */
        @Override
        public String getContentType() {
            return contentType;
        }

        /**
         * Returns stored name.
         * @return stored name.
         */
        @Override
        public String getName() {
            return name;
        }

    }

    /**
     * Creates an instance of this test.
     * @param testName Test name.
     */
    public XmlDataContentHandlerTest(String testName) {
        super(testName);
    }

    /**
     * Test MIME type processing in {@code isXml(ContentType)} method
     * of {@link XmlDataContentHandler} class with lower case mime type.
     * @throws ClassNotFoundException
     */
    public void testIsXmlLowerCase() throws Exception {
        checkIsXml("application/xml");
    }

    /**
     * Test MIME type processing in {@code isXml(ContentType)} method
     * of {@link XmlDataContentHandler} class with upper case mime type.
     * @throws ClassNotFoundException
     */
    public void testIsXmlUpperCase() throws ClassNotFoundException, IOException {
        checkIsXml("APPLICATION/XML");
    }

    /**
     * Test MIME type processing in {@code isXml(ContentType)} method
     * of {@link XmlDataContentHandler} class with mixed case mime type.
     * @throws ClassNotFoundException
     */
    public void testIsXmlMixedCase() throws ClassNotFoundException, IOException {
        checkIsXml("application/XML");
    }

    /**
     * Test MIME type processing in {@code isXml(ContentType)} method of {@link XmlDataContentHandler} class.
     * @param mimeType MIME type to be tested.
     */
    private void checkIsXml(final String mimeType) throws ClassNotFoundException {
        XmlDataContentHandler handler = new XmlDataContentHandler();
        DataSource ds = new DataSourceMockUp("dummy", mimeType);
        try {
            handler.getContent(ds);
        } catch (IOException ex) {
            fail(ex.getMessage());
        }
    }

}
