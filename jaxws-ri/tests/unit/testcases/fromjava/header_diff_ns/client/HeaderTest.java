/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.header_diff_ns.client;

import testutil.XMLTestCase;
import junit.framework.TestCase;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPath;
import java.io.IOException;
import java.net.URL;

import org.xml.sax.InputSource;

/**
 * @author Rama Pulavarthi
 */
public class HeaderTest extends XMLTestCase {
    public HeaderTest(String s) {
        super(s);
    }

    /**
     * Testcase for issue 733
     */
    public void testHeader() throws IOException, XPathExpressionException {
        String expr= "/*[name()='definitions']/*[name()='message'][@name='addNumbers']/*[name()='part'][@name='valueheader']/@element";
        String result = evaluateXpath(expr);
        assertNotNull(result);
        System.out.println(":::::::::"+result);
        String prefix = result.substring(0,result.indexOf(':'));
        String elementName = result.substring(result.indexOf(':')+1);
        assertEquals("valueheader",elementName);

        String prefixexpr ="//namespace::*[name() = '"+prefix+"']";
        String namespace = evaluateXpath(prefixexpr);
        assertEquals("urn:mycompany/headers", namespace);
    }

    private String evaluateXpath(String expr) throws XPathExpressionException, IOException {
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        xpath.setNamespaceContext(new NSContextImpl());
        String wsdl = System.getProperty("calculatorPortAddress") + "?wsdl";
        return xpath.evaluate(expr, new InputSource(new URL(wsdl).openStream()));
    }

}
