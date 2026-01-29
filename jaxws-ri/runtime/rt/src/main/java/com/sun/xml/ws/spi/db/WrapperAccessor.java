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

import java.util.Map;

import javax.xml.namespace.QName;

/**
 * WrapperAccessor 
 * 
 * @author shih-chang.chen@oracle.com
 */
public abstract class WrapperAccessor {
	protected Map<Object, PropertySetter> propertySetters;
	protected Map<Object, PropertyGetter> propertyGetters;
	protected boolean elementLocalNameCollision;

	/**
	 * Default constructor.
	 */
	protected WrapperAccessor() {}

	protected PropertySetter getPropertySetter(QName name) {
        Object key = (elementLocalNameCollision) ? name : name.getLocalPart();
        return propertySetters.get(key);
    }	    
	protected PropertyGetter getPropertyGetter(QName name) {
        Object key = (elementLocalNameCollision) ? name : name.getLocalPart();
        return propertyGetters.get(key);
    }
	
	public PropertyAccessor getPropertyAccessor(String ns, String name) {
		QName n = new QName(ns, name);
		final PropertySetter setter = getPropertySetter(n);
		final PropertyGetter getter = getPropertyGetter(n);		
		return new PropertyAccessor() {
			@Override
            public Object get(Object bean) throws DatabindingException {
				return getter.get(bean);
			}

			@Override
            public void set(Object bean, Object value) throws DatabindingException {
				setter.set(bean, value);				
			}			
		};		
	}
}
