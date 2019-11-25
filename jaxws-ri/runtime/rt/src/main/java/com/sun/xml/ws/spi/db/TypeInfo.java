/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.spi.db;

import java.lang.annotation.Annotation;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * A reference to a JAXB-bound type.
 *
 * <p>
 * <b>Subject to change without notice</b>.
 *
 * @since 2.0 EA1
 * @author Kohsuke Kawaguchi
 * @author shih-chang.chen@oracle.com
 */
public final class TypeInfo {

    /**
     * The associated XML element name that the JAX-RPC uses with this type reference.
     *
     * Always non-null. Strings are interned.
     */
    public final QName tagName;

    /**
     * The Java type that's being referenced.
     *
     * Always non-null.
     */
    public Type type;

    /**
     * The annotations associated with the reference of this type.
     *
     * Always non-null.
     */
    public final Annotation[] annotations;
    
    private Map<String, Object> properties = new HashMap<String, Object>();
    
    private boolean isGlobalElement = true;
    
    private TypeInfo parentCollectionType;
    
    private TypeInfo wrapperType;
   
    private Type genericType;
    
    private boolean nillable = true;

    public TypeInfo(QName tagName, Type type, Annotation... annotations) {
        if(tagName==null || type==null || annotations==null) {
            String nullArgs = "";

            if(tagName == null)     nullArgs = "tagName";
            if(type == null)        nullArgs += (nullArgs.length() > 0 ? ", type" : "type");
            if(annotations == null) nullArgs += (nullArgs.length() > 0 ? ", annotations" : "annotations");

//            Messages.ARGUMENT_CANT_BE_NULL.format(nullArgs);
            
            throw new IllegalArgumentException( "Argument(s) \"" + nullArgs + "\" can''t be null.)");
        }

        this.tagName = new QName(tagName.getNamespaceURI().intern(), tagName.getLocalPart().intern(), tagName.getPrefix());
        this.type = type;
        if (type instanceof Class && ((Class<?>)type).isPrimitive()) nillable = false;
        this.annotations = annotations;
    }

    /**
     * Finds the specified annotation from the array and returns it.
     * Null if not found.
     */
    public <A extends Annotation> A get( Class<A> annotationType ) {
        for (Annotation a : annotations) {
            if(a.annotationType()==annotationType)
                return annotationType.cast(a);
        }
        return null;
    }

    /**
     * Creates a {@link TypeInfo} for the item type,
     * if this {@link TypeInfo} represents a collection type.
     * Otherwise returns an identical type.
     */
    public TypeInfo toItemType() {
        // if we are to reinstitute this check, check JAXB annotations only 
        // assert annotations.length==0;   // not designed to work with adapters.
        Type t = (genericType != null)? genericType : type;
        Type base = Utils.REFLECTION_NAVIGATOR.getBaseClass(t, Collection.class);
        if(base==null)
            return this;    // not a collection

        return new TypeInfo(tagName, Utils.REFLECTION_NAVIGATOR.getTypeArgument(base,0));
    }

    public Map<String, Object> properties() {
		return properties;
	}
    
	public boolean isGlobalElement() {
		return isGlobalElement;
	}

	public void setGlobalElement(boolean isGlobalElement) {
		this.isGlobalElement = isGlobalElement;
	}

	public TypeInfo getParentCollectionType() {
		return parentCollectionType;
	}

	public void setParentCollectionType(TypeInfo parentCollectionType) {
		this.parentCollectionType = parentCollectionType;
	}

	public boolean isRepeatedElement() {
		return (parentCollectionType != null);
	}

	public Type getGenericType() {
		return genericType;
	}

	public void setGenericType(Type genericType) {
		this.genericType = genericType;
	}
    
    public boolean isNillable() {
        return nillable;
    }

    public void setNillable(boolean nillable) {
        this.nillable = nillable;
    }
	
    public String toString() {
        return new StringBuilder("TypeInfo: Type = ").append(type)
                .append(", tag = ").append(tagName).toString();
    }

    public TypeInfo getItemType() {
        if (type instanceof Class && ((Class)type).isArray() && !byte[].class.equals(type)) {
            Type componentType = ((Class)type).getComponentType();
            Type genericComponentType = null;
            if (genericType!= null && genericType instanceof GenericArrayType) {
                GenericArrayType arrayType = (GenericArrayType) type;
                genericComponentType = arrayType.getGenericComponentType();
                componentType = arrayType.getGenericComponentType();
            }
            TypeInfo ti =new TypeInfo(tagName, componentType, annotations);
            if (genericComponentType != null) ti.setGenericType(genericComponentType);
            for(Annotation anno : annotations) if (anno instanceof XmlElementWrapper) ti.wrapperType = this;
            return ti;
        }
//        if (type instanceof Class && java.util.Collection.class.isAssignableFrom((Class)type)) {
        Type t = (genericType != null)? genericType : type;
        Type base = Utils.REFLECTION_NAVIGATOR.getBaseClass(t, Collection.class);
        if ( base != null)  {
            return new TypeInfo(tagName, Utils.REFLECTION_NAVIGATOR.getTypeArgument(base,0), annotations);
        }    
        return null;
    }
    
    public TypeInfo getWrapperType() {
        return wrapperType;
    }
}
