/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.api.wsdl;

import org.w3c.dom.Element;
import org.xml.sax.Locator;

/**
 * Provides WSDL parsing context. It should be used by the WSDL extension handlers to register their namespaces so that
 * it can be latter used by other extensions to resolve the namespaces.
 *
 * @author Vivek Pandey
 * @deprecated This interface is deprecated, will be removed in JAX-WS 2.2 RI.
 */
@Deprecated
public interface TWSDLParserContext {

    /**
     * Pushes the parsing context
     */
    void push();

    /**
     * pops the parsing context
     */
    void pop();

    /**
     * Gives the namespace URI for a given prefix
     *
     * @param prefix non-null prefix
     * @return null of the prefix is not found
     */
    String getNamespaceURI(String prefix);

    /**
     * Gives the prefixes in the current context
     */
    Iterable<String> getPrefixes();

    /**
     * Gives default namespace
     *
     * @return null if there is no default namespace declaration found
     */
    String getDefaultNamespaceURI();

    /**
     * Registers naemespace declarations of a given {@link Element} found in the WSDL
     *
     * @param e {@link Element} whose namespace declarations need to be registered
     */
    void registerNamespaces(Element e);

    /**
     * gives the location information for the given Element.
     */
    Locator getLocation(Element e);

}
