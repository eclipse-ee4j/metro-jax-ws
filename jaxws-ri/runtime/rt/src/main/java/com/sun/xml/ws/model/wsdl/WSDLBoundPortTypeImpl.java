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
import com.sun.xml.ws.api.BindingID;
import com.sun.xml.ws.api.model.ParameterBinding;
import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
import com.sun.xml.ws.api.model.wsdl.WSDLBoundPortType;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLBoundOperation;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLBoundPortType;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLModel;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLPortType;
import com.sun.xml.ws.resources.ClientMessages;
import com.sun.xml.ws.util.QNameMap;
import com.sun.xml.ws.util.exception.LocatableWebServiceException;

import jakarta.jws.WebParam.Mode;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.Style;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;

/**
 * Implementation of {@link WSDLBoundPortType}
 *
 * @author Vivek Pandey
 */
public final class WSDLBoundPortTypeImpl extends AbstractFeaturedObjectImpl implements EditableWSDLBoundPortType {
    private final QName name;
    private final QName portTypeName;
    private EditableWSDLPortType portType;
    private BindingID bindingId;
    private final @NotNull EditableWSDLModel owner;
    private final QNameMap<EditableWSDLBoundOperation> bindingOperations = new QNameMap<>();

    /**
     * Operations keyed by the payload tag name.
     */
    private QNameMap<EditableWSDLBoundOperation> payloadMap;
    /**
     * {@link #payloadMap} doesn't allow null key, so we store the value for it here.
     */
    private EditableWSDLBoundOperation emptyPayloadOperation;



    public WSDLBoundPortTypeImpl(XMLStreamReader xsr,@NotNull EditableWSDLModel owner, QName name, QName portTypeName) {
        super(xsr);
        this.owner = owner;
        this.name = name;
        this.portTypeName = portTypeName;
        owner.addBinding(this);
    }

    @Override
    public QName getName() {
        return name;
    }

    @Override
    public @NotNull EditableWSDLModel getOwner() {
        return owner;
    }

    @Override
    public EditableWSDLBoundOperation get(QName operationName) {
        return bindingOperations.get(operationName);
    }

    /**
     * Populates the Map that holds operation name as key and {@link WSDLBoundOperation} as the value.
     *
     * @param opName Must be non-null
     * @param ptOp   Must be non-null
     * @throws NullPointerException if either opName or ptOp is null
     */
    @Override
    public void put(QName opName, EditableWSDLBoundOperation ptOp) {
        bindingOperations.put(opName,ptOp);
    }

    @Override
    public QName getPortTypeName() {
        return portTypeName;
    }

    @Override
    public EditableWSDLPortType getPortType() {
        return portType;
    }

    @Override
    public Iterable<EditableWSDLBoundOperation> getBindingOperations() {
        return bindingOperations.values();
    }

    @Override
    public BindingID getBindingId() {
        //Should the default be SOAP1.1/HTTP binding? For now lets keep it for
        //JBI bug 6509800 
        return (bindingId==null)?BindingID.SOAP11_HTTP:bindingId;
    }

    @Override
    public void setBindingId(BindingID bindingId) {
        this.bindingId = bindingId;
    }

    /**
     * sets whether the {@link WSDLBoundPortType} is rpc or lit
     */
    private Style style = Style.DOCUMENT;
    @Override
    public void setStyle(Style style){
        this.style = style;
    }

    @Override
    public SOAPBinding.Style getStyle() {
        return style;
    }

    public boolean isRpcLit(){
        return Style.RPC==style;
    }

    public boolean isDoclit(){
        return Style.DOCUMENT==style;
    }


    /**
     * Gets the {@link ParameterBinding} for a given operation, part name and the direction - IN/OUT
     *
     * @param operation wsdl:operation@name value. Must be non-null.
     * @param part      wsdl:part@name such as value of soap:header@part. Must be non-null.
     * @param mode      {@link Mode#IN} or {@link Mode#OUT}. Must be non-null.
     * @return null if the binding could not be resolved for the part.
     */
    @Override
    public ParameterBinding getBinding(QName operation, String part, Mode mode) {
        EditableWSDLBoundOperation op = get(operation);
        if (op == null) {
            //TODO throw exception
            return null;
        }
        if ((Mode.IN == mode) || (Mode.INOUT == mode))
            return op.getInputBinding(part);
        else
            return op.getOutputBinding(part);
    }

    @Override
    public EditableWSDLBoundOperation getOperation(String namespaceUri, String localName) {
        if(namespaceUri==null && localName == null)
            return emptyPayloadOperation;
        else{
            return payloadMap.get((namespaceUri==null)?"":namespaceUri,localName);
        }
    }

    @Override
    public void freeze() {
        portType = owner.getPortType(portTypeName);
        if(portType == null){
            throw new LocatableWebServiceException(
                    ClientMessages.UNDEFINED_PORT_TYPE(portTypeName), getLocation());
        }
        portType.freeze();

        for (EditableWSDLBoundOperation op : bindingOperations.values()) {
            op.freeze(owner);
        }

        freezePayloadMap();
        owner.finalizeRpcLitBinding(this);
    }

    private void freezePayloadMap() {
        if(style== Style.RPC) {
            payloadMap = new QNameMap<>();
            for(EditableWSDLBoundOperation op : bindingOperations.values()){
                payloadMap.put(op.getRequestPayloadName(), op);
            }
        } else {
            payloadMap = new QNameMap<>();
            // For doclit The tag will be the operation that has the same input part descriptor value
            for(EditableWSDLBoundOperation op : bindingOperations.values()){
                QName name = op.getRequestPayloadName();
                if(name == null){
                    //empty payload
                    emptyPayloadOperation = op;
                    continue;
                }

                payloadMap.put(name, op);
            }
        }
    }
}
