/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.model.wsdl;

import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLFault;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLMessage;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLModel;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLOperation;
import com.sun.istack.NotNull;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;

/**
 * @author Vivek Pandey
 */
public final class WSDLFaultImpl extends AbstractExtensibleImpl implements EditableWSDLFault {
    private final String name;
    private final QName messageName;
    private EditableWSDLMessage message;
    private EditableWSDLOperation operation;
    private String action = "";
    private boolean defaultAction = true;

    public WSDLFaultImpl(XMLStreamReader xsr, String name, QName messageName, EditableWSDLOperation operation) {
        super(xsr);
        this.name = name;
        this.messageName = messageName;
        this.operation = operation;
    }

    public String getName() {
        return name;
    }

    public EditableWSDLMessage getMessage() {
        return message;
    }

    @NotNull
    public EditableWSDLOperation getOperation() {
        return operation;
    }

    @NotNull
    public QName getQName() {
        return new QName(operation.getName().getNamespaceURI(), name);
    }

    @NotNull
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }

    public boolean isDefaultAction() {
        return defaultAction;
    }

    public void setDefaultAction(boolean defaultAction) {
        this.defaultAction = defaultAction;
    }

    public void freeze(EditableWSDLModel root){
        message = root.getMessage(messageName);
    }
}
