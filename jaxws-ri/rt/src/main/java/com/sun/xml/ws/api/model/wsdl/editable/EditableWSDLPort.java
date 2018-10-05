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
import com.sun.xml.ws.api.EndpointAddress;
import com.sun.xml.ws.api.addressing.WSEndpointReference;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;

public interface EditableWSDLPort extends WSDLPort {

    @Override
    @NotNull
    EditableWSDLBoundPortType getBinding();

    @Override
    @NotNull
    EditableWSDLService getOwner();

    /**
     * Sets endpoint address
     *
     * @param address Endpoint address
     */
    public void setAddress(EndpointAddress address);

    /**
     * Sets endpoint reference
     *
     * @param epr Endpoint reference
     */
    public void setEPR(@NotNull WSEndpointReference epr);

    /**
     * Freezes WSDL model to prevent further modification
     *
     * @param root WSDL Model
     */
    void freeze(EditableWSDLModel root);
}
