/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.spi.db;

import static com.sun.xml.ws.spi.db.PropertyGetterBase.verifyWrapperType;
import java.lang.reflect.Method;
import jakarta.xml.ws.WebServiceException;


/**
 * MethodInjection 
 * @author shih-chang.chen@oracle.com
 * @exclude
 */
public class MethodSetter extends PropertySetterBase {
    
    private Method method;
    
    public MethodSetter(Method m) {
        verifyWrapperType(m.getDeclaringClass());      
        method = m;
        type = m.getParameterTypes()[0];
    }
    
    public Method getMethod() {
        return method;
    }

    public <A> A getAnnotation(Class<A> annotationType) {
        Class c = annotationType;
        return (A) method.getAnnotation(c);
    }
    
    public void set(final Object instance, Object val) {
        final Object resource = (type.isPrimitive() && val == null)? uninitializedValue(type): val;
        final Object[] args = {resource};
        try {
            method.invoke(instance, args);
        } catch (Exception e) {
            throw new WebServiceException(e);
        }
    }
}
