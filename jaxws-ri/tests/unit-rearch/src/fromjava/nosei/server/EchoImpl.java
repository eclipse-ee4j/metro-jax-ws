/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.nosei.server;

import jakarta.jws.*;

import jakarta.xml.ws.Holder;
import jakarta.xml.ws.*;
import jakarta.xml.soap.*;
import jakarta.xml.ws.soap.SOAPFaultException;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.rmi.RemoteException;

@WebService(name="Echo", serviceName="echoService", targetNamespace="http://echo.org/")
public class EchoImpl {

    // Enum tests
    @WebMethod
    public Book echoBook(Book book) {
        return book;
    }
    public enum Status {RED, YELLOW, GREEN}
    @WebMethod
    public Status echoStatus(Status status) {
        return status;
    }
    
//    public Color color = Colors.UNKNOWN;
    public enum Color { RED, ORANGE, UNKNOWN};
    Color color = Color.UNKNOWN;
    @WebMethod
    public void setColor(Color color) {
        this.color = color;
    }
    
    @WebMethod
    public Color getColor() {
        return color;
    }
   

    // Generic Tests
    @WebMethod
    public GenericValue<String> echoGenericString(GenericValue<String> param) {
        if (param == null)
            return null;
        String tmp = param.value;
        return new GenericValue<String>(tmp+"&john");
    }

    @WebMethod
    public GenericValue<Integer> echoGenericInteger(GenericValue<Integer> value) {
  
        return value;
    }

    @WebMethod
    public <T extends Bar> T echoGenericBar(T  value) {
  
        return value;
    }
    
    @WebMethod
    public <T> T echoGenericObject(T obj) {
        return obj;
    }

    @WebMethod
    public String outGenericValueString(String tmp, @WebParam(mode=WebParam.Mode.OUT)Holder<GenericValue<String>> str, int age) {
        str.value.value = tmp+age;
	  return tmp;
    }

    @WebMethod
    public List<Bar> echoBarList(List<Bar> list) {
        return list;
    }

    @WebMethod
    public <T> T echoTList(List<T> list) {
        if (list.size() == 0)
            return null;
        return list.iterator().next();
    }

    @WebMethod
    public List<? extends Bar> echoWildcardBar(List<? extends Bar> list) {
        return list;
    }
    
    @WebMethod Map<String, String> echoStringMap(Map<String, String> map) {
        return map;
    }


    // Exclude Method
    @WebMethod(exclude=true)
    public String excludeMethod(String str) {
        return str;
    }

    // Result headers
    @WebMethod
    @WebResult(name="intHeaderResult", header=true)
    public int echoIntHeaderResult(int in) {
        return in*2;
    }


    //standard tests 
    @WebMethod(operationName="echoBar", action="urn:echoBar")
    public Bar echoBar(@WebParam(name="bar", mode=WebParam.Mode.IN)Bar param) throws Exception1 {
        return param;
    }

    @WebMethod
    public String echoString(@WebParam String str) throws Exception1, Fault1, WSDLBarException, Fault2,
        GenericException, RemoteException {
        if (str == null)
            return null;
        if (str.equals("GenericException")) {
            List<Bar> barList = new ArrayList<Bar>();
            Bar bar = new Bar();
            bar.setAge(33);
            barList.add(bar);
            bar = new Bar();
            bar.setAge(44);
            barList.add(bar);
            throw new GenericException("my genericException", new GenericValue<Integer>(33), bar, barList);
        }
        if (str.equals("Exception1")) 
            throw new Exception1("my exception1");
        if (str.equals("RemoteException")) 
            throw new RemoteException("my remote exception");
        if (str.equals("Fault1")) {
            FooException fooException = new FooException();
            fooException.setVarString("foo");
            fooException.setVarInt(33);
            fooException.setVarFloat(44F);
            throw new Fault1("fault1", fooException);
        }
        if (str.equals("WSDLBarException")) 
            throw new WSDLBarException("my barException", new Bar(33));
        if (str.equals("Fault2"))
            throw new Fault2("my fault2", 33, "myzing");
        return str;
    }

    @WebMethod
    public String[] echoStringArray(String[] str) {
        return str;
    }

    @WebMethod
    public String[] echoStringArrayNull(String[] str) {
        return null;
    }
    
    
    @WebMethod
    public long[] echoLongArray(long[] array) {
        return array;
    }

    @WebMethod
    public long echoLong(long lng) {
        return lng;
    }
    
    @WebMethod
    @WebResult(name="echoResult")
    public Bar[] echoBarArray(Bar[] bar) {
        return bar;
    }

    @WebMethod(operationName="echoBarAndBar", action="urn:echoBarAndBar")
    @WebResult(targetNamespace="urn:barresultns")
    public Bar[] echoTwoBar(@WebParam(targetNamespace="urn:mybarns")Bar bar, Bar bar2) {
        return new Bar[] { bar, bar2 };
    }

 
    boolean onewayCalled = false;	
    @WebMethod
    @Oneway
    public void oneway(String str, @WebParam(name="num2", header=true)float num) {
	onewayCalled = true;
    }

    @WebMethod
    public boolean verifyOneway() {
	return onewayCalled; 
    }


    boolean onewayHeaderCalled = false;
    @WebMethod
    public void onewayHeader(@WebParam(name="oneway1Header", header=true)float num) {
        onewayHeaderCalled = true;
    }

    @WebMethod
    public boolean verifyOnewayHeader() {
	return onewayHeaderCalled;
    }

    boolean oneway2Called = false;
    @WebMethod
    @Oneway
    public void oneway2(String str) {
        oneway2Called = true;
    }
    
    @WebMethod
    public boolean verifyOneway2() {
       return oneway2Called;
    }
    
    @WebMethod
    public void voidTest() {
    }

    // overload tests
    @WebMethod
    public String overloadedOperation(String param) throws java.rmi.RemoteException {
        return param;
    }

    @WebMethod(operationName="overloadedOperation2")
    @RequestWrapper(localName="req", targetNamespace="foo_bar", className="fromjava.nosei.server.jaxws.OverloadedOperation2")
    @ResponseWrapper(localName="res", targetNamespace="foo_bar", className="fromjava.nosei.server.jaxws.OverloadedOperation2Response")
    public String overloadedOperation(String param, String param2) throws java.rmi.RemoteException {
        return param + param2;
    }

    @WebMethod(operationName="overloadedOperation3")
    @RequestWrapper(targetNamespace="foo_bar", className="fromjava.nosei.server.jaxws.OverloadedOperation3")
    @ResponseWrapper(targetNamespace="foo_bar", className="fromjava.nosei.server.jaxws.OverloadedOperation3Response")
    public String overloadedOperation(String param, String param2, String param3) throws java.rmi.RemoteException {
        return param + param2 + param3;
    }

    @WebMethod(operationName="overloadedOperation4")
    @RequestWrapper(className="fromjava.nosei.server.jaxws.OverloadedOperation4")
    @ResponseWrapper(className="fromjava.nosei.server.jaxws.OverloadedOperation4Response")
    public String overloadedOperation(String param, String param2, String param3, String param4) throws java.rmi.RemoteException {
        return param + " "+ param2 + " "+ param3 + " "+ param4;
    }

    // Holders and modes
    @WebMethod
    public String outString(String tmp, @WebParam(mode=WebParam.Mode.OUT)Holder<String> str, int age) {
        str.value = tmp+age;
	  return tmp;
    }


    @WebMethod
    public String inOutString(String tmp, @WebParam(mode=WebParam.Mode.INOUT)Holder<String> str, int age) {
        str.value += str.value;
	  return tmp;
    }
 

    @WebMethod
    public int outLong(int age, @WebParam(mode=WebParam.Mode.OUT)Holder<Long> lng, String bogus) {
        lng.value = 345L;
	  return age;
    }

    @WebMethod
    public int inOutLong(int age, @WebParam(mode=WebParam.Mode.INOUT)Holder<Long> lng, String bogus) {
        lng.value = 2*lng.value;
	  return age;
    }


    // Headers, modes and holders
    @WebMethod
    public Long echoInHeader(int age, @WebParam(name="num", header=true, targetNamespace="foo/bar")Long num, String str) {
        return num;
    }

    @WebMethod
    public Long echoIn2Header(int age, @WebParam(name="num", header=true, targetNamespace="foo/bar")Long num, 
					@WebParam(name="name", header=true)String name, String str) {
System.out.println("name: "+name);
        return num;
    }


//    @WebMethod
//    public String echoInOutHeader(int age, @WebParam(name="num", mode=WebParam.Mode.INOUT, header=true, targetNamespace="foo/bar")LongHolder num, String str) {
//        num.value = num.value*2;
//        return str+num.value;
//    }
    
    @WebMethod
    public String echoInOutHeader(int age, @WebParam(name="num", mode=WebParam.Mode.INOUT, header=true, targetNamespace="foo/bar")Holder<Long> num, String str) {
        num.value = num.value*2;
        return str+num.value;
    }

//    @WebMethod
//    public String echoOutHeader(int age, @WebParam(name="num", mode=WebParam.Mode.OUT, header=true, targetNamespace="foo/bar")LongHolder num, String str) {
//        num.value = new Long(age);;
//        return str+num.value;
//    }
    
    @WebMethod
    public String echoOutHeader(int age, @WebParam(name="num", mode=WebParam.Mode.OUT, header=true, targetNamespace="foo/bar")Holder<Long> num, String str) {
        num.value = new Long(age);;
        return str+num.value;
    }

//    @WebMethod
//    public String echoOut2Header(int age, @WebParam(name="num", mode=WebParam.Mode.OUT, header=true, targetNamespace="foo/bar")LongHolder num, 
//					   @WebParam(name="name", header=true, mode=WebParam.Mode.OUT)Holder<String> name, String str) {
//        num.value = new Long(age);;
//        name.value = "Fred";
//        return str+num.value;
//    }

    @WebMethod
    public String echoOut2Header(int age, @WebParam(name="num", mode=WebParam.Mode.OUT, header=true, targetNamespace="foo/bar")Holder<Long> num,
                       @WebParam(name="name", header=true, mode=WebParam.Mode.OUT)Holder<String> name, String str) {
        num.value = new Long(age);;
        name.value = "Fred";
        return str+num.value;
    }
    
    @WebMethod
    public String throwException(String throwWhat) {
        if(throwWhat.equals("SFE")){
            throw createSOAPFaultException();
        }
        return "Can't do.";
    }
    
    private SOAPFaultException createSOAPFaultException(){
        try {
            String namespace = "http://example.com/auctiontraq/schemas/doclit";
            SOAPFactory soapFactory = SOAPFactory.newInstance();
            Name name = soapFactory.createName("MySOAPFault", "ns0",
                    namespace);
            Detail detail = soapFactory.createDetail();
            DetailEntry entry = detail.addDetailEntry(name);
            entry.addNamespaceDeclaration("data",namespace );
            Name attrName1 = soapFactory.createName("myAttr", "data",namespace);
            entry.addAttribute(attrName1, "myvalue");
            SOAPElement child = entry.addChildElement("message");
            child.addTextNode("Server Exception");
            
            Name name2 = soapFactory.createName("ExtraInformation", "ns0",
                    namespace);
            DetailEntry entry2 = detail.addDetailEntry(name2);
            
            SOAPElement child2 = entry2.addChildElement("Reason");
            child2.addTextNode("Address Not Found");
            
            QName qname = new QName("http://schemas.xmlsoap.org/soap/envelope/", "server");
            SOAPFault sf = soapFactory.createFault("SOAP Fault Exception:Address Not Found", qname);
            org.w3c.dom.Node n = sf.getOwnerDocument().importNode(detail, true);
            sf.appendChild(n);
            return new SOAPFaultException(sf); 
            //printDetail(detail);
           // return new SOAPFaultException(qname,
             //       "SOAP Fault Exception:Address Not Found", null, detail);
           
        } catch (SOAPException e) {
            e.printStackTrace();
            //QName qname = new QName("http://schemas.xmlsoap.org/soap/envelope/", "client");
            throw new WebServiceException("Exception While Creating SOAP Fault Exception",e);
        }
    }
}
