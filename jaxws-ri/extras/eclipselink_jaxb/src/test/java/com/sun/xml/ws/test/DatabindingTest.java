/*
 * Copyright (c) 2012, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.test;

import jakarta.jws.WebService;
import javax.xml.namespace.QName;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;

import com.oracle.webservices.api.databinding.Databinding;
import com.oracle.webservices.api.databinding.DatabindingFactory;
import com.oracle.webservices.api.databinding.WSDLGenerator;
import com.oracle.webservices.api.message.MessageContext;
import com.oracle.webservices.api.message.MessageContextFactory;

import com.sun.xml.ws.base.InVmWSDLResolver;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.message.saaj.SAAJMessage;

import junit.framework.TestCase;
import org.junit.Assert;

public class DatabindingTest extends TestCase {
    @WebService
    public static class Hello1 {
        public String echo(String str) { return str; }
    }
    @WebService
    public static interface Hello2 {
        public String echo(String str);
    }
    public void testWsdlGenHello() throws Exception {
        DatabindingFactory fac = DatabindingFactory.newInstance();
//        {
//        Databinding.Builder builder = fac.createBuilder(null, Hello1.class);
//        WSDLGenerator wsdlgen = builder.createWSDLGenerator();
//        wsdlgen.inlineSchema(true);
//        InVmWSDLResolver res = new InVmWSDLResolver();
//        wsdlgen.generate(res);
////        res.print();
//        Assert.assertEquals(1, res.getAll().size());
//        }
        //TODO serviceName and portName
        {
        Databinding.Builder builder = fac.createBuilder(Hello2.class, null);
        builder.targetNamespace("mytns");
        builder.serviceName(new QName("mytns", "myservice"));
        WSDLGenerator wsdlgen = builder.createWSDLGenerator();
        wsdlgen.inlineSchema(true);
        InVmWSDLResolver res = new InVmWSDLResolver();
        wsdlgen.generate(res);
        res.print();
        Assert.assertEquals(1, res.getAll().size());
        }
    }
    
    static class MySaajFac extends com.sun.xml.ws.api.message.saaj.SAAJFactory {
       boolean called = false;
       public SAAJMessage readAsSAAJ(Packet packet) throws SOAPException {
         called = true;
         return super.readAsSAAJ(packet);
       }
    }    
    public void testMessageContextFactory() throws Exception {
      DatabindingFactory fac = DatabindingFactory.newInstance();
      Databinding.Builder builder = fac.createBuilder(Hello2.class, null);
      builder.targetNamespace("mytns");
      builder.serviceName(new QName("mytns", "myservice"));
      MessageContextFactory mcf = MessageContextFactory.createFactory();
      MySaajFac saajFac = new MySaajFac();
      mcf.setSAAJFactory(saajFac);
      builder.property("com.sun.xml.ws.api.message.MessageContextFactory", mcf);
      com.oracle.webservices.api.databinding.Databinding db = builder.build();
        Assert.assertSame(((com.sun.xml.ws.api.databinding.Databinding) db).getMessageContextFactory(), mcf);
      Class[] paramType = {String.class};
      Object[] params = { "echoResponse" };
      com.oracle.webservices.api.databinding.JavaCallInfo call = 
          db.createJavaCallInfo(Hello2.class.getMethod("echo", paramType), params); 
      call.setReturnValue("echoResponse");
      MessageContext mc = db.serializeResponse(call);
      SOAPMessage soap = mc.getAsSOAPMessage();
      Assert.assertTrue(saajFac.called);
    }
}
