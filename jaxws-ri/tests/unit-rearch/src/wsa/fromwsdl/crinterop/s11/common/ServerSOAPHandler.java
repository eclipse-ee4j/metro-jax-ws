/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromwsdl.crinterop.s11.common;

import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.WebServiceException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPBody;

import testutil.WsaBaseSOAPHandler;

/**
 * @author Arun Gupta
 */
public class ServerSOAPHandler extends WsaBaseSOAPHandler {
    protected void checkInboundActions(String oper, String action) {
        if (oper.equals("test1130") || oper.equals("test1131") ||
                oper.equals("test1132") || oper.equals("test1133") ||
                oper.equals("test1134") || oper.equals("test1150") ||
                oper.equals("fault-test1152")) {
            if (!action.equals(TestConstants.ECHO_IN_ACTION))
                throw new WebServiceException("Unexpected action received" + action);
        } else if (oper.equals("1100") || oper.equals("1101") ||
                oper.equals("1102") || oper.equals("1103") ||
                oper.equals("1104") || oper.equals("1106") ||
                oper.equals("1107") || oper.equals("1108")) {
            System.out.println("checking one-way actions");
            if (!action.equals(TestConstants.NOTIFY_ACTION))
                throw new WebServiceException("Unexpected action received" + action);
        }
    }

    @Override
    public boolean handleFault(SOAPMessageContext smc) {
        return true;
    }

    @Override
    protected String getOperationName(SOAPBody soapBody) throws SOAPException {
        return soapBody.getFirstChild().getTextContent();
    }
}
