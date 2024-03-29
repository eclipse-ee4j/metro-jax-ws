/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.server;

import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.message.AttachmentSet;
import com.sun.xml.ws.api.message.Attachment;

import java.util.*;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.WebServiceContext;
import jakarta.activation.DataHandler;

/**
 * Implements {@link WebServiceContext}'s {@link MessageContext} on top of {@link Packet}.
 *
 * <p>
 * This class creates a {@link Map} view for APPLICATION scoped properties that
 * gets exposed to endpoint implementations during the invocation
 * of web methods. The implementations access this map using
 * WebServiceContext.getMessageContext().
 *
 * <p>
 * Some of the {@link Map} methods requre this class to
 * build the complete {@link Set} of properties, but we
 * try to avoid that as much as possible.
 *
 *
 * @author Jitendra Kotamraju
 */
public final class EndpointMessageContextImpl extends AbstractMap<String,Object> implements MessageContext {

    /**
     * Lazily computed.
     */
    private Set<Map.Entry<String,Object>> entrySet;
    private final Packet packet;

    /**
     * @param packet
     *      The {@link Packet} to wrap.
     */
    public EndpointMessageContextImpl(Packet packet) {
        this.packet = packet;
    }

    @Override
    @SuppressWarnings("element-type-mismatch")
    public Object get(Object key) {
        if (packet.supports(key)) {
            return packet.get(key);    // strongly typed
        }
        if (packet.getHandlerScopePropertyNames(true).contains(key)) {
            return null;            // no such application-scope property
        }
        Object value =  packet.invocationProperties.get(key);

        //add the attachments from the Message to the corresponding attachment property
        if(key.equals(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS) ||
                key.equals(MessageContext.INBOUND_MESSAGE_ATTACHMENTS)){
            Map<String, DataHandler> atts = (Map<String, DataHandler>) value;
            if(atts == null)
                atts = new HashMap<>();
            AttachmentSet attSet = packet.getMessage().getAttachments();
            for(Attachment att : attSet){
                atts.put(att.getContentId(), att.asDataHandler());
            }
            return atts;
        }
        return value;
    }

    @Override
    public Object put(String key, Object value) {
        if (packet.supports(key)) {
            return packet.put(key, value);     // strongly typed
        }
        Object old = packet.invocationProperties.get(key);
        if (old != null) {
            if (packet.getHandlerScopePropertyNames(true).contains(key)) {
                throw new IllegalArgumentException("Cannot overwrite property in HANDLER scope");
            }
            // Overwrite existing APPLICATION scoped property
            packet.invocationProperties.put(key, value);
            return old;
        }
        // No existing property. So Add a new property
        packet.invocationProperties.put(key, value);
        return null;
    }

    @Override
    @SuppressWarnings("element-type-mismatch")
    public Object remove(Object key) {
         if (packet.supports(key)) {
             return packet.remove(key);
        }
        Object old = packet.invocationProperties.get(key);
        if (old != null) {
            if (packet.getHandlerScopePropertyNames(true).contains(key)) {
                throw new IllegalArgumentException("Cannot remove property in HANDLER scope");
            }
            // Remove existing APPLICATION scoped property
            packet.invocationProperties.remove(key);
            return old;
        }
        // No existing property.
        return null;
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        if (entrySet == null) {
            entrySet = new EntrySet();
        }
        return entrySet;
    }

    @Override
    public void setScope(String name, MessageContext.Scope scope) {
        throw new UnsupportedOperationException(
                "All the properties in this context are in APPLICATION scope. Cannot do setScope().");
    }

    @Override
    public MessageContext.Scope getScope(String name) {
        throw new UnsupportedOperationException(
                "All the properties in this context are in APPLICATION scope. Cannot do getScope().");
    }

    private class EntrySet extends AbstractSet<Map.Entry<String, Object>> {

        @Override
        public Iterator<Map.Entry<String, Object>> iterator() {
            final Iterator<Map.Entry<String, Object>> it = createBackupMap().entrySet().iterator();

            return new Iterator<>() {
                Map.Entry<String, Object> cur;

                @Override
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override
                public Map.Entry<String, Object> next() {
                    cur = it.next();
                    return cur;
                }

                @Override
                public void remove() {
                    it.remove();
                    EndpointMessageContextImpl.this.remove(cur.getKey());
                }
            };
        }

        @Override
        public int size() {
            return createBackupMap().size();
        }

    }

    private Map<String, Object> createBackupMap() {
        Map<String, Object> backupMap = new HashMap<>(packet.asMap());
        Set<String> handlerProps = packet.getHandlerScopePropertyNames(true);
        for(Map.Entry<String, Object> e : packet.invocationProperties.entrySet()) {
            if (!handlerProps.contains(e.getKey())) {
                backupMap.put(e.getKey(), e.getValue());
            }
        }
        return backupMap;
    }

}
