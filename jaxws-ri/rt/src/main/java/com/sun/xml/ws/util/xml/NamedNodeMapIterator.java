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

import java.util.Iterator;

import org.w3c.dom.NamedNodeMap;

/**
 * @author WS Development Team
 */
public class NamedNodeMapIterator implements Iterator {

    protected NamedNodeMap _map;
    protected int _index;

    public NamedNodeMapIterator(NamedNodeMap map) {
        _map = map;
        _index = 0;
    }

    public boolean hasNext() {
        if (_map == null)
            return false;
        return _index < _map.getLength();
    }

    public Object next() {
        Object obj = _map.item(_index);
        if (obj != null)
            ++_index;
        return obj;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
