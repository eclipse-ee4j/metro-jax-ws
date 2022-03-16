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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Type;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

import org.glassfish.jaxb.runtime.api.TypeReference;
import org.glassfish.jaxb.runtime.api.JAXBRIContext;
import org.glassfish.jaxb.runtime.api.CompositeStructure;
import org.glassfish.jaxb.runtime.v2.ContextFactory;
import org.glassfish.jaxb.runtime.v2.model.annotation.RuntimeAnnotationReader;
import org.glassfish.jaxb.runtime.v2.runtime.MarshallerImpl;
import com.sun.xml.ws.developer.JAXBContextFactory;
import com.sun.xml.ws.spi.db.BindingContext;
import com.sun.xml.ws.spi.db.BindingContextFactory;
import com.sun.xml.ws.spi.db.BindingInfo;
import com.sun.xml.ws.spi.db.DatabindingException;
import com.sun.xml.ws.spi.db.TypeInfo;
import com.sun.xml.ws.spi.db.WrapperComposite;
import java.util.Arrays;

/**
 * JAXBRIContextFactory
 *
 * @author shih-chang.chen@oracle.com
 */
public class JAXBRIContextFactory extends BindingContextFactory {

    @Override
    public BindingContext newContext(JAXBContext context) {
        return new JAXBRIContextWrapper((JAXBRIContext) context, null);
    }

    @Override
    public BindingContext newContext(BindingInfo bi) {
        Class[] classes = bi.contentClasses().toArray(new Class[0]);
        for (int i = 0; i < classes.length; i++) {
            if (WrapperComposite.class.equals(classes[i])) {
                classes[i] = CompositeStructure.class;
            }
        }
        Map<TypeInfo, TypeReference> typeInfoMappings = typeInfoMappings(bi.typeInfos());
        Map<Class, Class> subclassReplacements = bi.subclassReplacements();
        String defaultNamespaceRemap = bi.getDefaultNamespace();
        Boolean c14nSupport = (Boolean) bi.properties().get("c14nSupport");
        RuntimeAnnotationReader ar = (RuntimeAnnotationReader) bi.properties().get("org.glassfish.jaxb.runtime.v2.model.annotation.RuntimeAnnotationReader");
        JAXBContextFactory jaxbContextFactory = (JAXBContextFactory) bi.properties().get(JAXBContextFactory.class.getName());
        try {
            JAXBRIContext context = (jaxbContextFactory != null)
                    ? jaxbContextFactory.createJAXBContext(
                    bi.getSEIModel(),
                    toList(classes),
                    toList(typeInfoMappings.values()))
                    : ContextFactory.createContext(
                    classes, typeInfoMappings.values(),
                    subclassReplacements, defaultNamespaceRemap,
                    (c14nSupport != null) ? c14nSupport : false,
                    ar, false, false, false);
            return new JAXBRIContextWrapper(context, typeInfoMappings);
        } catch (Exception e) {
            throw new DatabindingException(e);
        }
    }

    private <T> List<T> toList(T[] a) {
        List<T> l = new ArrayList<T>(Arrays.asList(a));
        return l;
    }

    private <T> List<T> toList(Collection<T> col) {
        if (col instanceof List) {
            return (List<T>) col;
        }
        List<T> l = new ArrayList<T>(col);
        return l;
    }

    private Map<TypeInfo, TypeReference> typeInfoMappings(Collection<TypeInfo> typeInfos) {
        Map<TypeInfo, TypeReference> map = new java.util.HashMap<>();
        for (TypeInfo ti : typeInfos) {
            Type type = WrapperComposite.class.equals(ti.type) ? CompositeStructure.class : ti.type;
            TypeReference tr = new TypeReference(ti.tagName, type, ti.annotations);
            map.put(ti, tr);
        }
        return map;
    }

    @Override
    protected BindingContext getContext(Marshaller m) {
        return newContext(((MarshallerImpl) m).getContext());
    }

    @Override
    protected boolean isFor(String str) {
        return (str.equals("glassfish.jaxb")
                || str.equals(this.getClass().getName())
                || str.equals("org.glassfish.jaxb.runtime.v2.runtime"));
    }
}
