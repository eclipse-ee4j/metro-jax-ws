/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromwsdl.delimiter.common;

import testutil.WsaBaseSOAPHandler;
import javax.xml.ws.WebServiceException;

/**
 * @author Arun Gupta
 */
public class ClientSOAPHandler extends WsaBaseSOAPHandler {
    @Override
    protected void checkInboundActions(String oper, String action) {
        if (oper.equals("addNumbersResponse")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_OUT_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (oper.equals("addNumbers2Response")) {
            if (!action.equals(TestConstants.ADD_NUMBERS2_OUT_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        }
    }

    @Override
    protected void checkFaultActions(String requestName, String detailName, String action) {
        if (requestName.equals("addNumbers") && detailName.equals("AddNumbersFault")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_ADDNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbers") && detailName.equals("TooBigNumbersFault")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_TOOBIGNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbers2") && detailName.equals("AddNumbersFault")) {
            if (!action.equals(TestConstants.ADD_NUMBERS2_ADDNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbers2") && detailName.equals("TooBigNumbersFault")) {
            if (!action.equals(TestConstants.ADD_NUMBERS2_TOOBIGNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        }
    }
}
