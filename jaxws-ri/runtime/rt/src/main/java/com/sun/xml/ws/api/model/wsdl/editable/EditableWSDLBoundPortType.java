/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.model.wsdl.editable;

import javax.jws.soap.SOAPBinding.Style;
import javax.xml.namespace.QName;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.BindingID;
import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
import com.sun.xml.ws.api.model.wsdl.WSDLBoundPortType;

public interface EditableWSDLBoundPortType extends WSDLBoundPortType {

	@Override
    @NotNull EditableWSDLModel getOwner();

	@Override
    public EditableWSDLBoundOperation get(QName operationName);

	@Override
    EditableWSDLPortType getPortType();

	@Override
    Iterable<? extends EditableWSDLBoundOperation> getBindingOperations();

	@Override
    @Nullable EditableWSDLBoundOperation getOperation(String namespaceUri, String localName);

    /**
     * Populates the Map that holds operation name as key and {@link WSDLBoundOperation} as the value.
     *
     * @param opName Must be non-null
     * @param ptOp   Must be non-null
     * @throws NullPointerException if either opName or ptOp is null
     */
    public void put(QName opName, EditableWSDLBoundOperation ptOp);

    /**
     * Sets the binding ID
     * @param bindingId Binding ID
     */
	public void setBindingId(BindingID bindingId);
	
    /**
     * sets whether the {@link WSDLBoundPortType} is rpc or lit
     */
	public void setStyle(Style style);
	
	/**
	 * Freezes WSDL model to prevent further modification
	 */
	public void freeze();
}
