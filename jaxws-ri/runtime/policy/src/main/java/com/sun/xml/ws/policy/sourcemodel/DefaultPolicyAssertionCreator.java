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

import com.sun.xml.ws.policy.*;
import com.sun.xml.ws.policy.spi.AssertionCreationException;
import com.sun.xml.ws.policy.spi.PolicyAssertionCreator;
import java.util.Collection;

/**
 * Default implementation of a policy assertion creator. This implementation is used to create policy assertions in case
 * no domain specific policy assertion creator is registered for the namespace of the policy assertion.
 *
 * This is the only PolicyAssertionCreator implementation that is allowed to break general contract, claiming that
 * {@code getSupportedDomainNamespaceUri()} must not return empty String without causing PolicyAssertionCreator registration
 * fail.
 *
 * @author Marek Potociar (marek.potociar at sun.com)
 */
class DefaultPolicyAssertionCreator implements PolicyAssertionCreator {    
    private static final class DefaultPolicyAssertion extends PolicyAssertion {
        DefaultPolicyAssertion(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) {
            super (data, assertionParameters, nestedAlternative);
        }
    }
    
    /** 
     * Creates a new instance of DefaultPolicyAssertionCreator 
     */
    DefaultPolicyAssertionCreator() {
        // nothing to initialize
    }

    /**
     * See {@link PolicyAssertionCreator#getSupportedDomainNamespaceURIs() method documentation in interface}
     */
    public String[] getSupportedDomainNamespaceURIs() {
        return null;
    }

    /**
     * See {@link PolicyAssertionCreator#createAssertion(AssertionData, Collection, AssertionSet, PolicyAssertionCreator) method documentation in interface}
     */
    public PolicyAssertion createAssertion(final AssertionData data, final Collection<PolicyAssertion> assertionParameters, final AssertionSet nestedAlternative, final PolicyAssertionCreator defaultCreator) throws AssertionCreationException {
        return new DefaultPolicyAssertion(data, assertionParameters, nestedAlternative);
    }    
}
