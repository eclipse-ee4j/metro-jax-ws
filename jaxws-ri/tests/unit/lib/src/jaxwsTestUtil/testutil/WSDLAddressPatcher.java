/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
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
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.DocumentException;
import org.dom4j.QName;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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
            Document doc = new SAXReader().read(this.wsdl);
            patch(null,doc.getRootElement());
            new XMLWriter(new FileOutputStream(wsdl)).write(doc);
        } catch (DocumentException e) {
            throw new BuildException(e);
        } catch (IOException e) {
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

        if(e.getNamespaceURI().equals("http://schemas.xmlsoap.org/wsdl/")
        && e.getName().equals("port"))
            portName = e.attributeValue("name",portName);

        if(e.getName().equals("address")) {
            // patch
            String loc = e.attributeValue("location");
            if(loc!=null) {
                // TODO: whether we should convert '\' to '/' should be given by an option
                e.addAttribute("location",address.replace('\\','/').replace("#PORTNAME#",portName));
            }
        }

        //Patch wsa:Address in wsa:EndpointReference as well
        Element wsaEprEl = e.element(QName.get("EndpointReference", "wsa", "http://www.w3.org/2005/08/addressing"));
        if(wsaEprEl != null) {
            Element wsaAddrEl = wsaEprEl.element(QName.get("Address", "wsa", "http://www.w3.org/2005/08/addressing"));
            wsaAddrEl.setText(address.replace('\\','/').replace("#PORTNAME#",portName));

        }

        for( Element c : (List<Element>)e.elements() )
            patch(portName, c);
    }

}
