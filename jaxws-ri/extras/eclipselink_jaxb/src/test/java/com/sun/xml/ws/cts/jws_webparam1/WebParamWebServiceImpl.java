/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.cts.jws_webparam1;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Holder;

@WebService(targetNamespace="http://server.webparam1.webparam.jws.tests.ts.sun.com/")
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT, use=SOAPBinding.Use.LITERAL, parameterStyle=SOAPBinding.ParameterStyle.BARE)
public class WebParamWebServiceImpl {
    
    @WebMethod(operationName="helloString", action="urn:HelloString")
    public String hello(@WebParam (name="string1") String name) {
      return "hello : Hello " + name + " to Web Service";
    }


    @WebMethod(operationName="helloString2", action="urn:HelloString2")
    public String hello2(@WebParam (name="name2", partName="string2", targetNamespace="helloString2/name") String name) {
      return "hello2 : Hello " + name + " to Web Service";
    }

    @WebMethod(operationName="helloString3", action="urn:HelloString3")
    public void hello3(@WebParam (name="name3", partName="string3", targetNamespace="helloString3/name", header=true) String name, @WebParam (name="Name", targetNamespace="helloString3/Name", mode=WebParam.Mode.INOUT) Holder<Name> name2) {
   
//      System.out.println(" Invoking hello3 ");
      
      Name newName = new Name();
     
      newName.setFirstName("jsr181"); 
      newName.setLastName("jsr109"); 

      name2.value = newName;

    }

    @WebMethod(operationName="helloString4", action="urn:HelloString4")
    public void hello4(@WebParam (name="name4", partName="string4") String name, @WebParam (name="Employee", mode=WebParam.Mode.OUT) Holder<com.sun.xml.ws.cts.jws_common.Employee> employee)       throws com.sun.xml.ws.cts.jws_common.NameException {
   
//      System.out.println(" Invoking hello4 ");
     
      com.sun.xml.ws.cts.jws_common.Name newName = new com.sun.xml.ws.cts.jws_common.Name();
      com.sun.xml.ws.cts.jws_common.Employee oldEmployee = new com.sun.xml.ws.cts.jws_common.Employee();
     
      newName.setFirstName("jsr181"); 
      newName.setLastName("jaxws"); 

      oldEmployee.setName(newName);

      employee.value = oldEmployee;

    }

   @WebMethod(operationName="helloString5", action="urn:HelloString5")
    public String hello5(String name) {
      return "hello5 : Hello " + name + " to Web Service";
    }

    
    @WebMethod(operationName="helloString6", action="urn:HelloString6")
    public String hello6(@WebParam() int age, @WebParam(name="name6", header=true) String name) {
      return "hello6 : Hello " + name + " "+  age + " to Web Service";
    }

    @WebMethod(operationName="helloString7", action="urn:HelloString7")
    public void hello7(@WebParam (name="name7", partName="string7", header=true) String name, @WebParam (name="name8", partName="string8") com.sun.xml.ws.cts.jws_common.Name name2, @WebParam (name="MyEmployee", mode=WebParam.Mode.OUT) Holder<com.sun.xml.ws.cts.jws_common.Employee> employee) throws com.sun.xml.ws.cts.jws_common.NameException {

//      System.out.println(" Invoking hello7 ");

      com.sun.xml.ws.cts.jws_common.Employee oldEmployee = new com.sun.xml.ws.cts.jws_common.Employee();
      oldEmployee.setName(name2);

      employee.value = oldEmployee;

    }

    @WebMethod(operationName="helloString8", action="urn:HelloString8")
    @SOAPBinding(style=SOAPBinding.Style.DOCUMENT, use=SOAPBinding.Use.LITERAL, parameterStyle=SOAPBinding
  .ParameterStyle.WRAPPED)
    public String hello8(@WebParam (name="string8") String name, Address address) {
      return "hello8 : " + address.getCity() + " to Web Service";
    }

  }
