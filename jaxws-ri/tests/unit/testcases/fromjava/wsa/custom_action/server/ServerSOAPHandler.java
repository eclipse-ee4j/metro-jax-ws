/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.wsa.custom_action.server;

import testutil.WsaBaseSOAPHandler;
import javax.xml.ws.WebServiceException;
import fromjava.wsa.custom_action.common.TestConstants;

/**
 * @author Arun Gupta
 */
public class ServerSOAPHandler extends WsaBaseSOAPHandler {
    protected void checkInboundActions(String oper, String action) {
        if (oper.equals("addNumbersNoAction")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_IN_NOACTION)) {
                throw new WebServiceException("Unexpected action received " + action);
            }
        } else if (oper.equals("addNumbersEmptyAction")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_IN_EMPTYACTION)) {
                throw new WebServiceException("Unexpected action received " + action);
            }
        } else if (oper.equals("addNumbers")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_IN_ACTION)) {
                throw new WebServiceException("Unexpected action received " + action);
            }
        } else if (oper.equals("addNumbers2")) {
            if (!action.equals(TestConstants.ADD_NUMBERS2_IN_ACTION)) {
                throw new WebServiceException("Unexpected action received " + action);
            }
        } else if (oper.equals("addNumbers3")) {
            if (!action.equals(TestConstants.ADD_NUMBERS3_IN_ACTION)) {
                throw new WebServiceException("Unexpected action received " + action);
            }
        } else if (oper.equals("addNumbersFault1")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT1_IN_ACTION)) {
                throw new WebServiceException("Unexpected action received " + action);
            }
        } else if (oper.equals("addNumbersFault2")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT2_IN_ACTION)) {
                throw new WebServiceException("Unexpected action received " + action);
            }
        } else if (oper.equals("addNumbersFault3")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT3_IN_ACTION)) {
                throw new WebServiceException("Unexpected action received " + action);
            }
        } else if (oper.equals("addNumbersFault4")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT4_IN_ACTION)) {
                throw new WebServiceException("Unexpected action received " + action);
            }
        } else if (oper.equals("addNumbersFault5")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT5_IN_ACTION)) {
                throw new WebServiceException("Unexpected action received " + action);
            }
        } else if (oper.equals("addNumbersFault6")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT6_IN_ACTION)) {
                throw new WebServiceException("Unexpected action received " + action);
            }
        } else if (oper.equals("addNumbersFault7")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT7_IN_ACTION)) {
                throw new WebServiceException("Unexpected action received " + action);
            }
        }
    }
}
