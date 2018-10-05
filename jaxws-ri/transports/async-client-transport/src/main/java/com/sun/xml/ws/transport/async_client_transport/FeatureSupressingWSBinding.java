/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.async_client_transport;

import com.oracle.webservices.api.message.MessageContextFactory;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.BindingID;
import com.sun.xml.ws.api.WSFeatureList;
import com.sun.xml.ws.api.addressing.AddressingVersion;
import com.sun.xml.ws.binding.WebServiceFeatureList;
import com.sun.istack.NotNull;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.WebServiceFeature;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

/**
 * Supresses a feature from WSBinding 
 * @author Rama.Pulavarthi@sun.com
 */
public class FeatureSupressingWSBinding implements WSBinding {
    WSBinding original;
    final WSFeatureList newFtrs;
    public FeatureSupressingWSBinding(Class<? extends WebServiceFeature> supressedftr, WSBinding binding) {
        this.original = binding;
        WebServiceFeature[] origFtrs= original.getFeatures().toArray();
        List<WebServiceFeature> newFtrList =  new ArrayList<WebServiceFeature>();
        for(WebServiceFeature ftr: origFtrs) {
            if(!ftr.getClass().equals(supressedftr)) {
                newFtrList.add(ftr);
            }
        }
        newFtrs = new WebServiceFeatureList(newFtrList.toArray(new WebServiceFeature[newFtrList.size()]));
    }

    @Override
    public SOAPVersion getSOAPVersion() {
        return original.getSOAPVersion();
    }

    @Override
    public AddressingVersion getAddressingVersion() {
        return original.getAddressingVersion();
    }

    @Override
    public BindingID getBindingId() {
        return original.getBindingId();
    }

    @Override
    public List<Handler> getHandlerChain() {
        return original.getHandlerChain();
    }

    @Override
    public void setHandlerChain(List<Handler> chain) {
        original.setHandlerChain(chain);
    }
    
    @Override
    public Set<QName> getKnownHeaders() {
    	return original.getKnownHeaders();
    }
    
    @Override
    public boolean addKnownHeader(QName knownHeader) {
        return original.addKnownHeader(knownHeader);
    }

    @Override
    public String getBindingID() {
        return original.getBindingID();
    }

    @Override
    public boolean isFeatureEnabled(@NotNull Class<? extends WebServiceFeature> feature) {
        return newFtrs.isEnabled(feature);
    }

    @Override
    public <F extends WebServiceFeature> F getFeature(@NotNull Class<F> featureType) {
        return newFtrs.get(featureType);
    }

    @Override
    public WSFeatureList getFeatures() {
        return newFtrs;
    }

    @Override
    public boolean isOperationFeatureEnabled(Class<? extends WebServiceFeature> type, QName qname) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <F extends WebServiceFeature> F getOperationFeature(Class<F> type, QName qname) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WSFeatureList getOperationFeatures(QName qname) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WSFeatureList getInputMessageFeatures(QName qname) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WSFeatureList getOutputMessageFeatures(QName qname) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WSFeatureList getFaultMessageFeatures(QName qname, QName qname1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MessageContextFactory getMessageContextFactory() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
