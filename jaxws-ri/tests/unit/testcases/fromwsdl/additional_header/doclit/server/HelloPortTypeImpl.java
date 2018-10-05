/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.additional_header.doclit.server;

import javax.jws.WebService;
import javax.xml.ws.Holder;

/**
 * @author Vivek Pandey
 */
@WebService(endpointInterface = "fromwsdl.additional_header.doclit.server.HelloPortType")
public class HelloPortTypeImpl implements HelloPortType {
    public String echoWithInHeaders(
    String reqInfo,
    String additionalHeader1,
    String additionalHeader2) {
        return reqInfo + additionalHeader1+additionalHeader2;
    }

    public String echoWithOutHeaders(
    String reqInfo,
    Holder<String> additionalHeader1,
    Holder<String> additionalHeader2) {
        additionalHeader1.value = reqInfo + "additionalHeader1";
        additionalHeader2.value = reqInfo + "additionalHeader2";
        return reqInfo + "additionalHeader1"+"additionalHeader2";
    }

    public String echoWithInOutHeaders(
    String reqInfo,
    Holder<String> additionalHeader1,
    Holder<String> additionalHeader2) {
        String resp =  reqInfo + additionalHeader1.value + "additionalHeader2";
        additionalHeader1.value = reqInfo + additionalHeader1.value;
        additionalHeader2.value = reqInfo + "additionalHeader2";
        return resp;
    }

    public EchoResponseType echoWithInOutHeadersBare(
    EchoType reqBody,
    String reqHeader,
    Holder<String> additionalHeader2,
    Holder<String> additionalHeader1) {
        EchoResponseType resp = new EchoResponseType();
        resp.setRespInfo(reqBody.getReqInfo() + reqHeader +"additionalHeader1" + additionalHeader2.value);
        additionalHeader2.value = reqBody.getReqInfo() + additionalHeader2.value;
        additionalHeader1.value = reqBody.getReqInfo() + "additionalHeader1";
        return resp;
    }
}
