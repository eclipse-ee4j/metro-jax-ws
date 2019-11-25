/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

import javax.activation.DataHandler;
import javax.xml.ws.handler.MessageContext;
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

    public void setScope(String name, Scope scope) {
        if(!containsKey(name))
            throw new IllegalArgumentException("Property " + name + " does not exist.");
        if(scope == Scope.APPLICATION) {
            handlerScopeProps.remove(name);
        } else {
            handlerScopeProps.add(name);

        }
    }

    public Scope getScope(String name) {
        if(!containsKey(name))
            throw new IllegalArgumentException("Property " + name + " does not exist.");
        if(handlerScopeProps.contains(name)) {
            return Scope.HANDLER;
        } else {
            return Scope.APPLICATION;
        }
    }

    public int size() {
        return asMapIncludingInvocationProperties.size();
    }

    public boolean isEmpty() {
        return asMapIncludingInvocationProperties.isEmpty();
    }

    public boolean containsKey(Object key) {
        return asMapIncludingInvocationProperties.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return asMapIncludingInvocationProperties.containsValue(value);
    }

    public Object put(String key, Object value) {
        if (!asMapIncludingInvocationProperties.containsKey(key)) {
            //new property, default to Scope.HANDLER
            handlerScopeProps.add(key);
        }
        return asMapIncludingInvocationProperties.put(key, value);
    }
    public Object get(Object key) {
        if(key == null)
            return null;
        Object value = asMapIncludingInvocationProperties.get(key);
        //add the attachments from the Message to the corresponding attachment property
        if(key.equals(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS) ||
            key.equals(MessageContext.INBOUND_MESSAGE_ATTACHMENTS)){
            Map<String, DataHandler> atts = (Map<String, DataHandler>) value;
            if(atts == null)
                atts = new HashMap<String, DataHandler>();
            AttachmentSet attSet = packet.getMessage().getAttachments();
            for(Attachment att : attSet){
                String cid = att.getContentId();
                if (cid.indexOf("@jaxws.sun.com") == -1) {
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

    public void putAll(Map<? extends String, ? extends Object> t) {
        for(String key: t.keySet()) {
            if(!asMapIncludingInvocationProperties.containsKey(key)) {
                //new property, default to Scope.HANDLER
                handlerScopeProps.add(key);
            }
        }
        asMapIncludingInvocationProperties.putAll(t);
    }

    public void clear() {
        asMapIncludingInvocationProperties.clear();
    }
    public Object remove(Object key){
        handlerScopeProps.remove(key);
        return asMapIncludingInvocationProperties.remove(key);
    }
    public Set<String> keySet() {
        return asMapIncludingInvocationProperties.keySet();
    }
    public Set<Map.Entry<String, Object>> entrySet(){
        return asMapIncludingInvocationProperties.entrySet();
    }
    public Collection<Object> values() {
        return asMapIncludingInvocationProperties.values();
    }
}
