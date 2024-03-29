/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.db.glassfish;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.SchemaOutputResolver;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import org.glassfish.jaxb.runtime.api.JAXBRIContext;
import org.glassfish.jaxb.runtime.api.TypeReference;
import org.glassfish.jaxb.runtime.v2.model.runtime.RuntimeTypeInfoSet;
import com.sun.xml.ws.spi.db.BindingContext;
import com.sun.xml.ws.spi.db.XMLBridge;
import com.sun.xml.ws.spi.db.TypeInfo;
import com.sun.xml.ws.spi.db.WrapperComposite;

class JAXBRIContextWrapper implements BindingContext {

    private Map<TypeInfo, TypeReference> typeRefs;
    private Map<TypeReference, TypeInfo> typeInfos;
    private JAXBRIContext context;

    JAXBRIContextWrapper(JAXBRIContext cxt, Map<TypeInfo, TypeReference> refs) {
        context = cxt;
        typeRefs = refs;
        if (refs != null) {
            typeInfos = new java.util.HashMap<>();
            for (TypeInfo ti : refs.keySet()) {
                typeInfos.put(typeRefs.get(ti), ti);
            }
        }
    }

    TypeReference typeReference(TypeInfo ti) {
        return (typeRefs != null) ? typeRefs.get(ti) : null;
    }

    TypeInfo typeInfo(TypeReference tr) {
        return (typeInfos != null) ? typeInfos.get(tr) : null;
    }

    @Override
    public Marshaller createMarshaller() throws JAXBException {
        return context.createMarshaller();
    }

    @Override
    public Unmarshaller createUnmarshaller() throws JAXBException {
        return context.createUnmarshaller();
    }

    @Override
    public void generateSchema(SchemaOutputResolver outputResolver)
            throws IOException {
        context.generateSchema(outputResolver);
    }

    @Override
    public String getBuildId() {
        return context.getBuildId();
    }

    @Override
    public QName getElementName(Class o) throws JAXBException {
        return context.getElementName(o);
    }

    @Override
    public QName getElementName(Object o) throws JAXBException {
        return context.getElementName(o);
    }

    @Override
    public <B, V> com.sun.xml.ws.spi.db.PropertyAccessor<B, V> getElementPropertyAccessor(
            Class<B> wrapperBean, String nsUri, String localName)
            throws JAXBException {
        return new RawAccessorWrapper(context.getElementPropertyAccessor(wrapperBean, nsUri, localName));
    }

    @Override
    public List<String> getKnownNamespaceURIs() {
        return context.getKnownNamespaceURIs();
    }

    public RuntimeTypeInfoSet getRuntimeTypeInfoSet() {
        return context.getRuntimeTypeInfoSet();
    }

    public QName getTypeName(org.glassfish.jaxb.runtime.api.TypeReference tr) {
        return context.getTypeName(tr);
    }

    @Override
    public int hashCode() {
        return context.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JAXBRIContextWrapper other = (JAXBRIContextWrapper) obj;
        return this.context == other.context || (this.context != null && this.context.equals(other.context));
    }

    @Override
    public boolean hasSwaRef() {
        return context.hasSwaRef();
    }

    @Override
    public String toString() {
        return JAXBRIContextWrapper.class.getName() + " : " + context.toString();
    }

    @Override
    public XMLBridge createBridge(TypeInfo ti) {
        TypeReference tr = typeRefs.get(ti);
        org.glassfish.jaxb.runtime.api.Bridge b = context.createBridge(tr);
        return WrapperComposite.class.equals(ti.type)
                ? new WrapperBridge(this, b)
                : new BridgeWrapper(this, b);
    }

    @Override
    public JAXBContext getJAXBContext() {
        return context;
    }

    @Override
    public QName getTypeName(TypeInfo ti) {
        TypeReference tr = typeRefs.get(ti);
        return context.getTypeName(tr);
    }

    @Override
    public XMLBridge createFragmentBridge() {
        return new MarshallerBridge((org.glassfish.jaxb.runtime.v2.runtime.JAXBContextImpl) context);
    }

    @Override
    public Object newWrapperInstace(Class<?> wrapperType)
            throws ReflectiveOperationException {
        return wrapperType.getConstructor().newInstance();
    }
}
