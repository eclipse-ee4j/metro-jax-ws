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

import com.sun.xml.ws.api.model.wsdl.WSDLDescriptorKind;
import com.sun.xml.ws.api.model.wsdl.WSDLPartDescriptor;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;

/**
 * @author Vivek Pandey
 */
public final class WSDLPartDescriptorImpl extends AbstractObjectImpl implements WSDLPartDescriptor {
    private QName name;
    private WSDLDescriptorKind type;

    public WSDLPartDescriptorImpl(XMLStreamReader xsr,QName name, WSDLDescriptorKind kind) {
        super(xsr);
        this.name = name;
        this.type = kind;
    }

    public QName name() {
        return name;
    }

    public WSDLDescriptorKind type() {
        return type;
    }
}
