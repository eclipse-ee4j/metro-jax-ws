/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

    public QName getElementName() {
        return MIMEConstants.QNAME_PART;
    }

    public String getName() {
        return _name;
    }

    public void setName(String s) {
        _name = s;
    }

    public String getNameValue() {
        return getName();
    }

    public String getNamespaceURI() {
        return getParent().getNamespaceURI();
    }

    public QName getWSDLElementName() {
        return getElementName();
    }

    public void addExtension(TWSDLExtension e) {
        _helper.addExtension(e);
    }

    public Iterable<TWSDLExtension> extensions() {
        return _helper.extensions();
    }

    public void withAllSubEntitiesDo(EntityAction action) {
        _helper.withAllSubEntitiesDo(action);
    }

    public void validateThis() {
    }

    private String _name;
    private ExtensibilityHelper _helper;
}
