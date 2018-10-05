/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package epr.wsepr_8188172.client;

import com.sun.xml.ws.api.addressing.AddressingVersion;
import com.sun.xml.ws.api.addressing.WSEndpointReference;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 * @author lingling.guo@oracle.com
 */
public class WseprTest extends TestCase {
	
    private String nameSpaceURI = "http://wseprservice.org/wsdl";
    private QName serviceName = new QName(nameSpaceURI, "WseprService");
    private QName portName = new QName(nameSpaceURI, "WseprPort");
    private QName portTypeName = new QName(nameSpaceURI, "Wsepr");
    private String address = "http://wseprservice.org/Wsepr";
    private List<Element> referenceParameters = new ArrayList<Element>();
    
    public WseprTest(String name) {
    	super(name);
    }
    
    /**
     * Empty ReferenceParameters element should not exist in  EndpointReference element.
     * @throws Exception
     */
    public void testReferenceParameters() throws Exception {   	
    	WSEndpointReference wSEndpointReference = new WSEndpointReference(AddressingVersion.W3C, address, serviceName, portName, portTypeName, null, null, referenceParameters);
    	Node epr = string2Doc(wSEndpointReference.toString(), "wsa:ReferenceParameters");
    	validateEndpointReference(wSEndpointReference.toString());
    	assertNull("EndpointReference element should not contain empty ReferenceParameters element.",epr);  	    	   	   	
    }
    
    /**
     * Empty Metadata element should not exsit in EndpointReference element.
     * @throws Exception
     */
    public void testMetadataWithNullParams() throws Exception {   	
    	WSEndpointReference wSEndpointReference = new WSEndpointReference(AddressingVersion.W3C, address, null, null, null, null, null, referenceParameters);
    	Node md = string2Doc(wSEndpointReference.toString(), "wsa:Metadata");	
    	validateEndpointReference(wSEndpointReference.toString());
    	assertNull("EndpointReference element should not contain empty Metadata element.",md);
    }

    /**
     * Empty Metadata element should not exsit in EndpointReference element.
     * @throws Exception
     */
    public void testMetadataWithEmptyParams() throws Exception {
      WSEndpointReference wSEndpointReference = new WSEndpointReference(AddressingVersion.W3C, address, new QName("",""), new QName("",""), new QName("",""), null, "", referenceParameters);
      Node md = string2Doc(wSEndpointReference.toString(), "wsa:Metadata");
      validateEndpointReference(wSEndpointReference.toString());
      assertNull("EndpointReference element should not contain empty Metadata element.",md);
    }

    private Node string2Doc (String xml, String tagName) throws Exception {
    	DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();	    	
    	InputStream stream = new ByteArrayInputStream(xml.getBytes());    		    	
    	Document doc = db.parse(stream);	    	
    	NodeList nl = doc.getElementsByTagName(tagName);
    	if(nl == null || nl.getLength() == 0) {
    		return null;
    	}
    	return nl.item(0); 	    	
    }
    
    private void validateEndpointReference(String wsepr) throws Exception{    	
    	InputStream is = new ByteArrayInputStream(wsepr.getBytes());
    	WSEndpointReference wSEndpointReference = new WSEndpointReference(is, AddressingVersion.W3C);
    }
}
