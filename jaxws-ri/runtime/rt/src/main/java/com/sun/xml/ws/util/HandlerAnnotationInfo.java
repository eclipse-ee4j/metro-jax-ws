/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util;

import java.util.List;
import java.util.Set;

import jakarta.xml.ws.handler.Handler;

/**
 * Used to hold a list of handlers and a set of roles from an
 * annotated endpoint. At runtime, these are created by the
 * HandlerAnnotationProcessor at the request of client and
 * server code to create the handler chains.
 *
 * @see com.sun.xml.ws.util.HandlerAnnotationProcessor
 *
 * @author JAX-WS Development Team
 */
public class HandlerAnnotationInfo {
    
    private List<Handler> handlers;
    private Set<String> roles;
    
    /**
     * Return the handlers specified by the handler chain descriptor.
     *
     * @return A list of jax-ws handler objects.
     */
    public List<Handler> getHandlers() {
        return handlers;
    }
    
    /**
     * This method should only be called by HandlerAnnotationProcessor.
     *
     * @param handlers The handlers specified by the handler chain descriptor.
     */
    public void setHandlers(List<Handler> handlers) {
        this.handlers = handlers;
    }
    
    /**
     * Return the roles contained in the handler chain descriptor.
     *
     * @return A set of roles.
     */
    public Set<String> getRoles() {
        return roles;
    }
    
    /**
     * This method should only be called by HandlerAnnotationProcessor.
     *
     * @param roles The roles contained in the handler chain descriptor.
     */
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
    
}
