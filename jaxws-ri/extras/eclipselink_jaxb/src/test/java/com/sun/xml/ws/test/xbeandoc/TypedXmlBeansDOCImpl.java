/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.test.xbeandoc;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(targetNamespace = "http://www.openuri.org/")
public class TypedXmlBeansDOCImpl {
    /**
     *
     * @param param1
     * @param countries
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "http://www.openuri.org/getCountryName")
    @WebResult(name = "name", targetNamespace = "http://www.openuri.org/")
    @RequestWrapper(localName = "getCountryName", targetNamespace = "http://www.openuri.org/", className = "com.sun.xml.ws.test.xbeandoc.GetCountryName")
    @ResponseWrapper(localName = "getCountryNameResponse", targetNamespace = "http://www.openuri.org/", className = "com.sun.xml.ws.test.xbeandoc.GetCountryNameResponse")
    public String getCountryName(
//        @WebParam(name = "Countries", targetNamespace = "http://www.bea.com/wli/sb/transports/ejb/test/xbean")
        Countries countries,
//        @WebParam(name = "param1", targetNamespace = "http://www.openuri.org/")
        String param1) {
        return param1+countries.getCountry().size();
    }

    /**
     *
     * @param param0
     * @param param1
     * @return
     *     returns com.sun.xml.ws.test.xbeandoc.CountryInfoType
     */
    @WebMethod(action = "http://www.openuri.org/getCountryInfo")
    @WebResult(name = "CountryInfoType", targetNamespace = "http://www.openuri.org/")
    @RequestWrapper(localName = "getCountryInfo", targetNamespace = "http://www.openuri.org/", className = "com.sun.xml.ws.test.xbeandoc.GetCountryInfo")
    @ResponseWrapper(localName = "getCountryInfoResponse", targetNamespace = "http://www.openuri.org/", className = "com.sun.xml.ws.test.xbeandoc.GetCountryInfoResponse")
    public CountryInfoType getCountryInfo(
        @WebParam(name = "param0", targetNamespace = "http://www.openuri.org/")
        String param0,
        @WebParam(name = "param1", targetNamespace = "http://www.openuri.org/")
        String param1) {
        CountryInfoType ci = new CountryInfoType();
        ci.setCode(param0);
        ci.setName(param1);
        return ci;
    }

    /**
     *
     * @param param1
     * @param countries
     */
    @WebMethod(action = "http://www.openuri.org/addCountry")
    @RequestWrapper(localName = "addCountry", targetNamespace = "http://www.openuri.org/", className = "com.sun.xml.ws.test.xbeandoc.AddCountry")
    @ResponseWrapper(localName = "addCountryResponse", targetNamespace = "http://www.openuri.org/", className = "com.sun.xml.ws.test.xbeandoc.AddCountryResponse")
    public void addCountry(
//        @WebParam(mode = WebParam.Mode.INOUT)
        Holder<Countries> countries,
//        @WebParam(name = "param1", targetNamespace = "http://www.openuri.org/")
        CountryInfoType param1) {
        countries.value.getCountry().add(param1);
    }

    /**
     *
     * @param param1
     * @param countries
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "http://www.openuri.org/getCountryNameXml")
    @WebResult(name = "name", targetNamespace = "http://www.openuri.org/")
    @RequestWrapper(localName = "getCountryNameXml", targetNamespace = "http://www.openuri.org/", className = "com.sun.xml.ws.test.xbeandoc.GetCountryNameXml")
    @ResponseWrapper(localName = "getCountryNameXmlResponse", targetNamespace = "http://www.openuri.org/", className = "com.sun.xml.ws.test.xbeandoc.GetCountryNameXmlResponse")
    public String getCountryNameXml(
//        @WebParam(name = "Countries", targetNamespace = "http://www.bea.com/wli/sb/transports/ejb/test/xbean")
        Countries countries,
        @WebParam(name = "param1", targetNamespace = "http://www.openuri.org/")
        String param1) {
        return param1;
    }
}
