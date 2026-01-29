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
import com.sun.tools.ws.wsdl.framework.*;
import com.sun.tools.ws.resources.WsdlMessages;
import com.sun.tools.ws.wscompile.AbortException;
import com.sun.tools.ws.wscompile.ErrorReceiver;
import org.xml.sax.Locator;

import javax.xml.namespace.QName;

/**
 * Entity corresponding to the "port" WSDL element.
 *
 * @author WS Development Team
 */
@SuppressWarnings({"deprecation"})
public class Port extends GlobalEntity implements TWSDLExtensible {

    public Port(Defining defining, Locator locator, ErrorReceiver errReceiver) {
        super(defining, locator, errReceiver);
        _helper = new ExtensibilityHelper();
    }

    public Service getService() {
        return _service;
    }

    public void setService(Service s) {
        _service = s;
    }

    public QName getBinding() {
        return _binding;
    }

    public void setBinding(QName n) {
        _binding = n;
    }

    public Binding resolveBinding(AbstractDocument document) {
        try{
            return (Binding) document.find(Kinds.BINDING, _binding);
        } catch (NoSuchEntityException e) {
            errorReceiver.error(getLocator(), WsdlMessages.ENTITY_NOT_FOUND_BINDING(_binding, new QName(getNamespaceURI(), getName())));
            throw new AbortException();
        }
    }

    @Override
    public Kind getKind() {
        return Kinds.PORT;
    }

    @Override
    public String getNameValue() {
        return getName();
    }

    @Override
    public String getNamespaceURI() {
        return getDefining().getTargetNamespaceURI();
    }

    @Override
    public QName getWSDLElementName() {
        return WSDLConstants.QNAME_PORT;
    }

    public Documentation getDocumentation() {
        return _documentation;
    }

    public void setDocumentation(Documentation d) {
        _documentation = d;
    }

    @Override
    public void withAllQNamesDo(QNameAction action) {
        super.withAllQNamesDo(action);

        if (_binding != null) {
            action.perform(_binding);
        }
    }

    @Override
    public void withAllEntityReferencesDo(EntityReferenceAction action) {
        super.withAllEntityReferencesDo(action);
        if (_binding != null) {
            action.perform(Kinds.BINDING, _binding);
        }
    }

    public void accept(WSDLDocumentVisitor visitor) throws Exception {
        visitor.preVisit(this);
        _helper.accept(visitor);
        visitor.postVisit(this);
    }

    @Override
    public void validateThis() {
        if (getName() == null) {
            failValidation("validation.missingRequiredAttribute", "name");
        }
        if (_binding == null) {
            failValidation("validation.missingRequiredAttribute", "binding");
        }
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

    private ExtensibilityHelper _helper;
    private Documentation _documentation;
    private Service _service;
    private QName _binding;

    @Override
    public QName getElementName() {
        return getWSDLElementName();
    }

    private TWSDLExtensible parent;
}
