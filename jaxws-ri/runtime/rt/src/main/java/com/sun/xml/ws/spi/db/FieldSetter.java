/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.spi.db;

import java.lang.reflect.Field;
import jakarta.xml.ws.WebServiceException;

/**
 * FieldSetter 
 * @author shih-chang.chen@oracle.com
 * @exclude
 */
public class FieldSetter extends PropertySetterBase {
    
    protected Field field;
    
    public FieldSetter(Field f) {
        PropertyGetterBase.verifyWrapperType(f.getDeclaringClass());
        field = f;
        type = f.getType();
    }
    
    public Field getField() {
        return field;
    }

    public void set(final Object instance, final Object val) {
        final Object resource = (type.isPrimitive() && val == null)? uninitializedValue(type): val;
        try {
            field.set(instance, resource);
        } catch (Exception e) {
            throw new WebServiceException(e);
        }
    }
    
    public <A> A getAnnotation(Class<A> annotationType) {
        Class c = annotationType;
        return (A) field.getAnnotation(c);
    }
}
