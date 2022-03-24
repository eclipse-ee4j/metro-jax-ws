/*
 * Copyright (c) 2017, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.ant;

import org.apache.tools.ant.Project;

/**
 *
 * @author lukas
 */
public class WsImport2 extends WsImportBase {

    /**
     * Default constructor.
     */
    public WsImport2() {}

    @Override
    public void setXendorsed(boolean xendorsed) {
        log("xendorsed attribute not supported", Project.MSG_WARN);
        //no-op
    }

}
