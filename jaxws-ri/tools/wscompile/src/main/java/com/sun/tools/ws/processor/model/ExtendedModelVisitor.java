/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.model;

import java.util.Iterator;

/**
 *
 * A model visitor incorporating all the logic required to walk through the model.
 *
 * @author WS Development Team
 */
public class ExtendedModelVisitor {

    public ExtendedModelVisitor() {}

    public void visit(Model model) throws Exception {
        preVisit(model);
        for (Service service : model.getServices()) {
            preVisit(service);
            for (Port port : service.getPorts()) {
                preVisit(port);
                if (shouldVisit(port)) {
                    for (Operation operation : port.getOperations()) {                        
                        preVisit(operation);
                        Request request = operation.getRequest();
                        if (request != null) {
                            preVisit(request);
                            for (Iterator<Block> iter4 = request.getHeaderBlocks();
                                iter4.hasNext();) {

                                Block block = iter4.next();
                                visitHeaderBlock(block);
                            }
                            for (Iterator<Block> iter4 = request.getBodyBlocks();
                                iter4.hasNext();) {

                                Block block = iter4.next();
                                visitBodyBlock(block);
                            }
                            for (Iterator<Parameter> iter4 = request.getParameters();
                                iter4.hasNext();) {

                                Parameter parameter = iter4.next();
                                visit(parameter);
                            }
                            postVisit(request);
                        }

                        Response response = operation.getResponse();
                        if (response != null) {
                            preVisit(response);
                            for (Iterator<Block> iter4 = response.getHeaderBlocks();
                                iter4.hasNext();) {

                                Block block = iter4.next();
                                visitHeaderBlock(block);
                            }
                            for (Iterator<Block> iter4 = response.getBodyBlocks();
                                iter4.hasNext();) {

                                Block block = iter4.next();
                                visitBodyBlock(block);
                            }
                            for (Iterator<Parameter> iter4 = response.getParameters();
                                iter4.hasNext();) {

                                Parameter parameter = iter4.next();
                                visit(parameter);
                            }
                            postVisit(response);
                        }

                        for (Iterator<Fault> iter4 = operation.getFaults();
                            iter4.hasNext();) {

                            Fault fault = iter4.next();
                            preVisit(fault);
                            visitFaultBlock(fault.getBlock());
                            postVisit(fault);
                        }
                        postVisit(operation);
                    }
                }
                postVisit(port);
            }
            postVisit(service);
        }
        postVisit(model);
    }

    protected boolean shouldVisit(Port port) {
        return true;
    }

    // these methods are intended for subclasses
    protected void preVisit(Model model) throws Exception {}
    protected void postVisit(Model model) throws Exception {}
    protected void preVisit(Service service) throws Exception {}
    protected void postVisit(Service service) throws Exception {}
    protected void preVisit(Port port) throws Exception {}
    protected void postVisit(Port port) throws Exception {}
    protected void preVisit(Operation operation) throws Exception {}
    protected void postVisit(Operation operation) throws Exception {}
    protected void preVisit(Request request) throws Exception {}
    protected void postVisit(Request request) throws Exception {}
    protected void preVisit(Response response) throws Exception {}
    protected void postVisit(Response response) throws Exception {}
    protected void preVisit(Fault fault) throws Exception {}
    protected void postVisit(Fault fault) throws Exception {}
    protected void visitBodyBlock(Block block) throws Exception {}
    protected void visitHeaderBlock(Block block) throws Exception {}
    protected void visitFaultBlock(Block block) throws Exception {}
    protected void visit(Parameter parameter) throws Exception {}
}
