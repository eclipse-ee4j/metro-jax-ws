/*
 * Copyright (c) 2006, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.http.client;

import java.net.URI;

/**
 * CookiePolicy implementations decide which cookies should be accepted
 * and which should be rejected. Three pre-defined policy implementations
 * are provided, namely ACCEPT_ALL, ACCEPT_NONE and ACCEPT_ORIGINAL_SERVER.
 *
 * <p>See RFC 2965 sec. 3.3 &amp; 7 for more detail.
 *
 * @version %I%, %E%
 * @author Edward Wang
 * @since 1.6
 */
public interface CookiePolicy {
    /**
     * One pre-defined policy which accepts all cookies.
     */
    CookiePolicy ACCEPT_ALL = new CookiePolicy(){
        @Override
        public boolean shouldAccept(URI uri, HttpCookie cookie) {
            return true;
        }
    };

    /**
     * One pre-defined policy which accepts no cookies.
     */
    CookiePolicy ACCEPT_NONE = new CookiePolicy(){
        @Override
        public boolean shouldAccept(URI uri, HttpCookie cookie) {
            return false;
        }
    };

    /**
     * One pre-defined policy which only accepts cookies from original server.
     */
    CookiePolicy ACCEPT_ORIGINAL_SERVER  = new CookiePolicy(){
        @Override
        public boolean shouldAccept(URI uri, HttpCookie cookie) {
            return HttpCookie.domainMatches(cookie.getDomain(), uri.getHost());
        }
    };


    /**
     * Will be called to see whether or not this cookie should be accepted.
     *
     * @param uri       the URI to consult accept policy with
     * @param cookie    the HttpCookie object in question
     * @return          <code>true</code> if this cookie should be accepted;
     *                  otherwise, <code>false</code>
     */
    boolean shouldAccept(URI uri, HttpCookie cookie);
}

