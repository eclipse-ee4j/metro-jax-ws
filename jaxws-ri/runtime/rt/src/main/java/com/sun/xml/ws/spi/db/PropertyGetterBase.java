/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.spi.db;

import javax.xml.ws.WebServiceException;

/**
 * This is the Gtter of a bean property. 
 * 
 * @author shih-chang.chen@oracle.com
 * @exclude
 */
public abstract class PropertyGetterBase implements PropertyGetter {
    protected Class type;
    
    public Class getType() {
        return type;
    }
    
    static public boolean getterPattern(java.lang.reflect.Method method) {
        if (!method.getReturnType().equals(void.class) &&
            (method.getParameterTypes() == null ||
             method.getParameterTypes().length == 0)) {
            if (method.getName().startsWith("get") &&
                method.getName().length() > 3) {
                return true;
            } else {
                if ((method.getReturnType().equals(boolean.class) || method.getReturnType().equals(Boolean.class)) &&
                    method.getName().startsWith("is") &&
                    method.getName().length() > 2) {
                    return true;
                }
            }
        } 
        return false;
    }
    
    static void verifyWrapperType(Class wrapperType) {
        String className = wrapperType.getName();
        if (className.startsWith("java.") || className.startsWith("javax.") || className.startsWith("jakarta.")) {
            throw new WebServiceException("Invalid wrapper type " + className);
        }
    }
}
