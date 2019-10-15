/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.wsdl.writer;

import com.sun.istack.NotNull;
import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.ws.api.model.CheckedException;
import com.sun.xml.ws.api.model.JavaMethod;
import com.sun.xml.ws.api.wsdl.writer.WSDLGenExtnContext;
import com.sun.xml.ws.api.wsdl.writer.WSDLGeneratorExtension;

/**
 * {@link WSDLGeneratorExtension} that delegates to
 * multiple {@link WSDLGeneratorExtension}s.
 *
 * <p>
 * This simplifies {@link WSDLGenerator} since it now
 * only needs to work with one {@link WSDLGeneratorExtension}.
 *
 *
 * @author Doug Kohlert
 */
final class WSDLGeneratorExtensionFacade extends WSDLGeneratorExtension {
    private final WSDLGeneratorExtension[] extensions;

    WSDLGeneratorExtensionFacade(WSDLGeneratorExtension... extensions) {
        assert extensions!=null;
        this.extensions = extensions;
    }

    public void start(WSDLGenExtnContext ctxt) {
        for (WSDLGeneratorExtension e : extensions)
            e.start(ctxt);
    }

    public void end(@NotNull WSDLGenExtnContext ctxt) {
        for (WSDLGeneratorExtension e : extensions)
            e.end(ctxt);
    }

    public void addDefinitionsExtension(TypedXmlWriter definitions) {
        for (WSDLGeneratorExtension e : extensions)
            e.addDefinitionsExtension(definitions);
    }

    public void addServiceExtension(TypedXmlWriter service) {
        for (WSDLGeneratorExtension e : extensions)
            e.addServiceExtension(service);
    }

    public void addPortExtension(TypedXmlWriter port) {
        for (WSDLGeneratorExtension e : extensions)
            e.addPortExtension(port);
    }

    public void addPortTypeExtension(TypedXmlWriter portType) {
        for (WSDLGeneratorExtension e : extensions)
            e.addPortTypeExtension(portType);
    }

    public void addBindingExtension(TypedXmlWriter binding) {
        for (WSDLGeneratorExtension e : extensions)
            e.addBindingExtension(binding);
    }

    public void addOperationExtension(TypedXmlWriter operation, JavaMethod method) {
        for (WSDLGeneratorExtension e : extensions)
            e.addOperationExtension(operation, method);
    }

    public void addBindingOperationExtension(TypedXmlWriter operation, JavaMethod method) {
        for (WSDLGeneratorExtension e : extensions)
            e.addBindingOperationExtension(operation, method);
    }

    public void addInputMessageExtension(TypedXmlWriter message, JavaMethod method) {
        for (WSDLGeneratorExtension e : extensions)
            e.addInputMessageExtension(message, method);
    }

    public void addOutputMessageExtension(TypedXmlWriter message, JavaMethod method) {
        for (WSDLGeneratorExtension e : extensions)
            e.addOutputMessageExtension(message, method);
    }

    public void addOperationInputExtension(TypedXmlWriter input, JavaMethod method) {
        for (WSDLGeneratorExtension e : extensions)
            e.addOperationInputExtension(input, method);
    }

    public void addOperationOutputExtension(TypedXmlWriter output, JavaMethod method) {
        for (WSDLGeneratorExtension e : extensions)
            e.addOperationOutputExtension(output, method);
    }

    public void addBindingOperationInputExtension(TypedXmlWriter input, JavaMethod method) {
        for (WSDLGeneratorExtension e : extensions)
            e.addBindingOperationInputExtension(input, method);
    }

    public void addBindingOperationOutputExtension(TypedXmlWriter output, JavaMethod method) {
        for (WSDLGeneratorExtension e : extensions)
            e.addBindingOperationOutputExtension(output, method);
    }

    public void addBindingOperationFaultExtension(TypedXmlWriter fault, JavaMethod method, CheckedException ce) {
        for (WSDLGeneratorExtension e : extensions)
            e.addBindingOperationFaultExtension(fault, method, ce);
    }

    public void addFaultMessageExtension(TypedXmlWriter message, JavaMethod method, CheckedException ce) {
        for (WSDLGeneratorExtension e : extensions)
            e.addFaultMessageExtension(message, method, ce);
    }

    public void addOperationFaultExtension(TypedXmlWriter fault, JavaMethod method, CheckedException ce) {
        for (WSDLGeneratorExtension e : extensions)
            e.addOperationFaultExtension(fault, method, ce);
    }
}
