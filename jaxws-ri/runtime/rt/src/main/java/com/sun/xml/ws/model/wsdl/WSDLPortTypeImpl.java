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

import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLModel;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLOperation;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLPortType;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;

import java.util.Hashtable;
import java.util.Map;

/**
 * Provides implementation of {@code com.sun.xml.ws.api.model.wsdl.WSDLPortType}
 *
 * @author Vivek Pandey
 */
public final class WSDLPortTypeImpl  extends AbstractExtensibleImpl implements EditableWSDLPortType {
    private QName name;
    private final Map<String, EditableWSDLOperation> portTypeOperations;
    private EditableWSDLModel owner;

    public WSDLPortTypeImpl(XMLStreamReader xsr, EditableWSDLModel owner, QName name) {
        super(xsr);
        this.name = name;
        this.owner = owner;
        portTypeOperations = new Hashtable<>();
    }

    @Override
    public QName getName() {
        return name;
    }

    @Override
    public EditableWSDLOperation get(String operationName) {
        return portTypeOperations.get(operationName);
    }

    @Override
    public Iterable<EditableWSDLOperation> getOperations() {
        return portTypeOperations.values();
    }

    /**
     * Populates the Map that holds operation name as key and {@link com.sun.xml.ws.api.model.wsdl.WSDLOperation} as the value.
     * @param opName Must be non-null
     * @param ptOp  Must be non-null
     * @throws NullPointerException if either opName or ptOp is null
     */
    @Override
    public void put(String opName, EditableWSDLOperation ptOp){
        portTypeOperations.put(opName, ptOp);
    }

    EditableWSDLModel getOwner(){
        return owner;
    }

    @Override
    public void freeze() {
        for(EditableWSDLOperation op : portTypeOperations.values()){
            op.freeze(owner);
        }
    }
}
