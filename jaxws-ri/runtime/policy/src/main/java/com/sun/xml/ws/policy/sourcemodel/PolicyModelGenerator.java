/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.sourcemodel;

import com.sun.xml.ws.policy.AssertionSet;
import com.sun.xml.ws.policy.NestedPolicy;
import com.sun.xml.ws.policy.Policy;
import com.sun.xml.ws.policy.PolicyAssertion;
import com.sun.xml.ws.policy.PolicyException;
import com.sun.xml.ws.policy.privateutil.LocalizationMessages;
import com.sun.xml.ws.policy.privateutil.PolicyLogger;

import java.util.Iterator;

/**
 * Translate a policy into a PolicySourceModel.
 *
 * Code that depends on JAX-WS should use com.sun.xml.ws.api.policy.ModelGenerator
 * instead of this class.
 *
 * @author Marek Potociar
 * @author Fabian Ritzmann
 */
public abstract class PolicyModelGenerator {

    private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyModelGenerator.class);
    
    /**
     * This protected constructor avoids direct instantiation from outside of the class
     */
    protected PolicyModelGenerator() {
        // nothing to initialize
    }
    
    /**
     * Factory method that returns a {@link PolicyModelGenerator} instance.
     *
     * @return {@link PolicyModelGenerator} instance
     */
    public static PolicyModelGenerator getGenerator() {
        return getNormalizedGenerator(new PolicySourceModelCreator());
    }

    /**
     * Allows derived classes to create instances of the package private
     * CompactModelGenerator.
     *
     * @param creator An implementation of the PolicySourceModelCreator.
     * @return An instance of CompactModelGenerator.
     */
    protected static PolicyModelGenerator getCompactGenerator(PolicySourceModelCreator creator) {
        return new CompactModelGenerator(creator);
    }

    /**
     * Allows derived classes to create instances of the package private
     * NormalizedModelGenerator.
     *
     * @param creator An implementation of the PolicySourceModelCreator.
     * @return An instance of NormalizedModelGenerator.
     */
    protected static PolicyModelGenerator getNormalizedGenerator(PolicySourceModelCreator creator) {
        return new NormalizedModelGenerator(creator);
    }

    /**
     * This method translates a {@link Policy} into a
     * {@link com.sun.xml.ws.policy.sourcemodel policy infoset}. The resulting
     * PolicySourceModel is disconnected from the input policy, thus any
     * additional changes in the policy will have no effect on the PolicySourceModel.
     *
     * @param policy The policy to be translated into an infoset. May be null.
     * @return translated The policy infoset. May be null if the input policy was
     * null.
     * @throws PolicyException in case Policy translation fails.
     */
    public abstract PolicySourceModel translate(final Policy policy) throws PolicyException;

    /**
     * Iterates through a nested policy and returns the corresponding policy info model.
     *
     * @param parentAssertion The parent node.
     * @param policy The nested policy.
     * @return The nested policy translated to the policy info model.
     */
    protected abstract ModelNode translate(final ModelNode parentAssertion, final NestedPolicy policy);

    /**
     * Add the contents of the assertion set as child node to the given model node.
     *
     * @param node The content of this assertion set are added as child nodes to this node.
     *     May not be null.
     * @param assertions The assertions that are to be added to the node. May not be null.
     */
    protected void translate(final ModelNode node, final AssertionSet assertions) {
        for (PolicyAssertion assertion : assertions) {
            final AssertionData data = AssertionData.createAssertionData(assertion.getName(), assertion.getValue(), assertion.getAttributes(), assertion.isOptional(), assertion.isIgnorable());
            final ModelNode assertionNode = node.createChildAssertionNode(data);
            if (assertion.hasNestedPolicy()) {
                translate(assertionNode, assertion.getNestedPolicy());
            }
            if (assertion.hasParameters()) {
                translate(assertionNode, assertion.getParametersIterator());
            }
        }
    }

    /**
     * Iterates through all contained assertions and adds them to the info model.
     *
     * @param assertionParametersIterator The contained assertions.
     * @param assertionNode The node to which the assertions are added as child nodes
     */
    protected void translate(final ModelNode assertionNode, final Iterator<PolicyAssertion> assertionParametersIterator) {
        while (assertionParametersIterator.hasNext()) {
            final PolicyAssertion assertionParameter = assertionParametersIterator.next();
            final AssertionData data = AssertionData.createAssertionParameterData(assertionParameter.getName(), assertionParameter.getValue(), assertionParameter.getAttributes());
            final ModelNode assertionParameterNode = assertionNode.createChildAssertionParameterNode(data);
            if (assertionParameter.hasNestedPolicy()) {
                throw LOGGER.logSevereException(new IllegalStateException(LocalizationMessages.WSP_0005_UNEXPECTED_POLICY_ELEMENT_FOUND_IN_ASSERTION_PARAM(assertionParameter)));
            }
            if (assertionParameter.hasNestedAssertions()) {
                translate(assertionParameterNode, assertionParameter.getNestedAssertionsIterator());
            }
        }
    }


    /**
     * Allows derived classes to pass their own implementation of PolicySourceModelCreator
     * into the PolicyModelGenerator instance.
     */
    protected static class PolicySourceModelCreator {

        /**
         * Create an instance of the PolicySourceModel.
         *
         * @param policy The policy that underlies the created PolicySourceModel.
         * @return An instance of the PolicySourceModel.
         */
        protected PolicySourceModel create(final Policy policy) {
            return PolicySourceModel.createPolicySourceModel(policy.getNamespaceVersion(),
                    policy.getId(), policy.getName());
        }

    }

}
