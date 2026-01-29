/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.httpspi.servlet;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.xml.ws.spi.http.HttpExchange;

import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {@link HttpExchange#getRequestHeaders} impl for servlet container.
 *
 * @author Jitendra Kotamraju
 */
class ExchangeRequestHeaders extends Headers {
    private final HttpServletRequest request;
    private boolean useMap = false;

    ExchangeRequestHeaders(HttpServletRequest request) {
        this.request = request;
    }

    private void convertToMap() {
        if (!useMap) {
            Enumeration<String> e = request.getHeaderNames();
            while(e.hasMoreElements()) {
                String name = e.nextElement();
                Enumeration<String> ev = request.getHeaders(name);
                while(ev.hasMoreElements()) {
                    super.add(name, ev.nextElement());
                }
            }
            useMap = true;
        }
    }

    @Override
    public int size() {
        convertToMap();
        return super.size();
    }

    @Override
    public boolean isEmpty() {
        convertToMap();
        return super.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        if (!(key instanceof String)) {
            return false;
        }
        return useMap ? super.containsKey(key) : request.getHeader((String)key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        convertToMap();
        return super.containsValue(value);
    }

    @Override
    public List<String> get(Object key) {
        convertToMap();
        return super.get(key);
    }

    @Override
    public String getFirst(String key) {
        return useMap ? super.getFirst(key) : request.getHeader(key);
    }

    @Override
    public List<String> put(String key, List<String> value) {
        convertToMap();
        return super.put(key, value);
    }

    @Override
    public void add(String key, String value) {
        convertToMap();
        super.add(key, value);
    }

    @Override
    public void set(String key, String value) {
        convertToMap();
        super.set(key, value);
    }
    @Override
    public List<String> remove(Object key) {
        convertToMap();
        return super.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends List<String>> t) {
        convertToMap();
        super.putAll(t);
    }

    @Override
    public void clear() {
        convertToMap();
        super.clear();
    }

    @Override
    public Set<String> keySet() {
        convertToMap();
        return super.keySet();
    }

    @Override
    public Collection<List<String>> values() {
        convertToMap();
        return super.values();
    }

    @Override
    public Set<Entry<String, List<String>>> entrySet() {
        convertToMap();
        return super.entrySet();
    }

    @Override
    public String toString() {
        convertToMap();
        return super.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
