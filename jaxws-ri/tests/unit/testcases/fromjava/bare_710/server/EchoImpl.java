/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.bare_710.server;

import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.*;

/**
 * fromjava doc/bare mapping
 *
 * @author Jitenddra Kotamraju
 */
@WebService(name="Echo", serviceName="EchoService", targetNamespace="http://echo.org/")
@SOAPBinding(parameterStyle=ParameterStyle.BARE)
public class EchoImpl {

    public int add(NumbersRequest numRequest) {
        if (numRequest.number1 != 10)
            throw new WebServiceException("numRequest.number1 expected=10"+" got="+numRequest.number1);
        if (numRequest.number2 != 20)
            throw new WebServiceException("numRequest.number2 expected=10"+" got="+numRequest.number2);
        if (numRequest.guess != 25)
            throw new WebServiceException("numRequest.guess expected=25"+" got="+numRequest.guess);
        return numRequest.number1+numRequest.number2;
    }

    public void addNumbers(NumbersRequest numRequest,
                        @WebParam(mode=WebParam.Mode.OUT)
                        Holder<Integer> res) {
        if (numRequest.number1 != 10)
            throw new WebServiceException("numRequest.number1 expected=10"+" got="+numRequest.number1);
        if (numRequest.number2 != 20)
            throw new WebServiceException("numRequest.number2 expected=10"+" got="+numRequest.number2);
        if (numRequest.guess != 25)
            throw new WebServiceException("numRequest.guess expected=25"+" got="+numRequest.guess);
        res.value = numRequest.number1+numRequest.number2;
    }

//    > 1 IN body part. Throws an exception
//    public void sumNumbers(NumbersRequest numRequest,
//                        @WebParam(mode=WebParam.Mode.INOUT)
//                        Holder<Integer> res) {
//        if (numRequest.number1 != 10)
//            throw new WebServiceException("numRequest.number1 expected=10"+" got"+numRequest.number1);
//        if (numRequest.number2 != 20)
//            throw new WebServiceException("numRequest.number2 expected=10"+" got"+numRequest.number2);
//        if (res.value != 25)
//            throw new WebServiceException("res.value expected=25"+" got"+res.value);
//
//        res.value = numRequest.number1+numRequest.number2;
//    }

    public void echoString(@WebParam(mode=WebParam.Mode.INOUT)
                           Holder<String> str) {        
        if (!str.value.equals("test"))
            throw new WebServiceException("str.value expected=test"+" got="+str.value);
    }

    @WebResult(header=true)
    public String echoHeaders(@WebParam(mode=WebParam.Mode.INOUT)
                           Holder<String> str,
                           @WebParam(header=true)
                           String inHeader,
                           @WebParam(header=true, mode=WebParam.Mode.OUT)
                           Holder<String> outHeader,
                           @WebParam(header=true, mode=WebParam.Mode.INOUT)
                           Holder<String> inoutHeader) {
        if (!str.value.equals("test"))
            throw new WebServiceException("str.value expected=test"+" got="+str.value);
        if (!inHeader.equals("inHeader"))
            throw new WebServiceException("inHeader expected=inHeader"+" got="+inHeader);
        if (!inoutHeader.value.equals("inoutHeader"))
            throw new WebServiceException("inoutHeader expected=inoutHeader"+" got="+inoutHeader);
        outHeader.value = "outHeader";
        return "returnHeader";
    }

}
