/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util.xml;

/**
 * @author WS Development Team
 */
public final class CDATA {

    public CDATA(String text) {
        _text = text;
    }

    public String getText() {
        return _text;
    }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof CDATA))
            return false;

        CDATA cdata = (CDATA) obj;

        return this._text.equals(cdata._text);
    }

    public int hashCode() {
        return _text.hashCode();
    }

    private String _text;
}
