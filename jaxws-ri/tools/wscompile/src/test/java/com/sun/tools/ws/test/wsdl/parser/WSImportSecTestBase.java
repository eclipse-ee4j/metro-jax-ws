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

import com.sun.tools.ws.wscompile.WsimportTool;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

/**
*
* @author zheng.jun.li@oracle.com
*/
public abstract class WSImportSecTestBase extends TestCase{

    protected static final String DISABLE_XML_SEC_SYS_PARAM = "com.sun.xml.ws.disableXmlSecurity";
    // Error messages that can be observed during the testing
    protected static final String BASIC_WSIMPORT_TOOL_MSG = "parsing WSDL...";
    protected static final String SECURE_PROCESSING_ON_MSG = "[ERROR] DOCTYPE is disallowed when the feature";
    protected static final String FILE_NOT_FOUND_MSG = "java.io.FileNotFoundException:";
    protected static final String RECURSIVE_ENTITY_REFERENCE_MSG = "Recursive entity reference \"foo\". (Reference path: foo -> bar -> foo)";
    protected static final String ENTITY_EXPANSION_LIMIT = "1024";
    protected static final String ENTITY_EXPANSION_LIMIT_MSG = "JAXP00010001: The parser has encountered more than \"" + ENTITY_EXPANSION_LIMIT + "\" entity expansions in this document; this is the limit imposed by the JDK";
    
    protected final static int PARAM_CHECK = 0;
    protected final static int TIMEOUT = 1;
	
	
	 public void runTests(TestParameters parameters) throws Exception {
	        try {
	            if (parameters.scenarioName.startsWith("XEE Scenario 5")) {
	                //Set limit for entity expansion to avoid unnecessary cpu heat and 120sec test timeout
	                System.setProperty("entityExpansionLimit", ENTITY_EXPANSION_LIMIT);
	            }

	            System.out.println("RUNNING " + parameters.scenarioName);
	            parameters.createTestInstance().test();
	            System.out.println(parameters.scenarioName + " PASSED");
	        } finally {
	            System.clearProperty("entityExpansionLimit");
	        }

	    }

    /**
     * Basic test for WSImport, runs tool and search for expected / unexpected messages.
     */
	 
     class WSImportTest {

        protected final String[] toolArgs;

        protected String expectedMessage;

        protected String unexpectedMessage;

        public WSImportTest(String[] toolArgs, String expectedMessage, String unexpectedMessage) {
            this.toolArgs = toolArgs;
            this.expectedMessage = expectedMessage;
            this.unexpectedMessage = unexpectedMessage;
        }

        public void test() throws Exception {

            System.out.println( "Launching wsimport tool with the following args:");
            for(String arg:toolArgs) {
                System.out.println("\t"+arg);
            }

            // Prepare stderr stream for tool output
            ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
            PrintStream out = new PrintStream(baOutputStream);
            System.setErr(out);

            // Launch wsimport tool
            WsimportTool wsimportTool = new WsimportTool(out);
            runWSImport(wsimportTool);
            out.flush();

            // Check tool output
            String toolOutput = baOutputStream.toString();
            checkError(toolOutput);

            // Close streams
            baOutputStream.close();
            out.close();

        }

        protected void error(String error, String toolOutput) {
            System.out.println("===== tool output start =====");
            System.out.println(toolOutput);
            System.out.println("===== tool output end =====");
            fail(error);
        }

        protected void runWSImport(WsimportTool tool) throws Exception {
            tool.run(toolArgs);
        }

        // Check wsimport tool output for the expected errors
        protected void checkError(String output) {
            int index = output.indexOf(expectedMessage);
            if (index == -1) {
                error("Didn't get expected ERROR: [" + expectedMessage +"]", output);
            }

            //Operations opening URL / FTP connection may have different output based on host setup
            if (unexpectedMessage != null) {
                int indexUnexpectedErr = output.indexOf(unexpectedMessage);
                if (indexUnexpectedErr != -1) {
                    error("Unexpected ERROR was observed: [" + unexpectedMessage + "]", output);
                }
            }
        }
    }

    /**
     * Test for scenarios causing tool hang when remote connection is open.
     * Tests there is no timeout when security is ENABLED.
     * Kills tool after small timeout when security is DISABLED.
     */
    
     class WSImportTimeoutTest extends WSImportTest {

        private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

        public WSImportTimeoutTest(String[] toolArgs, String expectedMessage, String unexpectedMessage) {
            super(toolArgs, expectedMessage, unexpectedMessage);
        }

        @Override
        protected void runWSImport(final WsimportTool tool) throws Exception {
            final Future<?> wsImportTask = executorService.submit(new Runnable() {
                @Override
                public void run() {
                    tool.run(toolArgs);
                }
            });
            executorService.schedule(new Runnable() {
                @Override
                public void run() {
                    if (!wsImportTask.isDone()) {
                        wsImportTask.cancel(true);
                    }
                }
            }, 1000, TimeUnit.MILLISECONDS);

            executorService.awaitTermination(2000, TimeUnit.MILLISECONDS);
        }

    }

     class TestParameters {
        final String [] toolArgs;
        final String expMsg;
        final String unexpMsg;
        final String scenarioName;
        final int type;

        TestParameters(String[] toolArgs, String expMsg, String unexpMsg, String scenarioName) {
            this(toolArgs, expMsg, unexpMsg, scenarioName, PARAM_CHECK);
            Objects.requireNonNull(unexpMsg);
        }

        TestParameters(String[] toolArgs, String expMsg, String scenarioName) {
            this(toolArgs, expMsg, null, scenarioName, TIMEOUT);
        }

        TestParameters(String[] toolArgs, String expMsg, String unexpMsg, String scenarioName, int testType) {
            this.toolArgs = toolArgs;
            this.expMsg = expMsg;
            this.unexpMsg = unexpMsg;
            this.scenarioName = scenarioName;
            this.type = testType;
        }

        WSImportTest createTestInstance() {
            switch (type) {
                case PARAM_CHECK:
                    return new WSImportTest(toolArgs, expMsg, unexpMsg);
                case TIMEOUT:
                    return new WSImportTimeoutTest(toolArgs, expMsg, unexpMsg);
                    default:
                        throw new IllegalStateException();
            }
        }
    }

}
