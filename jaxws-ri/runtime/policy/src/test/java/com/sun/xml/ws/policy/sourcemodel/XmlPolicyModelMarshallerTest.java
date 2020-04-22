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

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import junit.framework.TestCase;

/**
 *
 * @author Fabian Ritzmann
 */
public class XmlPolicyModelMarshallerTest extends TestCase {

    private PolicyModelMarshaller marshaller = PolicyModelMarshaller.getXmlMarshaller(false);
    private PolicyModelUnmarshaller unmarshaller = PolicyModelUnmarshaller.getXmlUnmarshaller();
    private XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();

    
    public XmlPolicyModelMarshallerTest(String testName) {
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

    /**
     * Test of marshal method, of class com.sun.xml.ws.policy.sourcemodel.XmlPolicyModelMarshaller.
     * @throws Exception
     */
    public void testMarshal() throws Exception {
        PolicySourceModel model = null;
        Object storage = null;

        try {
            marshaller.marshal(model, storage);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
        }

        StringWriter writer = new StringWriter();
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        XMLStreamWriter streamWriter = factory.createXMLStreamWriter(writer);
        storage = streamWriter;

        try {
            marshaller.marshal(model, storage);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
        }

        model = PolicyResourceLoader.unmarshallModel("complex_policy/nested_assertions_with_alternatives.xml");
        storage = null;

        try {
            marshaller.marshal(model, storage);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
        }

        storage = new Object();

        try {
            marshaller.marshal(model, storage);
            fail("Expected PolicyException");
        } catch (PolicyException e) {
        }

        storage = streamWriter;
        marshaller.marshal(model, storage);
        String policy = writer.toString();
        // Verifying that produced policy String is not empty
        assertTrue(policy.length() > 10);
    }

    public void testMarshallingAssertionsWithVisibilityAttribute() throws Exception {
        String[] modelFileNames = new String[]{
            "policy_0_visible",
            "policy_1_visible",
            "policy_2_visible"
        };

        for (String modelFileName : modelFileNames) {
            PolicySourceModel model = PolicyResourceLoader.unmarshallModel("visibility/" + modelFileName + ".xml");
            PolicySourceModel resultModel = marshalAndUnmarshalModel(model);
            PolicySourceModel expectedModel = PolicyResourceLoader.unmarshallModel("visibility/" + modelFileName + "_expected.xml");
            assertEquals(modelFileName, expectedModel, resultModel);
        }
    }

    public void testMarshallModelWithProperPolicyNamespaceVersion() throws Exception {
        PolicySourceModel model = marshalAndUnmarshalModel(PolicyResourceLoader.unmarshallModel("namespaces/policy-v1.2.xml"));
        assertEquals("Namespace version does not match after marshalling.", NamespaceVersion.v1_2, model.getNamespaceVersion());

        model = marshalAndUnmarshalModel(PolicyResourceLoader.unmarshallModel("namespaces/policy-v1.5.xml"));
        assertEquals("Namespace version does not match after marshalling.", NamespaceVersion.v1_5, model.getNamespaceVersion());
    }

    private PolicySourceModel marshalAndUnmarshalModel(PolicySourceModel model) throws PolicyException, XMLStreamException {
        StringWriter writer = new StringWriter();
        XMLStreamWriter streamWriter = xmlOutputFactory.createXMLStreamWriter(writer);
        marshaller.marshal(model, streamWriter);

        StringReader reader = new StringReader(writer.toString());
        PolicySourceModel resultModel = unmarshaller.unmarshalModel(reader);
        return resultModel;
    }

}
