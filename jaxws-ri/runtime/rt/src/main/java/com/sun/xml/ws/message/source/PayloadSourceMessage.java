/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.message.source;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.message.AttachmentSet;
import com.sun.xml.ws.api.message.HeaderList;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.MessageHeaders;
import com.sun.xml.ws.message.AttachmentSetImpl;
import com.sun.xml.ws.message.stream.PayloadStreamReaderMessage;
import com.sun.xml.ws.streaming.SourceReaderFactory;

import javax.xml.transform.Source;

/**
 * {@link Message} backed by {@link Source} as payload
 *
 * @author Vivek Pandey
 */
public class PayloadSourceMessage extends PayloadStreamReaderMessage {

    public PayloadSourceMessage(@Nullable MessageHeaders headers,
        @NotNull Source payload, @NotNull AttachmentSet attSet,
        @NotNull SOAPVersion soapVersion) {
        
        super(headers, SourceReaderFactory.createSourceReader(payload, true),
                attSet, soapVersion);
    }

    public PayloadSourceMessage(Source s, SOAPVersion soapVer) {
        this(null, s, new AttachmentSetImpl(), soapVer);
    }

}
