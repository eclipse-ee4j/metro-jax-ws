/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.modeler.annotation;

/**
 *
 * @author  dkohlert
 */
public class WrapperInfo {
    public String wrapperName;

    /** Creates a new instance of FaultInfo */
    public WrapperInfo() {
    }
    public WrapperInfo(String wrapperName) {
        this.wrapperName = wrapperName;
    }

    public void setWrapperName(String wrapperName) {
        this.wrapperName = wrapperName;
    }

    public String getWrapperName() {
        return wrapperName;
    }

}
