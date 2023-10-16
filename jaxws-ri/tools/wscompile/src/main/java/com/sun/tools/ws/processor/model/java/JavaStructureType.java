/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.model.java;

import com.sun.tools.ws.processor.model.ModelException;

import java.util.*;

/**
 *
 * @author WS Development Team
 */
public class JavaStructureType extends JavaType {

    public JavaStructureType() {}

    public JavaStructureType(String name, boolean present, Object owner) {
        super(name, present, "null");
        this.owner = owner;
    }

    public void add(JavaStructureMember m) {
        if (membersByName.containsKey(m.getName())) {
            throw new ModelException("model.uniqueness.javastructuretype",
                    m.getName(), getRealName());
        }
        members.add(m);
        membersByName.put(m.getName(), m);
    }


    public JavaStructureMember getMemberByName(String name) {
        if (membersByName.size() != members.size()) {
            initializeMembersByName();
        }
        return membersByName.get(name);
    }

    public Iterator<JavaStructureMember> getMembers() {
        return members.iterator();
    }

    public int getMembersCount() {
        return members.size();
    }

    /* serialization */
    public List<JavaStructureMember> getMembersList() {
        return members;
    }

    /* serialization */
    public void setMembersList(List<JavaStructureMember> l) {
        members = l;
    }

    private void initializeMembersByName() {
        membersByName = new HashMap<>();
        if (members != null) {
            for (JavaStructureMember m : members) {
                if (m.getName() != null &&
                    membersByName.containsKey(m.getName())) {

                    throw new ModelException("model.uniqueness");
                }
                membersByName.put(m.getName(), m);
            }
        }
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    public JavaStructureType getSuperclass() {
        return superclass;
    }

    public void setSuperclass(JavaStructureType superclassType) {
        superclass = superclassType;
    }

    public void addSubclass(JavaStructureType subclassType) {
        subclasses.add(subclassType);
        subclassType.setSuperclass(this);
    }

    public Iterator<JavaStructureType> getSubclasses() {
        if (subclasses == null || subclasses.isEmpty()) {
            return null;
        }
        return subclasses.iterator();
    }

    public Set<JavaStructureType> getSubclassesSet() {
        return subclasses;
    }

    /* serialization */
    public void setSubclassesSet(Set<JavaStructureType> s) {
        subclasses = s;
        for (JavaStructureType javaStructureType : s) {
            javaStructureType.setSuperclass(this);
        }
    }

    public Iterator<JavaStructureType> getAllSubclasses() {
        Set<JavaStructureType> subs = getAllSubclassesSet();
        if (subs.isEmpty()) {
            return null;
        }
        return subs.iterator();
    }

    public Set<JavaStructureType> getAllSubclassesSet() {
        Set<JavaStructureType> transitiveSet = new HashSet<>();
        for (JavaStructureType subclass : subclasses) {
            transitiveSet.addAll(
                    subclass.getAllSubclassesSet());
        }
        transitiveSet.addAll(subclasses);
        return transitiveSet;
    }

    public Object getOwner() {

        // usually a SOAPStructureType
        return owner;
    }

    public void setOwner(Object owner) {

        // usually a SOAPStructureType
        this.owner = owner;
    }

    private List<JavaStructureMember> members = new ArrayList<>();
    private Map<String, JavaStructureMember> membersByName = new HashMap<>();

    // known subclasses of this type
    private Set<JavaStructureType> subclasses = new HashSet<>();
    private JavaStructureType superclass;

    // usually a SOAPStructureType
    private Object owner;
    private boolean isAbstract = false;
}
