/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.assembler;

/**
* TODO: Write some description here ...
*
* @author Miroslav Kos (miroslav.kos at oracle.com)
*/
public class MetroConfigNameImpl implements MetroConfigName {

    private final String defaultFileName;
    private final String appFileName;

    public MetroConfigNameImpl(String defaultFileName, String appFileName) {
        this.defaultFileName = defaultFileName;
        this.appFileName = appFileName;
    }

    @Override
    public String getDefaultFileName() {
        return defaultFileName;
    }

    @Override
    public String getAppFileName() {
        return appFileName;
    }
}
