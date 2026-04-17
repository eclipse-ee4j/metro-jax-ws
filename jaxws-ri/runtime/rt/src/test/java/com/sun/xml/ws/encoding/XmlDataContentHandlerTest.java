/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
 * Copyright (c) 2016, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.encoding;

import jakarta.activation.DataSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import javax.xml.transform.stream.StreamSource;

import junit.framework.TestCase;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.Assume.assumeNoException;

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
         * @param mimeType Content type.
         */
        private DataSourceMockUp(final String name, final String contentType, final String charset) {
            this(name, new byte[0], contentType, charset);
        }

        private DataSourceMockUp(final String name, final byte[] body, final String contentType, final String charset) {
            this.name = name;
            this.contentType = contentType + (charset == null ? "" : ("; charset=" + charset));
            this.is = new ByteArrayInputStream(body);
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
     */
    public void testIsXmlLowerCase() throws Exception {
        checkIsXml("application/xml", UTF_8.name());
    }

    /**
     * Test MIME type processing in {@code isXml(ContentType)} method
     * of {@link XmlDataContentHandler} class with upper case mime type.
     */
    public void testIsXmlUpperCase() throws Exception {
        checkIsXml("APPLICATION/XML", UTF_8.name());
    }

    /**
     * Test MIME type processing in {@code isXml(ContentType)} method
     * of {@link XmlDataContentHandler} class with mixed case mime type.
     */
    public void testIsXmlMixedCase() throws Exception {
        checkIsXml("application/XML", UTF_8.name());
    }

    /**
     * Test MIME type processing in {@code isXml(ContentType)} method
     * of {@link XmlDataContentHandler} class with mixed case mime type.
     */
    public void testContentType_incompatibleCharset() throws Exception {
        String mimeType = "application/xml";
        XmlDataContentHandler handler = new XmlDataContentHandler();
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<body>Příliš žluťoučký kůň</body>\n";
        DataSource ds = new DataSourceMockUp("dummy", xml.getBytes(UTF_8), mimeType, ISO_8859_1.name());
        StreamSource content = (StreamSource) handler.getContent(ds);
        assertNull(content.getInputStream());
        Reader reader = content.getReader();
        assertNotNull(reader);
        CharBuffer buffer = CharBuffer.allocate(8192);
        assertEquals(82, reader.read(buffer));
        String output = buffer.rewind().toString();
        // To prove that the charset was respected - and broke the file content.
        assertThat(output, stringContainsInOrder("<?xml", "<body>", "¾", "</body>"));
    }

    /**
     * Test MIME type processing in {@code isXml(ContentType)} method
     * of {@link XmlDataContentHandler} class with mixed case mime type.
     */
    public void testContentType_wrongCharsetInXml() throws Exception {
        try {
            Charset.forName("ISO-8859-2");
        } catch (UnsupportedCharsetException e) {
            assumeNoException("Ignoring test because the required charset is not supported on this system.", e);
        }
        String mimeType = "application/xml";
        XmlDataContentHandler handler = new XmlDataContentHandler();
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<body>Příliš žluťoučký kůň</body>\n";
        DataSource ds = new DataSourceMockUp("dummy", xml.getBytes("ISO-8859-2"), mimeType, "iso-8859-2");
        StreamSource content = (StreamSource) handler.getContent(ds);
        assertNull(content.getInputStream());
        Reader reader = content.getReader();
        assertNotNull(reader);
        CharBuffer buffer = CharBuffer.allocate(8192);
        assertEquals(73, reader.read(buffer));
        String output = buffer.rewind().toString();
        // The string is not equal for some reason
        assertThat(output, stringContainsInOrder(xml));
    }

    /**
     * Test MIME type processing in {@code isXml(ContentType)} method of {@link XmlDataContentHandler} class.
     * @param mimeType MIME type to be tested.
     */
    private void checkIsXml(final String mimeType, String charset) throws ClassNotFoundException, IOException {
        XmlDataContentHandler handler = new XmlDataContentHandler();
        DataSource ds = new DataSourceMockUp("dummy", mimeType, charset);
        assertThat(handler.getContent(ds), instanceOf(StreamSource.class));
    }

}
