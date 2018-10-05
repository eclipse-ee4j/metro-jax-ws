/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromjava.default_action.client;

import java.io.InputStream;
import java.io.IOException;
import java.net.URL;

import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;

import junit.framework.TestCase;
import org.xml.sax.InputSource;

/**
 * @author Rama Pulavarthi
 */
public class AddNumbersClient extends TestCase {

    public AddNumbersClient(String name) {
        super(name);
    }

    private String evaluateXpath(String expr) throws XPathExpressionException, IOException {
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        xpath.setNamespaceContext(new NSContextImpl());
        URL res = AddNumbersClient.class.getResource("/AddNumbers.wsdl");
        System.out.println("res = " + res);
        InputStream stream = AddNumbersClient.class.getResourceAsStream("/AddNumbers.wsdl");
        return xpath.evaluate(expr, new InputSource(stream));
    }

    private void checkMessageAction(String method, String msg, String expected) throws XPathExpressionException, IOException {
        String expr = "/*[name()='definitions']/*[name()='portType']/*[name()='operation'][@name='" + method + "']/*[name()='" + msg + "']/@wsaw:Action";
        Object result = evaluateXpath(expr);
        assertNotNull(result);
        assertEquals(expected, result);
    }

    //No @Action, so default action must be generated
    public void testaddNumbersNoAction() throws Exception {
        checkMessageAction("addNumbersNoAction", "input", "http://foobar.org/AddNumbers/addNumbersNoActionRequest");
    }

    //@Action(input=""), so default action must be generated
    public void testaddNumbersEmptyAction() throws Exception {
        checkMessageAction("addNumbersEmptyAction", "input", "http://foobar.org/AddNumbers/addNumbersEmptyActionRequest");
    }

    //@WebMethod(action="..."), so action must equal explicit value.
    public void testaddNumbers() throws Exception {
        checkMessageAction("addNumbers", "input", "http://example.com/input");
    }

    //explicit @Action, so action must equal explicit value.
    public void testaddNumbers2() throws Exception {
        checkMessageAction("addNumbers2", "input", "http://example.com/input2");
    }

    //@WebMethod(action=""), since empty String generate default action.
    public void testaddNumbers3() throws Exception {
        checkMessageAction("addNumbers3", "input", "http://foobar.org/AddNumbers/addNumbers3Request");
    }

    //@Oneway with no @Action, so generate default action for oneway input message
    public void testonewayNumbers() throws Exception {
        checkMessageAction("onewayNumbers", "input", "http://foobar.org/AddNumbers/onewayNumbers");
    }

}
