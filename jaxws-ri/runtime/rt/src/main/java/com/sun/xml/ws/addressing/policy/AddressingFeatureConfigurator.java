/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.addressing.policy;

import com.sun.xml.ws.api.addressing.AddressingVersion;
import com.sun.xml.ws.policy.AssertionSet;
import com.sun.xml.ws.policy.NestedPolicy;
import com.sun.xml.ws.policy.Policy;
import com.sun.xml.ws.policy.PolicyAssertion;
import com.sun.xml.ws.policy.PolicyException;
import com.sun.xml.ws.policy.PolicyMap;
import com.sun.xml.ws.policy.PolicyMapKey;
import com.sun.xml.ws.policy.jaxws.spi.PolicyFeatureConfigurator;
import com.sun.xml.ws.policy.privateutil.PolicyLogger;
import com.sun.xml.ws.addressing.W3CAddressingMetadataConstants;
import com.sun.xml.ws.resources.ModelerMessages;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import javax.xml.namespace.QName;
import jakarta.xml.ws.WebServiceFeature;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.soap.AddressingFeature;

/**
 * This Policy extension configures the WSDLModel with AddressingFeature when Addressing assertions are present in the
 * PolicyMap.
 *
 * @author japod
 * @author Rama Pulavarthi
 */
public class AddressingFeatureConfigurator implements PolicyFeatureConfigurator {

    private static final PolicyLogger LOGGER = PolicyLogger.getLogger(AddressingFeatureConfigurator.class);

    private static final QName[] ADDRESSING_ASSERTIONS = {
        new QName(AddressingVersion.MEMBER.policyNsUri, "UsingAddressing")};

    /**
     * Creates a new instance of AddressingFeatureConfigurator
     */
    public AddressingFeatureConfigurator() {
    }

    @Override
    public Collection<WebServiceFeature> getFeatures(final PolicyMapKey key, final PolicyMap policyMap) throws PolicyException {
        LOGGER.entering(key, policyMap);
        final Collection<WebServiceFeature> features = new LinkedList<>();
        if ((key != null) && (policyMap != null)) {
            final Policy policy = policyMap.getEndpointEffectivePolicy(key);
            for (QName addressingAssertionQName : ADDRESSING_ASSERTIONS) {
                if ((policy != null) && policy.contains(addressingAssertionQName)) {
                    final Iterator <AssertionSet> assertions = policy.iterator();
                    while(assertions.hasNext()){
                        final AssertionSet assertionSet = assertions.next();
                        final Iterator<PolicyAssertion> policyAssertion = assertionSet.iterator();
                        while(policyAssertion.hasNext()){
                            final PolicyAssertion assertion = policyAssertion.next();
                            if(assertion.getName().equals(addressingAssertionQName)){
                                final WebServiceFeature feature = AddressingVersion.getFeature(addressingAssertionQName.getNamespaceURI(), true, !assertion.isOptional());
                                if (LOGGER.isLoggable(Level.FINE)) {
                                    LOGGER.fine("Added addressing feature \"" + feature + "\" for element \"" + key + "\"");
                                }
                                features.add(feature);
                            } // end-if non optional wsa assertion found
                        } // next assertion
                    } // next alternative
                } // end-if policy contains wsa assertion
            } //end foreach addr assertion

            // Deal with WS-Addressing 1.0 Metadata assertions
            if (policy != null && policy.contains(W3CAddressingMetadataConstants.WSAM_ADDRESSING_ASSERTION)) {
                for (AssertionSet assertions : policy) {
                    for (PolicyAssertion assertion : assertions) {
                        if (assertion.getName().equals(W3CAddressingMetadataConstants.WSAM_ADDRESSING_ASSERTION)) {
                            NestedPolicy nestedPolicy = assertion.getNestedPolicy();
                            boolean requiresAnonymousResponses = false;
                            boolean requiresNonAnonymousResponses = false;
                            if (nestedPolicy != null) {
                                requiresAnonymousResponses = nestedPolicy.contains(W3CAddressingMetadataConstants.WSAM_ANONYMOUS_NESTED_ASSERTION);
                                requiresNonAnonymousResponses = nestedPolicy.contains(W3CAddressingMetadataConstants.WSAM_NONANONYMOUS_NESTED_ASSERTION);
                            }
                            if(requiresAnonymousResponses && requiresNonAnonymousResponses) {
                                throw new WebServiceException("Only one among AnonymousResponses and NonAnonymousResponses can be nested in an Addressing assertion");
                            }

                            final WebServiceFeature feature;
                            if (requiresAnonymousResponses) {
                                feature = new AddressingFeature(true, !assertion.isOptional(), AddressingFeature.Responses.ANONYMOUS);
                            } else if (requiresNonAnonymousResponses) {
                                feature = new AddressingFeature(true, !assertion.isOptional(), AddressingFeature.Responses.NON_ANONYMOUS);
                            } else {
                                feature = new AddressingFeature(true, !assertion.isOptional());
                            }
                            if (LOGGER.isLoggable(Level.FINE)) {
                                LOGGER.fine("Added addressing feature \"" + feature + "\" for element \"" + key + "\"");
                            }
                            features.add(feature);
                        }
                    }
                }
            }
        }
        LOGGER.exiting(features);
        return features;
    }
}
