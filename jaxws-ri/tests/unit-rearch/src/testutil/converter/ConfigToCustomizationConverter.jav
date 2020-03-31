/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package testutil.converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Properties;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import testutil.converter.config.Configuration;
import testutil.converter.config.WsdlType;
import testutil.converter.custom.BindingsType;
import testutil.converter.custom.PackageType;
import testutil.converter.custom.JAXRPCPackageType;
import testutil.converter.custom.SchemaBindings;

import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPath;

import javax.xml.namespace.QName;
import java.util.List;

import static jakarta.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;

import com.sun.xml.bind.IXmlElementImpl;

/**
 * @author JAX-RPC Development Team
 */
public class ConfigToCustomizationConverter {
    File dir;
    
    private String FS = System.getProperty("file.separator");
    private String configClient = FS + "config" + FS + "config-client.xml";
    private String configServer = FS+ "config" + FS + "config-server.xml";
    private String customClientName = "custom-client.xml";
    private String customServerName = "custom-server.xml";
    private String customClient = FS + "config" + FS + customClientName;
    private String customServer = FS + "config" + FS + customServerName;
    private String buildProperties = FS + "config" + FS + "build.properties";
    
    public static void main(String[] args) {
        for (int i=0; i<args.length; i++)
            new ConfigToCustomizationConverter(args[i]);
    }
    
    public ConfigToCustomizationConverter(String dirName) {
        dir = new File(dirName);
        System.out.println("Processing \"" + dirName + "\" ...");
        if (!dir.isDirectory())
            throw new IllegalArgumentException(dirName + " must be a directory");
        convert();
    }
    
    protected void convert() {
        try {
            Properties props = readProperties();
            Configuration configuration = readConfig(configClient);
            convertToCustom(configuration, customClient, props.getProperty("client.features"));
            configuration = readConfig(configServer);
            updateProperties(configuration);
            convertToCustom(configuration, customServer, props.getProperty("server.features"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    protected Properties readProperties() throws Exception {
        FileInputStream fis = new FileInputStream(new File(dir + buildProperties));
        Properties props = new Properties();
        props.load(fis);
        
        return props;
    }
    
    protected Configuration readConfig(String configFile) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(testutil.converter.config.ObjectFactory.class);
        Unmarshaller u = jc.createUnmarshaller();
        Configuration configuration = (Configuration)u.unmarshal(new File(dir + configFile));
        
        return configuration;
    }
    
    protected void convertToCustom(Configuration configuration, String customFile, String features) throws Exception {
        WsdlType wsdlType = configuration.getWsdl();
        
        testutil.converter.custom.ObjectFactory of = new testutil.converter.custom.ObjectFactory();
        BindingsType bindingsType = new BindingsType();
        IXmlElementImpl<BindingsType> bindings = of.createBindings(bindingsType);

        bindingsType.setWsdlLocation(wsdlType.getLocation());

        if (features.contains("explicitcontext"))
            bindingsType.setEnableAdditionalSOAPHeaderMapping(Boolean.TRUE);
        
//        XPathFactory xpf = XPathFactory.newInstance();
//        XPath xpath = xpf.newXPath();
//        FileInputStream wsdlStream = new FileInputStream(wsdlType.getLocation());
//        String qname = xpath.evaluate("//definitions/types/schema", wsdlStream);
//        System.out.println(qname);
        
        List<BindingsType> childBindings = bindingsType.getBindings();
        BindingsType definitionsBindings = new BindingsType();
        definitionsBindings.setNode(new QName("http://schemas.xmlsoap.org/wsdl/", "definitions"));
        JAXRPCPackageType packageType = of.createJAXRPCPackageType();
        packageType.setName(wsdlType.getPackageName());
        definitionsBindings.setPackage(packageType);
        childBindings.add(definitionsBindings);

        BindingsType jaxbBindings = new BindingsType();
        childBindings.add(jaxbBindings);
        List<SchemaBindings> schemaBindingsList = jaxbBindings.getSchemaBindings();
        SchemaBindings schemaBindings = of.createSchemaBindings();
        PackageType jaxbPackageType = of.createPackageType();
        jaxbPackageType.setName(wsdlType.getPackageName());
        schemaBindings.setPackage(jaxbPackageType);
        schemaBindingsList.add(schemaBindings);
//        jaxbBindings.setNode(new QName("foobar", ""));
        
        OutputStream os = new FileOutputStream(dir + customFile);
        JAXBContext jc = JAXBContext.newInstance(testutil.converter.custom.ObjectFactory.class);
        Marshaller m = jc.createMarshaller();
        m.setProperty(JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(bindings, os);
        os.close();
    }
    
    protected void updateProperties(Configuration configuration) throws Exception {
        String wsdlLocation = configuration.getWsdl().getLocation();

        FileOutputStream fos = new FileOutputStream(new File(dir + buildProperties), true);
        PrintWriter writer = new PrintWriter(fos);
        writer.println();
        writer.println("wsdlname=" + wsdlLocation);
        writer.println();
        String basedir = wsdlLocation.substring(0, wsdlLocation.lastIndexOf("/"));
        writer.println("client.jaxrpc.binding=" + basedir + "/" + customClientName);
        writer.println("server.jaxrpc.binding=" + basedir + "/" + customServerName);
        writer.println("client.jaxb.binding=");
        writer.println("server.jaxb.binding=");
        writer.close();
    }
}

