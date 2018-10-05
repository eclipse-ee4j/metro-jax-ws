/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.nosei_bare_apt.server;

import fromjava.nosei_bare_apt.server.Bar;
import javax.jws.*;
import javax.jws.soap.*;
import javax.jws.soap.SOAPBinding.ParameterStyle;

import javax.xml.ws.Holder;
import org.omg.CORBA.BAD_CONTEXT;
@WebService(name="Echo", serviceName="EchoService", targetNamespace="http://echo.org/")
@SOAPBinding(parameterStyle=ParameterStyle.BARE)
public class EchoImpl {
    boolean log = false;

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


    // Generic Params
    @WebMethod
    public GenericValue<String> echoGenericString(GenericValue<String> param) {
        String tmp = param.value;
        return new GenericValue<String>(tmp+"&john");

    }

    @WebMethod
    public GenericValue<Integer> echoGenericInteger(GenericValue<Integer> value) {
        value.value = value.value*2;
        return value;
    }
    
    @WebMethod
    public <T> T echoGenericObject(T obj) {
        return obj;
    }
	
    @WebMethod(operationName="echoBar", action="urn:echoBar")
    @WebResult(name="echoBarResult")
    public Bar echoBar(@WebParam(name="barParam", mode=WebParam.Mode.IN)Bar param) throws Exception1 {
        return param;
    }

    @WebMethod
    public Bar.InnerBar echoInnerBar(Bar.InnerBar param) throws Exception1 {
        Bar.InnerBar innerBar = new Bar.InnerBar();
        innerBar.setName(param.getName()+param.getName());
        return innerBar;
    }    
    
    @WebMethod
    @WebResult(name="echoStringResult")
    public String echoString(@WebParam(name="str") String str) throws Exception1, WSDLBarException, Fault1, Fault2 {
        if (str.equals("Exception1")) 
            throw new Exception1("my exception1");
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
            throw new Fault2("my fault2", 33);
        return str;
    }


    @WebMethod
    @WebResult(name="echoStringArrayResult", targetNamespace="mynamespace2")
    public String[] echoStringArray(@WebParam(name="strArray", targetNamespace="mynamespace2") String[] str) {
        return str;
    }
    
    @WebMethod
    @WebResult(name="echoResult")
    public Bar[] echoBarArray(@WebParam(name="bar")Bar[] bar) {
        return bar;
    }
//    @WebMethod(operationName="echoBarAndBar", action="urn:echoBarAndBar")
//    public Bar[] echoTwoBar(Bar bar, Bar bar2) {
//        return new Bar[] { bar, bar2 };
//    }

    boolean onewayCalled = false;
    @WebMethod
    @Oneway
    public void oneway(String bogus) {
        onewayCalled = true;
    }

    @WebMethod
    public boolean verifyOneway(int i) {
        return onewayCalled;
    }

//    @WebResult(name="outStringStr")
    @WebMethod
    public void inOutString(@WebParam(name="inOutStringStr", mode=WebParam.Mode.INOUT)Holder<String> str) {
        log("-----------str: "+str);
        log("-----------str.value: "+str.value);
//	  String tmp = str.value;
        str.value += str.value;
//	  return tmp;
    }

//    @WebResult(name="inOutLong")
    @WebMethod
    public void inOutLong(@WebParam(name="InOutLong", mode=WebParam.Mode.INOUT)Holder<Long> lng) {
        log("-----------lng: "+lng);
        log("-----------lng.value: "+lng.value);
        lng.value = lng.value * 2l;
    }
 


//    @WebMethod
//    public void outLong(@WebParam(name="outLong", mode=WebParam.Mode.OUT)Holder<Long> lng) {
//        log("-----------lng: "+lng);
//        log("-----------lng.value: "+lng.value);
//     
//        lng.value = 345L;
//    }


    // Headers, modes and holders
    @WebResult(name="inHeaderResponse")
    @WebMethod
    public Long echoInHeader(@WebParam(name="inHeader")Integer age, @WebParam(name="num", header=true)Long num) {
        log("-----------num: "+num);
        return num+age;
    }

//    @WebMethod
//    @WebResult(name="inOutHeaderResponse")
//    public Long echoInOutHeader(@WebParam(name="inOutHeader")Integer age, @WebParam(name="num", mode=WebParam.Mode.INOUT, header=true)LongWrapperHolder num) {
//        num.value = num.value*2;
//        return num.value+age;
//    }
    
    @WebMethod
    @WebResult(name="inOutHeaderResponse")
    public Long echoInOutHeader(@WebParam(name="inOutHeader")Integer age, @WebParam(name="num", mode=WebParam.Mode.INOUT, header=true)Holder<Long> num) {
        num.value = num.value*2;
        return num.value+age;
    }    

//    @WebMethod
//    @WebResult(name="outHeaderResponse")
//    public Long echoOutHeader(@WebParam(name="outHeader")Integer age, @WebParam(name="num", mode=WebParam.Mode.OUT, header=true)LongWrapperHolder num) {
//        log("-----------age: "+age);
//        num.value = new Long(age);;
//        log("-----------num.value: "+num.value);
//        log("-----------num.value+age: "+(num.value+age));
//        return num.value+age;
//    }
    
    @WebMethod
    @WebResult(name="outHeaderResponse")
    public Long echoOutHeader(@WebParam(name="outHeader")Integer age, @WebParam(name="num", mode=WebParam.Mode.OUT, header=true)Holder<Long> num) {
        log("-----------age: "+age);
        num.value = new Long(age);;
        log("-----------num.value: "+num.value);
        log("-----------num.value+age: "+(num.value+age));
        return num.value+age;
    }    

    @WebMethod
//    @WebResult(name="overLoadResponse")
    public String overloadedOperation(@WebParam(name="overLoad")String param) throws java.rmi.RemoteException {
        return param;
    }

    @WebMethod(operationName="overloadedOperation2")
    @WebResult(name="overLoad2Response")
    public long overloadedOperation(@WebParam(name="overLoad2")long param) throws java.rmi.RemoteException {
        return param;
    }

    void log(String msg) {
        if (log)
            System.out.println(msg);
    }
}
