/*
 * Copyright (c) 2012, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.eclipselink;

import static javax.jws.soap.SOAPBinding.Style.RPC;
import static javax.jws.soap.SOAPBinding.Use.LITERAL;

import java.lang.reflect.Method;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import javax.xml.ws.WebServiceFeature;

import junit.framework.TestCase;

import com.oracle.webservices.api.databinding.Databinding;
import com.oracle.webservices.api.databinding.DatabindingFactory;
import com.oracle.webservices.api.databinding.DatabindingMode;
import com.oracle.webservices.api.databinding.DatabindingModeFeature;
import com.oracle.webservices.api.databinding.JavaCallInfo;

import org.w3c.dom.Node;

import com.oracle.webservices.api.message.MessageContext;
import com.sun.xml.ws.api.databinding.DatabindingConfig;
import com.sun.xml.ws.api.databinding.MappingInfo;
import com.sun.xml.ws.model.RuntimeModeler;

public class WrapperNSTest extends TestCase {

    @WebService(targetNamespace = "http://echo.org/")
    @SOAPBinding(style = RPC, use = LITERAL)
    static public interface MyHelloRPC {
        public String echoString(String str);
    }

    @WebService(targetNamespace = "http://echo.org/")
    @DatabindingMode(value = "eclipselink.jaxb")
    static public class MyHelloClass {
        @ResponseWrapper(className = "response1", localName="gigi")
        @RequestWrapper(className = "request1")
        public String echoString(String str) {return "Hello " + str; }
    }
    
    public void testWrapperNS() throws Exception {
        Class<?> sei = MyHelloRPC.class;
        DatabindingFactory fac = DatabindingFactory.newInstance();
        Databinding.Builder b = fac.createBuilder(sei, null);
        DatabindingModeFeature dbf = new DatabindingModeFeature(
                "eclipselink.jaxb");
        WebServiceFeature[] f = { dbf };
        b.feature(f);
        b.serviceName(new QName("http://echo.org/", "helloService"));
        b.portName(new QName("http://echo.org/", "helloPort"));
        Databinding db = b.build();

        {
            Method method = findMethod(sei, "echoString");
            Object[] args = { "test" };
            JavaCallInfo call = db.createJavaCallInfo(method, args);
            MessageContext mc = db.serializeRequest(call);
            SOAPMessage msg = mc.getSOAPMessage();
            // System.out.println("------------------ eclipselink");
            // msg.writeTo(System.out);
            // System.out.println();

            Node n = msg.getSOAPBody().getChildNodes().item(0);
            // System.out.println("num of attributes is: "+
            // n.getAttributes().getLength());
            assertTrue(n.getAttributes().getLength() == 1);
        }

    }
    
    public void testGenerateWsdl() throws Exception {
        Class<?> sei = MyHelloRPC.class;
        DatabindingFactory fac = DatabindingFactory.newInstance();
        Databinding.Builder b = fac.createBuilder(sei, null);
        DatabindingModeFeature dbf = new DatabindingModeFeature(
                "eclipselink.jaxb");
        WebServiceFeature[] f = { dbf };
		DatabindingConfig config = new DatabindingConfig();
		config.setFeatures(f);
		config.setEndpointClass(MyHelloClass.class);
		MappingInfo mi = new MappingInfo();
		mi.setServiceName(new QName("http://echo.org/", "helloService"));
		mi.setPortName(new QName("http://echo.org/", "helloPort"));
		config.setMappingInfo(mi);
		config.setClassLoader(this.getClass().getClassLoader());
		
		RuntimeModeler rtModeler = new RuntimeModeler(config);
		rtModeler.buildRuntimeModel();
    	
    }

    public void testWrapperNS_JAXBRI() throws Exception {
        Class<?> sei = MyHelloRPC.class;
        DatabindingFactory fac = DatabindingFactory.newInstance();
        Databinding.Builder b = fac.createBuilder(sei, null);
        DatabindingModeFeature dbf = new DatabindingModeFeature(
                "glassfish.jaxb");
        WebServiceFeature[] f = { dbf };
        b.feature(f);
        b.serviceName(new QName("http://echo.org/", "helloService"));
        b.portName(new QName("http://echo.org/", "helloPort"));
        Databinding db = b.build();

        {

            Method method = findMethod(sei, "echoString");
            Object[] args = { "test" };
            JavaCallInfo call = db.createJavaCallInfo(method, args);
            MessageContext mc = db.serializeRequest(call);
            SOAPMessage msg = mc.getSOAPMessage();
            // System.out.println("------------------ glassfish");
            // msg.writeTo(System.out);
            // System.out.println();
            Node n = msg.getSOAPBody().getChildNodes().item(0);
            // System.out.println("num of attributes is: "+
            // n.getAttributes().getLength());
            assertTrue(n.getAttributes().getLength() == 1);
        }

    }

    static public Method findMethod(Class<?> c, String name) {
        for (Method m : c.getMethods()) {
            if (m.getName().equals(name)) {
                return m;
            }
        }
        return null;
    }
}
