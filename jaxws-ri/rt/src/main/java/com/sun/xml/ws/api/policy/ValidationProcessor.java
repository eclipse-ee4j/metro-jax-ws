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

import com.sun.xml.ws.addressing.policy.AddressingPolicyValidator;
import com.sun.xml.ws.config.management.policy.ManagementPolicyValidator;
import com.sun.xml.ws.encoding.policy.EncodingPolicyValidator;
import com.sun.xml.ws.policy.AssertionValidationProcessor;
import com.sun.xml.ws.policy.PolicyException;
import com.sun.xml.ws.policy.spi.PolicyAssertionValidator;

import java.util.Arrays;

/**
 * Provides methods for assertion validation.
 *
 * @author Fabian Ritzmann
 */
public class ValidationProcessor extends AssertionValidationProcessor {

    private static final PolicyAssertionValidator[] JAXWS_ASSERTION_VALIDATORS = {
        new AddressingPolicyValidator(),
        new EncodingPolicyValidator(),
        new ManagementPolicyValidator()
    };

    /**
     * This constructor instantiates the object with a set of dynamically
     * discovered PolicyAssertionValidators.
     *
     * @throws PolicyException Thrown if the set of dynamically discovered
     *   PolicyAssertionValidators is empty.
     */
    private ValidationProcessor() throws PolicyException {
        super(Arrays.asList(JAXWS_ASSERTION_VALIDATORS));
    }

    /**
     * Factory method that returns singleton instance of the class.
     *
     * @return singleton An instance of the class.
     * @throws PolicyException If instantiation failed.
     */
    public static ValidationProcessor getInstance() throws PolicyException {
        return new ValidationProcessor();
    }

}
