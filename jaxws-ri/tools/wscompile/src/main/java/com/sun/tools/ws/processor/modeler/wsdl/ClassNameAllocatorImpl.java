/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.modeler.wsdl;

import com.sun.tools.ws.processor.util.ClassNameCollector;
import com.sun.tools.xjc.api.ClassNameAllocator;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of Callback interface that allows the driver of the XJC API to rename JAXB-generated classes/interfaces/enums.
 *
 * @author Vivek Pandey
 */
public class ClassNameAllocatorImpl implements ClassNameAllocator {
    public ClassNameAllocatorImpl(ClassNameCollector classNameCollector) {
        this.classNameCollector = classNameCollector;
        this.jaxbClasses = new HashSet<>();
    }

    @Override
    public String assignClassName(String packageName, String className) {
        if(packageName== null || className == null){
            //TODO: throw Exception
            return className;
        }

        //if either of the values are empty string return the default className
        if(packageName.equals("") || className.equals(""))
            return className;

        String fullClassName = packageName+"."+className;

        // Check if there is any conflict with jaxws generated classes
        Set<String> seiClassNames = classNameCollector.getSeiClassNames();
        if(seiClassNames != null && seiClassNames.contains(fullClassName)){
            className += TYPE_SUFFIX;
        }

        jaxbClasses.add(packageName+"."+className);
        return className;
    }

    /**
     *
     * @return jaxbGenerated classNames
     */
    public Set<String> getJaxbGeneratedClasses() {
        return jaxbClasses;
    }

    private final static String TYPE_SUFFIX = "_Type";
    private ClassNameCollector classNameCollector;
    private Set<String> jaxbClasses;
}
