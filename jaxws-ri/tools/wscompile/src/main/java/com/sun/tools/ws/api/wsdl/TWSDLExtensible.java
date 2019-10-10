/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.api.wsdl;


import javax.xml.namespace.QName;

/**
 * A WSDL element or attribute that can be extended.
 *
 * @author Vivek Pandey
 * @deprecated This interface is deprecated, will be removed in JAX-WS 2.2 RI.
 *
 */
public interface TWSDLExtensible {
    /**
     * Gives the wsdl extensiblity element's name attribute value. It can be null as @name on some of the wsdl
     * extensibility elements are optinal such as wsdl:input
     */
    String getNameValue();

    /**
     * Gives namespace URI of a wsdl extensibility element.
     */
    String getNamespaceURI();

    /**
     * Gives the WSDL element or WSDL extensibility element name
     */
    QName getWSDLElementName();

    /**
     * An {@link TWSDLExtensionHandler} will call this method to add an {@link TWSDLExtension} object
     *
     * @param e non-null extension object
     */
    void addExtension(TWSDLExtension e);

    /**
     * Gives iterator over {@link TWSDLExtension}s
     */
    Iterable<? extends TWSDLExtension> extensions();

    /**
     * Gives the parent of a wsdl extensibility element.
     * <pre>{@code
     * For example,
     *
     *     <wsdl:portType>
     *         <wsdl:operation>
     *     ...
     * }</pre>
     * Here, the {@link TWSDLExtensible}representing wsdl:operation's parent would be wsdl:portType
     *
     * @return null if the {@link TWSDLExtensible} has no parent, root of wsdl document - wsdl:definition.
     */
    TWSDLExtensible getParent();
}
