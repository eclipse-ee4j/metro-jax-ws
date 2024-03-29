/*
 * Copyright (c) 2013, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.model.wsdl.editable;

import com.sun.xml.ws.api.model.wsdl.WSDLPortType;

public interface EditableWSDLPortType extends WSDLPortType {

    @Override
    EditableWSDLOperation get(String operationName);

    @Override
    Iterable<? extends EditableWSDLOperation> getOperations();

    /**
     * Associate WSDL operation with operation name
     *
     * @param opName Operation name
     * @param ptOp   Operation
     */
    void put(String opName, EditableWSDLOperation ptOp);

    /**
     * Freezes WSDL model to prevent further modification
     */
    void freeze();
}
