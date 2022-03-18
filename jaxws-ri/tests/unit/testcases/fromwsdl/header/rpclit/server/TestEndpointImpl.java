/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.header.rpclit.server;

import jakarta.xml.ws.Holder;

/**
 * @author Vivek Pandey
 */
@jakarta.jws.HandlerChain(
    name="",
    file="handlers.xml"
)
@jakarta.jws.WebService(endpointInterface="fromwsdl.header.rpclit.server.HelloPortType")
public class TestEndpointImpl
        implements HelloPortType {

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

}
