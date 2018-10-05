/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.runtimemodeler.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.xml.namespace.QName;

import com.sun.xml.ws.model.RuntimeModeler;

/**
 * @author WS Development Team
 */
public class WhiteBoxTester extends TestCase {

    public WhiteBoxTester(String name) {
        super(name);
    }

    private static final String NS="http://client.runtimemodeler.whitebox/";

    public void testRuntimeModeler() {
        System.out.println("testing RuntimeModeler");
//        System.out.println("serviceName: " + RuntimeModeler.getServiceName(whitebox.runtimemodeler.client.RpcLitEndpoint.class));
        String packageName = "com.example.ws";
        assertTrue(RuntimeModeler.getNamespace("") == null);
        assertTrue(RuntimeModeler.getNamespace(packageName).equals("http://ws.example.com/"));
        packageName = "this.is.a.test.of.the.package.to.namespace.util";
        assertTrue(RuntimeModeler.getNamespace(packageName).equals("http://util.namespace.to.package.the.of.test.a.is.this/"));        

        packageName = RpcLitEndpoint.class.getPackage().getName();
        String ns = RuntimeModeler.getNamespace(packageName);
        System.out.println(ns);
        assertTrue(ns.equals(NS));
        assertTrue(RuntimeModeler.getServiceName(DefaultRpcLitEndpoint.class).equals(
                   new QName(NS, "DefaultRpcLitEndpointService")));
        assertTrue(RuntimeModeler.getPortName(DefaultRpcLitEndpoint.class, null).equals(
                   new QName(NS, "DefaultRpcLitEndpointPort")));
        assertTrue(RuntimeModeler.getPortTypeName(DefaultRpcLitEndpoint.class).equals(
                   new QName(NS, "DefaultRpcLitEndpoint")));


        assertTrue(RuntimeModeler.getServiceName(EndpointNoPortName.class).equals(
                   new QName(NS, "EndpointNoPortNameService")));
        assertTrue(RuntimeModeler.getPortName(EndpointNoPortName.class, null).equals(
                   new QName(NS, "RpcLitPort")));
        assertTrue(RuntimeModeler.getPortTypeName(EndpointNoPortName.class).equals(
                   new QName(NS, "RpcLit")));
        
        
        assertTrue(RuntimeModeler.getServiceName(RpcLitEndpoint.class).equals(
                   new QName("http://echo.org/", "RpcLitEndpoint")));
        assertTrue(RuntimeModeler.getPortName(RpcLitEndpoint.class, null).equals(
                   new QName("http://echo.org/", "RpcLitPort")));
        assertTrue(RuntimeModeler.getPortTypeName(RpcLitEndpoint.class).equals(
                   new QName("http://echo.org/", "RpcLit")));
        
        
        
//        RuntimeModeler modeler = new RuntimeModeler(whitebox.runtimemodeler.client.RpcLitEndpoint.class, null, null);
     
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(WhiteBoxTester.class);
        return suite;
    }
}

