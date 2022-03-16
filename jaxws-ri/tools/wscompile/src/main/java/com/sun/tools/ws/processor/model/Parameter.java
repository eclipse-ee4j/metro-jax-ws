/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.model;

import com.sun.tools.ws.processor.model.java.JavaParameter;
import com.sun.tools.ws.wsdl.framework.Entity;
import com.sun.tools.ws.wsdl.document.MessagePart;

import jakarta.jws.WebParam.Mode;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WS Development Team
 */
public class Parameter extends ModelObject {
    private final String entityName;

    public Parameter(String name, Entity entity) {
        super(entity);
        this.name = name;
        if(entity instanceof com.sun.tools.ws.wsdl.document.Message){
            this.entityName = ((com.sun.tools.ws.wsdl.document.Message)entity).getName();
        }else if(entity instanceof MessagePart){
            this.entityName = ((MessagePart)entity).getName();
        }else{
            this.entityName = name;
        }

    }


    public String getEntityName() {
        return entityName;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }

    public JavaParameter getJavaParameter() {
        return javaParameter;
    }

    public void setJavaParameter(JavaParameter p) {
        javaParameter = p;
    }

    public AbstractType getType() {
        return type;
    }

    public void setType(AbstractType t) {
        type = t;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String t) {
        typeName = t;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block d) {
        block = d;
    }

    public Parameter getLinkedParameter() {
        return link;
    }

    public void setLinkedParameter(Parameter p) {
        link = p;
    }

    public boolean isEmbedded() {
        return embedded;
    }

    public void setEmbedded(boolean b) {
        embedded = b;
    }

    public void accept(ModelVisitor visitor) throws Exception {
        visitor.visit(this);
    }

    private String name;
    private JavaParameter javaParameter;
    private AbstractType type;
    private Block block;
    private Parameter link;
    private boolean embedded;
    private String typeName;
    private String customName;
    private Mode mode;

    public int getParameterIndex() {
        return parameterOrderPosition;
    }

    public void setParameterIndex(int parameterOrderPosition) {
        this.parameterOrderPosition = parameterOrderPosition;
    }

    public boolean isReturn(){
        return (parameterOrderPosition == -1);
    }

    // 0 is the first parameter, -1 is the return type
    private int parameterOrderPosition;
    /**
     * @return Returns the customName.
     */
    public String getCustomName() {
        return customName;
    }
    /**
     * @param customName The customName to set.
     */
    public void setCustomName(String customName) {
        this.customName = customName;
    }

    private List<String> annotations = new ArrayList<>();

    /**
     * @return Returns the annotations.
     */
    public List<String> getAnnotations() {
        return annotations;
    }


    /**
     * @param annotations The annotations to set.
     */
    public void setAnnotations(List<String> annotations) {
        this.annotations = annotations;
    }

    public void setMode(Mode mode){
        this.mode = mode;
    }

    public boolean isIN(){
        return (mode == Mode.IN);
    }

    public boolean isOUT(){
        return (mode == Mode.OUT);
    }

    public boolean isINOUT(){
        return (mode == Mode.INOUT);
    }



}
