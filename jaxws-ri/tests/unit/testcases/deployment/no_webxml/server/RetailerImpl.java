/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package deployment.no_webxml.server;

import javax.xml.ws.Holder;
import java.math.BigDecimal;

@javax.jws.WebService(endpointInterface="deployment.no_webxml.server.RetailerPortType")
public class RetailerImpl implements RetailerPortType {
    public CatalogType getCatalog() {
             try{
                 ObjectFactory of = new ObjectFactory();
                 CatalogType ret = of.createCatalogType();
                 CatalogItem ci = of.createCatalogItem();
                 ci.setName("JAXWS RI 2.1.1");
                 ci.setBrand("java.net");
                 ci.setCategory("Web Services");
                 ci.setProductNumber(1234);
                 ci.setDescription("Coolest Webservice Product");
                 ci.setPrice(new BigDecimal("100"));
                 ret.getItem().add(ci);

                 ci = of.createCatalogItem();
                 ci.setName("JAXWS RI 2.0");
                 ci.setBrand("java.net");
                 ci.setCategory("Web Services");
                 ci.setProductNumber(5678);
                 ci.setDescription("Coolest Webservice Product");
                 ci.setPrice(new BigDecimal("200"));
                 ret.getItem().add(ci);
                return ret;
             }catch(Exception e){ e.printStackTrace();}
             return null;
    }

    public void getGCBug(String fn, String ln, String mn,int age,Holder<NameType> nameHolder,
            javax.xml.ws.Holder<PersonalDetailsType> personalHolder,
            javax.xml.ws.Holder<AddressType> addressHolder){
        try{
            ObjectFactory of = new ObjectFactory();
            NameType name = of.createNameType();
            AddressType address = of.createAddressType();
            PersonalDetailsType personal = of.createPersonalDetailsType();
            name.setFn(fn);
            name.setLn(ln);
            address.setStreet(" 1234 network circle");
            address.setZipcode(100001);
            personal.setSsn("123456789");
            personal.setDob("03-18-2005");
            nameHolder.value = name;
            addressHolder.value=address;
            personalHolder.value=personal;

        }catch(Exception e){ e.printStackTrace();}

}

    public CatalogType echoCatalog(CatalogType input)
    {
         return input;
    }

    public CatalogType testCatalog(Holder<String> name, int index)
    {
        name.value += ".net";
        try{
            ObjectFactory of = new ObjectFactory();
            CatalogType ret = of.createCatalogType();
            CatalogItem ci = of.createCatalogItem();
            ci.setName("JAXWS RI 2.1.1");
            ci.setBrand("java.net");
            ci.setCategory("Web Services");
            ci.setProductNumber(index);
            ci.setDescription("Coolest Webservice Product");
            ci.setPrice(new BigDecimal("100"));
            ret.getItem().add(ci);
            return ret;
        }catch(Exception e){e.printStackTrace();}
        return null;
    }

    public void helloOneWay(String parameters){
        if(!parameters.equals("JAXWS RI 2.1.1"))
             System.out.println("HelloOneWay FAILED: received \""+parameters+"\", expected \"JAXRPC 2.0\"");
    }

     public double testHolders(String name,Holder<Integer> inout){
            inout.value = 2;
            return 1.0;
     }

     public ShortArrayResponseType shortArrayTest(ShortArrayType parameters){
        try{
        ShortArrayResponseType resp = new ObjectFactory().createShortArrayResponseType();
        resp.getShortArray().addAll(parameters.getShortArray());
        return resp;
        }catch(Exception e){e.printStackTrace();}
        return null;
     }

     public int testParameterOrder(int bar, String foo, Holder<String> foo1){
         foo1.value = foo;
         return bar;
     }

     public int testUnboundedParts(String foo, String foo1, Holder<Integer> bar, Holder<Integer> bar1) throws UnboundedPartsException{
         if(foo1 != null)
             throw new UnboundedPartsException("foo1 MUST be null!", "foo1 MUST be null!");
         bar.value = Integer.valueOf(foo);
         bar1.value = bar.value;
         return bar.value;
     }
}
