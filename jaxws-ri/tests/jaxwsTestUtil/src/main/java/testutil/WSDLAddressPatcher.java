/*
 * Copyright (c) 2018, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package testutil;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Patches the endpoint address of WSDL.
 *
 * UGLY UGLY UGLY!
 *
 * @author Kohsuke Kawaguchi
 */
public class WSDLAddressPatcher extends Task {
    private String address;
    private File wsdl;

    /**
     * New address to be written into WSDL.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * WSDL file to be patched.
     */
    public void setWsdl(File wsdl) {
        this.wsdl = wsdl;
    }

    public void execute() throws BuildException {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(wsdl);
            patch(null, doc.getDocumentElement());
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(wsdl));
            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | SAXException | TransformerException |IOException e) {
            throw new BuildException(e);
        }
    }

    /**
     *
     * @param portName
     *      If this is inside &lt;wsdl:port>, the port name.
     *      Otherwise null
     * @param e
     *      Element that we are visiting.
     */
    private void patch(String portName, Element e) {

        if ("http://schemas.xmlsoap.org/wsdl/".equals(e.getNamespaceURI())
                && "port".equals(e.getLocalName())) {
            portName = e.getAttribute("name");
        }

        if ("address".equals(e.getLocalName())) {
            // patch
            String loc = e.getAttribute("location");
            if (loc != null) {
                // TODO: whether we should convert '\' to '/' should be given by an option
                e.setAttribute("location", address.replace('\\', '/').replace("#PORTNAME#", portName));
            }
        }

        //Patch wsa:Address in wsa:EndpointReference as well
        NodeList nl = e.getElementsByTagNameNS("http://www.w3.org/2005/08/addressing", "EndpointReference");
        Element wsaEprEl = nl.getLength() > 0 ? (Element) nl.item(0) : null;
        if (wsaEprEl != null) {
            nl = e.getElementsByTagNameNS("http://www.w3.org/2005/08/addressing", "Address");
            Element wsaAddrEl = nl.getLength() > 0 ? (Element) nl.item(0) : null;
            if (wsaAddrEl != null) {
                wsaAddrEl.setTextContent(address.replace('\\', '/').replace("#PORTNAME#", portName));
            }
        }

        NodeList children = e.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (Element.class.isAssignableFrom(child.getClass())) {
                patch(portName, (Element) child);
            }
        }

    }

}
