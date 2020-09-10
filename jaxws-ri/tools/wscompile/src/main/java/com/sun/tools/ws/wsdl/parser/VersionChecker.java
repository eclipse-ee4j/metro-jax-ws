/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.parser;

import com.sun.tools.ws.resources.WsdlMessages;
import com.sun.tools.ws.wsdl.document.jaxws.JAXWSBindingsConstants;
import org.xml.sax.*;
import org.xml.sax.helpers.LocatorImpl;
import org.xml.sax.helpers.XMLFilterImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Checks the jaxb:version attribute on a XML Schema document.
 *
 * jaxws:version is optional, if absent its value is assumed to be "3.0" and if present its value must be
 * "3.0" or more.
 *
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 *     Vivek Pandey
 */
public class VersionChecker extends XMLFilterImpl {

    /**
     * We store the value of the version attribute in this variable
     * when we hit the root element.
     */
    private String version = null ;

    /** Will be set to true once we hit the root element. */
    private boolean seenRoot = false;

    /** Will be set to true once we hit a binding declaration. */
    private boolean seenBindings = false;

    private Locator locator;

    /**
     * Stores the location of the start tag of the root tag.
     */
    private Locator rootTagStart;

    public VersionChecker( XMLReader parent ) {
        setParent(parent);
    }

    public VersionChecker( ContentHandler handler, ErrorHandler eh, EntityResolver er ) {
        setContentHandler(handler);
        if(eh!=null)    setErrorHandler(eh);
        if(er!=null)    setEntityResolver(er);
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
        throws SAXException {

        super.startElement(namespaceURI, localName, qName, atts);

        if(!seenRoot) {
            // if this is the root element
            seenRoot = true;
            rootTagStart = new LocatorImpl(locator);

            version = atts.getValue(JAXWSBindingsConstants.NS_JAXWS_BINDINGS,"version");
            if( namespaceURI.equals(JAXWSBindingsConstants.NS_JAXWS_BINDINGS) ) {
                String version2 = atts.getValue("","version");
                if( version!=null && version2!=null ) {
                    // we have both @version and @jaxb:version. error.
                    SAXParseException e = new SAXParseException(
                        WsdlMessages.INTERNALIZER_TWO_VERSION_ATTRIBUTES(), locator);
                    getErrorHandler().error(e);
                }
                //According to JAXWS 2.0 spec, if version attribute is missing its assumed to be "3.0"
                if( version==null)
                    version = (version2!=null)?version2:"3.0";
            }

        }

        if( JAXWSBindingsConstants.NS_JAXWS_BINDINGS.equals(namespaceURI)){
            seenBindings = true;
            if(version == null)
                version = "3.0";
        }

    }

    public void endDocument() throws SAXException {
        super.endDocument();

        if( seenBindings && version==null ) {
            // if we see a binding declaration but not version attribute
            SAXParseException e = new SAXParseException(WsdlMessages.INTERNALIZER_VERSION_NOT_PRESENT(), rootTagStart);
            getErrorHandler().error(e);
        }

        // if present, the value must be >= 3.0
        if( version!=null && !VERSIONS.contains(version) ) {
            SAXParseException e = new SAXParseException(WsdlMessages.INTERNALIZER_INCORRECT_VERSION(), rootTagStart);
            getErrorHandler().error(e);
        }
    }

    public void setDocumentLocator(Locator locator) {
        super.setDocumentLocator(locator);
        this.locator = locator;
    }

    private static final Set<String> VERSIONS = new HashSet<String>(Arrays.asList("3.0"));

}

