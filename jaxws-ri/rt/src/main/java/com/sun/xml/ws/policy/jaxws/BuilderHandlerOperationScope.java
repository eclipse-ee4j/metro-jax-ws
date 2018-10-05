/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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
final class BuilderHandlerOperationScope extends BuilderHandler{
    private final QName service;
    private final QName port;
    private final QName operation;
    
    /** Creates a new instance of WSDLServiceScopeBuilderHandler */
    BuilderHandlerOperationScope(
            Collection<String> policyURIs
            , Map<String,PolicySourceModel> policyStore
            , Object policySubject
            , QName service, QName port, QName operation) {
        
        super(policyURIs, policyStore, policySubject);
        this.service = service;
        this.port = port;
        this.operation = operation;
    }
    
    protected void doPopulate(final PolicyMapExtender policyMapExtender) throws PolicyException{
        final PolicyMapKey mapKey = PolicyMap.createWsdlOperationScopeKey(service, port, operation);
        for (PolicySubject subject : getPolicySubjects()) {
            policyMapExtender.putOperationSubject(mapKey, subject);
        }
    }
}
