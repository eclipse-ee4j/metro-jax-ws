/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.model.wsdl;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLBoundFault;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLBoundOperation;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLFault;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLOperation;

import javax.xml.stream.XMLStreamReader;
import javax.xml.namespace.QName;

/**
 * @author Vivek Pandey
 */
public class WSDLBoundFaultImpl extends AbstractExtensibleImpl implements EditableWSDLBoundFault {
    private final String name;
    private EditableWSDLFault fault;
    private EditableWSDLBoundOperation owner;

    public WSDLBoundFaultImpl(XMLStreamReader xsr, String name, EditableWSDLBoundOperation owner) {
        super(xsr);
        this.name = name;
        this.owner = owner;
    }

    @Override
    public
    @NotNull
    String getName() {
        return name;
    }

    @Override
    public QName getQName() {
        if(owner.getOperation() != null){
            return new QName(owner.getOperation().getName().getNamespaceURI(), name);
        }
        return null;
    }

    @Override
    public EditableWSDLFault getFault() {
        return fault;
    }

    @Override
    @NotNull
    public EditableWSDLBoundOperation getBoundOperation() {
        return owner;
    }

    @Override
    public void freeze(EditableWSDLBoundOperation root) {
        assert root != null;
        EditableWSDLOperation op = root.getOperation();
        if (op != null) {
            for (EditableWSDLFault f : op.getFaults()) {
                if (f.getName().equals(name)) {
                    this.fault = f;
                    break;
                }
            }
        }
    }
}
