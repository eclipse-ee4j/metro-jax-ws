/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
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
import com.sun.xml.ws.api.model.wsdl.WSDLOperation;

public interface EditableWSDLOperation extends WSDLOperation {

    @Override
    @NotNull
    EditableWSDLInput getInput();

    /**
     * Set input
     *
     * @param input Input
     */
    public void setInput(EditableWSDLInput input);

    @Override
    @Nullable
    EditableWSDLOutput getOutput();

    /**
     * Set output
     *
     * @param output Output
     */
    public void setOutput(EditableWSDLOutput output);

    @Override
    Iterable<? extends EditableWSDLFault> getFaults();

    /**
     * Add fault
     *
     * @param fault Fault
     */
    public void addFault(EditableWSDLFault fault);

    @Override
    @Nullable
    EditableWSDLFault getFault(QName faultDetailName);

    /**
     * Set parameter order
     *
     * @param parameterOrder Parameter order
     */
    public void setParameterOrder(String parameterOrder);

    /**
     * Freezes WSDL model to prevent further modification
     *
     * @param root WSDL Model
     */
    public void freeze(EditableWSDLModel root);
}
