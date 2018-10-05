/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.model.java;

import com.sun.tools.ws.processor.model.Parameter;

/**
 *
 * @author WS Development Team
 */
public class JavaParameter {

    public JavaParameter() {}

    public JavaParameter(String name, JavaType type, Parameter parameter) {
        this(name, type, parameter, false);
    }

    public JavaParameter(String name, JavaType type, Parameter parameter,
        boolean holder) {

        this.name = name;
        this.type = type;
        this.parameter = parameter;
        this.holder = holder;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }

    public JavaType getType() {
        return type;
    }

    public void setType(JavaType t) {
        type = t;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter p) {
        parameter = p;
    }

    public boolean isHolder() {
        return holder;
    }

    public void setHolder(boolean b) {
        holder = b;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    private String name;
    private JavaType type;
    private Parameter parameter;
    private boolean holder;
    private String holderName;
}
