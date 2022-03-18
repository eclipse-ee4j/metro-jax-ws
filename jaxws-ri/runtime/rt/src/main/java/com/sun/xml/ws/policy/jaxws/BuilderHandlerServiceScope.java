/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.jaxws;

import com.sun.xml.ws.policy.PolicyException;
import com.sun.xml.ws.policy.PolicyMap;
import com.sun.xml.ws.policy.PolicyMapExtender;
import com.sun.xml.ws.policy.PolicyMapKey;
import com.sun.xml.ws.policy.PolicySubject;
import com.sun.xml.ws.policy.sourcemodel.PolicySourceModel;

import java.util.Collection;
import java.util.Map;
import javax.xml.namespace.QName;

/**
 *
 * @author Jakub Podlesak (jakub.podlesak at sun.com)
 */
final class BuilderHandlerServiceScope extends BuilderHandler{
    private final QName service;
    
    /**
     * Creates a new instance of BuilderHandlerServiceScope
     */
    BuilderHandlerServiceScope(
            Collection<String> policyURIs, Map<String,PolicySourceModel> policyStore, Object policySubject, QName service) {
        
        super(policyURIs, policyStore, policySubject);
        this.service = service;
    }
    
    @Override
    protected void doPopulate(final PolicyMapExtender policyMapExtender) throws PolicyException{
        final PolicyMapKey mapKey = PolicyMap.createWsdlServiceScopeKey(service);
        for (PolicySubject subject : getPolicySubjects()) {
            policyMapExtender.putServiceSubject(mapKey, subject);
        }
    }
    
    @Override
    public String toString() {
        return service.toString();
    }
}
