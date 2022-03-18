/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy;

import com.sun.xml.ws.policy.privateutil.LocalizationMessages;
import com.sun.xml.ws.policy.privateutil.PolicyLogger;

import javax.xml.namespace.QName;

/**
 * This class provides implementation of PolicyMapKey interface to be used in connection with {@link PolicyMap}.
 * Instances of the class are created by a call to one of {@link PolicyMap} <code>createWsdl<strong>XXX</strong>PolicyMapKey(...)</code>
 * methods.
 * <br>
 * The class wraps scope information and adds a package setter method to allow injection of actual equality comparator/tester. This injection
 * is made within a <code>get...</code> call on {@link PolicyMap}, before the actual scope map search is performed.
 * 
 * 
 * @author Marek Potociar
 * @author Fabian Ritzmann
 */
final public class PolicyMapKey  {
    private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyMapKey.class);
    
    private final QName service;
    private final QName port;
    private final QName operation;
    private final QName faultMessage;
    
    private PolicyMapKeyHandler handler;
    
    PolicyMapKey(final QName service, final QName port, final QName operation, final PolicyMapKeyHandler handler) {
        this(service, port, operation, null, handler);
    }
    
    PolicyMapKey(final QName service, final QName port, final QName operation, final QName faultMessage, final PolicyMapKeyHandler handler) {
        if (handler == null) {
            throw LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0046_POLICY_MAP_KEY_HANDLER_NOT_SET()));
        }

        this.service = service;
        this.port = port;
        this.operation = operation;
        this.faultMessage = faultMessage;
        this.handler = handler;
    }
    
    PolicyMapKey(final PolicyMapKey that) {
        this.service = that.service;
        this.port = that.port;
        this.operation = that.operation;
        this.faultMessage = that.faultMessage;
        this.handler = that.handler;
    }

    public QName getOperation() {
        return operation;
    }

    public QName getPort() {
        return port;
    }

    public QName getService() {
        return service;
    }

    void setHandler(PolicyMapKeyHandler handler) {
        if (handler == null) {
            throw LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0046_POLICY_MAP_KEY_HANDLER_NOT_SET()));
        }

        this.handler = handler;
    }

    public QName getFaultMessage() {
        return faultMessage;
    }

    @Override
    public boolean equals(final Object that) {
        if (this == that) {
            return true; // we are lucky here => no special handling is required
        }
        
        if (that == null) {
            return false;
        }
        
        if (that instanceof PolicyMapKey) {
            return handler.areEqual(this, (PolicyMapKey) that);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return handler.generateHashCode(this);
    }    
    
    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("PolicyMapKey(");
        result.append(this.service).append(", ").append(port).append(", ").append(operation).append(", ").append(faultMessage);
        return result.append(")").toString();
    }
}
