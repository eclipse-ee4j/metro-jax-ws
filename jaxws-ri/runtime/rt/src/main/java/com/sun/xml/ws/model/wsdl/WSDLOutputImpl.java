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

import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLMessage;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLModel;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLOperation;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLOutput;
import com.sun.istack.NotNull;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;

/**
 * @author Vivek Pandey
 */
public final class WSDLOutputImpl extends AbstractExtensibleImpl implements EditableWSDLOutput {
    private String name;
    private QName messageName;
    private EditableWSDLOperation operation;
    private EditableWSDLMessage message;
    private String action;
    private boolean defaultAction = true;
    
    public WSDLOutputImpl(XMLStreamReader xsr,String name, QName messageName, EditableWSDLOperation operation) {
        super(xsr);
        this.name = name;
        this.messageName = messageName;
        this.operation = operation;
    }

    @Override
    public String getName() {
        return (name == null)?operation.getName().getLocalPart()+"Response":name;
    }

    @Override
    public EditableWSDLMessage getMessage() {
        return message;
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public boolean isDefaultAction() {
        return defaultAction;
    }

    @Override
    public void setDefaultAction(boolean defaultAction) {
        this.defaultAction = defaultAction;
    }

    @Override
    @NotNull
    public EditableWSDLOperation getOperation() {
        return operation;
    }

    @Override
    @NotNull
    public QName getQName() {
        return new QName(operation.getName().getNamespaceURI(), getName());
    }

    @Override
    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public void freeze(EditableWSDLModel root) {
        message = root.getMessage(messageName);
    }
}
