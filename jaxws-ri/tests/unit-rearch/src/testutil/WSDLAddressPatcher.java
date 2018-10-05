/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
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
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Martin Grebac
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
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(this.wsdl);

            patch(doc.getDocumentElement());

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();

            DOMSource source = new DOMSource();
            StreamResult result = new StreamResult(this.wsdl);
            transformer.transform(source, result);
        } catch (Exception e) {
            throw new BuildException(e);
        }
    }

    /**
     * @param e Element that we are visiting.
     */
    private void patch(Element e) {

        NodeList ports = e.getElementsByTagNameNS("http://schemas.xmlsoap.org/wsdl/", "port");
        for (int i=0; i<ports.getLength(); i++) {
            Element portE = (Element) ports.item(i);
            String portName = portE.getAttribute("name");
            Element addrE = (Element) portE.getElementsByTagName("address").item(0);
            String location = addrE.getAttribute("location");
            if (location != null) {
                addrE.removeAttribute("location");
                addrE.setAttribute("location", address.replace('\\','/').replace("#PORTNAME#",portName));
            }
            
            NodeList eprs = e.getElementsByTagNameNS("http://www.w3.org/2005/08/addressing", "EndpointReference");
            Element eprE = (Element) ((eprs != null && eprs.getLength()>0) ? eprs.item(0) : null);
            if (eprE != null) {
                NodeList addresses = eprE.getElementsByTagNameNS("http://www.w3.org/2005/08/addressing", "Address");
                Element eprAddrE = (Element) ((addresses != null && addresses.getLength() > 0) ? addresses.item(0) : null);
                if (eprAddrE != null) {
                    eprAddrE.setTextContent(address.replace('\\', '/').replace("#PORTNAME#", portName));
                }
            }
        }
    }
}
