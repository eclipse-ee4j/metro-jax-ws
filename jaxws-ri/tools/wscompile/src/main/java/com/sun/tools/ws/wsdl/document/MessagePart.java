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

import com.sun.tools.ws.wsdl.framework.Entity;
import com.sun.tools.ws.wsdl.framework.EntityReferenceAction;
import com.sun.tools.ws.wsdl.framework.Kind;
import com.sun.tools.ws.wsdl.framework.QNameAction;
import org.xml.sax.Locator;

import jakarta.jws.WebParam.Mode;
import javax.xml.namespace.QName;

/**
 * Entity corresponding to a WSDL message part.
 *
 * @author WS Development Team
 */
public class MessagePart extends Entity {

    public static final int SOAP_BODY_BINDING = 1;
    public static final int SOAP_HEADER_BINDING = 2;
    public static final int SOAP_HEADERFAULT_BINDING = 3;
    public static final int SOAP_FAULT_BINDING = 4;
    public static final int WSDL_MIME_BINDING = 5;
    public static final int PART_NOT_BOUNDED = -1;

    public MessagePart(Locator locator) {
        super(locator);
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public QName getDescriptor() {
        return _descriptor;
    }

    public void setDescriptor(QName n) {
        _descriptor = n;
    }

    public Kind getDescriptorKind() {
        return _descriptorKind;
    }

    public void setDescriptorKind(Kind k) {
        _descriptorKind = k;
    }

    @Override
    public QName getElementName() {
        return WSDLConstants.QNAME_PART;
    }

    public int getBindingExtensibilityElementKind(){
        return _bindingKind;
    }

    public void setBindingExtensibilityElementKind(int kind) {
        _bindingKind = kind;
    }

    @Override
    public void withAllQNamesDo(QNameAction action) {
        if (_descriptor != null) {
            action.perform(_descriptor);
        }
    }

    @Override
    public void withAllEntityReferencesDo(EntityReferenceAction action) {
        super.withAllEntityReferencesDo(action);
        if (_descriptor != null && _descriptorKind != null) {
            action.perform(_descriptorKind, _descriptor);
        }
    }

    public void accept(WSDLDocumentVisitor visitor) throws Exception {
        visitor.visit(this);
    }

    @Override
    public void validateThis() {
        if(_descriptor != null && _descriptor.getLocalPart().isEmpty()){
            failValidation("validation.invalidElement", _descriptor.toString());
        }
    }

    public void setMode(Mode mode){
        this.mode = mode;
    }

    public Mode getMode(){
        return mode;
    }

    public boolean isINOUT(){
        if(mode!=null)
            return (mode == Mode.INOUT);
        return false;
    }

    public boolean isIN(){
        if(mode!=null)
            return (mode == Mode.IN);
        return false;
    }

    public boolean isOUT(){
        if(mode!=null)
            return (mode == Mode.OUT);
        return false;
    }

    public void setReturn(boolean ret){
        isRet=ret;
    }

    public boolean isReturn(){
        return isRet;
    }


    private boolean isRet;
    private String _name;
    private QName _descriptor;
    private Kind _descriptorKind;
    private int _bindingKind;

    private Mode mode;
}
