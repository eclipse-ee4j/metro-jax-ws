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
import com.sun.xml.ws.api.model.wsdl.WSDLOperation;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLFault;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLInput;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLMessage;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLModel;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLOperation;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLOutput;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLPart;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLPortType;
import com.sun.xml.ws.util.QNameMap;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementaiton of {@link WSDLOperation}
 *
 * @author Vivek Pandey
 */
public final class WSDLOperationImpl extends AbstractExtensibleImpl implements EditableWSDLOperation {
    private final QName name;
    private String parameterOrder;
    private EditableWSDLInput input;
    private EditableWSDLOutput output;
    private final List<EditableWSDLFault> faults;
    private final QNameMap<EditableWSDLFault> faultMap;
    protected Iterable<EditableWSDLMessage> messages;
    private final EditableWSDLPortType owner;

    public WSDLOperationImpl(XMLStreamReader xsr, EditableWSDLPortType owner, QName name) {
        super(xsr);
        this.name = name;
        this.faults = new ArrayList<>();
        this.faultMap = new QNameMap<>();
        this.owner = owner;
    }

    @Override
    public QName getName() {
        return name;
    }

    @Override
    public String getParameterOrder() {
        return parameterOrder;
    }

    @Override
    public void setParameterOrder(String parameterOrder) {
        this.parameterOrder = parameterOrder;
    }

    @Override
    public EditableWSDLInput getInput() {
        return input;
    }

    @Override
    public void setInput(EditableWSDLInput input) {
        this.input = input;
    }

    @Override
    public EditableWSDLOutput getOutput() {
        return output;
    }

    @Override
    public boolean isOneWay() {
        return output == null;
    }

    @Override
    public void setOutput(EditableWSDLOutput output) {
        this.output = output;
    }

    @Override
    public Iterable<EditableWSDLFault> getFaults() {
        return faults;
    }

    @Override
    public EditableWSDLFault getFault(QName faultDetailName) {
        EditableWSDLFault fault = faultMap.get(faultDetailName);
        if(fault != null)
            return fault;

        for(EditableWSDLFault fi : faults){
            assert fi.getMessage().parts().iterator().hasNext();
            EditableWSDLPart part = fi.getMessage().parts().iterator().next();
            if(part.getDescriptor().name().equals(faultDetailName)){
                faultMap.put(faultDetailName, fi);
                return fi;
            }
        }
        return null;
    }
    
    @Override
    @NotNull
    public QName getPortTypeName() {
        return owner.getName();
    }

    @Override
    public void addFault(EditableWSDLFault fault) {
        faults.add(fault);
    }

    @Override
    public void freeze(EditableWSDLModel root) {
        assert input != null;
        input.freeze(root);
        if(output != null)
            output.freeze(root);
        for(EditableWSDLFault fault : faults){
            fault.freeze(root);
        }
    }
}
