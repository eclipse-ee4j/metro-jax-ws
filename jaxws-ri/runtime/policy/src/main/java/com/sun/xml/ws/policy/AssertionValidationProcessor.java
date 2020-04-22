/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy;

import com.sun.xml.ws.policy.privateutil.PolicyLogger;
import com.sun.xml.ws.policy.spi.PolicyAssertionValidator;
import static com.sun.xml.ws.policy.privateutil.LocalizationMessages.WSP_0076_NO_SERVICE_PROVIDERS_FOUND;

import java.util.Collection;
import java.util.LinkedList;
import java.util.ServiceLoader;

/**
 * Provides methods for assertion validation.
 *
 * @author Marek Potociar (marek.potociar at sun.com)
 * @author Fabian Ritzmann
 */
public class AssertionValidationProcessor {
    private static final PolicyLogger LOGGER = PolicyLogger.getLogger(AssertionValidationProcessor.class);
    
    private final Collection<PolicyAssertionValidator> validators = new LinkedList<PolicyAssertionValidator>();
    
    /**
     * This constructor instantiates the object with a set of dynamically
     * discovered PolicyAssertionValidators.
     *
     * @throws PolicyException Thrown if the set of dynamically discovered
     *   PolicyAssertionValidators is empty.
     */
    private AssertionValidationProcessor() throws PolicyException {
        this(null);
    }

    /**
     * This constructor adds the given set of policy validators to the dynamically
     * discovered PolicyAssertionValidators.
     *
     * This constructor is intended to be used by the JAX-WS com.sun.xml.ws.policy.api.ValidationProcessor.
     *
     * @param policyValidators A set of PolicyAssertionValidators. May be null
     * @throws PolicyException Thrown if the set of given PolicyAssertionValidators
     *   and dynamically discovered PolicyAssertionValidators is empty.
     */
    protected AssertionValidationProcessor(final Collection<PolicyAssertionValidator> policyValidators)
            throws PolicyException {
        for(PolicyAssertionValidator validator : ServiceLoader.load(PolicyAssertionValidator.class)) {
            validators.add(validator);
        }
        if (policyValidators != null) {
            for (PolicyAssertionValidator validator : policyValidators) {
                validators.add(validator);
            }
        }
        if (validators.size() == 0) {
            throw LOGGER.logSevereException(new PolicyException(WSP_0076_NO_SERVICE_PROVIDERS_FOUND(PolicyAssertionValidator.class.getName())));
        }
    }

    /**
     * Factory method that returns singleton instance of the class.
     * 
     * This method is only intended to be used by code that has no dependencies on
     * JAX-WS. Otherwise use com.sun.xml.ws.api.policy.ValidationProcessor.
     *
     * @return singleton An instance of the class.
     * @throws PolicyException If instantiation failed.
     */
    public static AssertionValidationProcessor getInstance() throws PolicyException {
        return new AssertionValidationProcessor();
    }
    
    /**
     * Validates fitness of the {@code assertion} on the client side.
     *
     * return client side {@code assertion} fitness 
     * @param assertion The assertion to be validated.
     * @return The fitness of the assertion on the client side.
     * @throws PolicyException If validation failed.
     */
    public PolicyAssertionValidator.Fitness validateClientSide(final PolicyAssertion assertion) throws PolicyException {
        PolicyAssertionValidator.Fitness assertionFitness = PolicyAssertionValidator.Fitness.UNKNOWN;
        for ( PolicyAssertionValidator validator : validators ) {
            assertionFitness = assertionFitness.combine(validator.validateClientSide(assertion));
            if (assertionFitness == PolicyAssertionValidator.Fitness.SUPPORTED) {
                break;
            }
        }
        
        return assertionFitness;
    }

    /**
     * Validates fitness of the {@code assertion} on the server side.
     *
     * return server side {@code assertion} fitness 
     * @param assertion The assertion to be validated.
     * @return The fitness of the assertion on the server side.
     * @throws PolicyException If validation failed.
     */
    public PolicyAssertionValidator.Fitness validateServerSide(final PolicyAssertion assertion) throws PolicyException {
        PolicyAssertionValidator.Fitness assertionFitness = PolicyAssertionValidator.Fitness.UNKNOWN;
        for (PolicyAssertionValidator validator : validators) {
            assertionFitness = assertionFitness.combine(validator.validateServerSide(assertion));
            if (assertionFitness == PolicyAssertionValidator.Fitness.SUPPORTED) {
                break;
            }
        }
        
        return assertionFitness;
    }
}
