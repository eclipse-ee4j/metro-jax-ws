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

import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLInput;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLMessage;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLModel;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLOperation;
import com.sun.istack.NotNull;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;

/**
 * @author Vivek Pandey
 */
public final class WSDLInputImpl extends AbstractExtensibleImpl implements EditableWSDLInput {
    private String name;
    private QName messageName;
    private EditableWSDLOperation operation;
    private EditableWSDLMessage message;
    private String action;
    private boolean defaultAction = true;

    public WSDLInputImpl(XMLStreamReader xsr,String name, QName messageName, EditableWSDLOperation operation) {
        super(xsr);
        this.name = name;
        this.messageName = messageName;
        this.operation = operation;
    }

    @Override
    public String getName() {
        if(name != null)
            return name;
        
        return (operation.isOneWay())?operation.getName().getLocalPart():operation.getName().getLocalPart()+"Request";
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
    @NotNull
    public EditableWSDLOperation getOperation() {
        return operation;
    }

    @Override
    public QName getQName() {
        return new QName(operation.getName().getNamespaceURI(), getName());
    }

    @Override
    public void setAction(String action) {
        this.action = action;
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
    public void freeze(EditableWSDLModel parent) {
        message = parent.getMessage(messageName);
    }
}
