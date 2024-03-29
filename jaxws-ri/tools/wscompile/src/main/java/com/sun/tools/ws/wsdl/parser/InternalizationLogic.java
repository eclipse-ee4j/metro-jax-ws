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

import org.w3c.dom.Element;
import org.xml.sax.helpers.XMLFilterImpl;

/**
 * Encapsulates schema-language dependent internalization logic.
 * <p>
 * {@code com.sun.tools.xjc.reader.internalizer.Internalizer} and {@link DOMForest} are responsible for
 * doing schema language independent part, and this object is responsible
 * for schema language dependent part.
 *
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 *     Vivek Pandey
 */
public interface InternalizationLogic {
    /**
     * Creates a new instance of XMLFilter that can be used to
     * find references to external schemas.
     *
     * <p>
     * Schemas that are included/imported need to be a part of
     * {@link DOMForest}, and this filter will be expected to
     * find such references.
     *
     * <p>
     * Once such a reference is found, the filter is expected to
     * call the parse method of DOMForest.
     *
     * <p>
     * {@link DOMForest} will register ErrorHandler to the returned
     * object, so any error should be sent to that error handler.
     *
     * @return
     *      This method returns {@link org.xml.sax.helpers.XMLFilterImpl} because
     *      the filter has to be usable for two directions
     *      (wrapping a reader and wrapping a ContentHandler)
     */
    XMLFilterImpl createExternalReferenceFinder( DOMForest parent );

    /**
     * Checks if the specified element is a valid target node
     * to attach a customization.
     *
     * @param parent
     *      The owner DOMForest object. Probably useful only
     *      to obtain context information, such as error handler.
     * @param bindings
     *      &lt;jaxb:bindings&gt; element or a customization element.
     * @return
     *      true if it's OK, false if not.
     */
    boolean checkIfValidTargetNode( DOMForest parent, Element bindings, Element target );

    /**
     * Prepares an element that actually receives customizations.
     *
     * <p>
     * For example, in XML Schema, target nodes can be any schema
     * element but it is always the &lt;xsd:appinfo&gt; element that
     * receives customization.
     *
     * @param target
     *      The target node designated by the customization.
     * @return
     *      Always return non-null valid object
     */
    Element refineSchemaTarget( Element target );

    /**
     * Prepares a WSDL element that actually receives customizations.
     *
     *
     * @param target
     *      The target node designated by the customization.
     * @return
     *      Always return non-null valid object
     */
    Element refineWSDLTarget( Element target );

}

