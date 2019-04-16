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
 * MappingServerConfig.java
 *
 */
package testutil;

import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This class is called from ant to create mapping-server.xml,
 * which is used for consuming mapping file with gen:server
 * option.
 */

public class MappingServerConfig {

    /**
     * Must pass in files config-server.xml, mapping file to be consumed
     * and the location to save newly created mapping-server.xml file. 
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args == null || args.length < 3) {
            System.err.println(
                "ERROR: need args: config-server.xml,\n"
                    + "mapping file and\n temp dir to save mapping-server.xml");
            return;
        }
        try {
            String serverConfig = args[0];
            String mappingFile = args[1];
            String mappingConfig = args[2] + "mapping-server.xml";
            String tempdir = args[2];

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();

            // get wsdl file names from config-server.xml
            Document doc = builder.parse(serverConfig);
            Element serviceElement =
                (Element) doc.getElementsByTagName("service").item(0);

            String wsdlName = "";
            if (serviceElement == null) {
                Element wsdlElement =
                    (Element) doc.getElementsByTagName("wsdl").item(0);
                String wsdlLocation = wsdlElement.getAttribute("location");
                wsdlName =
                    wsdlLocation.substring(
                        wsdlLocation.lastIndexOf("/"),
                        wsdlLocation.length());
            } else {
                wsdlName = serviceElement.getAttribute("name") + ".wsdl";
            }

            String mappingConfigString =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<configuration xmlns=\"http://java.sun.com/xml/ns/jax-rpc/ri/config\">\n"
                    + "<j2eeMappingFile location=\""
                    + tempdir
                    + mappingFile
                    + "\" "
                    + "wsdlLocation=\""
                    + tempdir
                    + wsdlName
                    + "\"/>\n"
                    + "</configuration>";

            FileOutputStream out;
            PrintStream ps;
            out = new FileOutputStream(mappingConfig);
            ps = new PrintStream(out);
            ps.println(mappingConfigString);
            ps.close();
        } catch (Exception e) {
            System.err.println("exception in JaxrpcRiRuntimeConfigCreator:");
            e.printStackTrace();
        }
    }
}
