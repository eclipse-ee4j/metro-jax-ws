/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.jaxws;

import com.sun.xml.ws.api.policy.AlternativeSelector;
import com.sun.xml.ws.api.policy.PolicyResolver;
import com.sun.xml.ws.api.policy.ValidationProcessor;
import com.sun.xml.ws.policy.AssertionSet;
import com.sun.xml.ws.policy.EffectivePolicyModifier;
import com.sun.xml.ws.policy.Policy;
import com.sun.xml.ws.policy.PolicyAssertion;
import com.sun.xml.ws.policy.PolicyException;
import com.sun.xml.ws.policy.PolicyMap;
import com.sun.xml.ws.policy.spi.PolicyAssertionValidator.Fitness;
import com.sun.xml.ws.resources.PolicyMessages;

import jakarta.xml.ws.WebServiceException;

/**
 * This default implementation runs the policy validators on the server side and
 * selects a policy alternative on the client side.
 *
 * @author Rama Pulavarthi
 * @author Fabian Ritzmann
 */
public class DefaultPolicyResolver implements PolicyResolver {

    @Override
    public PolicyMap resolve(ServerContext context) {
        PolicyMap map = context.getPolicyMap();
        if(map != null)
            validateServerPolicyMap(map);
        return map;
    }

    @Override
    public PolicyMap resolve(ClientContext context) {
        PolicyMap map = context.getPolicyMap();
        if(map != null)
            map =  doAlternativeSelection(map);
        return map;
    }
    
    /**
     * Checks if the PolicyMap has only single alternative in the scope.
     *
     * @param policyMap
     *      PolicyMap that needs to be validated.
     */
    private void validateServerPolicyMap(PolicyMap policyMap) {
        try {
            final ValidationProcessor validationProcessor = ValidationProcessor.getInstance();

            for (Policy policy : policyMap) {

                // TODO:  here is a good place to check if the actual policy has only one alternative...

                for (AssertionSet assertionSet : policy) {
                    for (PolicyAssertion assertion : assertionSet) {
                        Fitness validationResult = validationProcessor.validateServerSide(assertion);
                        if (validationResult != Fitness.SUPPORTED) {
                            throw new PolicyException(PolicyMessages.WSP_1015_SERVER_SIDE_ASSERTION_VALIDATION_FAILED(
                                    assertion.getName(),
                                    validationResult));
                        }
                    }
                }
            }
        } catch (PolicyException e) {
            throw new WebServiceException(e);
        }
    }

    /**
     * Selects a best alternative if there are multiple policy alternatives.
     *
     */
    private PolicyMap doAlternativeSelection(PolicyMap policyMap) {
        final EffectivePolicyModifier modifier = EffectivePolicyModifier.createEffectivePolicyModifier();
        modifier.connect(policyMap);
        try {
            AlternativeSelector.doSelection(modifier);
        } catch (PolicyException e) {
            throw new WebServiceException(e);
        }
        return policyMap;
    }
}
