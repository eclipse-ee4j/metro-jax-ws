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
import com.sun.xml.ws.fault.SOAPFaultBuilder;

import javax.xml.namespace.QName;

/**
 * This is used to represent Message creation exception when a {@link com.sun.xml.ws.api.pipe.Codec}
 * trying to create a {@link Message}.
 *
 * @author Jitendra Kotamraju
 */
public class MessageCreationException extends ExceptionHasMessage {

    private final SOAPVersion soapVersion;

    public MessageCreationException(SOAPVersion soapVersion, Object... args) {
        super("soap.msg.create.err", args);
        this.soapVersion = soapVersion;
    }

    public String getDefaultResourceBundleName() {
        return "com.sun.xml.ws.resources.soap";
    }

    public Message getFaultMessage() {
        QName faultCode = soapVersion.faultCodeClient;
        return SOAPFaultBuilder.createSOAPFaultMessage(
                soapVersion, getLocalizedMessage(), faultCode);
    }

}
