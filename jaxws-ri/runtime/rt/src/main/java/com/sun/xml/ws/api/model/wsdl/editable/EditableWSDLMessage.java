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

import com.sun.xml.ws.api.model.wsdl.WSDLMessage;

public interface EditableWSDLMessage extends WSDLMessage {

    @Override
    Iterable<? extends EditableWSDLPart> parts();

    /**
     * Add part
     *
     * @param part Part
     */
    void add(EditableWSDLPart part);

}
