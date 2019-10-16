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

import java.util.Map;

import javax.jws.WebParam.Mode;
import javax.jws.soap.SOAPBinding.Style;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;

public interface EditableWSDLBoundOperation extends WSDLBoundOperation {
	
	@Override
    @NotNull EditableWSDLOperation getOperation();

	@Override
    @NotNull EditableWSDLBoundPortType getBoundPortType();

	@Override
    @Nullable EditableWSDLPart getPart(@NotNull String partName, @NotNull Mode mode);

	@Override
    @NotNull Map<String,? extends EditableWSDLPart> getInParts();

	@Override
    @NotNull Map<String,? extends EditableWSDLPart> getOutParts();
    
	@Override
    @NotNull Iterable<? extends EditableWSDLBoundFault> getFaults();

	/**
	 * Add Part
	 * @param part Part
	 * @param mode Mode
	 */
    public void addPart(EditableWSDLPart part, Mode mode);

    /**
     * Add Fault
     * @param fault Fault
     */
    public void addFault(@NotNull EditableWSDLBoundFault fault);

    /**
     * Sets the soapbinding:binding/operation/wsaw:Anonymous.
     *
     * @param anonymous Anonymous value of the operation
     */
	public void setAnonymous(ANONYMOUS anonymous);
	
	/**
	 * Sets input explicit body parts
	 * @param b True, if input body part is explicit
	 */
	public void setInputExplicitBodyParts(boolean b);
	
	/**
	 * Sets output explicit body parts
	 * @param b True, if output body part is explicit
	 */
	public void setOutputExplicitBodyParts(boolean b);
	
	/**
	 * Sets fault explicit body parts
	 * @param b True, if fault body part is explicit
	 */
	public void setFaultExplicitBodyParts(boolean b);
	
	/**
	 * Set request namespace
	 * @param ns Namespace
	 */
	public void setRequestNamespace(String ns);
	
	/**
	 * Set response namespace
	 * @param ns Namespace
	 */
	public void setResponseNamespace(String ns);
	
	/**
	 * Set SOAP action
	 * @param soapAction SOAP action
	 */
	public void setSoapAction(String soapAction);
	
	/**
	 * Set parameter style
	 * @param style Style
	 */
	public void setStyle(Style style);
	
	/**
	 * Freezes WSDL model to prevent further modification
	 * @param root WSDL Model
	 */
	public void freeze(EditableWSDLModel root);
}
