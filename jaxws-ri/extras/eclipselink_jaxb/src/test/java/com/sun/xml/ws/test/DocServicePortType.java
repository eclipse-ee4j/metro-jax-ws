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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import jakarta.jws.Oneway;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import jakarta.xml.ws.Action;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.RequestWrapper;
import jakarta.xml.ws.ResponseWrapper;

@WebService(name = "DocServicePortType", targetNamespace = "http://performance.bea.com")
public interface DocServicePortType {

    /**
     *
     */
    @WebMethod
    @RequestWrapper(localName = "echoBaseStruct", targetNamespace = "http://performance.bea.com", className = "com.bea.performance.BaseStruct")
    @ResponseWrapper(localName = "BaseStructOutput", targetNamespace = "http://weblogic/performance/benchmarks/jwswsee/doc", className = "com.bea.performance.BaseStruct")
    @Action(input = "http://performance.bea.com/DocServicePortType/echoBaseStructRequest", output = "http://performance.bea.com/DocServicePortType/echoBaseStructResponse")
    public void echoBaseStruct(
        @WebParam(name = "floatMessage", targetNamespace = "", mode = WebParam.Mode.INOUT)
        Holder<Float> floatMessage,
        @WebParam(name = "shortMessage", targetNamespace = "", mode = WebParam.Mode.INOUT)
        Holder<Short> shortMessage);

    /**
     *
     */
    @WebMethod
    @RequestWrapper(localName = "echoExtendedStruct", targetNamespace = "http://performance.bea.com", className = "com.bea.performance.ExtendedStruct")
    @ResponseWrapper(localName = "ExtendedStructOutput", targetNamespace = "http://weblogic/performance/benchmarks/jwswsee/doc", className = "com.bea.performance.ExtendedStruct")
    @Action(input = "http://performance.bea.com/DocServicePortType/echoExtendedStructRequest", output = "http://performance.bea.com/DocServicePortType/echoExtendedStructResponse")
    public void echoExtendedStruct(
        @WebParam(name = "floatMessage", targetNamespace = "", mode = WebParam.Mode.INOUT)
        Holder<Float> floatMessage,
        @WebParam(name = "shortMessage", targetNamespace = "", mode = WebParam.Mode.INOUT)
        Holder<Short> shortMessage,
        @WebParam(name = "anotherIntMessage", targetNamespace = "", mode = WebParam.Mode.INOUT)
        Holder<Integer> anotherIntMessage,
        @WebParam(name = "intMessage", targetNamespace = "", mode = WebParam.Mode.INOUT)
        Holder<Integer> intMessage,
        @WebParam(name = "stringMessage", targetNamespace = "", mode = WebParam.Mode.INOUT)
        Holder<String> stringMessage);

  
    /**
     *
     */
    @WebMethod
    @RequestWrapper(localName = "modifyMoreExtendedStruct", targetNamespace = "http://performance.bea.com", className = "com.bea.performance.MoreExtendedStruct")
    @ResponseWrapper(localName = "MoreExtendedStructOutput", targetNamespace = "http://weblogic/performance/benchmarks/jwswsee/doc", className = "com.bea.performance.MoreExtendedStruct")
    @Action(input = "http://performance.bea.com/DocServicePortType/modifyMoreExtendedStructRequest", output = "http://performance.bea.com/DocServicePortType/modifyMoreExtendedStructResponse")
    public void modifyMoreExtendedStruct(
        @WebParam(name = "floatMessage", targetNamespace = "", mode = WebParam.Mode.INOUT)
        Holder<Float> floatMessage,
        @WebParam(name = "shortMessage", targetNamespace = "", mode = WebParam.Mode.INOUT)
        Holder<Short> shortMessage,
        @WebParam(name = "anotherIntMessage", targetNamespace = "", mode = WebParam.Mode.INOUT)
        Holder<Integer> anotherIntMessage,
        @WebParam(name = "intMessage", targetNamespace = "", mode = WebParam.Mode.INOUT)
        Holder<Integer> intMessage,
        @WebParam(name = "stringMessage", targetNamespace = "", mode = WebParam.Mode.INOUT)
        Holder<String> stringMessage,
        @WebParam(name = "booleanMessage", targetNamespace = "", mode = WebParam.Mode.INOUT)
        Holder<Boolean> booleanMessage);
}
