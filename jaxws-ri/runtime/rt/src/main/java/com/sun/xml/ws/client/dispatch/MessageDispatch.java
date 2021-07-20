/*
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package com.sun.xml.ws.client.dispatch;

import com.sun.xml.ws.api.addressing.WSEndpointReference;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.client.WSPortInfo;
import com.sun.xml.ws.binding.BindingImpl;
import com.sun.xml.ws.client.WSServiceDelegate;

import javax.xml.namespace.QName;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service.Mode;

/**
 * {@link Dispatch} implementation for {@link Message}.
 *
 * @author Kohsuke Kawaguchi
 * @since 2.1.1
 */
public class MessageDispatch extends DispatchImpl<Message> {

    @Deprecated
    public MessageDispatch(QName port, WSServiceDelegate service, Tube pipe, BindingImpl binding, WSEndpointReference epr) {
        super(port, Mode.MESSAGE, service, pipe, binding, epr);
    }

    public MessageDispatch(WSPortInfo portInfo, BindingImpl binding, WSEndpointReference epr) {
        super(portInfo, Mode.MESSAGE, binding, epr, true);
    }

    @Override
    Message toReturnValue(Packet response) {
        return response.getMessage();
    }

    @Override
    Packet createPacket(Message msg) {
        return new Packet(msg);
    }
}
