/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.document;

import com.sun.tools.ws.wsdl.framework.*;
import com.sun.tools.ws.wsdl.parser.MetadataFinder;
import com.sun.tools.ws.wscompile.ErrorReceiver;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A WSDL document.
 *
 * @author WS Development Team
 */
public class WSDLDocument extends AbstractDocument{

    public WSDLDocument(MetadataFinder forest, ErrorReceiver errReceiver) {
        super(forest, errReceiver);
    }

    public Definitions getDefinitions() {
        return _definitions;
    }

    public void setDefinitions(Definitions d) {
        _definitions = d;
    }

    public QName[] getAllServiceQNames() {

        ArrayList<QName> serviceQNames = new ArrayList<>();

        for (Iterator<Service> iter = getDefinitions().services(); iter.hasNext();) {
            Service next = iter.next();
            String targetNamespace = next.getDefining().getTargetNamespaceURI();
            String localName = next.getName();
            QName serviceQName = new QName(targetNamespace, localName);
            serviceQNames.add(serviceQName);
        }
        return serviceQNames.toArray(new QName[0]);
    }

    public QName[] getAllPortQNames() {
        ArrayList<QName> portQNames = new ArrayList<>();

        for (Iterator<Service> iter = getDefinitions().services(); iter.hasNext();) {
            Service next = iter.next();
            //Iterator ports = next.ports();
            for (Iterator<Port> piter = next.ports(); piter.hasNext();) {
                // If it's a relative import
                Port pnext = piter.next();
                String targetNamespace =
                    pnext.getDefining().getTargetNamespaceURI();
                String localName = pnext.getName();
                QName portQName = new QName(targetNamespace, localName);
                portQNames.add(portQName);
            }
        }
        return portQNames.toArray(new QName[0]);
    }

    public QName[] getPortQNames(String serviceNameLocalPart) {

        ArrayList<QName> portQNames = new ArrayList<>();

        for (Iterator<Service> iter = getDefinitions().services(); iter.hasNext();) {
            Service next = iter.next();
            if (next.getName().equals(serviceNameLocalPart)) {
                for (Iterator<Port> piter = next.ports(); piter.hasNext();) {
                    Port pnext = piter.next();
                    String targetNamespace =
                        pnext.getDefining().getTargetNamespaceURI();
                    String localName = pnext.getName();
                    QName portQName = new QName(targetNamespace, localName);
                    portQNames.add(portQName);
                }
            }
        }
        return portQNames.toArray(new QName[0]);
    }

    public void accept(WSDLDocumentVisitor visitor) throws Exception {
        _definitions.accept(visitor);
    }

    @Override
    public void validate(EntityReferenceValidator validator) {
        GloballyValidatingAction action =
            new GloballyValidatingAction(this, validator);
        withAllSubEntitiesDo(action);
        if (action.getException() != null) {
            throw action.getException();
        }
    }

    @Override
    protected Entity getRoot() {
        return _definitions;
    }

    private Definitions _definitions;

    private static class GloballyValidatingAction implements EntityAction, EntityReferenceAction {
        public GloballyValidatingAction(
            AbstractDocument document,
            EntityReferenceValidator validator) {
            _document = document;
            _validator = validator;
        }

        @Override
        public void perform(Entity entity) {
            try {
                entity.validateThis();
                entity.withAllEntityReferencesDo(this);
                entity.withAllSubEntitiesDo(this);
            } catch (ValidationException e) {
                if (_exception == null) {
                    _exception = e;
                }
            }
        }

        @Override
        public void perform(Kind kind, QName name) {
            try {
                _document.find(kind, name);
            } catch (NoSuchEntityException e) {
                // failed to resolve, check with the validator
                if (_exception == null) {
                    if (_validator == null
                        || !_validator.isValid(kind, name)) {
                        _exception = e;
                    }
                }
            }
        }

        public ValidationException getException() {
            return _exception;
        }

        private ValidationException _exception;
        private AbstractDocument _document;
        private EntityReferenceValidator _validator;
    }
}
