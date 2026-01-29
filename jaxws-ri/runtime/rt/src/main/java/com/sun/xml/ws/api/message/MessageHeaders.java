/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.message;

import java.util.List;
import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;

import com.sun.xml.ws.api.WSBinding;

/**
 * Interface representing all the headers of a {@link Message}
 */
public interface MessageHeaders {
    void understood(Header header);
    void understood(QName name);
    void understood(String nsUri, String localName);
    Header get(String nsUri, String localName, boolean markAsUnderstood);
    Header get(QName name, boolean markAsUnderstood);
    Iterator<Header> getHeaders(String nsUri, String localName, final boolean markAsUnderstood);
    /**
     * Get all headers in specified namespace
     */
    Iterator<Header> getHeaders(String nsUri, final boolean markAsUnderstood);
    Iterator<Header> getHeaders(QName headerName, final boolean markAsUnderstood);
    Iterator<Header> getHeaders();
    boolean hasHeaders();
    boolean add(Header header);
    Header remove(QName name);
    Header remove(String nsUri, String localName);
    //DONT public Header remove(Header header);
    void replace(Header old, Header header);
    
    /**
     * Replaces an existing {@link Header} or adds a new {@link Header}.
     *
     * <p>
     * Order doesn't matter in headers, so this method
     * does not make any guarantee as to where the new header
     * is inserted.
     *
     * @return
     *      always true. Don't use the return value.
     */
    boolean addOrReplace(Header header);
    
    /**
     * Return a Set of QNames of headers that have been explicitly marked as understood.
     * If none have been marked, this method could return null
     */
    Set<QName> getUnderstoodHeaders();
    
    /**
     * Returns a Set of QNames of headers that satisfy ALL the following conditions:
     * (a) Have mustUnderstand = true 
     * (b) have NOT been explicitly marked as understood
     * (c) If roles argument is non-null, the header has isIgnorable = false 
     * for the roles argument and SOAP version  
     * (d) If non-null binding is passed in, are NOT understood by the binding
     * (e) If (d) is met, the header is NOT in the knownHeaders list passed in
     *
     */
    Set<QName> getNotUnderstoodHeaders(Set<String> roles, Set<QName> knownHeaders, WSBinding binding);
    
    /**
     * True if the header has been explicitly marked understood, false otherwise
     */
    boolean isUnderstood(Header header);
    
    /**
     * True if the header has been explicitly marked understood, false otherwise
     */
    boolean isUnderstood(QName header);
    
    /**
     * True if the header has been explicitly marked understood, false otherwise
     */
    boolean isUnderstood(String nsUri, String header);
    
    /**
     * Returns <code>Header</code> instances in a <code>List</code>.
     * @return <code>List</code> containing <code>Header</code> instances
     */
    List<Header> asList();
}
