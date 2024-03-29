/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.parser;

import com.sun.tools.ws.wsdl.document.WSDLConstants;
import com.sun.tools.ws.wsdl.document.jaxws.JAXWSBindingsConstants;
import com.sun.tools.ws.wsdl.document.schema.SchemaConstants;
import com.sun.tools.xjc.util.DOMUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.XMLFilterImpl;

/**
 * @author Vivek Pandey
 */
public class WSDLInternalizationLogic implements InternalizationLogic{

    /**
     * Default constructor.
     */
    public WSDLInternalizationLogic() {}

    /**
     * This filter looks for {@code <xs:import>} and {@code <xs:include>}
     * and parses those documents referenced by them.
     */
    private static final class ReferenceFinder extends AbstractReferenceFinderImpl {
        ReferenceFinder( DOMForest parent) {
            super(parent);
        }

        @Override
        protected String findExternalResource( String nsURI, String localName, Attributes atts) {
            if(WSDLConstants.NS_WSDL.equals(nsURI) && "import".equals(localName)){
//                if(parent.isExtensionMode()){
//                    //TODO: add support for importing schema using wsdl:import
//                }
                return atts.getValue("location");
            }

            // We don't need to do this anymore, JAXB handles the schema imports, includes etc., but this is useful for the clientJar option in
            // fetching  the imported schemas to package in the jar..
            if (parent.options.clientjar != null) {
                if (SchemaConstants.NS_XSD.equals(nsURI) && "import".equals(localName)) {
                    return atts.getValue("schemaLocation");
                }
            }
            return null;
        }
    }
    @Override
    public XMLFilterImpl createExternalReferenceFinder(DOMForest parent) {
        return new ReferenceFinder(parent);
    }

    @Override
    public boolean checkIfValidTargetNode(DOMForest parent, Element bindings, Element target) {
        return false;
    }

    @Override
    public Element refineSchemaTarget(Element target) {
        // look for existing xs:annotation
        Element annotation = DOMUtils.getFirstChildElement(target, Constants.NS_XSD, "annotation");
        if(annotation==null)
            // none exists. need to make one
            annotation = insertXMLSchemaElement( target, "annotation" );

        // then look for appinfo
        Element appinfo = DOMUtils.getFirstChildElement(annotation, Constants.NS_XSD, "appinfo" );
        if(appinfo==null)
            // none exists. need to make one
            appinfo = insertXMLSchemaElement( annotation, "appinfo" );

        return appinfo;
        
    }

    @Override
    public Element refineWSDLTarget(Element target){
        // look for existing xs:annotation
        Element JAXWSBindings = DOMUtils.getFirstChildElement(target, JAXWSBindingsConstants.NS_JAXWS_BINDINGS, "bindings");
        if(JAXWSBindings==null)
            // none exists. need to make one
            JAXWSBindings = insertJAXWSBindingsElement(target, "bindings" );
        return JAXWSBindings;
    }

    private Element insertJAXWSBindingsElement( Element parent, String localName ) {
        String qname = "JAXWS:"+localName;

        Element child = parent.getOwnerDocument().createElementNS(JAXWSBindingsConstants.NS_JAXWS_BINDINGS, qname );

        NodeList children = parent.getChildNodes();

        if( children.getLength()==0 )
            parent.appendChild(child);
        else
            parent.insertBefore( child, children.item(0) );

        return child;
    }
    

    /**
     * Creates a new XML Schema element of the given local name
     * and insert it as the first child of the given parent node.
     *
     * @return
     *      Newly create element.
     */
    private Element insertXMLSchemaElement( Element parent, String localName ) {
        // use the same prefix as the parent node to avoid modifying
        // the namespace binding.
        String qname = parent.getTagName();
        int idx = qname.indexOf(':');
        if(idx==-1)     qname = localName;
        else            qname = qname.substring(0,idx+1)+localName;

        Element child = parent.getOwnerDocument().createElementNS( Constants.NS_XSD, qname );

        NodeList children = parent.getChildNodes();

        if( children.getLength()==0 )
            parent.appendChild(child);
        else
            parent.insertBefore( child, children.item(0) );

        return child;
    }

}
