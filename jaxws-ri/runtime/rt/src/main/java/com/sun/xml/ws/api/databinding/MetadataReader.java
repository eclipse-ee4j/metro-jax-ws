/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.databinding;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * MetadataReader
 * 
 * @author shih-chang.chen@oracle.com
 */
public interface MetadataReader {
	
    Annotation[] getAnnotations(Method m) ;
    
    Annotation[][] getParameterAnnotations(final Method method);
		
    <A extends Annotation> A getAnnotation(final Class<A> annType, final Method m);
		
    <A extends Annotation> A getAnnotation(final Class<A> annType, final Class<?> cls);

    Annotation[] getAnnotations(Class<?> c);

    void getProperties(final Map<String, Object> prop, final Class<?> cls);
    
    void getProperties(final Map<String, Object> prop, final Method method);
    
    void getProperties(final Map<String, Object> prop, final Method method, int pos);
    
}
