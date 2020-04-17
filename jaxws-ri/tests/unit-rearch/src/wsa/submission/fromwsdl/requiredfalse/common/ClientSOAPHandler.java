/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.submission.fromwsdl.requiredfalse.common;

import javax.xml.namespace.QName;

import jakarta.xml.ws.WebServiceException;
import testutil.WsaBaseSOAPHandler;
import testutil.MemberSubmissionAddressingConstants;

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
        } else if (oper.equals("addNumbers5Response")) {
            if (!action.equals(TestConstants.ADD_NUMBERS5_OUT_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (oper.equals("addNumbers6Response")) {
            if (!action.equals(TestConstants.ADD_NUMBERS6_OUT_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (oper.equals("addNumbers7Response")) {
            if (!action.equals(TestConstants.ADD_NUMBERS7_OUT_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (oper.equals("addNumbers8Response")) {
            if (!action.equals(TestConstants.ADD_NUMBERS8_OUT_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action); 
            }
        }
    }

    public QName getActionQName() {
        return MemberSubmissionAddressingConstants.actionTag;
    }
}
