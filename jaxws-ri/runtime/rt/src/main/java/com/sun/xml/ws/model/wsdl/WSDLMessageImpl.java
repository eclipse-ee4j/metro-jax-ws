/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.model.wsdl;

import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLMessage;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLPart;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;

import java.util.ArrayList;

/**
 * Provides abstraction for wsdl:message
 * @author Vivek Pandey
 */
public final class WSDLMessageImpl extends AbstractExtensibleImpl implements EditableWSDLMessage {
    private final QName name;
    private final ArrayList<EditableWSDLPart> parts;

    /**
     * @param name wsdl:message name attribute value
     */
    public WSDLMessageImpl(XMLStreamReader xsr,QName name) {
        super(xsr);
        this.name = name;
        this.parts = new ArrayList<EditableWSDLPart>();
    }

    public QName getName() {
        return name;
    }

    public void add(EditableWSDLPart part){
        parts.add(part);
    }

    public Iterable<EditableWSDLPart> parts(){
        return parts;
    }
}
