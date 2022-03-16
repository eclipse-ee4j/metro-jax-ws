/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.model.java;

import com.sun.tools.ws.processor.model.ModelException;
import com.sun.tools.ws.util.ClassNameInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author WS Development Team
 */
public class JavaInterface {

    public JavaInterface() {}

    public JavaInterface(String name) {
        this(name, null);
    }

    public JavaInterface(String name, String impl) {
        this.realName = name;
        this.name = name.replace('$', '.');
        this.impl = impl;
    }

    public String getName() {
        return name;
    }

    public String getFormalName() {
        return name;
    }

    public void setFormalName(String s) {
        name = s;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String s) {
        realName = s;
    }

    public String getImpl() {
        return impl;
    }

    public void setImpl(String s) {
        impl = s;
    }

    public Iterator getMethods() {
        return methods.iterator();
    }

    public boolean hasMethod(JavaMethod method) {
        for (int i=0; i<methods.size();i++) {
            if (method.equals(methods.get(i))) {
                return true;
            }
        }
        return false;
    }

    public void addMethod(JavaMethod method) {

        if (hasMethod(method)) {
            throw new ModelException("model.uniqueness");
        }
        methods.add(method);
    }

    /* serialization */
    public List getMethodsList() {
        return methods;
    }

    /* serialization */
    public void setMethodsList(List l) {
        methods = l;
    }

    public boolean hasInterface(String interfaceName) {
        for (int i=0; i<interfaces.size();i++) {
            if (interfaceName.equals(interfaces.get(i))) {
                return true;
            }
        }
        return false;
    }

    public void addInterface(String interfaceName) {

        // verify that an exception with this name does not already exist
        if (hasInterface(interfaceName)) {
            return;
        }
        interfaces.add(interfaceName);
    }

    public Iterator getInterfaces() {
        return interfaces.iterator();
    }

    /* serialization */
    public List getInterfacesList() {
        return interfaces;
    }

    /* serialization */
    public void setInterfacesList(List l) {
        interfaces = l;
    }
    
    public String getSimpleName() {
        return ClassNameInfo.getName(name);
    }

    /* NOTE - all these fields (except "interfaces") were final, but had to
     * remove this modifier to enable serialization
     */
    private String javadoc;

    public String getJavaDoc() {
        return javadoc;
    }

    public void setJavaDoc(String javadoc) {
        this.javadoc = javadoc;
    }

    private String name;
    private String realName;
    private String impl;
    private List methods = new ArrayList();
    private List interfaces = new ArrayList();
}
