/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.wsam_epr_interop.client;

import com.sun.xml.ws.api.addressing.WSEndpointReference;
import com.sun.xml.ws.api.server.Container;
import com.sun.xml.ws.api.wsdl.parser.WSDLParserExtension;
import com.sun.xml.ws.api.model.wsdl.WSDLModel;
import com.sun.xml.ws.util.xml.XmlUtil;
import com.sun.xml.ws.util.ServiceFinder;
import com.sun.xml.ws.wsdl.parser.RuntimeWSDLParser;
import junit.framework.TestCase;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.EndpointReference;
import javax.xml.namespace.QName;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * @author Rama Pulavarthi
 */

public class WSAM_EPRTester extends TestCase {
    private final XMLOutputFactory staxOut;

    public WSAM_EPRTester(String name) {
        super(name);
        this.staxOut = XMLOutputFactory.newInstance();
        staxOut.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
    }
    public void testWSAM_EPR_Interop() throws Exception {
        System.out.println("****************************************");
        System.out.println("Web Services Addressing 1.0 - Metadata");
        System.out.println("Section 2.1 - Interop Tests");
        System.out.println("Sun Microsystems Report - " + new Date());
        System.out.println("****************************************\n");
        runMandatoryEPRTests();
        runOptionalEPRTests();
    }


    private void runMandatoryEPRTests() throws Exception {
        URL res = getClass().getClassLoader().getResource("epr/wsamTest.wsdl");
        File folder = new File(new File(res.getFile()).getParentFile(), "mandatory");   // assuming that this is a file:// URL.
        System.out.println("\n\nMandatory Tests:\n");
        for (File f : folder.listFiles()) {
            if (!f.getName().endsWith(".xml"))
                continue;
            System.out.println("***************************");
            System.out.println("TestFile: " + f.getParentFile().getName() + "/" + f.getName());
            try {
                InputStream is = new FileInputStream(f);
                StreamSource s = new StreamSource(is);
                EndpointReference epr = EndpointReference.readFrom(s);
                WSEndpointReference wsepr = new WSEndpointReference(epr);
                System.out.println("Address: " + wsepr.getAddress());
                WSEndpointReference.Metadata metadata = wsepr.getMetaData();
                System.out.println("Metadata Valid?: true");
                if (metadata.getPortTypeName() != null)
                    System.out.println("InterfaceName: " + metadata.getPortTypeName());
                if (metadata.getServiceName() != null)
                    System.out.println("ServiceName: " + metadata.getServiceName());
                if (metadata.getPortName() != null)
                    System.out.println("Endpoint: " + metadata.getPortName().getLocalPart());
                String wsdliLocation = metadata.getWsdliLocation();
                if (metadata.getWsdliLocation() != null) {
                    System.out.println("wsdli:wsdlLocation: " + wsdliLocation);
                    String wsdlLocation = wsdliLocation.substring(wsdliLocation.lastIndexOf(" "));
                    WSDLModel wsdlModel = RuntimeWSDLParser.parse(new URL(wsdlLocation),
                            new StreamSource(wsdlLocation),
                            XmlUtil.createDefaultCatalogResolver(),
                            false, Container.NONE, ServiceFinder.find(WSDLParserExtension.class).toArray());
                    QName binding = wsdlModel.getBinding(metadata.getServiceName(), metadata.getPortName()).getName();
                    System.out.println("Binding from WSDL: " + binding);

                }
                System.out.println("");
            } catch (Exception e) {
                System.out.println("Metadata Valid?: false");
                System.out.println(e.getMessage());
//                e.printStackTrace();
            }
        }
    }

    private void runOptionalEPRTests() throws Exception {
        URL res = getClass().getClassLoader().getResource("epr/wsamTest.wsdl");
        File folder = new File(new File(res.getFile()).getParentFile(), "optional");   // assuming that this is a file:// URL.
        System.out.println("\n\nOptional Tests:\n");
        for (File f : folder.listFiles()) {
            if (!f.getName().endsWith(".xml"))
                continue;
            System.out.println("***************************");
            System.out.println("TestFile: " + f.getParentFile().getName() + "/" + f.getName());
            try {
                InputStream is = new FileInputStream(f);
                StreamSource s = new StreamSource(is);
                EndpointReference epr = EndpointReference.readFrom(s);
                WSEndpointReference wsepr = new WSEndpointReference(epr);
                System.out.println("Address: " + wsepr.getAddress());
                WSEndpointReference.Metadata metadata = wsepr.getMetaData();
                System.out.println("Metadata Valid?: true");
                if (metadata.getPortTypeName() != null)
                    System.out.println("InterfaceName: " + metadata.getPortTypeName());
                if (metadata.getServiceName() != null)
                    System.out.println("ServiceName: " + metadata.getServiceName());
                if (metadata.getPortName() != null)
                    System.out.println("Endpoint: " + metadata.getPortName().getLocalPart());
                String wsdliLocation = metadata.getWsdliLocation();
                if (metadata.getWsdliLocation() != null) {
                    System.out.println("wsdli:wsdlLocation: " + wsdliLocation);
                    String wsdlLocation = wsdliLocation.substring(wsdliLocation.lastIndexOf(" "));
                    WSDLModel wsdlModel = RuntimeWSDLParser.parse(new URL(wsdlLocation),
                            new StreamSource(wsdlLocation),
                            XmlUtil.createDefaultCatalogResolver(),
                            false, Container.NONE, ServiceFinder.find(WSDLParserExtension.class).toArray());
                    QName binding = wsdlModel.getBinding(metadata.getServiceName(), metadata.getPortName()).getName();
                    System.out.println("Binding from WSDL: " + binding);

                }
                System.out.println("");
            } catch (Exception e) {
                System.out.println("Metadata Valid?: false");
                System.out.println("Reason: "+ e.getMessage());
//                e.printStackTrace();
                System.out.println("");
            }
        }
    }

    public void xtestEPR() throws Exception {
        URL res = getClass().getClassLoader().getResource("epr/mandatory/epr3.xml");
        InputStream is = res.openStream();
        StreamSource s = new StreamSource(is);
        EndpointReference epr = EndpointReference.readFrom(s);
        WSEndpointReference wsepr = new WSEndpointReference(epr);
        WSEndpointReference.Metadata metadata = wsepr.getMetaData();
        System.out.println(metadata.getPortName());
        System.out.println(metadata.getServiceName());
        System.out.println(metadata.getPortTypeName());

    }
}
