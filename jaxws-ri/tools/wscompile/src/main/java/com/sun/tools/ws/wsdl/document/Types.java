/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.document;

import com.sun.tools.ws.api.wsdl.TWSDLExtensible;
import com.sun.tools.ws.api.wsdl.TWSDLExtension;
import com.sun.tools.ws.wsdl.framework.Entity;
import com.sun.tools.ws.wsdl.framework.EntityAction;
import com.sun.tools.ws.wsdl.framework.ExtensibilityHelper;
import com.sun.tools.ws.wsdl.framework.ExtensionVisitor;
import org.xml.sax.Locator;

import javax.xml.namespace.QName;

/**
 * Entity corresponding to the "types" WSDL element.
 *
 * @author WS Development Team
 */
@SuppressWarnings({"deprecation"})
public class Types extends Entity implements TWSDLExtensible {

    public Types(Locator locator) {
        super(locator);
        _helper = new ExtensibilityHelper();
    }

    @Override
    public QName getElementName() {
        return WSDLConstants.QNAME_TYPES;
    }

    public Documentation getDocumentation() {
        return _documentation;
    }

    public void setDocumentation(Documentation d) {
        _documentation = d;
    }

    public void accept(WSDLDocumentVisitor visitor) throws Exception {
        visitor.preVisit(this);
        _helper.accept(visitor);
        visitor.postVisit(this);
    }

    @Override
    public void validateThis() {
    }

    /**
     * wsdl:type does not have any name attribute
     */
    @Override
    public String getNameValue() {
        return null;
    }

    @Override
    public String getNamespaceURI() {
        return (parent == null) ? null : parent.getNamespaceURI();
    }

    @Override
    public QName getWSDLElementName() {
        return getElementName();
    }

    @Override
    public void addExtension(TWSDLExtension e) {
        _helper.addExtension(e);
    }

    @Override
    public Iterable<TWSDLExtension> extensions() {
        return _helper.extensions();
    }

    @Override
    public TWSDLExtensible getParent() {
        return parent;
    }

    public void setParent(TWSDLExtensible parent) {
        this.parent = parent;
    }

    @Override
    public void withAllSubEntitiesDo(EntityAction action) {
        _helper.withAllSubEntitiesDo(action);
    }

    public void accept(ExtensionVisitor visitor) throws Exception {
        _helper.accept(visitor);
    }

    private TWSDLExtensible parent;
    private ExtensibilityHelper _helper;
    private Documentation _documentation;
}
