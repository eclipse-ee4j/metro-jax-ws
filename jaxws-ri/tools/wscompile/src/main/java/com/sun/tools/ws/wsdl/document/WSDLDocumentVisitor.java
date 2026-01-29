/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.document;

import com.sun.tools.ws.wsdl.framework.ExtensionVisitor;

/**
 * A visitor for WSDL documents.
 *
 * @author WS Development Team
 */
public interface WSDLDocumentVisitor extends ExtensionVisitor {

    void preVisit(Definitions definitions) throws Exception;
    void postVisit(Definitions definitions) throws Exception;
    void visit(Import i) throws Exception;
    void preVisit(Types types) throws Exception;
    void postVisit(Types types) throws Exception;
    void preVisit(Message message) throws Exception;
    void postVisit(Message message) throws Exception;
    void visit(MessagePart part) throws Exception;
    void preVisit(PortType portType) throws Exception;
    void postVisit(PortType portType) throws Exception;
    void preVisit(Operation operation) throws Exception;
    void postVisit(Operation operation) throws Exception;
    void preVisit(Input input) throws Exception;
    void postVisit(Input input) throws Exception;
    void preVisit(Output output) throws Exception;
    void postVisit(Output output) throws Exception;
    void preVisit(Fault fault) throws Exception;
    void postVisit(Fault fault) throws Exception;
    void preVisit(Binding binding) throws Exception;
    void postVisit(Binding binding) throws Exception;
    void preVisit(BindingOperation operation) throws Exception;
    void postVisit(BindingOperation operation) throws Exception;
    void preVisit(BindingInput input) throws Exception;
    void postVisit(BindingInput input) throws Exception;
    void preVisit(BindingOutput output) throws Exception;
    void postVisit(BindingOutput output) throws Exception;
    void preVisit(BindingFault fault) throws Exception;
    void postVisit(BindingFault fault) throws Exception;
    void preVisit(Service service) throws Exception;
    void postVisit(Service service) throws Exception;
    void preVisit(Port port) throws Exception;
    void postVisit(Port port) throws Exception;
    void visit(Documentation documentation) throws Exception;
}
