/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.model.jaxb;

import com.sun.tools.ws.processor.model.java.JavaStructureMember;

import javax.xml.namespace.QName;

/**
 * @author Kathy Walsh, Vivek Pandey
 *
 * 
 */

public class JAXBElementMember {
    public JAXBElementMember() {
    }
    public JAXBElementMember(QName name, JAXBType type) {
        this(name, type, null);
    }
    public JAXBElementMember(QName name, JAXBType type,
            JavaStructureMember javaStructureMember) {
        _name = name;
        _type = type;
        _javaStructureMember = javaStructureMember;
    }
    public QName getName() {
        return _name;
    }
    public void setName(QName n) {
        _name = n;
    }
    public JAXBType getType() {
        return _type;
    }
    public void setType(JAXBType t) {
        _type = t;        
    }    
    public boolean isRepeated() {
        return _repeated;
    }
    public void setRepeated(boolean b) {
        _repeated = b;
    }
    public JavaStructureMember getJavaStructureMember() {
        return _javaStructureMember;
    }
    public void setJavaStructureMember(JavaStructureMember javaStructureMember) {
        _javaStructureMember = javaStructureMember;
    }
    public boolean isInherited() {
        return isInherited;
    }
    public void setInherited(boolean b) {
        isInherited = b;
    }
    public JAXBProperty getProperty() {
        if(_prop == null && _type != null) {
            for (JAXBProperty prop: _type.getWrapperChildren()){
                if(prop.getElementName().equals(_name))
                    setProperty(prop);
            }
        }
        return _prop;
    }
    public void setProperty(JAXBProperty prop) {
        _prop = prop;
    }
    
    private QName _name;
    private JAXBType _type;
    private JavaStructureMember _javaStructureMember;
    private boolean _repeated;
    private boolean isInherited = false;
    private JAXBProperty _prop;
    private static final String JAXB_UNIQUE_PARRAM = "__jaxbUniqueParam_";
}
