/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.protocol.soap;

import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.message.ExceptionHasMessage;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.encoding.soap.SOAP12Constants;
import com.sun.xml.ws.encoding.soap.SOAPConstants;
import com.sun.xml.ws.fault.SOAPFaultBuilder;

import javax.xml.namespace.QName;

/**
 * This is used to represent SOAP VersionMismatchFault. Use
 * this when the received soap envelope is in a different namespace
 * than what the specified Binding says.
 *
 * @author Jitendra Kotamraju
 */
public class VersionMismatchException extends ExceptionHasMessage {

    private final SOAPVersion soapVersion;

    public VersionMismatchException(SOAPVersion soapVersion, Object... args) {
        super("soap.version.mismatch.err", args);
        this.soapVersion = soapVersion;
    }

    public String getDefaultResourceBundleName() {
        return "com.sun.xml.ws.resources.soap";
    }

    public Message getFaultMessage() {
        QName faultCode = (soapVersion == SOAPVersion.SOAP_11)
            ? SOAPConstants.FAULT_CODE_VERSION_MISMATCH
            : SOAP12Constants.FAULT_CODE_VERSION_MISMATCH;
        return SOAPFaultBuilder.createSOAPFaultMessage(
                soapVersion, getLocalizedMessage(), faultCode);
    }

}
