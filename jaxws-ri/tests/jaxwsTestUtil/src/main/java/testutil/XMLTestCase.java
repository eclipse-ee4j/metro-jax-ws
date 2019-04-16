/*
 * Copyright (c) 2006, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package testutil;

import java.io.ByteArrayInputStream;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathConstants;

import junit.framework.TestCase;
import org.xml.sax.InputSource;

/**
 * @author Arun Gupta
 */
public class XMLTestCase extends TestCase {
    XPath xpath;

    public XMLTestCase(String name) {
        super(name);
        xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext(new NamespaceContextImpl());
    }

    public void assertXpathExists(String xp, String inXml) throws Exception {
        XPathExpression xpe = xpath.compile(xp);
        InputSource inputSource = new InputSource(new ByteArrayInputStream(inXml.getBytes()));
        Object obj = xpe.evaluate(inputSource, XPathConstants.NODE);
        if (obj == null)
            throw new AssertionError();
    }

    public void assertXpathEvaluatesTo(String xp, String inXml, String expected) throws Exception {
        XPathExpression xpe = xpath.compile(xp);
        InputSource inputSource = new InputSource(new ByteArrayInputStream(inXml.getBytes()));
        String value = (String)xpe.evaluate(inputSource, XPathConstants.STRING);
        if (!expected.equals(value))
            throw new AssertionError();
    }
}
