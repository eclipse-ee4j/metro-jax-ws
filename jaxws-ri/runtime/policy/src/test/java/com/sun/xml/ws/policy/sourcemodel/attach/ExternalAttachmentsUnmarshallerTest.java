/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.sourcemodel.attach;

import com.sun.xml.ws.policy.AssertionSet;
import com.sun.xml.ws.policy.Policy;
import com.sun.xml.ws.policy.PolicyAssertion;
import com.sun.xml.ws.policy.PolicyException;
import com.sun.xml.ws.policy.sourcemodel.AssertionData;
import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
import java.io.StringReader;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.xml.namespace.QName;
import junit.framework.TestCase;

/**
 *
 * @author Fabian Ritzmann
 */
public class ExternalAttachmentsUnmarshallerTest extends TestCase {

    public ExternalAttachmentsUnmarshallerTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testUnmarshalInvalidXml() {
        final String policies = "This is not XML";
        final StringReader reader = new StringReader(policies);
        try {
            final Map<URI, Policy> result = ExternalAttachmentsUnmarshaller.unmarshal(reader);
            fail("Expected PolicyException, got result = " + result);
        } catch (PolicyException e) {
        }
    }

    public void testUnmarshalInvalidStructure() {
        final String policies = "<wsp:PolicyAttachment " +
                "xmlns:sunman=\"http://java.sun.com/xml/ns/metro/management\" " +
                "xmlns:wsp=\"http://www.w3.org/ns/ws-policy\" " +
                "xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" " +
                "xmlns:foo=\"http://schemas.example.net/\">" +
                "  <wsp:AppliesTo>" +
                "    <wsp:URI>urn:uuid:c9bef600-0d7a-11de-abc1-0002a5d5c51b</wsp:URI>" +
                "  </wsp:AppliesTo>" +
                "  <wsp:Policy wsu:Id=\"binding-policy\">" +
                "    <foo:TopLevelAssertion_1 />" +
                "  </wsp:Policy>" +
                "</wsp:PolicyAttachment>";
        final StringReader reader = new StringReader(policies);
        try {
            final Map<URI, Policy> result = ExternalAttachmentsUnmarshaller.unmarshal(reader);
            fail("Expected PolicyException, got result = " + result);
        } catch (PolicyException e) {
        }
    }

    public void testUnmarshalUnknownElement() {
        final String policies = "<sunman:Policies " +
                "xmlns:sunman=\"http://java.sun.com/xml/ns/metro/management\" " +
                "xmlns:wsp=\"http://www.w3.org/ns/ws-policy\" " +
                "xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" " +
                "xmlns:foo=\"http://schemas.example.net/\">" +
                "  <wsp:PolicyAttachment>" +
                "    <wsp:AppliesTo>" +
                "      <wsp:URI>urn:uuid:c9bef600-0d7a-11de-abc1-0002a5d5c51b</wsp:URI>" +
                "    </wsp:AppliesTo>" +
                "    <UnknownElement>" +
                "    </UnknownElement>" +
                "  </wsp:PolicyAttachment>" +
                "</sunman:Policies>";
        final StringReader reader = new StringReader(policies);
        try {
            final Map<URI, Policy> result = ExternalAttachmentsUnmarshaller.unmarshal(reader);
            fail("Expected PolicyException, got result = " + result);
        } catch (PolicyException e) {
        }
    }

    public void testUnmarshalUnknownElement2() {
        final String policies = "<sunman:Policies " +
                "xmlns:sunman=\"http://java.sun.com/xml/ns/metro/management\" " +
                "xmlns:wsp=\"http://www.w3.org/ns/ws-policy\" " +
                "xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" " +
                "xmlns:foo=\"http://schemas.example.net/\">" +
                "  <UnknownElement>" +
                "    <wsp:AppliesTo>" +
                "      <wsp:URI>urn:uuid:c9bef600-0d7a-11de-abc1-0002a5d5c51b</wsp:URI>" +
                "    </wsp:AppliesTo>" +
                "    <wsp:Policy wsu:Id=\"binding-policy\">" +
                "      <foo:TopLevelAssertion_1 />" +
                "    </wsp:Policy>" +
                "  </UnknownElement>" +
                "</sunman:Policies>";
        final StringReader reader = new StringReader(policies);
        try {
            final Map<URI, Policy> result = ExternalAttachmentsUnmarshaller.unmarshal(reader);
            fail("Expected PolicyException, got result = " + result);
        } catch (PolicyException e) {
        }
    }

    public void testUnmarshalInvalidEndTag() {
        final String policies = "<sunman:Policies " +
                "xmlns:sunman=\"http://java.sun.com/xml/ns/metro/management\" " +
                "xmlns:wsp=\"http://www.w3.org/ns/ws-policy\" " +
                "xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" " +
                "xmlns:foo=\"http://schemas.example.net/\">" +
                "  <wsp:PolicyAttachment>" +
                "    <wsp:AppliesTo>" +
                "      <wsp:URI>urn:uuid:c9bef600-0d7a-11de-abc1-0002a5d5c51b</wsp:URI>" +
                "    </wsp:WrongTag>" +
                "    <wsp:Policy wsu:Id=\"binding-policy\">" +
                "      <foo:TopLevelAssertion_1 />" +
                "    </wsp:Policy>" +
                "  </wsp:PolicyAttachment>" +
                "</sunman:Policies>";
        final StringReader reader = new StringReader(policies);
        try {
            final Map<URI, Policy> result = ExternalAttachmentsUnmarshaller.unmarshal(reader);
            fail("Expected PolicyException, got result = " + result);
        } catch (PolicyException e) {
        }
    }

    /**
     * Test of unmarshal method, of class ExternalAttachmentsUnmarshaller.
     */
    @SuppressWarnings({"deprecation"})
    public void testUnmarshal() throws Exception {
        final String policies = "<sunman:Policies " +
                "xmlns:sunman=\"http://java.sun.com/xml/ns/metro/management\" " +
                "xmlns:wsp=\"http://www.w3.org/ns/ws-policy\" " +
                "xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" " +
                "xmlns:foo=\"http://schemas.example.net/\">" +
                "  <wsp:PolicyAttachment>" +
                "    <wsp:AppliesTo>" +
                "      <wsp:URI>urn:uuid:c9bef600-0d7a-11de-abc1-0002a5d5c51b</wsp:URI>" +
                "    </wsp:AppliesTo>" +
                "    <wsp:Policy wsu:Id=\"binding-policy\">" +
                "      <foo:TopLevelAssertion_1 />" +
                "    </wsp:Policy>" +
                "  </wsp:PolicyAttachment>" +
                "  <wsp:PolicyAttachment>" +
                "    <wsp:AppliesTo>" +
                "      <wsp:URI>urn:uuid:62e66b60-0d7b-11de-a1a2-0002a5d5c51b</wsp:URI>" +
                "    </wsp:AppliesTo>" +
                "    <wsp:Policy wsu:Id=\"operation-policy\">" +
                "      <foo:TopLevelAssertion_2 />" +
                "    </wsp:Policy>" +
                "  </wsp:PolicyAttachment>" +
                "  <wsp:PolicyAttachment>" +
                "    <wsp:AppliesTo>" +
                "      <wsp:URI>urn:uuid:730d8d20-0d7b-11de-84e9-0002a5d5c51b</wsp:URI>" +
                "    </wsp:AppliesTo>" +
                "    <wsp:Policy wsu:Id=\"input-policy\">" +
                "      <foo:TopLevelAssertion_3>" +
                "        <foo:TopLevelAssertion_3_Parameter1/>" +
                "        <foo:TopLevelAssertion_3_Parameter2>" +
                "          <foo:TopLevelAssertion_3_Parameter2_1/>" +
                "          <foo:TopLevelAssertion_3_Parameter2_2/>" +
                "        </foo:TopLevelAssertion_3_Parameter2>" +
                "        <wsp:Policy>" +
                "          <wsp:ExactlyOne>" +
                "              <foo:NestedAssertion_1/>" +
                "              <foo:NestedAssertion_2/>" +
                "          </wsp:ExactlyOne>" +
                "        </wsp:Policy>" +
                "      </foo:TopLevelAssertion_3>" +
                "    </wsp:Policy>" +
                "  </wsp:PolicyAttachment>" +
                "  <wsp:PolicyAttachment>" +
                "    <wsp:AppliesTo>" +
                "      <wsp:URI>urn:uuid:85b0f980-0d7b-11de-8e9d-0002a5d5c51b</wsp:URI>" +
                "    </wsp:AppliesTo>" +
                "    <wsp:Policy wsu:Id=\"output-policy\">" +
                "      <foo:TopLevelAssertion_4/>" +
                "    </wsp:Policy>" +
                "  </wsp:PolicyAttachment>" +
                "  <wsp:PolicyAttachment>" +
                "    <wsp:AppliesTo>" +
                "      <wsp:URI>urn:uuid:917cb060-0d7b-11de-9e80-0002a5d5c51b</wsp:URI>" +
                "    </wsp:AppliesTo>" +
                "    <wsp:Policy wsu:Id=\"fault-policy\">" +
                "      <foo:TopLevelAssertion_5/>" +
                "    </wsp:Policy>" +
                "  </wsp:PolicyAttachment>" +
                "</sunman:Policies>";

        final Map<URI, Policy> expResult = new HashMap<URI, Policy>();

        final AssertionData assertionData1 = AssertionData.createAssertionData(new QName("http://schemas.example.net/", "TopLevelAssertion_1"));
        final PolicyAssertion assertion1 = new PolicyAssertion(assertionData1, null) { };
        final LinkedList<PolicyAssertion> assertionList1 = new LinkedList<PolicyAssertion>();
        assertionList1.add(assertion1);
        final AssertionSet assertions1 = AssertionSet.createAssertionSet(assertionList1);
        final LinkedList<AssertionSet> alternatives1 = new LinkedList<AssertionSet>();
        alternatives1.add(assertions1);
        final Policy bindingPolicy = Policy.createPolicy(NamespaceVersion.v1_5, null, "binding-policy", alternatives1);
        expResult.put(new URI("urn:uuid:c9bef600-0d7a-11de-abc1-0002a5d5c51b"), bindingPolicy);

        final AssertionData assertionData2 = AssertionData.createAssertionData(new QName("http://schemas.example.net/", "TopLevelAssertion_2"));
        final PolicyAssertion assertion2 = new PolicyAssertion(assertionData2, null) { };
        final LinkedList<PolicyAssertion> assertionList2 = new LinkedList<PolicyAssertion>();
        assertionList2.add(assertion2);
        final AssertionSet assertions2 = AssertionSet.createAssertionSet(assertionList2);
        final LinkedList<AssertionSet> alternatives2 = new LinkedList<AssertionSet>();
        alternatives2.add(assertions2);
        final Policy operationPolicy = Policy.createPolicy(NamespaceVersion.v1_5, null, "operation-policy", alternatives2);
        expResult.put(new URI("urn:uuid:62e66b60-0d7b-11de-a1a2-0002a5d5c51b"), operationPolicy);

        final AssertionData assertionData31 = AssertionData.createAssertionData(new QName("http://schemas.example.net/", "TopLevelAssertion_3_Parameter1"));
        final PolicyAssertion assertion31 = new PolicyAssertion(assertionData31, null) { };
        final AssertionData assertionData321 = AssertionData.createAssertionData(new QName("http://schemas.example.net/", "TopLevelAssertion_3_Parameter2_1"));
        final PolicyAssertion assertion321 = new PolicyAssertion(assertionData321, null) { };
        final AssertionData assertionData322 = AssertionData.createAssertionData(new QName("http://schemas.example.net/", "TopLevelAssertion_3_Parameter2_2"));
        final PolicyAssertion assertion322 = new PolicyAssertion(assertionData322, null) { };
        final AssertionData assertionData32 = AssertionData.createAssertionData(new QName("http://schemas.example.net/", "TopLevelAssertion_3_Parameter2"));
        final LinkedList<PolicyAssertion> parameters32 = new LinkedList<PolicyAssertion>();
        parameters32.add(assertion321);
        parameters32.add(assertion322);
        final PolicyAssertion assertion32 = new PolicyAssertion(assertionData32, parameters32) { };
        final LinkedList<PolicyAssertion> parameters3 = new LinkedList<PolicyAssertion>();
        parameters3.add(assertion31);
        parameters3.add(assertion32);
        final AssertionData nestedAssertionData1 = AssertionData.createAssertionData(new QName("http://schemas.example.net/", "NestedAssertion_1"));
        final PolicyAssertion nestedAssertion1 = new PolicyAssertion(nestedAssertionData1, null) { };
        final LinkedList<PolicyAssertion> nestedAssertionList1 = new LinkedList<PolicyAssertion>();
        nestedAssertionList1.add(nestedAssertion1);
        final AssertionSet nestedAlternative1 = AssertionSet.createAssertionSet(nestedAssertionList1);
        final AssertionData assertionData3 = AssertionData.createAssertionData(new QName("http://schemas.example.net/", "TopLevelAssertion_3"));
        final PolicyAssertion assertion3Alt1 = new PolicyAssertion(assertionData3, parameters3, nestedAlternative1) { };
        final LinkedList<PolicyAssertion> assertionList31 = new LinkedList<PolicyAssertion>();
        assertionList31.add(assertion3Alt1);
        final AssertionSet assertions31 = AssertionSet.createAssertionSet(assertionList31);
        final LinkedList<AssertionSet> alternatives3 = new LinkedList<AssertionSet>();
        alternatives3.add(assertions31);
        final AssertionData nestedAssertionData2 = AssertionData.createAssertionData(new QName("http://schemas.example.net/", "NestedAssertion_2"));
        final PolicyAssertion nestedAssertion2 = new PolicyAssertion(nestedAssertionData2, null) { };
        final LinkedList<PolicyAssertion> nestedAssertionList2 = new LinkedList<PolicyAssertion>();
        nestedAssertionList2.add(nestedAssertion2);
        final AssertionSet nestedAlternative2 = AssertionSet.createAssertionSet(nestedAssertionList2);
        final PolicyAssertion assertion3Alt2 = new PolicyAssertion(assertionData3, parameters3, nestedAlternative2) { };
        final LinkedList<PolicyAssertion> assertionList32 = new LinkedList<PolicyAssertion>();
        assertionList32.add(assertion3Alt2);
        final AssertionSet assertions32 = AssertionSet.createAssertionSet(assertionList32);
        alternatives3.add(assertions32);
        final Policy inputPolicy = Policy.createPolicy(NamespaceVersion.v1_5, null, "input-policy", alternatives3);
        expResult.put(new URI("urn:uuid:730d8d20-0d7b-11de-84e9-0002a5d5c51b"), inputPolicy);

        final AssertionData assertionData4 = AssertionData.createAssertionData(new QName("http://schemas.example.net/", "TopLevelAssertion_4"));
        final PolicyAssertion assertion4 = new PolicyAssertion(assertionData4, null) { };
        final LinkedList<PolicyAssertion> assertionList4 = new LinkedList<PolicyAssertion>();
        assertionList4.add(assertion4);
        final AssertionSet assertions4 = AssertionSet.createAssertionSet(assertionList4);
        final LinkedList<AssertionSet> alternatives4 = new LinkedList<AssertionSet>();
        alternatives4.add(assertions4);
        final Policy outputPolicy = Policy.createPolicy(NamespaceVersion.v1_5, null, "output-policy", alternatives4);
        expResult.put(new URI("urn:uuid:85b0f980-0d7b-11de-8e9d-0002a5d5c51b"), outputPolicy);

        final AssertionData assertionData5 = AssertionData.createAssertionData(new QName("http://schemas.example.net/", "TopLevelAssertion_5"));
        final PolicyAssertion assertion5 = new PolicyAssertion(assertionData5, null) { };
        final LinkedList<PolicyAssertion> assertionList5 = new LinkedList<PolicyAssertion>();
        assertionList5.add(assertion5);
        final AssertionSet assertions5 = AssertionSet.createAssertionSet(assertionList5);
        final LinkedList<AssertionSet> alternatives5 = new LinkedList<AssertionSet>();
        alternatives5.add(assertions5);
        final Policy faultPolicy = Policy.createPolicy(NamespaceVersion.v1_5, null, "fault-policy", alternatives5);
        expResult.put(new URI("urn:uuid:917cb060-0d7b-11de-9e80-0002a5d5c51b"), faultPolicy);

        final StringReader reader = new StringReader(policies);
        final Map<URI, Policy> result = ExternalAttachmentsUnmarshaller.unmarshal(reader);
        assertEquals(expResult, result);
    }
    
}
