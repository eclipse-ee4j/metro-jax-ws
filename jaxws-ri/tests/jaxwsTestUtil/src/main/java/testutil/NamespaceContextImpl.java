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

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;
import javax.xml.XMLConstants;

import com.sun.xml.ws.addressing.W3CAddressingConstants;
import com.sun.xml.ws.addressing.v200408.MemberSubmissionAddressingConstants;

/**
 * @author Arun Gupta
 */
public class NamespaceContextImpl implements NamespaceContext {
    public NamespaceContextImpl() {
        bindPrefixToNS("S11", WsaUtils.S11_NS);
        bindPrefixToNS("S12", WsaUtils.S12_NS);
        bindPrefixToNS("wsa", W3CAddressingConstants.WSA_NAMESPACE_NAME);
        bindPrefixToNS("wsa04", MemberSubmissionAddressingConstants.WSA_NAMESPACE_NAME);
        bindPrefixToNS("ck", "http://example.org/customer");
    }

    private static final Map<String, String> prefixToNSMap = new HashMap<String, String>();
    private static final Map<String, String> nsToPrefixMap = new HashMap<String, String>();

    public void bindPrefixToNS(String prefix, String namespaceURI) {
        prefixToNSMap.put(prefix, namespaceURI);
        nsToPrefixMap.put(namespaceURI, prefix);
    }

    public String getNamespaceURI(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException(
                    "NamespaceContextImpl#getNamespaceURI(String prefix) with prefix == null");
        }

        // constants
        if (prefix.equals(XMLConstants.XML_NS_PREFIX)) {
            return XMLConstants.XML_NS_URI;
        }
        if (prefix.equals(XMLConstants.XMLNS_ATTRIBUTE)) {
            return XMLConstants.XMLNS_ATTRIBUTE_NS_URI;
        }

        // default
        if (prefix.equals(XMLConstants.DEFAULT_NS_PREFIX)) {
            if (prefixToNSMap.containsKey(prefix)) {
                return prefixToNSMap.get(prefix);
            } else {
                return XMLConstants.NULL_NS_URI;
            }
        }

        // bound
        if (prefixToNSMap.containsKey(prefix)) {
            return prefixToNSMap.get(prefix);
        }

        // unbound
        return XMLConstants.NULL_NS_URI;
    }

    public String getPrefix(String namespaceURI) {
        if (namespaceURI == null) {
            throw new IllegalArgumentException(
                    "NamespaceContextImpl#getPrefix(String namespaceURI) with namespaceURI == null");
        }

        // constants
        if (namespaceURI.equals(XMLConstants.XML_NS_URI)) {
            return XMLConstants.XML_NS_PREFIX;
        }
        if (namespaceURI.equals(XMLConstants.XMLNS_ATTRIBUTE_NS_URI)) {
            return XMLConstants.XMLNS_ATTRIBUTE;
        }

        // bound
        if (nsToPrefixMap.containsKey(namespaceURI)) {
            return nsToPrefixMap.get(namespaceURI);
        }

        // mimic "default Namespace URI"
        if (namespaceURI.equals(XMLConstants.NULL_NS_URI)) {
            return XMLConstants.DEFAULT_NS_PREFIX;
        }

        // unbound
        return null;
    }

    public Iterator getPrefixes(String namespaceURI) {
        return prefixToNSMap.keySet().iterator();
    }
}
