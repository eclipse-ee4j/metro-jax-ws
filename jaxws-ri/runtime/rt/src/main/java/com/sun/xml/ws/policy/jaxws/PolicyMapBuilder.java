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
import com.sun.xml.ws.policy.PolicyMapMutator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Used for populating changes into PolicyMap. Once a PolicyMap is created
 * PolicyMapBuilder notifies all the registered WSPolicyBuilderHandler to populate
 * changes to the PolicyMap.
 *
 *
 * @author Jakub Podlesak (jakub.podlesak at sun.com)
 */
class PolicyMapBuilder {
    /**
     * policyBuilders should contain list of registered PolicyBuilders
     */
    private List<BuilderHandler> policyBuilders = new LinkedList<BuilderHandler>();
        
    /**
     * Creates a new instance of PolicyMapBuilder
     */
    PolicyMapBuilder() {
        // nothing to initialize
    }
        
    /**
     *     Registers another builder, which has to be notified after a new
     *     PolicyMap is created in order to populate it's changes.
     *
     */
    void registerHandler(final BuilderHandler builder){
        if (null != builder) {
            policyBuilders.add(builder);
        }
    }    
   
    /**
     * Iterates all the registered PolicyBuilders and lets them populate
     * their changes into PolicyMap. Registers mutators given as a parameter
     * with the newly created map.
     */
    PolicyMap getPolicyMap(final PolicyMapMutator... externalMutators) throws PolicyException{
        return getNewPolicyMap(externalMutators);
    }
    
    
    /**
     * Iterates all the registered PolicyBuilders and lets them populate
     * their changes into PolicyMap. Registers mutators from collection given as a parameter
     * with the newly created map.
     */
    private PolicyMap getNewPolicyMap(final PolicyMapMutator... externalMutators) throws PolicyException{
        final HashSet<PolicyMapMutator> mutators = new HashSet<PolicyMapMutator>();
        final PolicyMapExtender myExtender = PolicyMapExtender.createPolicyMapExtender();
        mutators.add(myExtender);
        if (null != externalMutators) {
            mutators.addAll(Arrays.asList(externalMutators));
        }
        final PolicyMap policyMap = PolicyMap.createPolicyMap(mutators);
        for(BuilderHandler builder : policyBuilders){
            builder.populate(myExtender);
        }
        return policyMap;
    }
    
    void unregisterAll() {
        this.policyBuilders = null;
    }    
}
