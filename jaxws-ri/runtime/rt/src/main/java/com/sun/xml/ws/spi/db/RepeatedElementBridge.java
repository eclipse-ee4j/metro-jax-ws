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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.NoSuchElementException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.attachment.AttachmentMarshaller;
import javax.xml.bind.attachment.AttachmentUnmarshaller;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;

import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;

/**
 * RepeatedElementBridge
 * 
 * @author shih-chang.chen@oracle.com
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class RepeatedElementBridge<T> implements XMLBridge<T> {
    
    XMLBridge<T> delegate;    
    CollectionHandler collectionHandler;

    public RepeatedElementBridge(TypeInfo typeInfo, XMLBridge xb) { 
        delegate = xb;
        collectionHandler = create(typeInfo);
    }
    
    public CollectionHandler collectionHandler() {
        return collectionHandler;
    }

    @Override
    public BindingContext context() {
        return delegate.context();
    }

    @Override
    public void marshal(T object, XMLStreamWriter output, AttachmentMarshaller am) throws JAXBException {
        delegate.marshal(object, output, am);
    }

    @Override
    public void marshal(T object, OutputStream output, NamespaceContext nsContext, AttachmentMarshaller am) throws JAXBException {
        delegate.marshal(object, output, nsContext, am);
    }

    @Override
    public void marshal(T object, Node output) throws JAXBException {
        delegate.marshal(object, output);
    }

    @Override
    public void marshal(T object, ContentHandler contentHandler, AttachmentMarshaller am) throws JAXBException {
        delegate.marshal(object, contentHandler, am);
    }

    @Override
    public void marshal(T object, Result result) throws JAXBException {
        delegate.marshal(object, result);
    }

    @Override
    public T unmarshal(XMLStreamReader in, AttachmentUnmarshaller au) throws JAXBException {
        return delegate.unmarshal(in, au);
    }

    @Override
    public T unmarshal(Source in, AttachmentUnmarshaller au) throws JAXBException {
        return delegate.unmarshal(in, au);
    }

    @Override
    public T unmarshal(InputStream in) throws JAXBException {
        return delegate.unmarshal(in);
    }

    @Override
    public T unmarshal(Node n, AttachmentUnmarshaller au) throws JAXBException {
        return delegate.unmarshal(n, au);
    }

    @Override
    public TypeInfo getTypeInfo() {
        return delegate.getTypeInfo();
    }

    @Override
    public boolean supportOutputStream() {
        return delegate.supportOutputStream();
    }
    
    static public interface CollectionHandler {
        int getSize(Object c);
        Iterator iterator(Object c);
        Object convert(List list);
    }  
    
    static class BaseCollectionHandler implements CollectionHandler {
        Class type;
        BaseCollectionHandler(Class c) {type = c;}
        @Override
        public int getSize(Object c) { return ((Collection) c).size(); }
        @Override
        public Object convert(List list) {
            try {
                Object o = type.newInstance();
                ((Collection)o).addAll(list);
                return o;
            } catch (Exception e) {
                e.printStackTrace();
            } 
            return list;
        }
        @Override
        public Iterator iterator(Object c) {return ((Collection)c).iterator();}   
    }
    
    static final CollectionHandler ListHandler = new BaseCollectionHandler(List.class) {
        @Override
        public Object convert(List list) {return list;}
    };
  
    static final CollectionHandler HashSetHandler = new BaseCollectionHandler(HashSet.class) {
        @Override
        public Object convert(List list) { return new HashSet(list);}
    };
    
    static public CollectionHandler create(TypeInfo ti) {
        Class javaClass = (Class) ti.type;
        if (javaClass.isArray()) {
            return new ArrayHandler((Class) ti.getItemType().type);
        } else if (List.class.equals(javaClass) || Collection.class.equals(javaClass)) {
            return ListHandler;
        } else if (Set.class.equals(javaClass) || HashSet.class.equals(javaClass)) {
            return HashSetHandler;
        } else {
            return new BaseCollectionHandler(javaClass);
        }
    }
    
    static class ArrayHandler implements CollectionHandler {
        Class componentClass;            
        public ArrayHandler(Class component) {
            componentClass = component;
        }            
        @Override
        public int getSize(Object c) {
            return java.lang.reflect.Array.getLength(c);
        }            
        @Override
        public Object convert(List list) {
            Object array = java.lang.reflect.Array.newInstance(componentClass, list.size());
            for (int i = 0; i < list.size(); i++) {
                java.lang.reflect.Array.set(array, i, list.get(i));
            }
            return array;
        }            
        @Override
        public Iterator iterator(final Object c) {
            return new Iterator() {
                int index = 0;
                @Override
                public boolean hasNext() {
                    if (c == null || java.lang.reflect.Array.getLength(c) == 0) {
                        return false;
                    }
                    return (index != java.lang.reflect.Array.getLength(c));
                }   
                @Override
                public Object next() throws NoSuchElementException {
                    Object retVal = null;
                    try {
                        retVal = java.lang.reflect.Array.get(c, index++);
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        throw new NoSuchElementException();
                    }
                    return retVal;
                }
                @Override
                public void remove() {}        
            };
        }       
    }    
}
