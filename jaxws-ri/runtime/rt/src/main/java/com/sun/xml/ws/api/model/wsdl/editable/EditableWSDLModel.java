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

import java.util.Map;

import javax.xml.namespace.QName;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.model.wsdl.WSDLModel;
import com.sun.xml.ws.policy.PolicyMap;

public interface EditableWSDLModel extends WSDLModel {

    @Override
    EditableWSDLPortType getPortType(@NotNull QName name);

    /**
     * Add Binding
     *
     * @param portType Bound port type
     */
    void addBinding(EditableWSDLBoundPortType portType);

    @Override
    EditableWSDLBoundPortType getBinding(@NotNull QName name);

    @Override
    EditableWSDLBoundPortType getBinding(@NotNull QName serviceName, @NotNull QName portName);

    @Override
    EditableWSDLService getService(@NotNull QName name);

    @Override
    @NotNull
    Map<QName, ? extends EditableWSDLMessage> getMessages();

    /**
     * Add message
     *
     * @param msg Message
     */
    public void addMessage(EditableWSDLMessage msg);

    @Override
    @NotNull
    Map<QName, ? extends EditableWSDLPortType> getPortTypes();

    /**
     * Add port type
     *
     * @param pt Port type
     */
    public void addPortType(EditableWSDLPortType pt);

    @Override
    @NotNull
    Map<QName, ? extends EditableWSDLBoundPortType> getBindings();

    @Override
    @NotNull
    Map<QName, ? extends EditableWSDLService> getServices();

    /**
     * Add service
     *
     * @param svc Service
     */
    public void addService(EditableWSDLService svc);

    @Override
    public EditableWSDLMessage getMessage(QName name);

    /**
     * @param policyMap
     * @deprecated
     */
    public void setPolicyMap(PolicyMap policyMap);

    /**
     * Finalize rpc-lit binding
     *
     * @param portType Binding
     */
    public void finalizeRpcLitBinding(EditableWSDLBoundPortType portType);

    /**
     * Freezes WSDL model to prevent further modification
     */
    public void freeze();

}
