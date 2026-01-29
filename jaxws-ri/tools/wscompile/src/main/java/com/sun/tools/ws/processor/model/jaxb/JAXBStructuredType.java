/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.model.jaxb;

import com.sun.tools.ws.processor.model.ModelException;
import com.sun.tools.ws.processor.model.java.JavaStructureType;

import javax.xml.namespace.QName;
import java.util.*;

/**
 * Top-level binding between JAXB generated Java type
 * and XML Schema element declaration.
 *
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
public class JAXBStructuredType extends JAXBType {

    public JAXBStructuredType(JAXBType jaxbType){
        super(jaxbType);
    }

    public JAXBStructuredType() {}

    public JAXBStructuredType(QName name) {
        this(name, null);
    }

    public JAXBStructuredType(QName name, JavaStructureType javaType) {
        super(name, javaType);
    }

    public void add(JAXBElementMember m) {
        if (_elementMembersByName.containsKey(m.getName().getLocalPart())) {
            throw new ModelException("model.uniqueness");
        }
        _elementMembers.add(m);
        if (m.getName() != null) {
            _elementMembersByName.put(m.getName().getLocalPart(), m);
        }
    }

    public Iterator<JAXBElementMember> getElementMembers() {
        return _elementMembers.iterator();
    }

    public int getElementMembersCount() {
        return _elementMembers.size();
    }

    /* serialization */
    public List<JAXBElementMember> getElementMembersList() {
        return _elementMembers;
    }

    /* serialization */
    public void setElementMembersList(List<JAXBElementMember> l) {
        _elementMembers = l;
    }

    public void addSubtype(JAXBStructuredType type) {
        if (_subtypes == null) {
            _subtypes = new HashSet<>();
        }
        _subtypes.add(type);
        type.setParentType(this);
    }

    public Iterator<JAXBStructuredType> getSubtypes() {
        if (_subtypes != null) {
            return _subtypes.iterator();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see JAXBType#isUnwrapped()
     */
    @Override
    public boolean isUnwrapped() {
        return true;
    }
    /* serialization */
    public Set<JAXBStructuredType> getSubtypesSet() {
        return _subtypes;
    }

    /* serialization */
    public void setSubtypesSet(Set<JAXBStructuredType> s) {
        _subtypes = s;
    }

    public void setParentType(JAXBStructuredType parent) {
        if (_parentType != null &&
            parent != null &&
            !_parentType.equals(parent)) {

            throw new ModelException("model.parent.type.already.set",
                    getName().toString(),
                    _parentType.getName().toString(),
                    parent.getName().toString());
        }
        this._parentType = parent;
    }

    public JAXBStructuredType getParentType() {
        return _parentType;
    }


    private List<JAXBElementMember> _elementMembers = new ArrayList<>();
    private Map<String, JAXBElementMember> _elementMembersByName = new HashMap<>();
    private Set<JAXBStructuredType> _subtypes = null;
    private JAXBStructuredType _parentType = null;
}
