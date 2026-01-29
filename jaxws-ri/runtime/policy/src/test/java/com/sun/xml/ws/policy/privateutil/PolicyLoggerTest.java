/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.privateutil;

import com.sun.xml.ws.policy.PolicyException;

import java.util.logging.Level;

import junit.framework.TestCase;

/**
 *
 * @author Marek Potociar
 */
public class PolicyLoggerTest extends TestCase {
    private PolicyLogger instance;
    
    public PolicyLoggerTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        instance = PolicyLogger.getLogger(PolicyLoggerTest.class);
    }
    
    @Override
    protected void tearDown() throws Exception {
    }
    
    /**
     * Test of getLogger method, of class com.sun.xml.ws.policy.privateutil.PolicyLogger.
     */
    public void testGetLogger() {
        PolicyLogger result = PolicyLogger.getLogger(PolicyLoggerTest.class);
        assertNotNull(result);
        
        try {
            PolicyLogger.getLogger(null);
            fail("NullPointerException expected");
        } catch (NullPointerException e) { /* ok */ }
    }
    
    /**
     * Test of log method, of class com.sun.xml.ws.policy.privateutil.PolicyLogger.
     */
    public void testLog() {
        Level level = Level.FINEST;
        String message = "Test";
        
        instance.log(level, message);
    }
    
    /**
     * Test of finest method, of class com.sun.xml.ws.policy.privateutil.PolicyLogger.
     */
    public void testFinest() {
        String message = "Test";
        
        instance.finest(message);
    }
    
    /**
     * Test of finer method, of class com.sun.xml.ws.policy.privateutil.PolicyLogger.
     */
    public void testFiner() {
        String message = "Test";
        
        instance.finer(message);
    }
    
    /**
     * Test of fine method, of class com.sun.xml.ws.policy.privateutil.PolicyLogger.
     */
    public void testFine() {
        String message = "Test";
        
        instance.fine(message);
    }
    
    /**
     * Test of info method, of class com.sun.xml.ws.policy.privateutil.PolicyLogger.
     */
    public void testInfo() {
        String message = "Test";
        
        instance.info(message);
    }
    
    /**
     * Test of config method, of class com.sun.xml.ws.policy.privateutil.PolicyLogger.
     */
    public void testConfig() {
        String message = "Test";
        
        instance.config(message);
    }
    
    /**
     * Test of warning method, of class com.sun.xml.ws.policy.privateutil.PolicyLogger.
     */
    public void testWarning() {
        String message = "Test";
        
        instance.warning(message);
    }
    
    /**
     * Test of severe method, of class com.sun.xml.ws.policy.privateutil.PolicyLogger.
     */
    public void testSevere() {
        String message = "Test";

        instance.severe(message);
    }
    
    /**
     * Test of isMethodCallLoggable method, of class com.sun.xml.ws.policy.privateutil.PolicyLogger.
     */
    public void testIsMethodCallLoggable() {
        boolean expResult = true;
        instance.setLevel(Level.FINEST);
        boolean result = instance.isMethodCallLoggable();
        assertEquals(expResult, result);

        expResult = false;
        instance.setLevel(Level.FINER);
        result = instance.isMethodCallLoggable();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of isLoggable method, of class com.sun.xml.ws.policy.privateutil.PolicyLogger.
     */
    public void testIsLoggable() {
        boolean expResult, result;
        
        instance.setLevel(Level.ALL);
        expResult = true;
        result = instance.isLoggable(Level.FINEST);
        assertEquals(expResult, result);
        result = instance.isLoggable(Level.FINER);
        assertEquals(expResult, result);
        result = instance.isLoggable(Level.FINE);
        assertEquals(expResult, result);
        result = instance.isLoggable(Level.CONFIG);
        assertEquals(expResult, result);
        result = instance.isLoggable(Level.INFO);
        assertEquals(expResult, result);
        result = instance.isLoggable(Level.WARNING);
        assertEquals(expResult, result);
        result = instance.isLoggable(Level.SEVERE);
        assertEquals(expResult, result);
        result = instance.isLoggable(Level.ALL);
        assertEquals(expResult, result);

        instance.setLevel(Level.OFF);
        expResult = false;
        result = instance.isLoggable(Level.FINEST);
        assertEquals(expResult, result);
        result = instance.isLoggable(Level.FINER);
        assertEquals(expResult, result);
        result = instance.isLoggable(Level.FINE);
        assertEquals(expResult, result);
        result = instance.isLoggable(Level.CONFIG);
        assertEquals(expResult, result);
        result = instance.isLoggable(Level.INFO);
        assertEquals(expResult, result);
        result = instance.isLoggable(Level.WARNING);
        assertEquals(expResult, result);
        result = instance.isLoggable(Level.SEVERE);
        assertEquals(expResult, result);
        result = instance.isLoggable(Level.ALL);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of setLevel method, of class com.sun.xml.ws.policy.privateutil.PolicyLogger.
     */
    public void testSetLevel() {
        instance.setLevel(Level.FINE);        
        assertFalse(instance.isLoggable(Level.FINER));
        assertTrue(instance.isLoggable(Level.INFO));
    }

    /**
     * Test of entering method, of class com.sun.xml.ws.policy.privateutil.PolicyLogger.
     */
    public void testEntering() {
        instance.entering();
    }
    
    /**
     * Test of exiting method, of class com.sun.xml.ws.policy.privateutil.PolicyLogger.
     */
    public void testExiting() {
        instance.exiting();
    }
    
    /**
     * Test of createAndLogException method, of class com.sun.xml.ws.policy.privateutil.PolicyUtils.Commons.
     */
    public void testCommonsCreateAndLogException() {
        PolicyLogger logger = PolicyLogger.getLogger(PolicyLoggerTest.class);
        Throwable cause, result;
        String message;
        
        cause = new Exception();
        message = "Test message.";
        result = logger.logSevereException(new IllegalArgumentException(message), cause);
        assertEquals(message, result.getMessage());
        assertEquals(cause, result.getCause());
        assertEquals("testCommonsCreateAndLogException", result.getStackTrace()[0].getMethodName());
        
        message = "Test message.";
        result = logger.logSevereException(new NullPointerException(message), cause);
        assertEquals(message, result.getMessage());
        assertEquals(cause, result.getCause());
        assertEquals("testCommonsCreateAndLogException", result.getStackTrace()[0].getMethodName());
        
        message = null;
        result = logger.logSevereException(new PolicyException(message), true);
        assertEquals(message, result.getMessage());
        assertNull(result.getCause());
        assertEquals("testCommonsCreateAndLogException", result.getStackTrace()[0].getMethodName());
        
        message = "Test message.";
        result = logger.logSevereException(new PolicyException(message), false);
        assertEquals(message, result.getMessage());
        assertNull(result.getCause());
        assertEquals("testCommonsCreateAndLogException", result.getStackTrace()[0].getMethodName());
        
        cause = new NullPointerException("test");
        message = null;
        result = logger.logSevereException(new PolicyException(message, cause), true);
        assertEquals(message, result.getMessage());
        assertEquals(cause, result.getCause());
        assertEquals("testCommonsCreateAndLogException", result.getStackTrace()[0].getMethodName());
        
        cause = new NullPointerException("test");
        message = "Test message.";
        result = logger.logSevereException(new PolicyException(message, cause), false);
        assertEquals(message, result.getMessage());
        assertEquals(cause, result.getCause());
        assertEquals("testCommonsCreateAndLogException", result.getStackTrace()[0].getMethodName());
        
        message = null;
        result = logger.logSevereException(new PolicyException(message));
        assertEquals(message, result.getMessage());
        assertNull(result.getCause());
        assertEquals("testCommonsCreateAndLogException", result.getStackTrace()[0].getMethodName());
        
        message = "Test message.";
        result = logger.logSevereException(new PolicyException(message));
        assertEquals(message, result.getMessage());
        assertNull(result.getCause());
        assertEquals("testCommonsCreateAndLogException", result.getStackTrace()[0].getMethodName());
        
        cause = new NullPointerException("test");
        message = null;
        result = logger.logSevereException(new PolicyException(message, cause));
        assertEquals(message, result.getMessage());
        assertEquals(cause, result.getCause());
        assertEquals("testCommonsCreateAndLogException", result.getStackTrace()[0].getMethodName());
        
        cause = new NullPointerException("test");
        message = "Test message.";
        result = logger.logSevereException(new PolicyException(message, cause));
        assertEquals(message, result.getMessage());
        assertEquals(cause, result.getCause());
        assertEquals("testCommonsCreateAndLogException", result.getStackTrace()[0].getMethodName());
    }
}
