/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromwsdl.soapaction.common;

import testutil.WsaBaseSOAPHandler;
import jakarta.xml.ws.WebServiceException;

/**
 * @author Arun Gupta
 */
public class ClientSOAPHandler extends WsaBaseSOAPHandler {
    protected void checkInboundActions(String oper, String action) {
        if (oper.equals("addNumbersResponse")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_OUT_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (oper.equals("addNumbers2Response")) {
            if (!action.equals(TestConstants.ADD_NUMBERS2_OUT_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (oper.equals("addNumbers3Response")) {
            if (!action.equals(TestConstants.ADD_NUMBERS3_OUT_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (oper.equals("addNumbers4Response")) {
            if (!action.equals(TestConstants.ADD_NUMBERS4_OUT_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action); 
            }
        }
    }
}
