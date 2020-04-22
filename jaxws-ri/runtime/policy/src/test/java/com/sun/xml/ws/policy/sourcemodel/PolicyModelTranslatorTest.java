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

import com.sun.xml.ws.policy.Policy;
import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
import com.sun.xml.ws.policy.testutils.PolicyResourceLoader;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;


public class PolicyModelTranslatorTest extends TestCase {
    
    private static final String COMPACT_MODEL_SUFFIX = ".xml";
    private static final String NORMALIZED_MODEL_SUFFIX = "_normalized.xml";
    private static final Map<String, Integer> COMPLEX_POLICIES;

    static {
        Map<String, Integer> tempMap = new HashMap<String, Integer>();
        tempMap.put("complex_policy/nested_assertions_with_alternatives", 3);
        tempMap.put("complex_policy/single_choice1", 3);
        tempMap.put("complex_policy/single_choice2", 5);
        tempMap.put("complex_policy/nested_choice1", 3);
        tempMap.put("complex_policy/nested_choice2", 3);
        tempMap.put("complex_policy/assertion_parameters1", 1);
        COMPLEX_POLICIES = Collections.unmodifiableMap(tempMap);
    }

    private PolicyModelTranslator translator;

    public PolicyModelTranslatorTest(String testName) {
        super(testName);
    }            

    @Override
    protected void setUp() throws Exception {
        translator = PolicyModelTranslator.getTranslator();
    }

    /**
     * Test of getTranslator method, of class PolicyModelTranslator.
     * @throws Exception
     */
    public void testGetTranslator() throws Exception {
        PolicyModelTranslator result = PolicyModelTranslator.getTranslator();
        assertNotNull(result);
    }

    /**
     * Test of translate method, of class PolicyModelTranslator.
     * @throws Exception
     */
    public void testTranslateEmpty() throws Exception {
        PolicySourceModel model = PolicySourceModel.createPolicySourceModel(NamespaceVersion.v1_5, "id", null);
        PolicyModelTranslator instance = PolicyModelTranslator.getTranslator();
        Policy expResult = Policy.createEmptyPolicy(NamespaceVersion.v1_5, null, "id");
        Policy result = instance.translate(model);
        assertEquals(expResult, result);
    }

    /**
     * Test of translate method, of class com.sun.xml.ws.policy.PolicyModelTranslator.
     * @throws Exception
     */
    public void testTranslateComplexPoliciesWithMultipleNestedPolicyAlternatives() throws Exception {
        int index = 0;
        for (Map.Entry<String, Integer> entry : COMPLEX_POLICIES.entrySet()) {
            String compactResourceName = entry.getKey() + COMPACT_MODEL_SUFFIX;
            String normalizedResouceName = entry.getKey() + NORMALIZED_MODEL_SUFFIX;
            int expectedNumberOfAlternatives = entry.getValue();

            PolicySourceModel compactModel = PolicyResourceLoader.unmarshallModel(compactResourceName);
            PolicySourceModel normalizedModel = PolicyResourceLoader.unmarshallModel(normalizedResouceName);

//            System.out.println(index + ". compact model: " + compactModel);
//            System.out.println("===========================================================");
//            System.out.println(index + ". normalized model: " + normalizedModel);
//            System.out.println("===========================================================");

            Policy compactModelPolicy = translator.translate(compactModel);
            Policy normalizedModelPolicy = translator.translate(normalizedModel);

            assertEquals("Normalized and compact model policy instances should contain equal number of alternatives", normalizedModelPolicy.getNumberOfAssertionSets(), compactModelPolicy.getNumberOfAssertionSets());
            assertEquals("This policy should contain '" + expectedNumberOfAlternatives + "' alternatives", expectedNumberOfAlternatives, compactModelPolicy.getNumberOfAssertionSets());
            assertEquals("Normalized and compact policy expression should form equal Policy instances", normalizedModelPolicy, compactModelPolicy);

//            System.out.println(index + ". compact model policy: " + compactModelPolicy);
//            System.out.println("===========================================================");
//            System.out.println(index + ". normalized model policy: " + normalizedModelPolicy);

            index++;
        }
    }

    public void testPreserveOriginalNamespaceInformation() throws Exception {
        Policy policy = translator.translate(PolicyResourceLoader.unmarshallModel("namespaces/policy-v1.2.xml"));
        assertEquals("Namespace does not match original", NamespaceVersion.v1_2, policy.getNamespaceVersion());
        policy = translator.translate(PolicyResourceLoader.unmarshallModel("namespaces/policy-v1.5.xml"));
        assertEquals("Namespace does not match original", NamespaceVersion.v1_5, policy.getNamespaceVersion());
    }

}
