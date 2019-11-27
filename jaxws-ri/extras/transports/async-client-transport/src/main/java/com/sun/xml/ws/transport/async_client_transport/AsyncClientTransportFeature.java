/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.async_client_transport;

import javax.xml.ws.WebServiceFeature;

/**
 * @author Rama.Pulavarthi@sun.com
 */
public class AsyncClientTransportFeature extends WebServiceFeature{
    /**
     * Constant value identifying the NonAnonymousFeature
     */
    public static final String ID = "http://java.sun.com/xml/ns/jaxws/async/client/transport";
    private NonAnonymousResponsesReceiver receiver;
    private String nonanonAddress;

    public AsyncClientTransportFeature() {
        this(null,null);
    }
    public AsyncClientTransportFeature(String nonanonAddress, NonAnonymousResponsesReceiver receiver) {
        this.receiver = receiver;
        this.nonanonAddress = nonanonAddress;
    }

    public NonAnonymousResponsesReceiver getReceiver() {
        return receiver;
    }

    public String getID() {
        return ID;
    }

    public boolean isEnabled() {
        return true;
    }

    public String getNonanonAddress() {
        return nonanonAddress;
    }

}
