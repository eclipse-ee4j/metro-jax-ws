/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.subject;

import com.sun.xml.ws.policy.PolicyMap;
import com.sun.xml.ws.policy.PolicyMapKey;
import com.sun.xml.ws.policy.privateutil.PolicyLogger;
import com.sun.xml.ws.policy.subject.WsdlBindingSubject.WsdlMessageType;
import javax.xml.namespace.QName;

/**
 * Computes a PolicyMapKey instance for a given WsdlBindingSubject.
 *
 * @author Fabian Ritzmann
 */
public class PolicyMapKeyConverter {

    private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyMapKeyConverter.class);

    private final QName serviceName;
    private final QName portName;

    public PolicyMapKeyConverter(QName serviceName, QName portName) {
        this.serviceName = serviceName;
        this.portName = portName;
    }
    
    public PolicyMapKey getPolicyMapKey(final WsdlBindingSubject subject) {
        LOGGER.entering(subject);

        PolicyMapKey key = null;
        if (subject.isBindingSubject()) {
            key = PolicyMap.createWsdlEndpointScopeKey(this.serviceName, this.portName);
        }
        else if (subject.isBindingOperationSubject()) {
            key = PolicyMap.createWsdlOperationScopeKey(this.serviceName, this.portName, subject.getName());
        }
        else if (subject.isBindingMessageSubject()) {
            if (subject.getMessageType() == WsdlMessageType.FAULT) {
                key = PolicyMap.createWsdlFaultMessageScopeKey(this.serviceName, this.portName,
                        subject.getParent().getName(), subject.getName());
            }
            else {
                key = PolicyMap.createWsdlMessageScopeKey(this.serviceName, this.portName, subject.getParent().getName());
            }
        }

        LOGGER.exiting(key);
        return key;
    }

}
