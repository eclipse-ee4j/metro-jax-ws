/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.db.glassfish;

import com.sun.xml.ws.spi.db.DatabindingException;
import com.sun.xml.ws.spi.db.PropertyAccessor;
import com.sun.xml.bind.api.AccessorException;
import com.sun.xml.bind.api.RawAccessor;

@SuppressWarnings("unchecked")
public class RawAccessorWrapper implements PropertyAccessor {

    private RawAccessor accessor;

    public RawAccessorWrapper(RawAccessor a) {
        accessor = a;
    }

    @Override
    public boolean equals(Object obj) {
        return accessor.equals(obj);
    }

    @Override
    public Object get(Object bean) throws DatabindingException {
        try {
            return accessor.get(bean);
        } catch (AccessorException e) {
            throw new DatabindingException(e);
        }
    }

    @Override
    public int hashCode() {
        return accessor.hashCode();
    }

    @Override
    public void set(Object bean, Object value) throws DatabindingException {
        try {
            accessor.set(bean, value);
        } catch (AccessorException e) {
            throw new DatabindingException(e);
        }
    }

    @Override
    public String toString() {
        return accessor.toString();
    }
}
