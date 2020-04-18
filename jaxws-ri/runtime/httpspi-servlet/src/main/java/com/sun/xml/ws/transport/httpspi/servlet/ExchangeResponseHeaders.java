/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.httpspi.servlet;

import javax.servlet.http.HttpServletResponse;
import jakarta.xml.ws.spi.http.HttpExchange;
import java.util.*;

/**
 * {@link HttpExchange#getResponseHeaders} impl for servlet container.
 *
 * @author Jitendra Kotamraju
 */
class ExchangeResponseHeaders extends Headers {
    private final HttpServletResponse response;

    ExchangeResponseHeaders(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return super.containsValue(value);
    }

    @Override
    public List<String> get(Object key) {
        return super.get(key);
    }

    @Override
    public String getFirst(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> put(String key, List<String> value) {
        for(String val : value) {
            response.addHeader(key, val);
        }
        return super.put(key, value);
    }

    @Override
    public void add(String key, String value) {
        response.addHeader(key, value);
        super.add(key, value);
    }

    @Override
    public void set(String key, String value) {
        response.addHeader(key, value);
        super.set(key, value);
    }

    @Override
    public List<String> remove(Object key) {
        //TODO how to delete a header in response
        return super.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends List<String>> t) {
        // TODO
        super.putAll(t);
    }

    @Override
    public void clear() {
        // TODO
        super.clear();
    }

    @Override
    public Set<String> keySet() {
        return super.keySet();
    }

    @Override
    public Collection<List<String>> values() {
        return super.values();
    }

    @Override
    public Set<Entry<String, List<String>>> entrySet() {
        return super.entrySet();
    }

    @Override
    public String toString() {
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
