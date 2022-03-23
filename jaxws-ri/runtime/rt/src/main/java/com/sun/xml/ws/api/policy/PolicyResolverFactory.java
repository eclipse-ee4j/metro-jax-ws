/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.policy;

import com.sun.xml.ws.util.ServiceFinder;
import com.sun.xml.ws.policy.jaxws.DefaultPolicyResolver;

/**
 * PolicyResolverFactory provides a way to override Effective Policy Resolution for a Service or Client.
 * JAX-WS provides DEFAULT_POLICY_RESOLVER implementation that
 *      on server-side validates that Policy has single alternative in the scope of each subject
 *      on client-side updates with the effective policy by doing alternative selection.
 *
 * Extensions can override this to consult other forms of configuration to give the effective PolicyMap.
 * 
 * @author Rama Pulavarthi
 */
public abstract class PolicyResolverFactory {

    /**
     * Default constructor.
     */
    protected PolicyResolverFactory() {}

    public abstract PolicyResolver doCreate();

    public static PolicyResolver create(){
        for (PolicyResolverFactory factory : ServiceFinder.find(PolicyResolverFactory.class)) {
            PolicyResolver policyResolver = factory.doCreate();
            if (policyResolver != null) {
                return policyResolver;
            }
        }
         // return default policy resolver.
        return DEFAULT_POLICY_RESOLVER;
    }

    /**
     * JAX-WS provided DEFAULT_POLICY_RESOLVER implementation that
     *      on server-side validates that Policy has single alternative in the scope of each subject
     *      on client-side updates with the effective policy by doing alternative selection.
     */
    public static final PolicyResolver DEFAULT_POLICY_RESOLVER =  new DefaultPolicyResolver();

    
}
