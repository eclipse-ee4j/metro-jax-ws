/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.handler;

import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.Packet;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import jakarta.xml.ws.handler.MessageContext;

/**
 * The class represents a MessageContext(Properties) and also allows the Message to be modified.
 * This is extended by SOAPMessageContextImpl and LogicalMessageContextImpl.
 *
 * @author WS Development Team
 */
public abstract class MessageUpdatableContext implements MessageContext {
    final Packet packet;
    private MessageContextImpl ctxt;
    /** Creates a new instance of MessageUpdatableContext */
    public MessageUpdatableContext(Packet packet) {
        ctxt = new MessageContextImpl(packet);
        this.packet = packet;
    }
    
    /**
     * Updates Message in the packet with user modifications
     */
    abstract void updateMessage(); 
    
    /**
     * Updates Message in the packet with user modifications
     * returns the new packet's message
     */
    Message getPacketMessage(){
        updateMessage();
        return packet.getMessage();
    }
    
    /**
     * Sets Message in the packet
     * Any user modifications done on previous Message are lost. 
     */
    abstract void setPacketMessage(Message newMessage);
    
    /**
     * Updates the complete packet with user modfications to the message and 
     * properties cahnges in MessageContext
     */
    public final void updatePacket() {
        updateMessage();
    }
    
    MessageContextImpl getMessageContext() {
        return ctxt;
    }
    
    @Override
    public void setScope(String name, Scope scope) {
        ctxt.setScope(name, scope);
    }

    @Override
    public Scope getScope(String name) {
        return ctxt.getScope(name);
    }

    /* java.util.Map methods below here */

    @Override
    public void clear() {
        ctxt.clear();
    }

    @Override
    public boolean containsKey(Object obj) {
        return ctxt.containsKey(obj);
    }

    @Override
    public boolean containsValue(Object obj) {
        return ctxt.containsValue(obj);
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return ctxt.entrySet();
    }

    @Override
    public Object get(Object obj) {
        return ctxt.get(obj);
    }

    @Override
    public boolean isEmpty() {
        return ctxt.isEmpty();
    }

    @Override
    public Set<String> keySet() {
        return ctxt.keySet();
    }

    @Override
    public Object put(String str, Object obj) {
        return ctxt.put(str, obj);
    }

    @Override
    public void putAll(Map<? extends String, ?> map) {
        ctxt.putAll(map);
    }

    @Override
    public Object remove(Object obj) {
        return ctxt.remove(obj);
    }

    @Override
    public int size() {
        return ctxt.size();
    }

    @Override
    public Collection<Object> values() {
        return ctxt.values();
    }
    
}
