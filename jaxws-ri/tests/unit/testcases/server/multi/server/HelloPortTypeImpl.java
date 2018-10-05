/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.multi.server;

import javax.xml.ws.Holder;
import javax.jws.WebService;

/**
 * @author Jitendra Kotamraju
 */
@WebService(endpointInterface="server.multi.server.HelloPortType")
public class HelloPortTypeImpl implements HelloPortType {

    public EchoResponseType echo(EchoType reqBody, EchoType reqHeader,
                                 Echo2Type req2Header) {
        EchoResponseType response = new EchoResponseType();
        response.setRespInfo(reqBody.getReqInfo()
                + reqHeader.getReqInfo() + req2Header.getReqInfo());
        return response;
    }

    public String echo2(String info) {
        return info;
    }

    public void echo3(Holder<java.lang.String> reqInfo) {
    }

    public void echo4(Echo4Type reqBody, Echo4Type reqHeader,
                      String req2Header, Holder<String> respBody,
                      Holder<String> respHeader) {
    }

}
