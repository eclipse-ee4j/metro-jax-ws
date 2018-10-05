/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.submission.fromwsdl.fault.common;

import testutil.MemberSubmissionAddressingConstants;
import testutil.WsaBaseSOAPHandler;

import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;

/**
 * @author Arun Gupta
 */
public class ClientSOAPHandler extends WsaBaseSOAPHandler {
    @Override
    protected void checkInboundActions(String oper, String action) {
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
        } else if (requestName.equals("addNumbers2") && detailName.equals("AddNumbers2Fault")) {
            if (!action.equals(TestConstants.ADD_NUMBERS2_ADDNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbers2") && detailName.equals("TooBigNumbers2Fault")) {
            if (!action.equals(TestConstants.ADD_NUMBERS2_TOOBIGNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbers3") && detailName.equals("AddNumbers3Fault")) {
            if (!action.equals(TestConstants.ADD_NUMBERS3_ADDNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbers3") && detailName.equals("TooBigNumbers3Fault")) {
            if (!action.equals(TestConstants.ADD_NUMBERS3_TOOBIGNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbers4") && detailName.equals("AddNumbers4Fault")) {
            if (!action.equals(TestConstants.ADD_NUMBERS4_ADDNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbers4") && detailName.equals("TooBigNumbers4Fault")) {
            if (!action.equals(TestConstants.ADD_NUMBERS4_TOOBIGNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbers5") && detailName.equals("AddNumbers5Fault")) {
            if (!action.equals(TestConstants.ADD_NUMBERS5_ADDNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbers6") && detailName.equals("AddNumbers6Fault")) {
            if (!action.equals(TestConstants.ADD_NUMBERS6_ADDNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action); 
            }
        }
    }

    public QName getActionQName() {
        return MemberSubmissionAddressingConstants.actionTag;
    }
}
