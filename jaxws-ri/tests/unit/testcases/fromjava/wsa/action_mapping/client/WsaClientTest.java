/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.wsa.action_mapping.client;

import testutil.XMLTestCase;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPath;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;

/**
 * @author Rama Pulavarthi
 */
public class WsaClientTest extends XMLTestCase {
    private Document wsdlDoc;

    public WsaClientTest(String s) throws ParserConfigurationException, IOException, SAXException {
        super(s);
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        String wsdl = System.getProperty("addNumbersPortAddress") + "?wsdl";
        wsdlDoc = builder.parse(wsdl);


    }

    private String evaluateXpath(String expr) throws XPathExpressionException, IOException {
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        xpath.setNamespaceContext(new NSContextImpl());
        //return xpath.evaluate(expr, new InputSource(new URL(wsdl).openStream()));
        return xpath.evaluate(expr, wsdlDoc);
    }

    public void testOnewayDefaultActionMapping() throws IOException, XPathExpressionException {
        String inputExpr = getInputExpression("notify");
        Object result = evaluateXpath(inputExpr);
        assertNotNull(result);
        assertEquals(TestConstants.NOTIFY_IN_NOACTION, result);
    }

    public void testDefaultActions() throws Exception {
        String inputExpr = getInputExpression("addNumbersNoAction");
        Object result = evaluateXpath(inputExpr);
        assertNotNull(result);
        assertEquals(TestConstants.ADD_NUMBERS_IN_NOACTION, result);

        String outputExpr = getOutputExpression("addNumbersNoAction");
        result = evaluateXpath(outputExpr);
        assertNotNull(result);
        assertEquals(TestConstants.ADD_NUMBERS_OUT_NOACTION, result);

        String faultExpr = getFaultExpression("addNumbersNoAction");
        result = evaluateXpath(faultExpr);
        assertNotNull(result);
        assertEquals(TestConstants.ADD_NUMBERS_FAULT_NOACTION, result);

    }

     public void testEmptyActions() throws Exception {
        String inputExpr = getInputExpression("addNumbersEmptyAction");
        Object result = evaluateXpath(inputExpr);
        assertNotNull(result);
        assertEquals(TestConstants.ADD_NUMBERS_IN_EMPTYACTION, result);

        String outputExpr = getOutputExpression("addNumbersEmptyAction");
        result = evaluateXpath(outputExpr);
        assertNotNull(result);
        assertEquals(TestConstants.ADD_NUMBERS_OUT_EMPTYACTION, result);

    }

    public void testNonEmptySOAPAction() throws Exception {
        String inputExpr = getInputExpression("addNumbersSOAPAction");
        Object result = evaluateXpath(inputExpr);
        assertNotNull(result);
        assertEquals(TestConstants.ADD_NUMBERS_IN_SOAPACTION, result);

        String outputExpr = getOutputExpression("addNumbersSOAPAction");
        result = evaluateXpath(outputExpr);
        assertNotNull(result);
        assertEquals(TestConstants.ADD_NUMBERS_OUT_SOAPACTION, result);

    }

    public void testExplicitInputOutputAction1() throws Exception {
        String inputExpr = getInputExpression("addNumbers");
        Object result = evaluateXpath(inputExpr);
        assertNotNull(result);
        assertEquals(TestConstants.ADD_NUMBERS_IN_ACTION, result);

        String outputExpr = getOutputExpression("addNumbers");
        result = evaluateXpath(outputExpr);
        assertNotNull(result);
        assertEquals(TestConstants.ADD_NUMBERS_OUT_ACTION, result);

    }

    public void testExplicitInputOutputAction2() throws Exception {
        String inputExpr = getInputExpression("addNumbers2");
        Object result = evaluateXpath(inputExpr);
        assertNotNull(result);
        assertEquals(TestConstants.ADD_NUMBERS2_IN_ACTION, result);

        String outputExpr = getOutputExpression("addNumbers2");
        result = evaluateXpath(outputExpr);
        assertNotNull(result);
        assertEquals(TestConstants.ADD_NUMBERS2_OUT_ACTION, result);

    }

    public void testExplicitInputOutputFaultAction1() throws Exception {
        String inputExpr = getInputExpression("addNumbersFault1");
        Object result = evaluateXpath(inputExpr);
        assertNotNull(result);
        assertEquals(TestConstants.ADD_NUMBERS_FAULT1_IN_ACTION, result);

        String outputExpr = getOutputExpression("addNumbersFault1");
        result = evaluateXpath(outputExpr);
        assertNotNull(result);
        assertEquals(TestConstants.ADD_NUMBERS_FAULT1_OUT_ACTION, result);

        String faultExpr = getFaultExpression("addNumbersFault1");
        result = evaluateXpath(faultExpr);
        assertNotNull(result);
        assertEquals(TestConstants.ADD_NUMBERS_FAULT1_ADDNUMBERS_ACTION, result);

    }

    public void testExplicitInputOutputFaultAction2() throws Exception {
        String inputExpr = getInputExpression("addNumbersFault2");
        Object result = evaluateXpath(inputExpr);
        assertNotNull(result);
        assertEquals(TestConstants.ADD_NUMBERS_FAULT2_IN_ACTION, result);

        String outputExpr = getOutputExpression("addNumbersFault2");
        result = evaluateXpath(outputExpr);
        assertNotNull(result);
        assertEquals(TestConstants.ADD_NUMBERS_FAULT2_OUT_ACTION, result);

        String faultExpr1 = getFaultExpression("addNumbersFault2","AddNumbersException");
        result = evaluateXpath(faultExpr1);
        assertNotNull(result);
        assertEquals(TestConstants.ADD_NUMBERS_FAULT2_ADDNUMBERS_ACTION, result);

        String faultExpr2 = getFaultExpression("addNumbersFault2","TooBigNumbersException");
        result = evaluateXpath(faultExpr2);
        assertNotNull(result);
        assertEquals(TestConstants.ADD_NUMBERS_FAULT2_TOOBIGNUMBERS_ACTION, result);

    }

    public void testEmptyFaultAction() throws Exception {
        String faultExpr1 = getFaultExpression("addNumbersFault7","AddNumbersException");
        Object result = evaluateXpath(faultExpr1);
        assertNotNull(result);
        assertEquals(TestConstants.ADD_NUMBERS_FAULT7_ADDNUMBERS_ACTION, result);

        String faultExpr2 = getFaultExpression("addNumbersFault7","TooBigNumbersException");
        result = evaluateXpath(faultExpr2);
        assertNotNull(result);
        assertEquals(TestConstants.ADD_NUMBERS_FAULT7_TOOBIGNUMBERS_ACTION, result);

    }

    private String getInputExpression(String operation) {
        String expr = "/*[name()='definitions']/*[name()='portType']/*[name()='operation'][@name='" + operation + "']/*[name()='input']/@wsam:Action";
        return expr;
    }

    private String getOutputExpression(String operation) {
        String expr = "/*[name()='definitions']/*[name()='portType']/*[name()='operation'][@name='" + operation + "']/*[name()='output']/@wsam:Action";
        return expr;
    }

    private String getFaultExpression(String operation) {
        String expr = "/*[name()='definitions']/*[name()='portType']/*[name()='operation'][@name='" + operation + "']/*[name()='fault']/@wsam:Action";
        return expr;
    }

    private String getFaultExpression(String operation, String exception) {
        String expr = "/*[name()='definitions']/*[name()='portType']/*[name()='operation'][@name='" + operation + "']/*[name()='fault'][@name='"+exception+"']/@wsam:Action";
        return expr;
    }
}
