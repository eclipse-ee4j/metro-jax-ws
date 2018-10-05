/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromjava.custom_action.common;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;

import javax.xml.ws.WebServiceException;
import testutil.WsaBaseSOAPHandler;

/**
 * @author Arun Gupta
 */
public class ClientSOAPHandler extends WsaBaseSOAPHandler {
    @Override
    protected void checkInboundActions(String oper, String action) {
        if (oper.equals("addNumbersNoActionResponse")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_OUT_NOACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (oper.equals("addNumbersEmptyActionResponse")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_OUT_EMPTYACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (oper.equals("addNumbersResponse")) {
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
        }
    }

    @Override
    protected void checkFaultActions(String requestName, String detailName, String action) {
        if (requestName.equals("addNumbersFault1") && detailName.equals("AddNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT1_ADDNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault2") && detailName.equals("AddNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT2_ADDNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault2") && detailName.equals("TooBigNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT2_TOOBIGNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault3") && detailName.equals("AddNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT3_ADDNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault3") && detailName.equals("TooBigNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT3_TOOBIGNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault4") && detailName.equals("AddNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT4_ADDNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault4") && detailName.equals("TooBigNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT4_TOOBIGNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault5") && detailName.equals("AddNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT5_ADDNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault5") && detailName.equals("TooBigNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT5_TOOBIGNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault6") && detailName.equals("AddNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT6_ADDNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault6") && detailName.equals("TooBigNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT6_TOOBIGNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault7") && detailName.equals("AddNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT7_ADDNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault7") && detailName.equals("TooBigNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT7_TOOBIGNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        }
        super.checkFaultActions(requestName, detailName, action);
    }

    @Override
    protected String getOperationName(SOAPBody soapBody) throws SOAPException {
        String opName = super.getOperationName(soapBody);
        if (!opName.startsWith("addNumbersFault"))
            return opName;

        if (opName.equals("addNumbersFault1"))
            return opName;

        if (opName.equals("addNumbersFault2")) {
            soapBody.getFirstChild().getFirstChild().getNodeValue();
        }
        return opName;
    }
}
