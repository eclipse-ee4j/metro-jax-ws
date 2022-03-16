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

import com.sun.tools.ws.resources.ModelMessages;
import com.sun.tools.ws.wscompile.ErrorReceiver;
import com.sun.tools.ws.wscompile.WsimportOptions;
import com.sun.tools.ws.processor.model.Parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * @author WS Development Team
 */
public class JavaMethod {

    private final ErrorReceiver errorReceiver;
    private final String name;
    private final List<JavaParameter> parameters = new ArrayList<>();
    private final List<String> exceptions = new ArrayList<>();
    private final WsimportOptions options;
    private JavaType returnType;

    public JavaMethod(String name, WsimportOptions options, ErrorReceiver receiver) {
        this.name = name;
        this.returnType = null;
        this.errorReceiver = receiver;
        this.options = options;
    }

    public String getName() {
        return name;
    }

    public JavaType getReturnType() {
        return returnType;
    }

    public void setReturnType(JavaType returnType) {
        this.returnType = returnType;
    }

    private boolean hasParameter(String paramName) {
        for (JavaParameter parameter : parameters) {
            if (paramName.equals(parameter.getName())) {
                return true;
            }
        }
        return false;
    }

    private Parameter getParameter(String paramName){
        for (JavaParameter parameter : parameters) {
            if (paramName.equals(parameter.getName())) {
                return parameter.getParameter();
            }
        }
        return null;
    }

    public void addParameter(JavaParameter param) {
        // verify that this member does not already exist
        if (hasParameter(param.getName())) {
            if (options.isExtensionMode()) {
                param.setName(getUniqueName(param.getName()));
            } else {
                Parameter duplicParam = getParameter(param.getName());
                if(param.getParameter().isEmbedded()){
                    errorReceiver.error(param.getParameter().getLocator(), ModelMessages.MODEL_PARAMETER_NOTUNIQUE_WRAPPER(param.getName(), param.getParameter().getEntityName()));
                    errorReceiver.error(duplicParam.getLocator(), ModelMessages.MODEL_PARAMETER_NOTUNIQUE_WRAPPER(param.getName(), duplicParam.getEntityName()));                    
                } else {
                    errorReceiver.error(param.getParameter().getLocator(), ModelMessages.MODEL_PARAMETER_NOTUNIQUE(param.getName(), param.getParameter().getEntityName()));
                    errorReceiver.error(duplicParam.getLocator(), ModelMessages.MODEL_PARAMETER_NOTUNIQUE(param.getName(), duplicParam.getEntityName()));
                }
                return;
            }
        }
        parameters.add(param);
    }

    public List<JavaParameter> getParametersList() {
        return parameters;
    }

    public void addException(String exception) {
        // verify that this exception does not already exist
        if (!exceptions.contains(exception)) {
            exceptions.add(exception);
        }
    }

    /** TODO: NB uses it, remove it once we expose it thru some API **/
    public Iterator<String> getExceptions() {
        return exceptions.iterator();
    }

    private String getUniqueName(String param){
        int parmNum = 0;
        while (hasParameter(param)) {
            param = param + parmNum++;
        }
        return param;
    }
}
