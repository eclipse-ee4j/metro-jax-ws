/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.header.rpclit.server;

import jakarta.xml.ws.Holder;
import jakarta.jws.WebMethod;
import jakarta.jws.WebResult;
import jakarta.jws.WebParam;

@jakarta.jws.WebService(endpointInterface="fromwsdl.header.rpclit.server.HelloPortType")
public class HelloPortTypeImpl
        implements HelloPortType {
    public void hello(HelloType param1, String param2,
                      Holder<String> param3,
                      Holder<String> param4,
                      String param5) {
        return;
    }

    public EchoResponseType echo(EchoType reqBody,
                                 EchoType reqHeader) {
        EchoResponseType response = null;
        try {
            //test rpclit parameter nullability
            if(reqBody.getReqInfo().equals("sendNull"))
                return null;
            ObjectFactory of = new ObjectFactory();
            response = of.createEchoResponseType();
            response.setRespInfo(reqBody.getReqInfo() +
                                 reqHeader.getReqInfo());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }
    
    public void echo1(Holder<String> echo1Header) {
        echo1Header.value += " World!";
    }

    public Echo2ResponseType echo2(EchoType reqBody,
                                   EchoType req1Header,
                                   Echo2Type req2Header) {
        Echo2ResponseType response = null;
        try {
            ObjectFactory of = new ObjectFactory();
            response = of.createEcho2ResponseType();
            response.setRespInfo(reqBody.getReqInfo() +
                                 req1Header.getReqInfo() +
                                 req2Header.getReqInfo());
            System.out.println("Set the response object");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }

    public int echo3(String echo3Req) {
        return Integer.valueOf(echo3Req);
    }

}
