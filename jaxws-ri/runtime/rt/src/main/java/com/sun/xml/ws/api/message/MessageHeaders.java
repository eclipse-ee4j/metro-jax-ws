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
    public void understood(Header header);
    public void understood(QName name);
    public void understood(String nsUri, String localName);
    public Header get(String nsUri, String localName, boolean markAsUnderstood);
    public Header get(QName name, boolean markAsUnderstood);
    public Iterator<Header> getHeaders(String nsUri, String localName, final boolean markAsUnderstood);
    /**
     * Get all headers in specified namespace
     */
    public Iterator<Header> getHeaders(String nsUri, final boolean markAsUnderstood);
    public Iterator<Header> getHeaders(QName headerName, final boolean markAsUnderstood);
    public Iterator<Header> getHeaders();    
    public boolean hasHeaders();    
    public boolean add(Header header);
    public Header remove(QName name);
    public Header remove(String nsUri, String localName);
    //DONT public Header remove(Header header);
    public void replace(Header old, Header header);
    
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
    public boolean addOrReplace(Header header);
    
    /**
     * Return a Set of QNames of headers that have been explicitly marked as understood.
     * If none have been marked, this method could return null
     */
    public Set<QName> getUnderstoodHeaders();
    
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
    public Set<QName> getNotUnderstoodHeaders(Set<String> roles, Set<QName> knownHeaders, WSBinding binding);
    
    /**
     * True if the header has been explicitly marked understood, false otherwise
     */
    public boolean isUnderstood(Header header);
    
    /**
     * True if the header has been explicitly marked understood, false otherwise
     */
    public boolean isUnderstood(QName header);
    
    /**
     * True if the header has been explicitly marked understood, false otherwise
     */
    public boolean isUnderstood(String nsUri, String header);
    
    /**
     * Returns <code>Header</code> instances in a <code>List</code>.
     * @return <code>List</code> containing <code>Header</code> instances
     */
    public List<Header> asList();
}
