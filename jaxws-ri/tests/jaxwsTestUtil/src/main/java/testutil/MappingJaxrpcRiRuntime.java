/*
 * Copyright (c) 2004, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/*
 * MappingJaxrpcRiRuntime.java
 *
 */
package testutil;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This class is called from ant to create jaxrpc-ri-runtime.xml
 * from jaxrpc-ri.xml. It adds url-pattern and WSDL location to 
 * endpoint element so that Client when run locally can use this 
 * WSDL location and url-pattern for stub generation. 
 * This class puts the information needed by LocalConfigTransformer
 * class to run it locally.
 */

public class MappingJaxrpcRiRuntime {

    /**
     * Must pass in files jaxrpc-ri.xml, config-server.xml
     * and the location to save newly created jaxrpc-ri-runtime.xml file. 
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args == null || args.length < 3) {
            System.err.println(
                "ERROR: need args: jaxrpc-ri.xml,\n"
                    + "config-server.xml,\ntemp dir");
            return;
        }
        try {
            String jaxrpcri = args[0];
            String serverConfig = args[1];
            String newConfig = args[2] + "jaxrpc-ri-runtime.xml";

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();

            Document newdoc = builder.newDocument();
            Element root = (Element) newdoc.createElement("endpoints");
            newdoc.appendChild(root);
            root.setAttribute(
                "xmlns",
                "http://java.sun.com/xml/ns/jax-rpc/ri/runtime");
            root.setAttribute("version", "1.0");

            Comment comment =
                newdoc.createComment(
                    "This xml file is not created by WsDeploy and is just hand-coded from jaxrpc-ri.xml"
                        + " to run cleints locally");

            root.appendChild(comment);

            // get wsdl file names from jaxrpc-ri-runtime.xml
            Document doc = builder.parse(jaxrpcri);
            Element endpointElement =
                (Element) doc.getElementsByTagName("endpoint").item(0);

            Attr wsdlAttr = endpointElement.getAttributeNode("wsdl");

            if (wsdlAttr == null) {
                // starting from SEI, so no wsdl attribute
                // Add wsdl attribute by getting service name from server-config.xml

                Document doc1 = builder.parse(serverConfig);
                Element serviceElement =
                    (Element) doc1.getElementsByTagName("service").item(0);
                String serviceName = serviceElement.getAttribute("name");
                String wsdlLocation = "/WEB-INF/" + serviceName + ".wsdl";
                endpointElement.setAttribute("wsdl", wsdlLocation);
            }

            Element webServicesElement =
                (Element) doc.getElementsByTagName("webServices").item(0);
            Element endpointMappingElement =
                (Element) webServicesElement.getElementsByTagName(
                    "endpointMapping").item(
                    0);
            String urlPatternAttr =
                endpointMappingElement.getAttribute("urlPattern");
            endpointElement.setAttribute("urlpattern", urlPatternAttr);

            Node endpointNode = newdoc.importNode(endpointElement, true);
            root.appendChild(endpointNode);

            // save file
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(
                new DOMSource(newdoc),
                new StreamResult(newConfig));

        } catch (Exception e) {
            System.err.println("exception in JaxrpcRiRuntimeConfigCreator:");
            e.printStackTrace();
        }
    }
}
