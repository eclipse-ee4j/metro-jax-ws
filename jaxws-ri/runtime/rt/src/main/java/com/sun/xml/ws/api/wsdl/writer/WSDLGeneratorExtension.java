/*
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.wsdl.writer;

import com.sun.istack.NotNull;
import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.model.CheckedException;
import com.sun.xml.ws.api.model.JavaMethod;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.api.server.Container;
import com.sun.xml.ws.api.server.WSEndpoint;

/**
 * This is a callback interface used to extend the WSDLGenerator.  Implementors
 * of this interface can add their own WSDL extensions to the generated WSDL.
 * There are a number of methods that will be invoked allowing the extensions
 * to be generated on various WSDL elements.
 * <br>
 * The JAX-WS WSDLGenerator uses TXW to serialize the WSDL out to XML.
 * More information about TXW can be located at
 * <a href="http://txw.java.net">http://txw.java.net</a>.
 */
public abstract class WSDLGeneratorExtension {
    /**
     * Called at the very beginning of the process.
     * <br>
     * This method is invoked so that the root element can be manipulated before
     * any tags have been written. This allows to set e.g. namespace prefixes.
     * <br>
     * Another purpose of this method is to let extensions know what model
     * we are generating a WSDL for.
     *
     * @param root      This is the root element of the generated WSDL.
     * @param model     WSDL is being generated from this {@link SEIModel}.
     * @param binding   The binding for which we generate WSDL. the binding {@link WSBinding} represents a particular
     *                  configuration of JAXWS. This can be typically be overriden by
     * @param container The entry point to the external environment.
     *                  If this extension is used at the runtime to generate WSDL, you get a {@link Container}
     *                  that was given to {@link WSEndpoint#create}.
     *                  TODO: think about tool side
     * @deprecated
     */
    public void start(@NotNull TypedXmlWriter root, @NotNull SEIModel model, @NotNull WSBinding binding, @NotNull Container container) {
    }

    /**
     * Called before writing &lt;/wsdl:defintions&gt;.
     *
     * @param ctxt
     */
    public void end(@NotNull WSDLGenExtnContext ctxt) {
    }

    /**
     * Called at the very beginning of the process.
     * <br>
     * This method is invoked so that the root element can be manipulated before
     * any tags have been written. This allows to set e.g. namespace prefixes.
     * <br>
     * Another purpose of this method is to let extensions know what model
     * we are generating a WSDL for.
     *
     * @param ctxt Provides the context for the generator extensions
     */
    public void start(WSDLGenExtnContext ctxt) {
    }

    /**
     * This method is invoked so that extensions to a <code>wsdl:definitions</code>
     * element can be generated.
     *
     * @param definitions This is the <code>wsdl:defintions</code> element that the extension can be added to.
     */
    public void addDefinitionsExtension(TypedXmlWriter definitions) {
    }

    /**
     * This method is invoked so that extensions to a <code>wsdl:service</code>
     * element can be generated.
     *
     * @param service This is the <code>wsdl:service</code> element that the extension can be added to.
     */
    public void addServiceExtension(TypedXmlWriter service) {
    }

    /**
     * This method is invoked so that extensions to a <code>wsdl:port</code>
     * element can be generated.
     *
     * @param port This is the wsdl:port element that the extension can be added to.
     */
    public void addPortExtension(TypedXmlWriter port) {
    }

    /**
     * This method is invoked so that extensions to a <code>wsdl:portType</code>
     * element can be generated.
     * <br>
     *
     * @param portType This is the wsdl:portType element that the extension can be added to.
     */
    public void addPortTypeExtension(TypedXmlWriter portType) {
    }

    /**
     * This method is invoked so that extensions to a <code>wsdl:binding</code>
     * element can be generated.
     * <br>
     * <br>
     * TODO:  Some other information may need to be passed
     *
     * @param binding This is the wsdl:binding element that the extension can be added to.
     */
    public void addBindingExtension(TypedXmlWriter binding) {
    }

    /**
     * This method is invoked so that extensions to a <code>wsdl:portType/wsdl:operation</code>
     * element can be generated.
     *
     * @param operation This is the wsdl:portType/wsdl:operation  element that the
     *                  extension can be added to.
     * @param method    {@link JavaMethod} which captures all the information to generate wsdl:portType/wsdl:operation
     */
    public void addOperationExtension(TypedXmlWriter operation, JavaMethod method) {
    }


    /**
     * This method is invoked so that extensions to a <code>wsdl:binding/wsdl:operation</code>
     * element can be generated.
     *
     * @param operation This is the wsdl:binding/wsdl:operation  element that the
     *                  extension can be added to.
     * @param method    {@link JavaMethod} which captures all the information to generate wsdl:portType/wsdl:operation
     */
    public void addBindingOperationExtension(TypedXmlWriter operation, JavaMethod method) {
    }

    /**
     * This method is invoked so that extensions to an input <code>wsdl:message</code>
     * element can be generated.
     *
     * @param message This is the input wsdl:message element that the
     *                extension can be added to.
     * @param method  {@link JavaMethod} which captures all the information to generate wsdl:portType/wsdl:operation
     */
    public void addInputMessageExtension(TypedXmlWriter message, JavaMethod method) {
    }

    /**
     * This method is invoked so that extensions to an output <code>wsdl:message</code>
     * element can be generated.
     *
     * @param message This is the output wsdl:message element that the
     *                extension can be added to.
     * @param method  {@link JavaMethod} which captures all the information to generate wsdl:portType/wsdl:operation
     */
    public void addOutputMessageExtension(TypedXmlWriter message, JavaMethod method) {
    }


    /**
     * This method is invoked so that extensions to a
     * <code>wsdl:portType/wsdl:operation/wsdl:input</code>
     * element can be generated.
     *
     * @param input  This is the wsdl:portType/wsdl:operation/wsdl:input  element that the
     *               extension can be added to.
     * @param method {@link JavaMethod} which captures all the information to generate wsdl:portType/wsdl:operation
     */
    public void addOperationInputExtension(TypedXmlWriter input, JavaMethod method) {
    }


    /**
     * This method is invoked so that extensions to a <code>wsdl:portType/wsdl:operation/wsdl:output</code>
     * element can be generated.
     *
     * @param output This is the wsdl:portType/wsdl:operation/wsdl:output  element that the
     *               extension can be added to.
     * @param method {@link JavaMethod} which captures all the information to generate wsdl:portType/wsdl:operation
     */
    public void addOperationOutputExtension(TypedXmlWriter output, JavaMethod method) {
    }

    /**
     * This method is invoked so that extensions to a
     * <code>wsdl:binding/wsdl:operation/wsdl:input</code>
     * element can be generated.
     *
     * @param input  This is the wsdl:binding/wsdl:operation/wsdl:input  element that the
     *               extension can be added to.
     * @param method {@link JavaMethod} which captures all the information to generate wsdl:portType/wsdl:operation
     */
    public void addBindingOperationInputExtension(TypedXmlWriter input, JavaMethod method) {
    }


    /**
     * This method is invoked so that extensions to a  <code>wsdl:binding/wsdl:operation/wsdl:output</code>
     * element can be generated.
     *
     * @param output This is the wsdl:binding/wsdl:operation/wsdl:output  element that the
     *               extension can be added to.
     * @param method {@link JavaMethod} which captures all the information to generate wsdl:portType/wsdl:operation
     */
    public void addBindingOperationOutputExtension(TypedXmlWriter output, JavaMethod method) {
    }

    /**
     * This method is invoked so that extensions to a <code>wsdl:binding/wsdl:operation/wsdl:fault</code>
     * element can be generated.
     *
     * @param fault  This is the wsdl:binding/wsdl:operation/wsdl:fault or wsdl:portType/wsdl:output/wsdl:operation/wsdl:fault
     *               element that the extension can be added to.
     * @param method {@link JavaMethod} which captures all the information to generate wsdl:portType/wsdl:operation
     */
    public void addBindingOperationFaultExtension(TypedXmlWriter fault, JavaMethod method, CheckedException ce) {
    }

    /**
     * This method is invoked so that extensions to a <code>wsdl:portType/wsdl:operation/wsdl:fault</code>
     * element can be generated.
     *
     * @param message This is the fault wsdl:message element that the
     *                extension can be added to.
     * @param method  {@link JavaMethod} which captures all the information to generate wsdl:portType/wsdl:operation
     *
     * @param ce      {@link CheckedException} that abstracts wsdl:fault
     */
    public void addFaultMessageExtension(TypedXmlWriter message, JavaMethod method, CheckedException ce) {
    }

    /**
     * This method is invoked so that extensions to a <code>wsdl:portType/wsdl:operation/wsdl:fault</code>
     * element can be generated.
     *
     * @param fault  This is the wsdl:portType/wsdl:operation/wsdl:fault  element that the
     *               extension can be added to.
     * @param method {@link JavaMethod} which captures all the information to generate wsdl:portType/wsdl:operation
     * @param ce     {@link CheckedException} that abstracts wsdl:fault
     */
    public void addOperationFaultExtension(TypedXmlWriter fault, JavaMethod method, CheckedException ce) {
    }

}
