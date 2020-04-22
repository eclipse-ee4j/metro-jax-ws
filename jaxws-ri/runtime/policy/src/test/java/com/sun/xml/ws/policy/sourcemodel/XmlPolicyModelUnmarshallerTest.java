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

import com.sun.xml.ws.policy.PolicyException;
import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
import com.sun.xml.ws.policy.testutils.PolicyResourceLoader;

import java.io.Reader;
import java.io.StringReader;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

/**
 *
 * @author Fabian Ritzmann
 */
public class XmlPolicyModelUnmarshallerTest extends TestCase {

    public XmlPolicyModelUnmarshallerTest(String testName) {
        super(testName);
    }

    /**
     * Test of unmarshalModel method, of class XmlPolicyModelUnmarshaller.
     * @throws PolicyException
     */
    public void testUnmarshalModel() throws PolicyException {
        final String policy = "<wsp:Policy " +
        "xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\" " +
        "xmlns:foo=\"http://schemas.foo.com/\">" +
        "<foo:TopLevelAssertion_1 />" +
        "<foo:TopLevelAssertion_2 />" +
        "<foo:TopLevelAssertion_3>" +
        "    <foo:TopLevelAssertion_3_Parameter1/>" +
        "    <foo:TopLevelAssertion_3_Parameter2>" +
        "        <foo:TopLevelAssertion_3_Parameter2_1/>" +
        "        <foo:TopLevelAssertion_3_Parameter2_2/>" +
        "    </foo:TopLevelAssertion_3_Parameter2>" +
        "    <wsp:Policy>" +
        "        <wsp:ExactlyOne>" +
        "            <foo:NestedAssertion_1/>" +
        "            <foo:NestedAssertion_2/>" +
        "        </wsp:ExactlyOne>" +
        "    </wsp:Policy>" +
        "</foo:TopLevelAssertion_3 >" +
        "</wsp:Policy>";

        StringReader storage = new StringReader(policy);
        XmlPolicyModelUnmarshaller instance = new XmlPolicyModelUnmarshaller();
        PolicySourceModel expResult = PolicySourceModel.createPolicySourceModel(NamespaceVersion.v1_2);
        ModelNode policyRoot = expResult.getRootNode();
        AssertionData assertion1 = AssertionData.createAssertionData(new QName("http://schemas.foo.com/", "TopLevelAssertion_1"));
        policyRoot.createChildAssertionNode(assertion1);
        AssertionData assertion2 = AssertionData.createAssertionData(new QName("http://schemas.foo.com/", "TopLevelAssertion_2"));
        policyRoot.createChildAssertionNode(assertion2);
        AssertionData assertion3 = AssertionData.createAssertionData(new QName("http://schemas.foo.com/", "TopLevelAssertion_3"));
        ModelNode assertion3Node = policyRoot.createChildAssertionNode(assertion3);
        AssertionData parameter1 = AssertionData.createAssertionParameterData(
                new QName("http://schemas.foo.com/", "TopLevelAssertion_3_Parameter1"));
        assertion3Node.createChildAssertionParameterNode(parameter1);
        AssertionData parameter2 = AssertionData.createAssertionParameterData(
                new QName("http://schemas.foo.com/", "TopLevelAssertion_3_Parameter2"));
        ModelNode parameter2Node = assertion3Node.createChildAssertionParameterNode(parameter2);
        AssertionData parameter21 = AssertionData.createAssertionParameterData(
                new QName("http://schemas.foo.com/", "TopLevelAssertion_3_Parameter2_1"));
        parameter2Node.createChildAssertionParameterNode(parameter21);
        AssertionData parameter22 = AssertionData.createAssertionParameterData(
                new QName("http://schemas.foo.com/", "TopLevelAssertion_3_Parameter2_2"));
        parameter2Node.createChildAssertionParameterNode(parameter22);
        ModelNode nestedPolicy = assertion3Node.createChildPolicyNode();
        ModelNode nestedExactlyOne = nestedPolicy.createChildExactlyOneNode();
        AssertionData nestedAssertion1 = AssertionData.createAssertionData(new QName("http://schemas.foo.com/", "NestedAssertion_1"));
        nestedExactlyOne.createChildAssertionNode(nestedAssertion1);
        AssertionData nestedAssertion2 = AssertionData.createAssertionData(new QName("http://schemas.foo.com/", "NestedAssertion_2"));
        nestedExactlyOne.createChildAssertionNode(nestedAssertion2);
        PolicySourceModel result = instance.unmarshalModel(storage);
        assertEquals(expResult, result);
    }

    public void testUnmarshallSingleSimplePolicyModel() throws Exception {
        unmarshalModel("single_alternative_policy/policy5.xml");
    }

    public void testUnmarshallSingleComplexPolicyModel() throws Exception {
        unmarshalModel("complex_policy/nested_assertions_with_alternatives.xml");
    }

    public void testUnmarshallComplexPolicyModelWithAssertionParameters() throws Exception {
        unmarshalModel("complex_policy/assertion_parameters1.xml");
    }

    public void testUnmarshallComplexPolicyModelWithAssertionParametersWithValues() throws Exception {
        unmarshalModel("bug_reproduction/assertion_parameter_value_unmarshalling.xml");
    }

    public void testUnmarshallPolicyModelWithPolicyReference() throws Exception {
        unmarshalModel("bug_reproduction/policy_reference1.xml");
    }

    public void testUnmarshallPolicyModelWithXmlId() throws Exception {
        PolicySourceModel model = unmarshalModel("complex_policy/policy_with_xmlid.xml");
        assertEquals("Unmarshalled xml:id is not the same as expected", "testXmlId", model.getPolicyId());
    }

    public void testUnmarshallPolicyModelWithWsuId() throws Exception {
        PolicySourceModel model = unmarshalModel("complex_policy/policy_with_wsuid.xml");
        assertEquals("Unmarshalled wsu:Id is not the same as expected", "testWsuId", model.getPolicyId());
    }

    public void testUnmarshallPolicyModelWithXmlIdAndWsuId() throws Exception {
        try {
            unmarshalModel("complex_policy/policy_with_xmlid_and_wsuid.xml");
            fail("Should throw an exception");
        } catch (PolicyException ignored) {
            // ok.
        } catch (Exception e) {
            fail("Should throw PolicyException instead: " + e);
        }
    }

    public void testUnmarshallModelWithProperPolicyNamespaceVersion() throws Exception {
        PolicySourceModel model = unmarshalModel("namespaces/policy-v1.2.xml");
        assertEquals("Unmarshalled policy namespace version does not match with original.",
                NamespaceVersion.v1_2, model.getNamespaceVersion());

        model = unmarshalModel("namespaces/policy-v1.5.xml");
        assertEquals("Unmarshalled policy namespace version does not match with original.",
                NamespaceVersion.v1_5, model.getNamespaceVersion());
    }

    public void testWhitespace() throws Exception {
        final PolicySourceModel model = unmarshalModel("bug_reproduction/whitespace1.xml");
        final ModelNode root = model.getRootNode();
        final ModelNode exactlyOne = root.iterator().next();
        final ModelNode all = exactlyOne.iterator().next();
        for (ModelNode symmetricBinding : all) {
            if (symmetricBinding.getNodeData().getName().equals(
                    new QName("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702", "SymmetricBinding"))) {
                final ModelNode policy = symmetricBinding.iterator().next();
                for (ModelNode protectionToken : policy) {
                    if (protectionToken.getNodeData().getName().equals(
                            new QName("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702", "ProtectionToken"))) {
                        final ModelNode policy2 = protectionToken.iterator().next();
                        final ModelNode issuedToken = policy2.iterator().next();
                        for (ModelNode requestToken : issuedToken) {
                            if (requestToken.getType() == ModelNode.Type.ASSERTION_PARAMETER_NODE &&
                                requestToken.getNodeData().getName().equals(
                                    new QName("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702", "RequestSecurityTokenTemplate"))) {
                                for (ModelNode keyType : requestToken) {
                                    if (keyType.getNodeData().getName().equals(
                                            new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "KeyType"))) {
                                        assertEquals("http://docs.oasis-open.org/ws-sx/ws-trust/200512/SymmetricKey",
                                                keyType.getNodeData().getValue());
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        fail("Failed to find KeyType assertion parameter.");
    }

    public void testComment() throws Exception {
         final PolicySourceModel model = unmarshalModel("bug_reproduction/comment1.xml");
        final ModelNode root = model.getRootNode();
        final ModelNode exactlyOne = root.iterator().next();
        final ModelNode all = exactlyOne.iterator().next();
        for (ModelNode symmetricBinding : all) {
            if (symmetricBinding.getNodeData().getName().equals(
                    new QName("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702", "SymmetricBinding"))) {
                final ModelNode policy = symmetricBinding.iterator().next();
                for (ModelNode protectionToken : policy) {
                    if (protectionToken.getNodeData().getName().equals(
                            new QName("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702", "ProtectionToken"))) {
                        final ModelNode policy2 = protectionToken.iterator().next();
                        final ModelNode issuedToken = policy2.iterator().next();
                        for (ModelNode requestToken : issuedToken) {
                            if (requestToken.getType() == ModelNode.Type.ASSERTION_PARAMETER_NODE &&
                                requestToken.getNodeData().getName().equals(
                                    new QName("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702", "RequestSecurityTokenTemplate"))) {
                                for (ModelNode keyType : requestToken) {
                                    if (keyType.getNodeData().getName().equals(
                                            new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "KeyType"))) {
                                        assertEquals("http://docs.oasis-open.org/ws-sx/ws-trust/200512/SymmetricKey",
                                                keyType.getNodeData().getValue());
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        fail("Failed to find KeyType assertion parameter.");
   }

    /**
     * Testcase for https://wsit.dev.java.net/issues/show_bug.cgi?id=1305
     * @throws Exception
     */
    public void testPolicyAssertionOptionalFalse() throws Exception {
        PolicySourceModel model = unmarshalModel("bug_reproduction/assertion_optional_false_unmarshalling.xml");
        assertFalse(model.getRootNode().getChildren().iterator().next().getNodeData().isOptionalAttributeSet());        
    }
    
    private PolicySourceModel unmarshalModel(String resource) throws Exception {
        Reader reader = PolicyResourceLoader.getResourceReader(resource);
        final PolicyModelUnmarshaller xmlUnmarshaller = PolicyModelUnmarshaller.getXmlUnmarshaller();
        PolicySourceModel model = xmlUnmarshaller.unmarshalModel(reader);
        reader.close();
        return model;
    }

}
