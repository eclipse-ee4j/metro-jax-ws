/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.submission.fromwsdl.issue564.server;

import javax.xml.ws.*;
import javax.xml.ws.soap.SOAPBinding;

import com.sun.xml.ws.developer.MemberSubmissionAddressing;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.Messages;
import com.sun.xml.ws.api.addressing.AddressingVersion;
import com.sun.xml.ws.api.SOAPVersion;


/**
 * @author Rama Pulavarthi
 */
@WebServiceProvider()
@ServiceMode(value= Service.Mode.MESSAGE)
@BindingType(value= SOAPBinding.SOAP12HTTP_BINDING)
@MemberSubmissionAddressing(required=false, validation=MemberSubmissionAddressing.Validation.STRICT)
//@Addressing
public class AddNumbersImpl implements Provider<Message> {


    public Message invoke(Message request) {
        Message m2 = Messages.create("Test Unsupported", AddressingVersion.W3C, SOAPVersion.SOAP_12);
        return m2;

    }
}
