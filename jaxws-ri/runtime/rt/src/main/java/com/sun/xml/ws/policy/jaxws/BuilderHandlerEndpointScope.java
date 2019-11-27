/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
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
final class BuilderHandlerEndpointScope extends BuilderHandler{
    private final QName service;
    private final QName port;
    
    /** Creates a new instance of WSDLServiceScopeBuilderHandler */
    BuilderHandlerEndpointScope(Collection<String> policyURIs, Map<String,PolicySourceModel> policyStore, Object policySubject, QName service, QName port) {
        
        super(policyURIs, policyStore, policySubject);
        this.service = service;
        this.port = port;
    }
    
    protected void doPopulate(final PolicyMapExtender policyMapExtender) throws PolicyException {
        final PolicyMapKey mapKey = PolicyMap.createWsdlEndpointScopeKey(service, port);
        for (PolicySubject subject : getPolicySubjects()) {
            policyMapExtender.putEndpointSubject(mapKey, subject);
        }
    }
    
    @Override
    public String toString() {
        return (new StringBuffer(service.toString())).append(":").append(port.toString()).toString();
    }
}
