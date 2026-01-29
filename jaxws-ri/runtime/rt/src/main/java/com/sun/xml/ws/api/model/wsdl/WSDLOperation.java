/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.model.wsdl;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

import javax.xml.namespace.QName;

/**
 * Provides abstraction of wsdl:portType/wsdl:operation.
 *
 * @author Vivek Pandey
 */
public interface WSDLOperation extends WSDLObject, WSDLExtensible {
    /**
     * Gets the name of the wsdl:portType/wsdl:operation@name attribute value as local name and wsdl:definitions@targetNamespace
     * as the namespace uri.
     */
    @NotNull QName getName();

    /**
     * Gets the wsdl:input of this operation
     */
    @NotNull WSDLInput getInput();

    /**
     * Gets the wsdl:output of this operation.
     *
     * @return
     *      null if this is an one-way operation.
     */
    @Nullable WSDLOutput getOutput();

    /**
     * Returns true if this operation is an one-way operation.
     */
    boolean isOneWay();

    /**
     * Gets the {@link WSDLFault} corresponding to wsdl:fault of this operation.
     */
    Iterable<? extends WSDLFault> getFaults();

    /**
     * Gives {@link WSDLFault} for the given soap fault detail value.
     *
     * <pre>
     *
     * Given a wsdl fault:
     *
     * &lt;wsdl:message nae="faultMessage"&gt;
     *  &lt;wsdl:part name="fault" element="<b>ns:myException</b>/&gt;
     * &lt;/wsdl:message&gt;
     *
     * &lt;wsdl:portType&gt;
     *  &lt;wsdl:operation ...&gt;
     *      &lt;wsdl:fault name="aFault" message="faultMessage"/&gt;
     *  &lt;/wsdl:operation&gt;
     * &lt;wsdl:portType&gt;
     *
     *
     * For example given a soap 11 soap message:
     *
     * &lt;soapenv:Fault&gt;
     *      ...
     *      &lt;soapenv:detail&gt;
     *          &lt;<b>ns:myException</b>&gt;
     *              ...
     *          &lt;/ns:myException&gt;
     *      &lt;/soapenv:detail&gt;
     *
     * QName faultQName = new QName(ns, "myException");
     * WSDLFault wsdlFault  = getFault(faultQName);
     *
     * The above call will return a WSDLFault that abstracts wsdl:portType/wsdl:operation/wsdl:fault.
     *
     * </pre>
     *
     * @param faultDetailName tag name of the element inside soaenv:Fault/detail/, must be non-null.
     * @return returns null if a wsdl fault corresponding to the detail entry name not found.
     */
    @Nullable WSDLFault getFault(QName faultDetailName);

    /**
     * Gives the enclosing wsdl:portType@name attribute value.
     */
    @NotNull QName getPortTypeName();
    
    /**
     * Returns parameter order
     * @return Parameter order
     */
    String getParameterOrder();
}
