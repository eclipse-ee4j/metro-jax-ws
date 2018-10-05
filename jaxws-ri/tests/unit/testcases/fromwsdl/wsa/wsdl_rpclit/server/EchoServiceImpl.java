/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.wsa.wsdl_rpclit.server;

@javax.jws.WebService(endpointInterface="fromwsdl.wsa.wsdl_rpclit.server.EchoMsgPortType")
public class EchoServiceImpl implements EchoMsgPortType {
    public String echoMsgOperation(String input) {
        return "Hello " +input;
    }
}
