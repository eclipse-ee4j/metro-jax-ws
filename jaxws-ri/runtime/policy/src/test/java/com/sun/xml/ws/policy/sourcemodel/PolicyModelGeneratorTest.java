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
import com.sun.xml.ws.policy.PolicyException;
import com.sun.xml.ws.policy.sourcemodel.PolicyModelGenerator.PolicySourceModelCreator;
import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
import com.sun.xml.ws.policy.testutils.PolicyResourceLoader;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 *
 * @author Fabian Ritzmann
 */
public class PolicyModelGeneratorTest extends TestCase {

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
        tempMap.put("complex_policy/assertion_parameters2", 1);
        COMPLEX_POLICIES = Collections.unmodifiableMap(tempMap);
    }

    private PolicyModelTranslator translator;
    private PolicyModelGenerator normalGenerator = PolicyModelGenerator.getGenerator();
    private PolicyModelGenerator compactGenerator = PolicyModelGenerator.getCompactGenerator(
            new PolicySourceModelCreator());
    
    
    public PolicyModelGeneratorTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        translator = PolicyModelTranslator.getTranslator();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getGenerator method, of class PolicyModelGenerator.
     */
    public void testGetGenerator() {
        PolicyModelGenerator result = PolicyModelGenerator.getGenerator();
        assertNotNull(result);
    }

    /**
     * Test of translate method, of class PolicyModelGenerator.
     * @throws PolicyException
     */
    public void testTranslateEmpty() throws PolicyException {
        String expResult = "id";
        Policy policy = Policy.createEmptyPolicy(NamespaceVersion.v1_5, null, expResult);
        PolicyModelGenerator instance = PolicyModelGenerator.getGenerator();
        PolicySourceModel model = instance.translate(policy);
        String result = model.getPolicyId();
        assertEquals(expResult, result);
    }

    /**
     * Test of translate method, of class PolicyModelGenerator.
     * @throws PolicyException
     */
    public void testTranslateNull() throws PolicyException {
        PolicyModelGenerator instance = PolicyModelGenerator.getGenerator();
        PolicySourceModel model = instance.translate(null);
        assertNull(model);
    }

    public void testGenerate() throws Exception {
        for (Map.Entry<String, Integer> entry : COMPLEX_POLICIES.entrySet()) {
            String compactResourceName = entry.getKey() + COMPACT_MODEL_SUFFIX;
            String normalizedResouceName = entry.getKey() + NORMALIZED_MODEL_SUFFIX;
            int expectedNumberOfAlternatives = entry.getValue();

            PolicySourceModel compactModel = PolicyResourceLoader.unmarshallModel(compactResourceName);
            PolicySourceModel normalizedModel = PolicyResourceLoader.unmarshallModel(normalizedResouceName);
            Policy compactModelPolicy = translator.translate(compactModel);
            Policy normalizedModelPolicy = translator.translate(normalizedModel);

            PolicySourceModel generatedCompactModel = compactGenerator.translate(compactModelPolicy);
            PolicySourceModel generatedNormalizedModel = normalGenerator.translate(normalizedModelPolicy);

            Policy generatedCompactModelPolicy = translator.translate(generatedCompactModel);
            Policy generatedNormalizedModelPolicy = translator.translate(generatedNormalizedModel);

            assertEquals("Generated compact policy should contain '" + expectedNumberOfAlternatives + "' alternatives",
                    expectedNumberOfAlternatives, generatedCompactModelPolicy.getNumberOfAssertionSets());
            assertEquals("Generated and translated compact model policy instances should contain equal number of alternatives",
                    compactModelPolicy.getNumberOfAssertionSets(), generatedCompactModelPolicy.getNumberOfAssertionSets());
            assertEquals("Generated and translated compact policy expression should form equal Policy instances",
                    compactModelPolicy, generatedCompactModelPolicy);

            assertEquals("Generated normalized policy should contain '" + expectedNumberOfAlternatives + "' alternatives",
                    expectedNumberOfAlternatives, generatedNormalizedModelPolicy.getNumberOfAssertionSets());
            assertEquals("Generated and translated normalized model policy instances should contain equal number of alternatives",
                    normalizedModelPolicy.getNumberOfAssertionSets(), generatedNormalizedModelPolicy.getNumberOfAssertionSets());
            assertEquals("Generated and translated normalized policy expression should form equal Policy instances",
                    normalizedModelPolicy, generatedNormalizedModelPolicy);

            // TODO: somehow compare models, because now the test only checks if the translation does not end in some exception...
        }
    }

    public void testPreserveOriginalNamespaceInformation() throws Exception {
        PolicySourceModel model = normalGenerator.translate(PolicyResourceLoader.loadPolicy("namespaces/policy-v1.2.xml"));
        assertEquals("Namespace does not match original", NamespaceVersion.v1_2, model.getNamespaceVersion());
        model = normalGenerator.translate(PolicyResourceLoader.loadPolicy("namespaces/policy-v1.5.xml"));
        assertEquals("Namespace does not match original", NamespaceVersion.v1_5, model.getNamespaceVersion());
    }

    public void testPreserveOriginalNamespaceInformationCompact() throws Exception {
        PolicySourceModel model = compactGenerator.translate(PolicyResourceLoader.loadPolicy("namespaces/policy-v1.2.xml"));
        assertEquals("Namespace does not match original", NamespaceVersion.v1_2, model.getNamespaceVersion());
        model = compactGenerator.translate(PolicyResourceLoader.loadPolicy("namespaces/policy-v1.5.xml"));
        assertEquals("Namespace does not match original", NamespaceVersion.v1_5, model.getNamespaceVersion());
    }

}
