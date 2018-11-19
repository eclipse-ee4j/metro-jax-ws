/*
 * Copyright (c) 2011, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package com.sun.tools.ws.test.wsdl.parser;

/**
 *
 * @author zheng.jun.li@oracle.com
 */
public class WSImportSecTest extends WSImportSecTestBase {

    /**
     * Scenario for reading local file contents
     */
    public void testXmlSecSysParam1() throws Exception {
        runTests(new TestParameters(new String[]{"-Xdebug", WSImportSecTest.class.getResource("wsimport_1.wsdl").toExternalForm()},
                SECURE_PROCESSING_ON_MSG, FILE_NOT_FOUND_MSG, "XXE Scenario 1 WITH secure processing:"));
        runTests(new TestParameters(new String[]{"-Xdebug", "-disableXmlSecurity", WSImportSecTest.class.getResource("wsimport_1.wsdl").toExternalForm()},
                FILE_NOT_FOUND_MSG, SECURE_PROCESSING_ON_MSG, "XXE Scenario 1 WITHOUT secure processing:"));
    }

    /**
     * Scenario for URL connection
     */
    public void testXmlSecSysParam2() throws Exception {
        runTests(new TestParameters(new String[]{"-Xdebug", WSImportSecTest.class.getResource("wsimport_2.wsdl").toExternalForm()},
                SECURE_PROCESSING_ON_MSG, "XXE Scenario 2 WITH secure processing:"));
        runTests(new TestParameters(new String[]{"-Xdebug", "-disableXmlSecurity", WSImportSecTest.class.getResource("wsimport_2.wsdl").toExternalForm()},
                BASIC_WSIMPORT_TOOL_MSG, SECURE_PROCESSING_ON_MSG,
                "XXE Scenario 2 WITHOUT secure processing:", TIMEOUT));
    }

    /**
     * Scenario for FTP connection timeout
     */
    public void testXmlSecSysParam3() throws Exception {
        runTests(new TestParameters(new String[]{"-Xdebug", WSImportSecTest.class.getResource("wsimport_3.wsdl").toExternalForm()},
                SECURE_PROCESSING_ON_MSG, "XXE Scenario 3 WITH secure processing:"));
        runTests(new TestParameters(new String[]{"-Xdebug", "-disableXmlSecurity", WSImportSecTest.class.getResource("wsimport_3.wsdl").toExternalForm()},
                BASIC_WSIMPORT_TOOL_MSG, SECURE_PROCESSING_ON_MSG, "XXE Scenario 3 WITHOUT secure processing:", TIMEOUT));
    }

    /**
     * Scenario for recursive DTD Entity expansion
     */
    public void testXmlSecSysParam4() throws Exception {
        runTests(new TestParameters(new String[]{"-Xdebug", WSImportSecTest.class.getResource("wsimport_4.wsdl").toExternalForm()},
                SECURE_PROCESSING_ON_MSG, RECURSIVE_ENTITY_REFERENCE_MSG, "XEE Scenario 4 WITH secure processing:"));
        runTests(new TestParameters(new String[]{"-Xdebug", "-disableXmlSecurity", WSImportSecTest.class.getResource("wsimport_4.wsdl").toExternalForm()},
                RECURSIVE_ENTITY_REFERENCE_MSG, SECURE_PROCESSING_ON_MSG, "XEE Scenario 4 WITHOUT secure processing:"));
    }

    /**
     * Scenario for exceeding JDK limit of entity expansions.
     */
    public void testXmlSecSysParam5() throws Exception {
        runTests(new TestParameters(new String[]{"-Xdebug", WSImportSecTest.class.getResource("wsimport_5.wsdl").toExternalForm()},
                SECURE_PROCESSING_ON_MSG, ENTITY_EXPANSION_LIMIT_MSG, "XEE Scenario 5 WITH secure processing:"));
        runTests(new TestParameters(new String[]{"-Xdebug", "-disableXmlSecurity", WSImportSecTest.class.getResource("wsimport_5.wsdl").toExternalForm()},
                ENTITY_EXPANSION_LIMIT_MSG, SECURE_PROCESSING_ON_MSG, "XEE Scenario 5 WITHOUT secure processing:"));
    }

}
