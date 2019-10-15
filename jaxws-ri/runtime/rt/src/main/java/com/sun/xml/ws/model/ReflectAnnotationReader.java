/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;

import com.sun.xml.ws.api.databinding.MetadataReader;

/**
 * ReflectAnnotationReader
 * 
 * @author shih-chang.chen@oracle.com
 */
public class ReflectAnnotationReader implements MetadataReader {
//getAnnotationOnImpl SEIorIMpl
	public Annotation[] getAnnotations(Method m) {
		return m.getAnnotations();
	}

	public Annotation[][] getParameterAnnotations(final Method method) {
        return AccessController.doPrivileged(new PrivilegedAction<Annotation[][]>() {
           public Annotation[][] run() {
               return method.getParameterAnnotations();
           }
        });
    }
	
	public <A extends Annotation> A getAnnotation(final Class<A> annType, final Method m) {
        return AccessController.doPrivileged(new PrivilegedAction<A>() {
            public A run() {
                return m.getAnnotation(annType);
            }
         });
	}
	
	public <A extends Annotation> A getAnnotation(final Class<A> annType, final Class<?> cls) {
        return AccessController.doPrivileged(new PrivilegedAction<A>() {
            public A run() {
                return cls.getAnnotation(annType);
            }
        });
	}

    public Annotation[] getAnnotations(final Class<?> cls) {
        return AccessController.doPrivileged(new PrivilegedAction<Annotation[]>() {
            public Annotation[] run() {
                return cls.getAnnotations();
            }
        });
    }

    public void getProperties(final Map<String, Object> prop, final Class<?> cls){}
    
    public void getProperties(final Map<String, Object> prop, final Method method){}  
    
    public void getProperties(final Map<String, Object> prop, final Method method, int pos){}
}
