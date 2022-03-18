/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.spi;

import com.sun.xml.ws.policy.PolicyAssertion;

/**
 *
 *
 * @author Marek Potociar (marek.potociar at sun.com)
 */
public interface PolicyAssertionValidator {
    
    enum Fitness {
        UNKNOWN,
        INVALID,
        UNSUPPORTED,
        SUPPORTED;
        
        public Fitness combine(Fitness other) {
            if (this.compareTo(other) < 0) {
                return other;
            } else {
                return this;
            }
        }
    }
    
    
    /**
     * An implementation of this method must return:
     * <ul>
     *      <li>
     *          {@code Fitness.UNKNOWN} if the policy assertion type is not recognized
     *      </li>
     *      <li>
     *          {@code Fitness.SUPPORTED} if the policy assertion is supported in the
     *          client-side context
     *      </li>
     *      <li>
     *          {@code Fitness.UNSUPPORTED} if the policy assertion is recognized however
     *          it's content is not supported. For each assetion that will be eventually marked with
     *          this validation value, the policy processor will log a WARNING message however
     *          an attempt to call the web service will be made.
     *      </li>
     *      <li>
     *          {@code Fitness.INVALID} if the policy assertion is recognized however
     *          its content (value, parameters, nested assertions) is invalid. For each assetion
     *          that will be eventually marked with this validation value, the policy processor
     *          will log a SEVERE error and throw an exception. No further attempts to call
     *          the web service will be made.
     *      </li>
     * </ul>
     *
     * @param assertion A policy asssertion (See {@link com.sun.xml.ws.policy.PolicyAssertion PolicyAssertion}).
     * May contain nested policies and assertions.
     * @return fitness of the {@code assertion} on in the client-side context. Must not be {@code null}.
     */
    Fitness validateClientSide(PolicyAssertion assertion);
    
    /**
     * An implementation of this method must return:
     * <ul>
     *      <li>
     *          {@code Fitness.UNKNOWN} if the policy assertion type is not recognized
     *      </li>
     *      <li>
     *          {@code Fitness.SUPPORTED} if the policy assertion is supported in the
     *          server-side context
     *      </li>
     *      <li>
     *          {@code Fitness.UNSUPPORTED} if the policy assertion is recognized however
     *          it's content is not supported.
     *      </li>
     *      <li>
     *          {@code Fitness.INVALID} if the policy assertion is recognized however
     *          its content (value, parameters, nested assertions) is invalid.
     *      </li>
     * </ul>
     *
     * For each assetion that will be eventually marked with validation value of
     * UNKNOWN, UNSUPPORTED or INVALID, the policy processor will log a SEVERE error
     * and throw an exception.
     *
     * @param assertion A policy asssertion (See {@link com.sun.xml.ws.policy.PolicyAssertion PolicyAssertion}).
     * May contain nested policies and assertions.
     * @return fitness of the {@code assertion} on in the server-side context. Must not be {@code null}.
     */
    Fitness validateServerSide(PolicyAssertion assertion);
    
    /**
     * Each service provider that implements this SPI must make sure to identify all possible domains it supports.
     * This operation must be implemented as idempotent (must return same values on multiple calls).
     * <br>
     * It is legal for two or more {@code PolicyAssertionValidator}s to support the same domain. In such case,
     * the most significant result returned from validation methods will be eventually assigned to the assertion.
     * The significance of validation results is as follows (from most to least significant):
     * <ol>
     *      <li>SUPPORTED</li>
     *      <li>UNSUPPORTED</li>
     *      <li>INVALID</li>
     *      <li>UNKNOWN</li>
     * </ol>
     *
     *
     * @return {@code String} array holding {@code String} representations of identifiers of all supported domains.
     * Usually a domain identifier is represented by a namespace.
     */
    String[] declareSupportedDomains();
}
