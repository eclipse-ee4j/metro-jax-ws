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

import com.sun.xml.ws.api.model.ParameterBinding;
import com.sun.xml.ws.api.model.wsdl.WSDLPart;
import com.sun.xml.ws.api.model.wsdl.WSDLPartDescriptor;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLPart;

import javax.xml.stream.XMLStreamReader;

/**
 * Implementation of {@link WSDLPart}
 *
 * @author Vivek Pandey
 */
public final class WSDLPartImpl extends AbstractObjectImpl implements EditableWSDLPart {
    private final String name;
    private ParameterBinding binding;
    private int index;
    private final WSDLPartDescriptor descriptor;

    public WSDLPartImpl(XMLStreamReader xsr, String partName, int index, WSDLPartDescriptor descriptor) {
        super(xsr);
        this.name = partName;
        this.binding = ParameterBinding.UNBOUND;
        this.index = index;
        this.descriptor = descriptor;

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ParameterBinding getBinding() {
        return binding;
    }

    @Override
    public void setBinding(ParameterBinding binding) {
        this.binding = binding;
    }

    @Override
    public int getIndex() {
        return index;
    }

    //need to set the index in case of rpclit to reorder the body parts
    @Override
    public void setIndex(int index){
        this.index = index;
    }

    @Override
    public WSDLPartDescriptor getDescriptor() {
        return descriptor;
    }
}
