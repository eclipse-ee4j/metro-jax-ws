/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.test.wsdl.parser;

import com.sun.tools.ws.processor.modeler.wsdl.ConsoleErrorReporter;
import com.sun.tools.ws.wscompile.AbortException;
import com.sun.tools.ws.wscompile.ErrorReceiverFilter;
import com.sun.tools.ws.wscompile.WsimportListener;
import com.sun.tools.ws.wscompile.WsimportOptions;
import com.sun.tools.ws.wsdl.document.WSDLDocument;
import com.sun.tools.ws.wsdl.parser.InternalizationLogic;
import com.sun.tools.ws.wsdl.parser.WSDLParser;

import java.net.URL;

import junit.framework.TestCase;

import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Fabian Ritzmann
 */
public class WSDLParserTest extends TestCase {
    
    public WSDLParserTest(String testName) {
        super(testName);
    }

    public void testParseNull() throws Exception {
        try {
            final WSDLParser instance = new WSDLParser(null, null, null);
            instance.parse();
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    public void testParseEmpty() throws Exception {
        final ErrorReceiverFilter errorReceiver = new ErrorReceiverFilter();
        final InputSource source = getResourceSource("empty.wsdl");
        final WsimportOptions options = new WsimportOptions();
        options.addWSDL(source);
        final WSDLParser instance = new WSDLParser(options, errorReceiver, null);
        final WSDLDocument wsdl = instance.parse();
        assertNull(wsdl);
        assertTrue(errorReceiver.hadError());
    }

    // testJaxws994
    private class MyLocator implements org.xml.sax.Locator {
      boolean hasEntry;
      MyLocator(boolean hasEntry) {
        this.hasEntry = hasEntry;
      }
      public java.lang.String getPublicId() { return "id";}
      public java.lang.String getSystemId() { 
        if (hasEntry) {
          return "jar:file:my.jar!/wsdls/simple.wsdl";
        } else {
         return "jar:file:my.jar";
        }
      }
      public int getLineNumber() { return 0;}
      public int getColumnNumber() { return 0;}
    }
    private class MyFinder extends com.sun.tools.ws.wsdl.parser.AbstractReferenceFinderImpl {
      MyFinder(boolean hasEntry) { 
        super(new MyDOMForest(null,null, null, null, hasEntry)); 
      }
      protected String findExternalResource( String nsURI, String localName, org.xml.sax.Attributes atts) {
        return nsURI;
      }
    }
    private class MyDOMForest extends com.sun.tools.ws.wsdl.parser.DOMForest {
      boolean hasEntry;
      MyDOMForest(InternalizationLogic logic, org.xml.sax.EntityResolver entityResolver, WsimportOptions options, com.sun.tools.ws.wscompile.ErrorReceiver errReceiver, boolean hasEntry) {
       super(logic, entityResolver, options, errReceiver);
       this.hasEntry = hasEntry;
     }

     public org.w3c.dom.Document parse(String systemId, boolean root) throws org.xml.sax.SAXException, java.io.IOException {
       if (hasEntry) {
         assertEquals("fail to resolve jar:file resource", "jar:file:my.jar!/wsdls/simple.wsdl", systemId);
       } else {
         assertEquals("fail to resolve jar:file resource", "externalResource", systemId);
       }
       return null;
     }
    }
    public void testJaxws994() throws Exception {
      boolean hasEntry = false;
      MyLocator locator = new MyLocator(hasEntry);
      MyFinder finder = new MyFinder(hasEntry);
      finder.setDocumentLocator(locator);
      finder.startElement(null, null, null, null);

      hasEntry = true;
      locator = new MyLocator(hasEntry);
      finder = new MyFinder(hasEntry);
      finder.setDocumentLocator(locator);
      finder.startElement(null, null, null, null);
    }

    public void testParseSimpleSystemIdNull() throws Exception {
        final ErrorReceiverFilter errorReceiver = new ErrorReceiverFilter();
        final InputSource source = getResourceSource("simple.wsdl");
        source.setSystemId(null);
        final WsimportOptions options = new WsimportOptions();
        options.addWSDL(source);
        try {   
            final WSDLParser instance = new WSDLParser(options, errorReceiver, null);
            final WSDLDocument wsdl = instance.parse();
            fail("Expected IllegalArgumentException, instead got " + wsdl);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    public void testParseSimple() throws Exception {
        final ErrorReceiverFilter errorReceiver = createErrorReceiver();
        final InputSource source = getResourceSource("simple.wsdl");
        final WsimportOptions options = new WsimportOptions();
        options.addWSDL(source);
        final WSDLParser instance = new WSDLParser(options, errorReceiver, null);
        final WSDLDocument wsdl = instance.parse();
        assertNotNull(wsdl);
        assertFalse(errorReceiver.hadError());
    }

    public void testParsePolicy12() throws Exception {
        final ErrorReceiverFilter errorReceiver = createErrorReceiver();
        final InputSource source = getResourceSource("policy12.wsdl");
        final WsimportOptions options = new WsimportOptions();
        options.addWSDL(source);
        final WSDLParser instance = new WSDLParser(options, errorReceiver, null);
        final WSDLDocument wsdl = instance.parse();
        assertNotNull(wsdl);
        assertFalse(errorReceiver.hadError());
    }

    public void testParsePolicy15() throws Exception {
        final ErrorReceiverFilter errorReceiver = createErrorReceiver();
        final InputSource source = getResourceSource("policy15.wsdl");
        final WsimportOptions options = new WsimportOptions();
        options.addWSDL(source);
        final WSDLParser instance = new WSDLParser(options, errorReceiver, null);
        final WSDLDocument wsdl = instance.parse();
        assertNotNull(wsdl);
        assertFalse(errorReceiver.hadError());
    }

    public void testParseUsingPolicy() throws Exception {
        final ErrorReceiverFilter errorReceiver = createErrorReceiver();
        final InputSource source = getResourceSource("usingpolicy.wsdl");
        final WsimportOptions options = new WsimportOptions();
        options.addWSDL(source);
        final WSDLParser instance = new WSDLParser(options, errorReceiver, null);
        final WSDLDocument wsdl = instance.parse();
        assertNotNull(wsdl);
        assertFalse(errorReceiver.hadError());
    }

    public void testParseUsingPolicyRequired() throws Exception {
        final ErrorReceiverFilter errorReceiver = createErrorReceiver();
        final InputSource source = getResourceSource("usingpolicy-required.wsdl");
        final WsimportOptions options = new WsimportOptions();
        options.addWSDL(source);
        final WSDLParser instance = new WSDLParser(options, errorReceiver, null);
        final WSDLDocument wsdl = instance.parse();
        assertNotNull(wsdl);
        assertFalse(errorReceiver.hadError());
    }

    private static URL getResourceAsUrl(String resourceName) throws RuntimeException {
        URL input = WSDLParserTest.class.getResource(resourceName);
        if (input == null) {
            throw new RuntimeException("Failed to find resource \"" + resourceName + "\"");
        }
        return input;
    }

    private static InputSource getResourceSource(String resourceName) throws RuntimeException {
        // We go through the URL because that sets the system id of the InputSource.
        // The WSDLParser throws an exception if the system id is not set.
        return new InputSource(getResourceAsUrl(resourceName).toExternalForm());
    }

    private static ErrorReceiverFilter createErrorReceiver() {
        class Listener extends WsimportListener {

            ConsoleErrorReporter cer = new ConsoleErrorReporter(System.err);

            @Override
            public void generatedFile(String fileName) {
                message(fileName);
            }

            @Override
            public void message(String msg) {
                System.out.println(msg);
            }

            @Override
            public void error(SAXParseException exception) {
                cer.error(exception);
            }

            @Override
            public void fatalError(SAXParseException exception) {
                cer.fatalError(exception);
            }

            @Override
            public void warning(SAXParseException exception) {
                cer.warning(exception);
            }

            @Override
            public void debug(SAXParseException exception) {
                cer.debug(exception);
            }

            @Override
            public void info(SAXParseException exception) {
                cer.info(exception);
            }

            public void enableDebugging() {
                cer.enableDebugging();
            }
        }
        final Listener listener = new Listener();
        ErrorReceiverFilter errorReceiver = new ErrorReceiverFilter(listener) {

            public void info(SAXParseException exception) {
                super.info(exception);
            }

            public void warning(SAXParseException exception) {
                super.warning(exception);
            }

            @Override
            public void pollAbort() throws AbortException {
                if (listener.isCanceled()) {
                    throw new AbortException();
                }
            }

            @Override
            public void debug(SAXParseException exception) {
                listener.debug(exception);
            }
        };
        return errorReceiver;
    }

}
