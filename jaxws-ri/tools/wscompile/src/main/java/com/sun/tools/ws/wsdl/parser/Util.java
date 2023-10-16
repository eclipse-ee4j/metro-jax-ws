/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.parser;

import com.sun.tools.ws.wsdl.framework.ParseException;
import com.sun.xml.ws.util.xml.XmlUtil;
import org.w3c.dom.Comment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import javax.xml.namespace.QName;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

/**2
 * Defines various utility methods.
 *
 * @author WS Development Team
 */
public final class Util {

    private Util() {}

    public static String getRequiredAttribute(Element element, String name) {
        String result = XmlUtil.getAttributeOrNull(element, name);
        if (result == null)
            fail(
                "parsing.missingRequiredAttribute",
                element.getTagName(),
                name);
        return result;
    }

    public static void verifyTag(Element element, String tag) {
        if (!element.getLocalName().equals(tag))
            fail("parsing.invalidTag", element.getTagName(), tag);
    }

    public static void verifyTagNS(Element element, String tag, String nsURI) {
        if (!element.getLocalName().equals(tag)
            || (element.getNamespaceURI() != null
                && !element.getNamespaceURI().equals(nsURI)))
            fail(
                "parsing.invalidTagNS",
                new Object[] {
                    element.getTagName(),
                    element.getNamespaceURI(),
                    tag,
                    nsURI });
    }

    public static void verifyTagNS(Element element, QName name) {
        if (!isTagName(element, name))
            fail(
                "parsing.invalidTagNS",
                new Object[] {
                    element.getTagName(),
                    element.getNamespaceURI(),
                    name.getLocalPart(),
                    name.getNamespaceURI()});
    }

    public static boolean isTagName(Element element, QName name){
        return (element.getLocalName().equals(name.getLocalPart())
            && (element.getNamespaceURI() != null
                && element.getNamespaceURI().equals(name.getNamespaceURI())));

    }

    public static void verifyTagNSRootElement(Element element, QName name) {
        if (!element.getLocalName().equals(name.getLocalPart())
            || (element.getNamespaceURI() != null
                && !element.getNamespaceURI().equals(name.getNamespaceURI())))
            fail(
                "parsing.incorrectRootElement",
                new Object[] {
                    element.getTagName(),
                    element.getNamespaceURI(),
                    name.getLocalPart(),
                    name.getNamespaceURI()});
    }

    public static Element nextElementIgnoringCharacterContent(Iterator iter) {
        while (iter.hasNext()) {
            Node n = (Node) iter.next();
            if (n instanceof Text)
                continue;
            if (n instanceof Comment)
                continue;
            if (!(n instanceof Element))
                fail("parsing.elementExpected");
            return (Element) n;
        }

        return null;
    }

    public static Element nextElement(Iterator iter) {
        while (iter.hasNext()) {
            Node n = (Node) iter.next();
            if (n instanceof Text) {
                Text t = (Text) n;
                if (t.getData().trim().isEmpty())
                    continue;
                fail("parsing.nonWhitespaceTextFound", t.getData().trim());
            }
            if (n instanceof Comment)
                continue;
            if (!(n instanceof Element))
                fail("parsing.elementExpected");
            return (Element) n;
        }

        return null;
    }

    public static String processSystemIdWithBase(
        String baseSystemId,
        String systemId) {
        try {
            URL base = null;
            try {
                base = new URL(baseSystemId);
            } catch (MalformedURLException e) {
                base = new File(baseSystemId).toURI().toURL();
            }

            try {
                URL url = new URL(base, systemId);
                return url.toString();
            } catch (MalformedURLException e) {
                fail("parsing.invalidURI", systemId);
            }

        } catch (MalformedURLException e) {
            fail("parsing.invalidURI", baseSystemId);
        }

        return null; // keep compiler happy
    }

    public static void fail(String key) {
        throw new ParseException(key);
    }

    public static void fail(String key, String arg) {
        throw new ParseException(key, arg);
    }

    public static void fail(String key, String arg1, String arg2) {
        throw new ParseException(key, arg1, arg2);
    }

    public static void fail(String key, Object[] args) {
        throw new ParseException(key, args);
    }
}
