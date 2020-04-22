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

import com.sun.xml.txw2.TXW;
import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.output.DomSerializer;
import com.sun.xml.ws.policy.PolicyConstants;
import com.sun.xml.ws.policy.PolicyException;
import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
import java.io.StringWriter;
import java.util.Collection;
import java.util.LinkedList;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import junit.framework.TestCase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 *
 * @author Fabian Ritzmann
 */
public class PolicyModelMarshallerTest extends TestCase {

    private DocumentBuilder builder;
    private XMLOutputFactory factory;


    public PolicyModelMarshallerTest(String testName) {
        super(testName);
    }            

    @Override
    protected void setUp() throws ParserConfigurationException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        documentFactory.setNamespaceAware(true);
        builder = documentFactory.newDocumentBuilder();
        factory = XMLOutputFactory.newInstance();
    }

    /**
     * Test of getXmlMarshaller method, of class PolicyModelMarshaller.
     */
    public void testGetXmlMarshallerVisible() {
        boolean marshallInvisible = false;
        PolicyModelMarshaller result = PolicyModelMarshaller.getXmlMarshaller(marshallInvisible);
        assertNotNull(result);
    }

    /**
     * Test of getXmlMarshaller method, of class PolicyModelMarshaller.
     */
    public void testGetXmlMarshallerInvisible() {
        boolean marshallInvisible = true;
        PolicyModelMarshaller result = PolicyModelMarshaller.getXmlMarshaller(marshallInvisible);
        assertNotNull(result);
    }

    /**
     * Test of marshal method, of class PolicyModelMarshaller.
     * @throws PolicyException
     */
    public void testMarshal_PolicySourceModel_Object() throws PolicyException {
        PolicySourceModel model = PolicySourceModel.createPolicySourceModel(NamespaceVersion.v1_2);
        Document document = builder.newDocument();
        TypedXmlWriter storage = TXW.create(new QName("root"), TypedXmlWriter.class, new DomSerializer(document));
        PolicyModelMarshaller instance = PolicyModelMarshaller.getXmlMarshaller(false);
        instance.marshal(model, storage);
        storage.commit();
        Element element = document.getDocumentElement();
        Node policyElement = element.getFirstChild();
        assertEquals(policyElement.getLocalName(), "Policy");
    }

    /**
     * Test of marshal method, of class PolicyModelMarshaller.
     * @throws PolicyException
     */
    public void testMarshal_Collection_Object() throws PolicyException {
        Collection<PolicySourceModel> models = new LinkedList<PolicySourceModel>();
        PolicySourceModel model = PolicySourceModel.createPolicySourceModel(NamespaceVersion.v1_2);
        PolicySourceModel model2 = PolicySourceModel.createPolicySourceModel(NamespaceVersion.v1_5);
        models.add(model);
        models.add(model2);
        Document document = builder.newDocument();
        TypedXmlWriter storage = TXW.create(new QName("policies"), TypedXmlWriter.class, new DomSerializer(document));
        PolicyModelMarshaller instance = PolicyModelMarshaller.getXmlMarshaller(false);
        instance.marshal(models, storage);
        storage.commit();
        Element element = document.getDocumentElement();
        Node policyElement = element.getFirstChild();
        assertEquals(policyElement.getLocalName(), "Policy");
    }

    public void testMarshalPrefixStream() throws PolicyException, XMLStreamException {
        PolicySourceModel model = PolicySourceModel.createPolicySourceModel(NamespaceVersion.v1_2, "id", null);
        ModelNode root = model.getRootNode();
        ModelNode all = root.createChildAllNode();
        AssertionData assertion = AssertionData.createAssertionData(new QName("http://schemas.foo.com/", "Assertion"));
        all.createChildAssertionNode(assertion);

        StringWriter writer = new StringWriter();
        XMLStreamWriter storage = factory.createXMLStreamWriter(writer);
        PolicyModelMarshaller instance = PolicyModelMarshaller.getXmlMarshaller(false);

        instance.marshal(model, storage);
        storage.flush();

        String result = writer.toString();
        assertTrue(result.contains("xmlns:wsp1_2"));
        assertTrue(result.contains("xmlns:wsu"));
    }

    public void testMarshalPrefixWrite() throws PolicyException {
        PolicySourceModel model = PolicySourceModel.createPolicySourceModel(NamespaceVersion.v1_5, "id", null);
        ModelNode root = model.getRootNode();
        ModelNode all = root.createChildAllNode();
        AssertionData assertion = AssertionData.createAssertionData(new QName("http://schemas.foo.com/", "Assertion"));
        all.createChildAssertionNode(assertion);

        Document document = builder.newDocument();
        TypedXmlWriter storage = TXW.create(new QName("root"), TypedXmlWriter.class, new DomSerializer(document));
        PolicyModelMarshaller instance = PolicyModelMarshaller.getXmlMarshaller(false);

        instance.marshal(model, storage);
        storage.commit();

        Element element = document.getDocumentElement();
        Node policyElement = element.getFirstChild();
        assertEquals(NamespaceVersion.v1_5.getDefaultNamespacePrefix(), policyElement.getPrefix());

        NamedNodeMap map = policyElement.getAttributes();
        Node id = map.getNamedItemNS(PolicyConstants.WSU_ID.getNamespaceURI(), PolicyConstants.WSU_ID.getLocalPart());
        assertEquals(PolicyConstants.WSU_NAMESPACE_PREFIX, id.getPrefix());
    }

}
