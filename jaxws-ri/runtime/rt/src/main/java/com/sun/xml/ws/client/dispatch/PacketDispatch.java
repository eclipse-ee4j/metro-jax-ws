/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client.dispatch;

import com.sun.istack.Nullable;
import com.sun.xml.ws.api.addressing.WSEndpointReference;
import com.sun.xml.ws.api.client.ThrowableInPacketCompletionFeature;
import com.sun.xml.ws.api.client.WSPortInfo;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.pipe.Fiber;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.binding.BindingImpl;
import com.sun.xml.ws.client.WSServiceDelegate;

import javax.xml.namespace.QName;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service.Mode;

/**
 * {@link Dispatch} implementation for {@link Packet}.
 * 
 * @since 2.2.6
 */
public class PacketDispatch extends DispatchImpl<Packet> {
    private final boolean isDeliverThrowableInPacket;
	
    @Deprecated
    public PacketDispatch(QName port, WSServiceDelegate owner, Tube pipe, BindingImpl binding, @Nullable WSEndpointReference epr) {
    	super(port, Mode.MESSAGE, owner, pipe, binding, epr);
    	isDeliverThrowableInPacket = calculateIsDeliverThrowableInPacket(binding);
    }


    public PacketDispatch(WSPortInfo portInfo, Tube pipe, BindingImpl binding, WSEndpointReference epr) {
        this(portInfo, pipe, binding, epr, true);
    }

    public PacketDispatch(WSPortInfo portInfo, Tube pipe, BindingImpl binding, WSEndpointReference epr, boolean allowFaultResponseMsg) {
        super(portInfo, Mode.MESSAGE, pipe, binding, epr, allowFaultResponseMsg);
        isDeliverThrowableInPacket = calculateIsDeliverThrowableInPacket(binding);
    }

    public PacketDispatch(WSPortInfo portInfo, BindingImpl binding, WSEndpointReference epr) {
        super(portInfo, Mode.MESSAGE, binding, epr, true);
        isDeliverThrowableInPacket = calculateIsDeliverThrowableInPacket(binding);
    }

    private boolean calculateIsDeliverThrowableInPacket(BindingImpl binding) {
        return binding.isFeatureEnabled(ThrowableInPacketCompletionFeature.class);
    }
    
    @Override
    protected void configureFiber(Fiber fiber) {
        fiber.setDeliverThrowableInPacket(isDeliverThrowableInPacket);
    }
    
    @Override
    Packet toReturnValue(Packet response) {
        return response;
    }

    @Override
    Packet createPacket(Packet request) {
        return request;
    }


}
