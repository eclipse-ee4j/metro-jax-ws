/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.wsdl_rpclit_get_catalog.client;

import junit.framework.TestCase;
import testutil.ClientServerTestUtil;

import javax.xml.namespace.QName;
import java.math.BigDecimal;
import java.math.BigInteger;

public class TestApp extends TestCase{

    private static RetailerPortType stub;
    public TestApp(String name) throws Exception{
        super(name);
        RetailerService service = new RetailerService();
        stub = service.getRetailerPort();
        ClientServerTestUtil.setTransport(stub);
    }

    public void testGetCatalog() throws Exception {
        fromwsdl.wsdl_rpclit_get_catalog.client.CatalogItem[] cil = {new fromwsdl.wsdl_rpclit_get_catalog.client.ObjectFactory().createCatalogItem(), new fromwsdl.wsdl_rpclit_get_catalog.client.ObjectFactory().createCatalogItem()};
        cil[0].setName("JAXRPC SI 1.1.2");
        cil[0].setBrand("Sun Microsystems, Inc.");
        cil[0].setCategory("Web Services");
        cil[0].setProductNumber(1234);
        cil[0].setDescription("Coolest Webservice Product");
        cil[0].setPrice(new BigDecimal("100"));

        cil[1].setName("JAXRPC SI 2.0");
        cil[1].setBrand("Sun Microsystems, Inc.");
        cil[1].setCategory("Web Services");
        cil[1].setProductNumber(5678);
        cil[1].setDescription("Coolest Webservice Product");
        cil[1].setPrice(new BigDecimal("200"));

        fromwsdl.wsdl_rpclit_get_catalog.client.CatalogType ret = stub.getCatalog();
        for(int i = 0; i < ret.getItem().size(); i++){
            fromwsdl.wsdl_rpclit_get_catalog.client.CatalogItem gci = (fromwsdl.wsdl_rpclit_get_catalog.client.CatalogItem)ret.getItem().get(i);
            assertTrue(gci.getName().equals(cil[i].getName()));
            assertTrue(gci.getBrand().equals(cil[i].getBrand()));
            assertTrue(gci.getCategory().equals(cil[i].getCategory()));
            assertTrue(gci.getProductNumber() == cil[i].getProductNumber());
            assertTrue(gci.getDescription().equals(cil[i].getDescription()));
            assertTrue(gci.getPrice().equals(cil[i].getPrice()));

        }
    }

    public void testEchoCatalog() throws Exception {
        fromwsdl.wsdl_rpclit_get_catalog.client.CatalogType in = new fromwsdl.wsdl_rpclit_get_catalog.client.ObjectFactory().createCatalogType();
        fromwsdl.wsdl_rpclit_get_catalog.client.CatalogItem[] cil = {new fromwsdl.wsdl_rpclit_get_catalog.client.ObjectFactory().createCatalogItem(), new fromwsdl.wsdl_rpclit_get_catalog.client.ObjectFactory().createCatalogItem()};
        cil[0].setName("JAXRPC SI 1.1.2");
        cil[0].setBrand("Sun Microsystems, Inc.");
        cil[0].setCategory("Web Services");
        cil[0].setProductNumber(1234);
        cil[0].setDescription("Coolest Webservice Product");
        cil[0].setPrice(new BigDecimal("100"));
        cil[1].setName("JAXRPC SI 2.0");
        cil[1].setBrand("Sun Microsystems, Inc.");
        cil[1].setCategory("Web Services");
        cil[1].setProductNumber(5678);
        cil[1].setDescription("Coolest Webservice Product");
        cil[1].setPrice(new BigDecimal("200"));
        in.getItem().add(cil[0]);
        in.getItem().add(cil[1]);
        fromwsdl.wsdl_rpclit_get_catalog.client.CatalogType ret = stub.echoCatalog(in);
        for(int i = 0; i < ret.getItem().size(); i++){
            fromwsdl.wsdl_rpclit_get_catalog.client.CatalogItem gci = (fromwsdl.wsdl_rpclit_get_catalog.client.CatalogItem)ret.getItem().get(i);

//                System.out.println("\nName: "+gci.getName());
//                System.out.println("Brand: "+gci.getBrand());
//                System.out.println("Category: "+gci.getCategory());
//                System.out.println("ProductNumber: "+gci.getProductNumber());
//                System.out.println("Description: "+gci.getDescription());
//                System.out.println("Price: "+gci.getPrice());

            assertTrue(gci.getName().equals(cil[i].getName()));
            assertTrue(gci.getBrand().equals(cil[i].getBrand()));
            assertTrue(gci.getCategory().equals(cil[i].getCategory()));
            assertTrue(gci.getProductNumber() == cil[i].getProductNumber());
            assertTrue(gci.getDescription().equals(cil[i].getDescription()));
            assertTrue(gci.getPrice().equals(cil[i].getPrice()));

        }
    }




    public void testTestCatalog() throws Exception {
        fromwsdl.wsdl_rpclit_get_catalog.client.CatalogType in = new fromwsdl.wsdl_rpclit_get_catalog.client.ObjectFactory().createCatalogType();
        fromwsdl.wsdl_rpclit_get_catalog.client.CatalogItem cil = new fromwsdl.wsdl_rpclit_get_catalog.client.ObjectFactory().createCatalogItem();
        cil.setName("JAXRPC SI 1.1.2");
        cil.setBrand("Sun Microsystems, Inc.");
        cil.setCategory("Web Services");
        cil.setProductNumber(1234);
        cil.setDescription("Coolest Webservice Product");
        cil.setPrice(new BigDecimal("100"));

        String expectedName = "Sun Microsystems";

        jakarta.xml.ws.Holder<java.lang.String> name = new jakarta.xml.ws.Holder<java.lang.String>();
        name.value = "Sun";

        int index = 1234;

        fromwsdl.wsdl_rpclit_get_catalog.client.CatalogType ret = stub.testCatalog(name, index);
        assertEquals(name.value, expectedName);

        fromwsdl.wsdl_rpclit_get_catalog.client.CatalogItem gci = (fromwsdl.wsdl_rpclit_get_catalog.client.CatalogItem)ret.getItem().get(0);
        assertEquals(gci.getName(), cil.getName());
        assertEquals(gci.getBrand(), cil.getBrand());
        assertEquals(gci.getCategory(), cil.getCategory());
        assertTrue(gci.getProductNumber() == cil.getProductNumber());
        assertEquals(gci.getDescription(), cil.getDescription());
        assertEquals(gci.getPrice(), cil.getPrice());
    }

    public void testHelloOneWay() throws Exception{
        String str = "JAXRPC 2.0";
        stub.helloOneWay(str);
    }


    public void testHolders() throws Exception{
        String str = "1";
        jakarta.xml.ws.Holder<java.lang.Integer> inout = new jakarta.xml.ws.Holder<java.lang.Integer>();
        inout.value = 1;
        double out = stub.testHolders(str, inout);
        assertEquals((int)inout.value, 2);
        assertEquals(out, 1.0);
    }

    public void testShortArrayTest() throws Exception{
        ShortArrayTest req = new ObjectFactory().createShortArrayTest();
        req.getShortArray().add((short)100);
        req.getShortArray().add((short)200);
        req.getShortArray().add((short)300);
        ShortArrayTestResponse resp = stub.shortArrayTest(req);
        assertTrue((resp.getShortArray().get(0) == 100) && (resp.getShortArray().get(1)== 200) && (resp.getShortArray().get(2) == 300));
    }

    public void testParameterOrder() throws Exception {
        int bar = 1;
        String foo = "Hello World!";
        jakarta.xml.ws.Holder<java.lang.String> foo1 = new jakarta.xml.ws.Holder<java.lang.String>();
        int resp = stub.testParameterOrder(bar, foo, foo1);
        assertTrue(bar == resp && foo.equals(foo1.value));
    }
    
    /**
     *
     * @throws Exception
     */
    public void testUnboundedParts() throws Exception{
        String foo = "3";
        String foo1 = "4";
        jakarta.xml.ws.Holder<java.lang.Integer> bar = new jakarta.xml.ws.Holder<java.lang.Integer>();
        jakarta.xml.ws.Holder<java.lang.Integer> bar1 = new jakarta.xml.ws.Holder<java.lang.Integer>();
        int resp = stub.testUnboundedParts(foo, foo1, bar, bar1);
        assertTrue(bar.value.intValue() == 3);
        assertTrue(foo1.equals("4"));
        assertTrue(bar1.value == null);
        assertTrue(resp == 0);
    }

   public void testGCBug() throws Exception{
        String fn = "foo";
        String ln = "bar";
        String mn = "duke";
        int age = 100;
        jakarta.xml.ws.Holder<fromwsdl.wsdl_rpclit_get_catalog.client.NameType> nameHolder = new jakarta.xml.ws.Holder<fromwsdl.wsdl_rpclit_get_catalog.client.NameType>();
        jakarta.xml.ws.Holder<fromwsdl.wsdl_rpclit_get_catalog.client.AddressType> addressHolder = new jakarta.xml.ws.Holder<fromwsdl.wsdl_rpclit_get_catalog.client.AddressType>();
        jakarta.xml.ws.Holder<fromwsdl.wsdl_rpclit_get_catalog.client.PersonalDetailsType> personalHolder = new jakarta.xml.ws.Holder<fromwsdl.wsdl_rpclit_get_catalog.client.PersonalDetailsType>();
        stub.getGCBug(fn, ln, mn, age, nameHolder, personalHolder,addressHolder);
        assertTrue(nameHolder.value.getFn().equals(fn));
        assertTrue(nameHolder.value.getLn().equals(ln));
    }
    
}
