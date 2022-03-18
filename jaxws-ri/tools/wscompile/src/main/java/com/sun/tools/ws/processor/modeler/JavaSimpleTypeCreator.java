/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.modeler;

import com.sun.tools.ws.processor.model.java.JavaSimpleType;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author WS Development Team
 */
public final class JavaSimpleTypeCreator {

    /*
     * Mapped JavaSimpleTypes
     */
    public static final JavaSimpleType BOOLEAN_JAVATYPE = new JavaSimpleType(ModelerConstants.BOOLEAN_CLASSNAME.getValue(), ModelerConstants.FALSE_STR.getValue());
    public static final JavaSimpleType BOXED_BOOLEAN_JAVATYPE = new JavaSimpleType(ModelerConstants.BOXED_BOOLEAN_CLASSNAME.getValue(), ModelerConstants.NULL_STR.getValue());
    public static final JavaSimpleType BYTE_JAVATYPE = new JavaSimpleType(ModelerConstants.BYTE_CLASSNAME.getValue(), "(byte)" + ModelerConstants.ZERO_STR.getValue());
    public static final JavaSimpleType BYTE_ARRAY_JAVATYPE = new JavaSimpleType(ModelerConstants.BYTE_ARRAY_CLASSNAME.getValue(), ModelerConstants.NULL_STR.getValue());
    public static final JavaSimpleType BOXED_BYTE_JAVATYPE = new JavaSimpleType(ModelerConstants.BOXED_BYTE_CLASSNAME.getValue(), ModelerConstants.NULL_STR.getValue());
    public static final JavaSimpleType BOXED_BYTE_ARRAY_JAVATYPE = new JavaSimpleType(ModelerConstants.BOXED_BYTE_ARRAY_CLASSNAME.getValue(), ModelerConstants.NULL_STR.getValue());
    public static final JavaSimpleType DOUBLE_JAVATYPE = new JavaSimpleType(ModelerConstants.DOUBLE_CLASSNAME.getValue(), ModelerConstants.ZERO_STR.getValue());
    public static final JavaSimpleType BOXED_DOUBLE_JAVATYPE = new JavaSimpleType(ModelerConstants.BOXED_DOUBLE_CLASSNAME.getValue(), ModelerConstants.NULL_STR.getValue());
    public static final JavaSimpleType FLOAT_JAVATYPE = new JavaSimpleType(ModelerConstants.FLOAT_CLASSNAME.getValue(), ModelerConstants.ZERO_STR.getValue());
    public static final JavaSimpleType BOXED_FLOAT_JAVATYPE = new JavaSimpleType(ModelerConstants.BOXED_FLOAT_CLASSNAME.getValue(), ModelerConstants.NULL_STR.getValue());
    public static final JavaSimpleType INT_JAVATYPE = new JavaSimpleType(ModelerConstants.INT_CLASSNAME.getValue(), ModelerConstants.ZERO_STR.getValue());
    public static final JavaSimpleType BOXED_INTEGER_JAVATYPE = new JavaSimpleType(ModelerConstants.BOXED_INTEGER_CLASSNAME.getValue(), ModelerConstants.NULL_STR.getValue());
    public static final JavaSimpleType LONG_JAVATYPE = new JavaSimpleType(ModelerConstants.LONG_CLASSNAME.getValue(), ModelerConstants.ZERO_STR.getValue());
    public static final JavaSimpleType BOXED_LONG_JAVATYPE = new JavaSimpleType(ModelerConstants.BOXED_LONG_CLASSNAME.getValue(), ModelerConstants.NULL_STR.getValue());
    public static final JavaSimpleType SHORT_JAVATYPE = new JavaSimpleType(ModelerConstants.SHORT_CLASSNAME.getValue(), "(short)" + ModelerConstants.ZERO_STR.getValue());
    public static final JavaSimpleType BOXED_SHORT_JAVATYPE = new JavaSimpleType(ModelerConstants.BOXED_SHORT_CLASSNAME.getValue(), ModelerConstants.NULL_STR.getValue());
    public static final JavaSimpleType DECIMAL_JAVATYPE = new JavaSimpleType(ModelerConstants.BIGDECIMAL_CLASSNAME.getValue(), ModelerConstants.NULL_STR.getValue());
    public static final JavaSimpleType BIG_INTEGER_JAVATYPE = new JavaSimpleType(ModelerConstants.BIGINTEGER_CLASSNAME.getValue(), ModelerConstants.NULL_STR.getValue());
    public static final JavaSimpleType CALENDAR_JAVATYPE = new JavaSimpleType(ModelerConstants.CALENDAR_CLASSNAME.getValue(), ModelerConstants.NULL_STR.getValue());
    public static final JavaSimpleType DATE_JAVATYPE = new JavaSimpleType(ModelerConstants.DATE_CLASSNAME.getValue(), ModelerConstants.NULL_STR.getValue());
    public static final JavaSimpleType STRING_JAVATYPE = new JavaSimpleType(ModelerConstants.STRING_CLASSNAME.getValue(), ModelerConstants.NULL_STR.getValue());
    public static final JavaSimpleType STRING_ARRAY_JAVATYPE = new JavaSimpleType(ModelerConstants.STRING_ARRAY_CLASSNAME.getValue(), ModelerConstants.NULL_STR.getValue());
    public static final JavaSimpleType QNAME_JAVATYPE = new JavaSimpleType(ModelerConstants.QNAME_CLASSNAME.getValue(), ModelerConstants.NULL_STR.getValue());
    public static final JavaSimpleType VOID_JAVATYPE = new JavaSimpleType(ModelerConstants.VOID_CLASSNAME.getValue(), null);
    public static final JavaSimpleType OBJECT_JAVATYPE = new JavaSimpleType(ModelerConstants.OBJECT_CLASSNAME.getValue(), null);
    public static final JavaSimpleType SOAPELEMENT_JAVATYPE = new JavaSimpleType(ModelerConstants.SOAPELEMENT_CLASSNAME.getValue(), null);
    public static final JavaSimpleType URI_JAVATYPE = new JavaSimpleType(ModelerConstants.URI_CLASSNAME.getValue(), null);

    // Attachment types
    public static final JavaSimpleType IMAGE_JAVATYPE = new JavaSimpleType(ModelerConstants.IMAGE_CLASSNAME.getValue(), null);
    public static final JavaSimpleType MIME_MULTIPART_JAVATYPE = new JavaSimpleType(ModelerConstants.MIME_MULTIPART_CLASSNAME.getValue(), null);
    public static final JavaSimpleType SOURCE_JAVATYPE = new JavaSimpleType(ModelerConstants.SOURCE_CLASSNAME.getValue(), null);
    public static final JavaSimpleType DATA_HANDLER_JAVATYPE = new JavaSimpleType(ModelerConstants.DATA_HANDLER_CLASSNAME.getValue(), null);

    // bug fix: 4923650
    private static final Map<String, JavaSimpleType> JAVA_TYPES = new HashMap<>(31);

    static {
        JAVA_TYPES.put(ModelerConstants.BOOLEAN_CLASSNAME.getValue(), BOOLEAN_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.BOXED_BOOLEAN_CLASSNAME.getValue(), BOXED_BOOLEAN_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.BYTE_CLASSNAME.getValue(), BYTE_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.BYTE_ARRAY_CLASSNAME.getValue(), BYTE_ARRAY_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.BOXED_BYTE_CLASSNAME.getValue(), BOXED_BYTE_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.BOXED_BYTE_ARRAY_CLASSNAME.getValue(), BOXED_BYTE_ARRAY_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.DOUBLE_CLASSNAME.getValue(), DOUBLE_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.BOXED_DOUBLE_CLASSNAME.getValue(), BOXED_DOUBLE_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.FLOAT_CLASSNAME.getValue(), FLOAT_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.BOXED_FLOAT_CLASSNAME.getValue(), BOXED_FLOAT_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.INT_CLASSNAME.getValue(), INT_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.BOXED_INTEGER_CLASSNAME.getValue(), BOXED_INTEGER_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.LONG_CLASSNAME.getValue(), LONG_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.BOXED_LONG_CLASSNAME.getValue(), BOXED_LONG_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.SHORT_CLASSNAME.getValue(), SHORT_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.BOXED_SHORT_CLASSNAME.getValue(), BOXED_SHORT_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.BIGDECIMAL_CLASSNAME.getValue(), DECIMAL_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.BIGINTEGER_CLASSNAME.getValue(), BIG_INTEGER_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.CALENDAR_CLASSNAME.getValue(), CALENDAR_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.DATE_CLASSNAME.getValue(), DATE_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.STRING_CLASSNAME.getValue(), STRING_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.STRING_ARRAY_CLASSNAME.getValue(), STRING_ARRAY_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.QNAME_CLASSNAME.getValue(), QNAME_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.VOID_CLASSNAME.getValue(), VOID_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.OBJECT_CLASSNAME.getValue(), OBJECT_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.SOAPELEMENT_CLASSNAME.getValue(), SOAPELEMENT_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.URI_CLASSNAME.getValue(), URI_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.IMAGE_CLASSNAME.getValue(), IMAGE_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.MIME_MULTIPART_CLASSNAME.getValue(), MIME_MULTIPART_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.SOURCE_CLASSNAME.getValue(), SOURCE_JAVATYPE);
        JAVA_TYPES.put(ModelerConstants.DATA_HANDLER_CLASSNAME.getValue(), DATA_HANDLER_JAVATYPE);
    }

    private JavaSimpleTypeCreator() {
    }

    //  bug fix: 4923650
    public static JavaSimpleType getJavaSimpleType(String className) {
        return JAVA_TYPES.get(className);
    }
}
