/*
 * Copyright (c) 2011, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.parser;


/**
*
* @author zheng.jun.li@oracle.com
*/
public class WSImportSecTest extends WSImportSecTestBase{

    public void testXmlSecSysParam() throws Exception{
    	 /**
         * Scenario for reading local file contents
        */
    	runTests( new TestParameters(new String[] {"-Xdebug", "./src/test/resources/com/sun/tools/ws/wsdl/parser/wsimport_1.wsdl"},
                            SECURE_PROCESSING_ON_MSG, FILE_NOT_FOUND_MSG, "XXE Scenario 1 WITH secure processing:"));
    	runTests( new TestParameters(new String[] {"-Xdebug", "-disableXmlSecurity", "./src/test/resources/com/sun/tools/ws/wsdl/parser/wsimport_1.wsdl"},
                            FILE_NOT_FOUND_MSG, SECURE_PROCESSING_ON_MSG, "XXE Scenario 1 WITHOUT secure processing:"));
        /**
         * Scenario for URL connection
        */
    	runTests( new TestParameters(new String[] {"-Xdebug", "./src/test/resources/com/sun/tools/ws/wsdl/parser/wsimport_2.wsdl"},
                            SECURE_PROCESSING_ON_MSG, "XXE Scenario 2 WITH secure processing:"));
    	runTests( new TestParameters(new String[] {"-Xdebug", "-disableXmlSecurity", "./src/test/resources/com/sun/tools/ws/wsdl/parser/wsimport_2.wsdl"},
                            BASIC_WSIMPORT_TOOL_MSG, SECURE_PROCESSING_ON_MSG,
                            "XXE Scenario 2 WITHOUT secure processing:", TIMEOUT));

        /**
         * Scenario for FTP connection timeout
        */
    	runTests( new TestParameters(new String[]{"-Xdebug", "./src/test/resources/com/sun/tools/ws/wsdl/parser/wsimport_3.wsdl"},
                            SECURE_PROCESSING_ON_MSG, "XXE Scenario 3 WITH secure processing:"));
    	runTests( new TestParameters(new String[]{"-Xdebug", "-disableXmlSecurity", "./src/test/resources/com/sun/tools/ws/wsdl/parser/wsimport_3.wsdl"},
                            BASIC_WSIMPORT_TOOL_MSG, SECURE_PROCESSING_ON_MSG, "XXE Scenario 3 WITHOUT secure processing:", TIMEOUT));
        /**
         * Scenario for recursive DTD Entity expansion
        */
    	runTests( new TestParameters(new String[] {"-Xdebug", "./src/test/resources/com/sun/tools/ws/wsdl/parser/wsimport_4.wsdl"},
                            SECURE_PROCESSING_ON_MSG, RECURSIVE_ENTITY_REFERENCE_MSG, "XEE Scenario 4 WITH secure processing:"));
    	runTests( new TestParameters(new String[] {"-Xdebug", "-disableXmlSecurity", "./src/test/resources/com/sun/tools/ws/wsdl/parser/wsimport_4.wsdl"},
                            RECURSIVE_ENTITY_REFERENCE_MSG, SECURE_PROCESSING_ON_MSG, "XEE Scenario 4 WITHOUT secure processing:"));

        /**
         * Scenario for exceeding JDK limit of entity expansions.
        */
    	runTests( new TestParameters(new String[] {"-Xdebug", "./src/test/resources/com/sun/tools/ws/wsdl/parser/wsimport_5.wsdl"},
                            SECURE_PROCESSING_ON_MSG, ENTITY_EXPANSION_LIMIT_MSG, "XEE Scenario 5 WITH secure processing:"));
    	runTests( new TestParameters(new String[] {"-Xdebug", "-disableXmlSecurity", "./src/test/resources/com/sun/tools/ws/wsdl/parser/wsimport_5.wsdl"},
                            ENTITY_EXPANSION_LIMIT_MSG, SECURE_PROCESSING_ON_MSG, "XEE Scenario 5 WITHOUT secure processing:"));
    }

}
