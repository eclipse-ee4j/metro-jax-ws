/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client;

import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.message.AttachmentSet;
import com.sun.xml.ws.api.message.Attachment;

import javax.xml.ws.handler.MessageContext;
import javax.activation.DataHandler;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implements "response context" on top of {@link Packet}.
 *
 * <p>
 * This class creates a read-only {@link Map} view that
 * gets exposed to client applications after an invocation
 * is complete.
 *
 * <p>
 * The design goal of this class is to make it efficient
 * to create a new {@link ResponseContext}, at the expense
 * of making some {@link Map} operations slower. This is
 * justified because the response context is mostly just
 * used to query a few known values, and operations like
 * enumeration isn't likely.
 *
 * <p>
 * Some of the {@link Map} methods requre this class to
 * build the complete {@link Set} of properties, but we
 * try to avoid that as much as possible.
 *
 *
 * <pre>
 * TODO: are we exposing all strongly-typed fields, or
 * do they have appliation/handler scope notion?
 * </pre>
 *
 * @author Kohsuke Kawaguchi
 */
@SuppressWarnings({"SuspiciousMethodCalls"})    // IDE doesn't like me calling Map methods with key typed as Object
public class ResponseContext extends AbstractMap<String,Object> {
    private final Packet packet;

    /**
     * Lazily computed.
     */
    private Set<Map.Entry<String,Object>> entrySet;

    /**
     * @param packet
     *      The {@link Packet} to wrap.
     */
    public ResponseContext(Packet packet) {
        this.packet = packet;
    }

    public boolean containsKey(Object key) {
        if(packet.supports(key))
            return packet.containsKey(key);    // strongly typed

        if(packet.invocationProperties.containsKey(key))
            // if handler-scope, hide it
            return !packet.getHandlerScopePropertyNames(true).contains(key);

        return false;
    }

    public Object get(Object key) {
        if(packet.supports(key))
            return packet.get(key);    // strongly typed

        if(packet.getHandlerScopePropertyNames(true).contains(key))
            return null;            // no such application-scope property

        Object value =  packet.invocationProperties.get(key);

        //add the attachments from the Message to the corresponding attachment property
        if(key.equals(MessageContext.INBOUND_MESSAGE_ATTACHMENTS)){
            Map<String, DataHandler> atts = (Map<String, DataHandler>) value;
            if(atts == null)
                atts = new HashMap<String, DataHandler>();
            AttachmentSet attSet = packet.getMessage().getAttachments();
            for(Attachment att : attSet){
                atts.put(att.getContentId(), att.asDataHandler());
            }
            return atts;
        }
        return value;
    }

    public Object put(String key, Object value) {
        // response context is read-only
        throw new UnsupportedOperationException();
    }

    public Object remove(Object key) {
        // response context is read-only
        throw new UnsupportedOperationException();
    }

    public void putAll(Map<? extends String, ? extends Object> t) {
        // response context is read-only
        throw new UnsupportedOperationException();
    }

    public void clear() {
        // response context is read-only
        throw new UnsupportedOperationException();
    }

    public Set<Entry<String, Object>> entrySet() {
        if(entrySet==null) {
            // this is where the worst case happens. we have to clone the whole properties
            // to get this view.

            // use TreeSet so that toString() sort them nicely. It's easier for apps.
            Map<String,Object> r = new HashMap<String,Object>();

            // export application-scope properties
            r.putAll(packet.invocationProperties);

            // hide handler-scope properties
            r.keySet().removeAll(packet.getHandlerScopePropertyNames(true));

            // and all strongly typed ones
            r.putAll(packet.createMapView());

            entrySet = Collections.unmodifiableSet(r.entrySet());
        }

        return entrySet;
    }

}
