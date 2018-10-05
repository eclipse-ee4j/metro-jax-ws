/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.model.wsdl.editable;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.model.wsdl.WSDLBoundFault;

public interface EditableWSDLBoundFault extends WSDLBoundFault {

    @Override
    @Nullable
    EditableWSDLFault getFault();

    @Override
    @NotNull
    EditableWSDLBoundOperation getBoundOperation();

    /**
     * Freezes WSDL model to prevent further modification
     *
     * @param operation Operation
     */
    void freeze(EditableWSDLBoundOperation operation);
}
