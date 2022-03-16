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

public abstract class MessageContextFactory
{   
    private static final MessageContextFactory DEFAULT = new com.sun.xml.ws.api.message.MessageContextFactory(new WebServiceFeature[0]);
    
    protected com.sun.xml.ws.api.message.saaj.SAAJFactory saajFactory = null;

    protected abstract MessageContextFactory newFactory(WebServiceFeature ... f);
    
    public abstract MessageContext createContext();

    public abstract MessageContext createContext(SOAPMessage m);
    
    public abstract MessageContext createContext(Source m);

    public abstract MessageContext createContext(Source m, EnvelopeStyle.Style envelopeStyle);
    
    public abstract MessageContext createContext(InputStream in, String contentType) throws IOException;

    /**
     * @deprecated http://java.net/jira/browse/JAX_WS-1077
     */
    @Deprecated 
    public abstract MessageContext createContext(InputStream in, MimeHeaders headers) throws IOException;
    
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

    @Deprecated
    public abstract MessageContext doCreate();

    @Deprecated
    public abstract MessageContext doCreate(SOAPMessage m);

    //public abstract MessageContext doCreate(InputStream x);

    @Deprecated
    public abstract MessageContext doCreate(Source x, SOAPVersion soapVersion);

    @Deprecated
    public static MessageContext create(final ClassLoader... classLoader) {
        return serviceFinder(classLoader,
                             new Creator() {
                                 public MessageContext create(final MessageContextFactory f) {
                                     return f.doCreate();
                                 }
                             });
    }

    @Deprecated
    public static MessageContext create(final SOAPMessage m, final ClassLoader... classLoader) {
        return serviceFinder(classLoader,
                             new Creator() {
                                 public MessageContext create(final MessageContextFactory f) {
                                     return f.doCreate(m);
                                 }
                             });
    }

    @Deprecated
    public static MessageContext create(final Source m, final SOAPVersion v, final ClassLoader... classLoader) {
        return serviceFinder(classLoader,
                             new Creator() {
                                 public MessageContext create(final MessageContextFactory f) {
                                     return f.doCreate(m, v);
                                 }
                             });
    }

    @Deprecated
    private static MessageContext serviceFinder(final ClassLoader[] classLoader, final Creator creator) {
        final ClassLoader cl = classLoader.length == 0 ? null : classLoader[0];
        for (MessageContextFactory factory : ServiceFinder.find(MessageContextFactory.class, cl)) {
            final MessageContext messageContext = creator.create(factory);
            if (messageContext != null)
                return messageContext;
        }
        return creator.create(DEFAULT);
    }

    @Deprecated
    private interface Creator {
        MessageContext create(MessageContextFactory f);
    }
    
    public void setSAAJFactory(com.sun.xml.ws.api.message.saaj.SAAJFactory saajFactory) {
        this.saajFactory = saajFactory;
    }
}

