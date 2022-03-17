/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.document.mime;

import com.sun.tools.ws.api.wsdl.TWSDLExtensible;
import com.sun.tools.ws.api.wsdl.TWSDLExtension;
import com.sun.tools.ws.wsdl.framework.EntityAction;
import com.sun.tools.ws.wsdl.framework.ExtensibilityHelper;
import com.sun.tools.ws.wsdl.framework.ExtensionImpl;
import org.xml.sax.Locator;

import javax.xml.namespace.QName;

/**
 * A MIME part extension.
 *
 * @author WS Development Team
 */
public class MIMEPart extends ExtensionImpl implements TWSDLExtensible {

    public MIMEPart(Locator locator) {
        super(locator);
        _helper = new ExtensibilityHelper();
    }

    @Override
    public QName getElementName() {
        return MIMEConstants.QNAME_PART;
    }

    public String getName() {
        return _name;
    }

    public void setName(String s) {
        _name = s;
    }

    @Override
    public String getNameValue() {
        return getName();
    }

    @Override
    public String getNamespaceURI() {
        return getParent().getNamespaceURI();
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
    public void withAllSubEntitiesDo(EntityAction action) {
        _helper.withAllSubEntitiesDo(action);
    }

    @Override
    public void validateThis() {
    }

    private String _name;
    private ExtensibilityHelper _helper;
}
