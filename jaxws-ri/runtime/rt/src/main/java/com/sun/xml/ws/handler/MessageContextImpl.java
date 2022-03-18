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

import com.sun.xml.ws.api.message.Attachment;
import com.sun.xml.ws.api.message.AttachmentSet;
import com.sun.xml.ws.api.message.Packet;

import jakarta.activation.DataHandler;
import jakarta.xml.ws.handler.MessageContext;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 *
 * @author WS Development Team
 */

class MessageContextImpl implements MessageContext {
    private final Set<String> handlerScopeProps;
    private final Packet packet;
    private final Map<String, Object> asMapIncludingInvocationProperties;

    /** Creates a new instance of MessageContextImpl */
    public MessageContextImpl(Packet packet) {
        this.packet = packet;
        this.asMapIncludingInvocationProperties = packet.asMapIncludingInvocationProperties();
        this.handlerScopeProps =  packet.getHandlerScopePropertyNames(false);
    }
    
    protected void updatePacket() {
        throw new UnsupportedOperationException("wrong call");
    }

    @Override
    public void setScope(String name, Scope scope) {
        if(!containsKey(name))
            throw new IllegalArgumentException("Property " + name + " does not exist.");
        if(scope == Scope.APPLICATION) {
            handlerScopeProps.remove(name);
        } else {
            handlerScopeProps.add(name);

        }
    }

    @Override
    public Scope getScope(String name) {
        if(!containsKey(name))
            throw new IllegalArgumentException("Property " + name + " does not exist.");
        if(handlerScopeProps.contains(name)) {
            return Scope.HANDLER;
        } else {
            return Scope.APPLICATION;
        }
    }

    @Override
    public int size() {
        return asMapIncludingInvocationProperties.size();
    }

    @Override
    public boolean isEmpty() {
        return asMapIncludingInvocationProperties.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return asMapIncludingInvocationProperties.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return asMapIncludingInvocationProperties.containsValue(value);
    }

    @Override
    public Object put(String key, Object value) {
        if (!asMapIncludingInvocationProperties.containsKey(key)) {
            //new property, default to Scope.HANDLER
            handlerScopeProps.add(key);
        }
        return asMapIncludingInvocationProperties.put(key, value);
    }
    @Override
    public Object get(Object key) {
        if(key == null)
            return null;
        Object value = asMapIncludingInvocationProperties.get(key);
        //add the attachments from the Message to the corresponding attachment property
        if(key.equals(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS) ||
            key.equals(MessageContext.INBOUND_MESSAGE_ATTACHMENTS)){
            Map<String, DataHandler> atts = (Map<String, DataHandler>) value;
            if(atts == null)
                atts = new HashMap<>();
            AttachmentSet attSet = packet.getMessage().getAttachments();
            for(Attachment att : attSet){
                String cid = att.getContentId();
                if (!cid.contains("@jaxws.sun.com")) {
                    Object a = atts.get(cid);
                    if (a == null) {
                        a = atts.get("<" + cid + ">");
                        if (a == null) atts.put(att.getContentId(), att.asDataHandler());
                    }
                } else {
                    atts.put(att.getContentId(), att.asDataHandler());
                }
            }
            return atts;
        }
        return value;
    }

    @Override
    public void putAll(Map<? extends String, ?> t) {
        for(String key: t.keySet()) {
            if(!asMapIncludingInvocationProperties.containsKey(key)) {
                //new property, default to Scope.HANDLER
                handlerScopeProps.add(key);
            }
        }
        asMapIncludingInvocationProperties.putAll(t);
    }

    @Override
    public void clear() {
        asMapIncludingInvocationProperties.clear();
    }
    @Override
    public Object remove(Object key){
        handlerScopeProps.remove(key);
        return asMapIncludingInvocationProperties.remove(key);
    }
    @Override
    public Set<String> keySet() {
        return asMapIncludingInvocationProperties.keySet();
    }
    @Override
    public Set<Map.Entry<String, Object>> entrySet(){
        return asMapIncludingInvocationProperties.entrySet();
    }
    @Override
    public Collection<Object> values() {
        return asMapIncludingInvocationProperties.values();
    }
}
