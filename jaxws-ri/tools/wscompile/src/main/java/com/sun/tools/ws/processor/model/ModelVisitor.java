/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.model;

/**
 *
 * @author WS Development Team
 */
public interface ModelVisitor {
    void visit(Model model) throws Exception;
    void visit(Service service) throws Exception;
    void visit(Port port) throws Exception;
    void visit(Operation operation) throws Exception;
    void visit(Request request) throws Exception;
    void visit(Response response) throws Exception;
    void visit(Fault fault) throws Exception;
    void visit(Block block) throws Exception;
    void visit(Parameter parameter) throws Exception;
}
