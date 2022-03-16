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

import java.util.HashMap;
import java.util.Map;

/**
 * This is the Setter of a bean property.
 * @author shih-chang.chen@oracle.com
 * @exclude
 */
public abstract class PropertySetterBase implements PropertySetter {
    protected Class type;
    
    public Class getType() {
        return type;
    }
        
    static public boolean setterPattern(java.lang.reflect.Method method) {
        return (method.getName().startsWith("set") &&
                method.getName().length() > 3 &&
                method.getReturnType().equals(void.class) &&
                method.getParameterTypes() != null &&
                method.getParameterTypes().length == 1);
    }

    /**
     * Uninitialized map keyed by their classes.
     */
    private static final Map<Class, Object> uninitializedValues = new HashMap<>();
    static {
        uninitializedValues.put(byte.class, Byte.valueOf((byte) 0));
        uninitializedValues.put(boolean.class, false);
        uninitializedValues.put(char.class, Character.valueOf((char) 0));
        uninitializedValues.put(float.class, Float.valueOf(0));
        uninitializedValues.put(double.class, Double.valueOf(0));
        uninitializedValues.put(int.class, Integer.valueOf(0));
        uninitializedValues.put(long.class, Long.valueOf(0));
        uninitializedValues.put(short.class, Short.valueOf((short) 0));
    }
    static protected Object uninitializedValue(Class cls) { return uninitializedValues.get(cls); }    
}



