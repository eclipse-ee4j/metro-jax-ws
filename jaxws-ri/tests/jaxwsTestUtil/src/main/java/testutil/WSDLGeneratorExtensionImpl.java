/*
 * Copyright (c) 2018, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package testutil;

import javax.xml.namespace.QName;

import com.sun.xml.txw2.TypedXmlWriter;

import com.sun.xml.ws.api.model.CheckedException;
import com.sun.xml.ws.api.wsdl.writer.WSDLGeneratorExtension;

import java.lang.reflect.Method;


public class WSDLGeneratorExtensionImpl extends WSDLGeneratorExtension {
    private void addAttribute(TypedXmlWriter writer, String attrName, String attrValue) {
        writer._attribute(new QName("jaxws_test", attrName), attrValue);
    }

    @Override
    public void addServiceExtension(TypedXmlWriter service) {
        addAttribute(service, "serviceAttr", "myService");
    }

    @Override
    public void addPortExtension(TypedXmlWriter port) {
        addAttribute(port, "portAttr", "myPort");
    }

    @Override
    public void addPortTypeExtension(TypedXmlWriter portType) {
        addAttribute(portType, "portTypeAttr", "myPortType");
    }

    @Override
    public void addBindingExtension(TypedXmlWriter binding) {
        addAttribute(binding, "bindingAttr", "myBinding");
    }

    public void addOperationExtension(TypedXmlWriter operation, Method method) {
        addAttribute(operation, "operationAttr", "myOperation");
    }

    public void addBindingOperationExtension(TypedXmlWriter operation, Method method) {
        addAttribute(operation, "bindingOperationAttr", "myBindingOperation");
    }

    public void addInputMessageExtension(TypedXmlWriter message, Method method) {
        addAttribute(message, "inputMessageAttr", "myInputMessage");
    }

    public void addOutputMessageExtension(TypedXmlWriter message, Method method) {
        addAttribute(message, "outputMessageAttr", "myOutputMessage");
    }

    public void addOperationInputExtension(TypedXmlWriter input, Method method) {
        addAttribute(input, "operationInputAttr", "myOperationInput");
    }

    public void addOperationOutputExtension(TypedXmlWriter output, Method method) {
        addAttribute(output, "operationOutputAttr", "myOperationOutput");
    }

    public void addBindingOperationInputExtension(TypedXmlWriter input, Method method) {
        addAttribute(input, "bindingOperationInputAttr", "myBindingOperationInput");
    }

    public void addBindingOperationOutputExtension(TypedXmlWriter output, Method method) {
        addAttribute(output, "bindingOperationOutputAttr", "myBindingOperationOutput");
    }

    public void addBindingOperationFaultExtension(TypedXmlWriter fault, Method method) {
        addAttribute(fault, "bindingOperationFaultAttr", "myBindingOperationFault");
    }

    public void addFaultMessageExtension(TypedXmlWriter message, Method method) {
        addAttribute(message, "faultMessageAttr", "myFaultMessage");
    }

    public void addOperationFaultExtension(TypedXmlWriter fault, Method method, CheckedException ce) {
        addAttribute(fault, "operationFaultAttr", "myOperationFault");
    }
}
