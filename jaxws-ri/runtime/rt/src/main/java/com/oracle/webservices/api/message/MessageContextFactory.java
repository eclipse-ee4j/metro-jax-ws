/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.oracle.webservices.api.message;

import java.io.IOException;
import java.io.InputStream;

import com.oracle.webservices.api.EnvelopeStyle;
import com.sun.xml.ws.api.SOAPVersion; // TODO leaking RI APIs
import com.sun.xml.ws.util.ServiceFinder;

import jakarta.xml.soap.MimeHeaders;
import jakarta.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import jakarta.xml.ws.WebServiceFeature;

public abstract class MessageContextFactory {

    /**
     * Default constructor.
     */
    public MessageContextFactory() {}

    protected com.sun.xml.ws.api.message.saaj.SAAJFactory saajFactory = null;

    protected abstract MessageContextFactory newFactory(WebServiceFeature ... f);
    
    public abstract MessageContext createContext();

    public abstract MessageContext createContext(SOAPMessage m);
    
    public abstract MessageContext createContext(Source m);

    public abstract MessageContext createContext(Source m, EnvelopeStyle.Style envelopeStyle);
    
    public abstract MessageContext createContext(InputStream in, String contentType) throws IOException;

    static public MessageContextFactory createFactory(WebServiceFeature ... f) {
        return createFactory(null, f);
    }
    
    static public MessageContextFactory createFactory(ClassLoader cl, WebServiceFeature ...f) {
        for (MessageContextFactory factory : ServiceFinder.find(MessageContextFactory.class, cl)) {
            MessageContextFactory newfac = factory.newFactory(f);
            if (newfac != null) return newfac;
        }
        return new com.sun.xml.ws.api.message.MessageContextFactory(f);
    }

    public void setSAAJFactory(com.sun.xml.ws.api.message.saaj.SAAJFactory saajFactory) {
        this.saajFactory = saajFactory;
    }
}
