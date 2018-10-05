/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.parser;

import com.sun.istack.SAXParseException2;
import com.sun.tools.ws.resources.WsdlMessages;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * XMLFilter that finds references to other schema files from
 * SAX events.
 *
 * This implementation is a base implementation for typical case
 * where we just need to look for a particular attribute which
 * contains an URL to another schema file.
 *
 * @author
 *  Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 *  Vivek Pandey
 */
public abstract class AbstractReferenceFinderImpl extends XMLFilterImpl {
    protected final DOMForest parent;

    protected AbstractReferenceFinderImpl( DOMForest _parent ) {
        this.parent = _parent;
    }

    /**
     * IF the given element contains a reference to an external resource,
     * return its URL.
     *
     * @param nsURI
     *      Namespace URI of the current element
     * @param localName
     *      Local name of the current element
     * @return
     *      It's OK to return a relative URL.
     */
    protected abstract String findExternalResource( String nsURI, String localName, Attributes atts);

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
        throws SAXException {
        super.startElement(namespaceURI, localName, qName, atts);

        String relativeRef = findExternalResource(namespaceURI,localName,atts);
        if(relativeRef==null)   return; // non found

        try {
            // absolutize URL.
            assert locator != null;
            String lsi = locator.getSystemId();
            String ref;
            if (lsi.startsWith("jar:")) {
                    int bangIdx = lsi.indexOf('!');
                    if (bangIdx > 0) {
                            ref = new URL(new URL(lsi), relativeRef).toString();
                    } else
                            ref = relativeRef;
            } else
                    ref = new URI(lsi).resolve(new URI(relativeRef)).toString();

            // then parse this schema as well,
            // but don't mark this document as a root.
            parent.parse(ref,false);
        } catch( URISyntaxException e ) {
            SAXParseException spe = new SAXParseException2(
                    WsdlMessages.ABSTRACT_REFERENCE_FINDER_IMPL_UNABLE_TO_PARSE(relativeRef,e.getMessage()),
                locator, e );

            fatalError(spe);
            throw spe;
        } catch( IOException e ) {
            SAXParseException spe = new SAXParseException2(
                WsdlMessages.ABSTRACT_REFERENCE_FINDER_IMPL_UNABLE_TO_PARSE(relativeRef,e.getMessage()),
                locator, e );

            fatalError(spe);
            throw spe;
        }
    }

    private Locator locator;

    @Override
    public void setDocumentLocator(Locator locator) {
        super.setDocumentLocator(locator);
        this.locator = locator;
    }
}
