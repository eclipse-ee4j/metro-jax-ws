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
 * LocalConfigFileTransformer.java
 *
 */
package testutil;

import java.io.*;
import java.util.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 * This class is called from ant to transform
 * a regular config.xml file so that it looks
 * for the wsdl file in a local location instead
 * of getting it through http.
 */
public class LocalConfigFileTransformer {
    
    /**
     * Must pass in location of orginal file and
     * location to save new file to. 
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args == null || args.length < 3) {
            System.err.println("ERROR: need args: old config file location,\n" +
                "new (generated) config,\ntemp dir");
            return;
        }
        try {
            String oldConfig = args[0];
            String newConfig = args[2] + args[1];
            String tempdir = args[2];
            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();

            // get wsdl file names from sun-jaxws.xml
            Document doc = builder.parse(tempdir + "WEB-INF/sun-jaxws.xml");
            Map endpointMap = buildMap(doc.getElementsByTagName("endpoint"));

            // make change in config file
            doc = builder.parse(oldConfig);
            Element wsdlElement = (Element) doc.getElementsByTagName("wsdl").item(0);
            Attr wsdlAttr = wsdlElement.getAttributeNode("location");
            String location = wsdlAttr.getValue();
            String urlpattern = location.substring(
                location.lastIndexOf("/"),
                location.lastIndexOf("?"));
            String newLocation = tempdir + endpointMap.get(urlpattern);
            wsdlAttr.setValue(newLocation);
            
            // save file
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(new DOMSource(doc),
                new StreamResult(newConfig));
            
            
        } catch (Exception e) {
            System.err.println("exception in LocalConfigFileTransformer:");
            e.printStackTrace();
        }
    }
    
    /*
     * Make sure that the element was found. It will be null
     * when there is a problem with the jaxrpc-ri file.
     */
    private static void checkEndpoint(Element endpoint) {
        if (endpoint == null) {
            System.err.println("\nLocalConfigFileTransformer could not " +
                "find \"endpoint\" element in sun-jaxws.xml file.\n" +
                "Please check file and verify that it was generated correctly.\n");
            throw new RuntimeException("Cannot process sun-jaxws.xml file");
        }
    }

    private static Map buildMap(NodeList nodeList) throws Exception {
        Map map = new HashMap(nodeList.getLength());
        Element endpoint = null;
        for (int i=0; i<nodeList.getLength(); i++) {
            endpoint = (Element) nodeList.item(i);
            checkEndpoint(endpoint);
            String urlpattern = endpoint.getAttributeNode("urlpattern").getValue();
            String wsdl = endpoint.getAttributeNode("wsdl").getValue();
            
            // remove the leading "/" from "/WEB-INF/filename.wsdl
            map.put(urlpattern, wsdl.substring(1, wsdl.length()));
        }
        return map;
    }
    
}
    
