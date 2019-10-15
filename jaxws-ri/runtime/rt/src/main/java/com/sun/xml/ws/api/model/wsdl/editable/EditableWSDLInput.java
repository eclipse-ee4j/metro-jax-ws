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

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.model.wsdl.WSDLInput;

public interface EditableWSDLInput extends WSDLInput {

    @Override
    EditableWSDLMessage getMessage();

    @Override
    @NotNull
    EditableWSDLOperation getOperation();

    /**
     * Sets action
     *
     * @param action Action
     */
    public void setAction(String action);

    /**
     * Set to true if this is the default action
     *
     * @param defaultAction True, if default action
     */
    public void setDefaultAction(boolean defaultAction);

    /**
     * Freezes WSDL model to prevent further modification
     *
     * @param root WSDL Model
     */
    public void freeze(EditableWSDLModel root);

}
