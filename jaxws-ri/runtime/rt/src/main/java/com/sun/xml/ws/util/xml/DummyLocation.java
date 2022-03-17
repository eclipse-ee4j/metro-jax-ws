/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util.xml;

import javax.xml.stream.Location;

/**
 * {@link Location} that returns no info.
 *
 * @author Santiago.PericasGeertsen@sun.com
 */
public final class DummyLocation implements Location {
    private DummyLocation() {}

    public static final Location INSTANCE = new DummyLocation();

    @Override
    public int getCharacterOffset() {
        return -1;
    }
    @Override
    public int getColumnNumber() {
        return -1;
    }
    @Override
    public int getLineNumber() {
        return -1;
    }
    @Override
    public String getPublicId() {
        return null;
    }
    @Override
    public String getSystemId() {
        return null;
    }
}
