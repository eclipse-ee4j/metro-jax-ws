/*
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.model.wsdl;

import com.sun.istack.NotNull;

import javax.xml.namespace.QName;

/**
 * Abstraction of wsdl:portType/wsdl:operation/wsdl:input
 *
 * @author Vivek Pandey
 */
public interface WSDLInput extends WSDLObject, WSDLExtensible {
    /**
     * Gives the wsdl:portType/wsdl:operation/wsdl:input@name
     */
    String getName();

    /**
     * Gives the WSDLMessage corresponding to wsdl:input@message
     * <br>
     * This method should not be called before the entire WSDLModel is built. Basically after the WSDLModel is built
     * all the references are resolve in a post processing phase. IOW, the WSDL extensions should
     * not call this method.
     *
     * @return Always returns null when called from inside WSDL extensions.
     */
    WSDLMessage getMessage();

    /**
     * Gives the Action Message Addressing Property value for
     * {@link WSDLInput} message.
     * <br>
     * This method provides the correct value irrespective of
     * whether the Action is explicitly specified in the WSDL or
     * implicitly derived using the rules defined in WS-Addressing.
     *
     * @return Action
     */
    String getAction();

    /**
     * Gives the owning {@link WSDLOperation}
     */
    @NotNull
    WSDLOperation getOperation();

    /**
     * Gives qualified name of the wsdl:input 'name' attribute value. If there is no name, then it computes the name from:
     *
     * If the wsdl:operation is oneway:
     *
     * wsdl:operation@name value, which is local name of {@link WSDLOperation#getName()}
     * <br>
     * otherwise
     *
     * wsdl:operation@name+"Request", which is local name of {@link WSDLOperation#getName()} + "Request"
     * <br>
     *
     * The namespace uri is determined from the enclosing wsdl:operation.
     */
    @NotNull
    QName getQName();
    
    /**
     * Checks if the Action value is implicitly derived using the rules defined in WS-Addressing.
     * 
     * @return true if the Action value is implicitly derived using the rules defined in WS-Addressing.
     */
    boolean isDefaultAction();
}
