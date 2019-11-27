/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.handler;

import com.sun.istack.Nullable;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.handler.MessageHandlerContext;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;

import java.util.Set;

/**
 * @author Rama Pulavarthi
 */
public class MessageHandlerContextImpl extends MessageUpdatableContext implements MessageHandlerContext {
    private @Nullable SEIModel seiModel;
    private Set<String> roles;
    private WSBinding binding;
    private @Nullable WSDLPort wsdlModel;

    public MessageHandlerContextImpl(@Nullable SEIModel seiModel, WSBinding binding, @Nullable WSDLPort wsdlModel, Packet packet, Set<String> roles) {
        super(packet);
        this.seiModel = seiModel;
        this.binding = binding;
        this.wsdlModel = wsdlModel;
        this.roles = roles;
    }
    public Message getMessage() {
        return packet.getMessage();
    }

    public void setMessage(Message message) {
        packet.setMessage(message);
    }

    public Set<String> getRoles() {
        return roles;
    }

    public WSBinding getWSBinding() {
        return binding;
    }

    public @Nullable SEIModel getSEIModel() {
        return seiModel;
    }

    public @Nullable WSDLPort getPort() {
        return wsdlModel;
    }
    
    void updateMessage() {
       // Do Nothing
    }

    void setPacketMessage(Message newMessage) {
        setMessage(newMessage);
    }
}
