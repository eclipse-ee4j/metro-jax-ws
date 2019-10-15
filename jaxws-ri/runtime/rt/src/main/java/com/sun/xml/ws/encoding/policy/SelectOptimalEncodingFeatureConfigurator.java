/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.encoding.policy;

import com.sun.xml.ws.api.client.SelectOptimalEncodingFeature;
import com.sun.xml.ws.policy.AssertionSet;
import com.sun.xml.ws.policy.Policy;
import com.sun.xml.ws.policy.PolicyAssertion;
import com.sun.xml.ws.policy.PolicyException;
import com.sun.xml.ws.policy.PolicyMap;
import com.sun.xml.ws.policy.PolicyMapKey;
import com.sun.xml.ws.policy.jaxws.spi.PolicyFeatureConfigurator;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import javax.xml.namespace.QName;

import static com.sun.xml.ws.encoding.policy.EncodingConstants.SELECT_OPTIMAL_ENCODING_ASSERTION;
import javax.xml.ws.WebServiceFeature;
        
/**
 * A configurator provider for FastInfoset policy assertions.
 *
 * @author Paul.Sandoz@Sun.Com
 * @author Fabian Ritzmann
 */
public class SelectOptimalEncodingFeatureConfigurator implements PolicyFeatureConfigurator {
    public static final QName enabled = new QName("enabled");
    
    /**
     * Process SelectOptimalEncoding policy assertions.
     *
     * @param key Key that identifies the endpoint scope.
     * @param policyMap The policy map.
     * @throws PolicyException If retrieving the policy triggered an exception.
     */
    public Collection<WebServiceFeature> getFeatures(PolicyMapKey key, PolicyMap policyMap) throws PolicyException {
        final Collection<WebServiceFeature> features = new LinkedList<WebServiceFeature>();
        if ((key != null) && (policyMap != null)) {
            Policy policy = policyMap.getEndpointEffectivePolicy(key);
            if (null!=policy && policy.contains(SELECT_OPTIMAL_ENCODING_ASSERTION)) {
                Iterator <AssertionSet> assertions = policy.iterator();
                while(assertions.hasNext()){
                    AssertionSet assertionSet = assertions.next();
                    Iterator<PolicyAssertion> policyAssertion = assertionSet.iterator();
                    while(policyAssertion.hasNext()){
                        PolicyAssertion assertion = policyAssertion.next();
                        if(SELECT_OPTIMAL_ENCODING_ASSERTION.equals(assertion.getName())){
                            String value = assertion.getAttributeValue(enabled);
                            boolean isSelectOptimalEncodingEnabled = value == null || Boolean.valueOf(value.trim());
                            features.add(new SelectOptimalEncodingFeature(isSelectOptimalEncodingEnabled));
                        }
                    }
                }
            }
        }
        return features;
    }
}
