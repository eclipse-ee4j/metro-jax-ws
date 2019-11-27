/*
 * Copyright (c) 2013, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.model.wsdl.editable;

import javax.xml.namespace.QName;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.model.wsdl.WSDLService;

public interface EditableWSDLService extends WSDLService {

    @Override
    @NotNull
    EditableWSDLModel getParent();

    @Override
    EditableWSDLPort get(QName portName);

    @Override
    EditableWSDLPort getFirstPort();

    @Override
    @Nullable
    EditableWSDLPort getMatchingPort(QName portTypeName);

    @Override
    Iterable<? extends EditableWSDLPort> getPorts();

    /**
     * Associate WSDL port with port QName
     *
     * @param portName Port QName
     * @param port     Port
     */
    public void put(QName portName, EditableWSDLPort port);

    /**
     * Freezes WSDL model to prevent further modification
     *
     * @param root WSDL Model
     */
    void freeze(EditableWSDLModel root);
}
