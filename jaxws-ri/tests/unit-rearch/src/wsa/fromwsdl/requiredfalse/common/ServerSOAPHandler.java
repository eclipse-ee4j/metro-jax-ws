/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromwsdl.requiredfalse.common;

import javax.xml.ws.WebServiceException;
import testutil.WsaBaseSOAPHandler;

/**
 * @author Arun Gupta
 */
public class ServerSOAPHandler extends WsaBaseSOAPHandler {
    protected void checkInboundActions(String oper, String action) {
        if (oper.equals("addNumbers")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_IN_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (oper.equals("addNumbers2")) {
            if (!action.equals(TestConstants.ADD_NUMBERS2_IN_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (oper.equals("addNumbers3")) {
            if (!action.equals(TestConstants.ADD_NUMBERS3_IN_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (oper.equals("addNumbers4")) {
            if (!action.equals(TestConstants.ADD_NUMBERS4_IN_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (oper.equals("addNumbers5")) {
            if (!action.equals(TestConstants.ADD_NUMBERS5_IN_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (oper.equals("addNumbers6")) {
            if (!action.equals(TestConstants.ADD_NUMBERS6_IN_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (oper.equals("addNumbers7")) {
            if (!action.equals(TestConstants.ADD_NUMBERS7_IN_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (oper.equals("addNumbers8")) {
            if (!action.equals(TestConstants.ADD_NUMBERS8_IN_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action); 
            }
        }
    }
}
