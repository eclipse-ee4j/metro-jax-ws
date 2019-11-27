/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.message;

import com.sun.istack.Nullable;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.FilterMessageImpl;
import com.sun.xml.ws.api.model.wsdl.WSDLFault;

import javax.xml.namespace.QName;

/**
 * SOAP Fault message. It has optimized implementation to get
 * first detail entry's name. This is useful to identify the
 * corresponding {@link WSDLFault}
 *
 * @author Jitendra Kotamraju
 */
public class FaultMessage extends FilterMessageImpl {

    private final @Nullable QName detailEntryName;

    public FaultMessage(Message delegate, @Nullable QName detailEntryName) {
        super(delegate);
        this.detailEntryName = detailEntryName;
    }

    @Override
    public @Nullable QName getFirstDetailEntryName() {
        return detailEntryName;
    }

}
