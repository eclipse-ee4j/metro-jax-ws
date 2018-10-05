/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.xmlutil.client;

import com.sun.xml.messaging.saaj.util.ByteOutputStream;
import com.sun.xml.ws.util.xml.XMLStreamReaderToXMLStreamWriter;
import junit.framework.TestCase;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author Rama Pulavarthi
 */

public class XMLUtilTester extends TestCase {
    private final XMLOutputFactory staxOut;
    private final XMLInputFactory staxIn;
    final File folder = new File(System.getProperty("tempdir") + "/classes/xmldoc");

    public XMLUtilTester(String name) {
        super(name);
        this.staxOut = XMLOutputFactory.newInstance();
        this.staxIn = XMLInputFactory.newInstance();
        staxOut.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
    }

    public void testXMLStreamReaderToXMLStreamWriter() throws Exception {
        for (File f : folder.listFiles()) {
            System.out.println("\n\n***********"+ f.getName() + "***********");
            XMLStreamReaderToXMLStreamWriter readerToWriter = new XMLStreamReaderToXMLStreamWriter();
            XMLStreamReader reader = staxIn.createXMLStreamReader(new FileInputStream(f));
            final ByteOutputStream bos = new ByteOutputStream();
            XMLStreamWriter writer = staxOut.createXMLStreamWriter(bos);
            writer.writeStartDocument();
            readerToWriter.bridge(reader,writer);
            writer.writeEndDocument();
            writer.close();
            reader.close();
            printStream(bos.newInputStream());
            System.out.println("\n*****************************************");
        }
    }

    public static void printStream(InputStream is) {
        StreamSource source = new StreamSource(is);
        String msgString = null;
        try {
            Transformer xFormer = TransformerFactory.newInstance().newTransformer();
            xFormer.setOutputProperty("omit-xml-declaration", "yes");
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            Result result = new StreamResult(outStream);
            xFormer.transform(source, result);
            outStream.writeTo(System.out);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
