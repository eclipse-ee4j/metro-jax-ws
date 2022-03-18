/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.wsa.wsdl_rpclit.server;

@jakarta.jws.WebService(endpointInterface="fromwsdl.wsa.wsdl_rpclit.server.EchoMsgPortType")
public class EchoServiceImpl implements EchoMsgPortType {
    public String echoMsgOperation(String input) {
        return "Hello " +input;
    }
}
