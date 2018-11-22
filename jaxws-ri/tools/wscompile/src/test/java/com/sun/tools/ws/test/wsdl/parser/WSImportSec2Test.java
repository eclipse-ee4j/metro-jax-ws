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
//NOTE: this test requires -Dcom.sun.xml.ws.disableXmlSecurity=true
public class WSImportSec2Test extends WSImportSecTestBase {

    /**
     * Scenario for reading local file contents
     */
    public void testDisableXmlSecSysParam1() throws Exception {
        runTests(new TestParameters(new String[]{"-Xdebug", WSImportSec2Test.class.getResource("wsimport_1.wsdl").toExternalForm()},
                FILE_NOT_FOUND_MSG, SECURE_PROCESSING_ON_MSG, "XXE Scenario 1 WITHOUT secure processing set by system param:"));
    }

    /**
     * Scenario for URL connection
     */
    public void testDisableXmlSecSysParam2() throws Exception {
        runTests(new TestParameters(new String[]{"-Xdebug", WSImportSec2Test.class.getResource("wsimport_2.wsdl").toExternalForm()},
                BASIC_WSIMPORT_TOOL_MSG, SECURE_PROCESSING_ON_MSG,
                "XXE Scenario 2 WITHOUT secure processing set by system param", TIMEOUT));
    }

    /**
     * Scenario for FTP connection timeout
     */
    public void testDisableXmlSecSysParam3() throws Exception {
        runTests(new TestParameters(new String[]{"-Xdebug", WSImportSec2Test.class.getResource("wsimport_3.wsdl").toExternalForm()},
                BASIC_WSIMPORT_TOOL_MSG, SECURE_PROCESSING_ON_MSG,
                "XXE Scenario 3 WITHOUT secure processing set by system param", TIMEOUT));
    }

    /**
     * Scenario for recursive DTD Entity expansion
     */
    public void testDisableXmlSecSysParam4() throws Exception {
        runTests(new TestParameters(new String[]{"-Xdebug", WSImportSec2Test.class.getResource("wsimport_4.wsdl").toExternalForm()},
                RECURSIVE_ENTITY_REFERENCE_MSG, SECURE_PROCESSING_ON_MSG, "XEE Scenario 4 WITHOUT secure processing set by system param"));
    }

    /**
     * Scenario for exceeding JDK limit of entity expansions.
     */
    public void testDisableXmlSecSysParam5() throws Exception {
        runTests(new TestParameters(new String[]{"-Xdebug", WSImportSec2Test.class.getResource("wsimport_5.wsdl").toExternalForm()},
                ENTITY_EXPANSION_LIMIT_MSG, SECURE_PROCESSING_ON_MSG, "XEE Scenario 5 WITHOUT secure processing set by system param"));
    }

}
