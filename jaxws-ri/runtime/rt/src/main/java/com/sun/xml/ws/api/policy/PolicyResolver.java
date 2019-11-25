/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.policy;

import com.sun.xml.ws.policy.PolicyMap;
import com.sun.xml.ws.policy.PolicyMapMutator;
import com.sun.xml.ws.api.server.Container;
import com.sun.istack.Nullable;
import java.util.Arrays;
import java.util.Collection;
import javax.xml.ws.WebServiceException;

/**
 * PolicyResolver  will be used to resolve the PolicyMap created by configuration understood by JAX-WS.
 *
 * Extensions of this can return effective PolicyMap after merge policies from other configurations.
 * @author Rama Pulavarthi
 */
public interface PolicyResolver {
    /**
     * Creates a PolicyResolver
     *
     * @param context
     *      ServerContext that captures information useful for resolving Policy on server-side
     *
     * @return  
     *      A PolicyMap with single policy alternative that gets created after consulting various configuration models.
     * 
     * @throws WebServiceException
     *      If resolution failed
     */
    PolicyMap resolve(ServerContext context) throws WebServiceException;

    /**
     * Creates a PolicyResolver
     *
     * @param context
     *      ServerContext that captures information useful for resolving Policy on client-side
     *
     * @return
     *      A PolicyMap with single policy alternative that gets created after consulting various configuration models.
     *
     * @throws WebServiceException
     *      If resolution failed
     */
    PolicyMap resolve(ClientContext context) throws WebServiceException;

   public class ServerContext {
       private final PolicyMap policyMap;
       private final Class endpointClass;
       private final Container container;
       private final boolean hasWsdl;
       private final Collection<PolicyMapMutator> mutators;

        /**
         * The abstraction of PolicyMap is not finalized, and will change in few months. It is highly discouraged to use
         * PolicyMap until it is finalized.
         *
         * In presence of WSDL, JAX-WS by default creates PolicyMap from Policy Attachemnts in WSDL.
         * In absense of WSDL, JAX-WS creates PolicyMap from WebServiceFeatures configured on the endpoint implementation
         *
         * @param policyMap
         *      PolicyMap created from PolicyAttachments in WSDL or Feature annotations on endpoint implementation class.
         * @param container
         * @param endpointClass
         * @param mutators
         *      List of PolicyMapMutators that are run eventually when a PolicyMap is created
         */
        public ServerContext(@Nullable PolicyMap policyMap, Container container,
                             Class endpointClass, final PolicyMapMutator... mutators) {
            this.policyMap = policyMap;
            this.endpointClass = endpointClass;
            this.container = container;
            this.hasWsdl = true;
            this.mutators = Arrays.asList(mutators);
        }

        /**
         * The abstraction of PolicyMap is not finalized, and will change in few months. It is highly discouraged to use
         * PolicyMap until it is finalized.
         *
         * In presence of WSDL, JAX-WS by default creates PolicyMap from Policy Attachemnts in WSDL.
         * In absense of WSDL, JAX-WS creates PolicyMap from WebServiceFeatures configured on the endpoint implementation
         *
         * @param policyMap
         *      PolicyMap created from PolicyAttachments in WSDL or Feature annotations on endpoint implementation class.
         * @param container
         * @param endpointClass
         * @param hasWsdl Set to true, if this service is bundled with WSDL, false otherwise
         * @param mutators
         *      List of PolicyMapMutators that are run eventually when a PolicyMap is created
         */
        public ServerContext(@Nullable PolicyMap policyMap, Container container,
                             Class endpointClass, boolean hasWsdl, final PolicyMapMutator... mutators) {
            this.policyMap = policyMap;
            this.endpointClass = endpointClass;
            this.container = container;
            this.hasWsdl = hasWsdl;
            this.mutators = Arrays.asList(mutators);
        }

        public @Nullable PolicyMap getPolicyMap() {
            return policyMap;
        }

        public @Nullable Class getEndpointClass() {
           return endpointClass;
        }

        public Container getContainer() {
           return container;
        }

        /**
         * Return true, if this service is bundled with WSDL, false otherwise
         * @return
         */
        public boolean hasWsdl() {
            return hasWsdl;
        }

        public Collection<PolicyMapMutator> getMutators() {
            return mutators;
        }
    }

    public class ClientContext {
        private PolicyMap policyMap;
        private Container container;

        /**
         * The abstraction of PolicyMap is not finalized, and will change in few months. It is highly discouraged to use
         * PolicyMap until it is finalized.
         *
         * In presence of WSDL, JAX-WS by default creates PolicyMap from Policy Attachemnts in WSDL.
         *
         * @param policyMap PolicyMap created from PolicyAttachemnts in WSDL
         * @param container 
         */
        public ClientContext(@Nullable PolicyMap policyMap, Container container) {
            this.policyMap = policyMap;
            this.container = container;
        }

        public @Nullable PolicyMap getPolicyMap() {
            return policyMap;
        }

        public Container getContainer() {
           return container;
        }
    }
}
