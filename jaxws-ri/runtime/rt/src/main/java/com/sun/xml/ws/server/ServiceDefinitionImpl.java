/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.server;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.server.SDDocument;
import com.sun.xml.ws.api.server.SDDocumentFilter;
import com.sun.xml.ws.api.server.ServiceDefinition;
import com.sun.xml.ws.wsdl.SDDocumentResolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * {@link ServiceDefinition} implementation.
 *
 * <p>
 * You construct a {@link ServiceDefinitionImpl} by first constructing
 * a list of {@link SDDocumentImpl}s.
 *
 * @author Kohsuke Kawaguchi
 */
public final class ServiceDefinitionImpl implements ServiceDefinition, SDDocumentResolver {
    private final Collection<SDDocumentImpl> docs;

    private final Map<String,SDDocumentImpl> bySystemId;
    private final @NotNull SDDocumentImpl primaryWsdl;

    /**
     * Set when {@link WSEndpointImpl} is created.
     */
    /*package*/ WSEndpointImpl<?> owner;

    /*package*/ final List<SDDocumentFilter> filters = new ArrayList<SDDocumentFilter>();

    /**
     * @param docs
     *      List of {@link SDDocumentImpl}s to form the description.
     *      There must be at least one entry.
     *      The first document is considered {@link #getPrimary() primary}.
     */
    public ServiceDefinitionImpl(Collection<SDDocumentImpl> docs, @NotNull SDDocumentImpl primaryWsdl) {
        assert docs.contains(primaryWsdl);
        this.docs = docs;
        this.primaryWsdl = primaryWsdl;
        this.bySystemId = new HashMap<String, SDDocumentImpl>();
    }

    private boolean isInitialized = false;
    
    private synchronized void init() {
        if (isInitialized)
            return;
        isInitialized = true;
        
        for (SDDocumentImpl doc : docs) {
            bySystemId.put(doc.getURL().toExternalForm(),doc);
            doc.setFilters(filters);
            doc.setResolver(this);
        }
    }
    
    /**
     * The owner is set when {@link WSEndpointImpl} is created.
     */
    /*package*/ void setOwner(WSEndpointImpl<?> owner) {
        assert owner!=null && this.owner==null;
        this.owner = owner;
    }

    public @NotNull SDDocument getPrimary() {
        return primaryWsdl;
    }

    public void addFilter(SDDocumentFilter filter) {
        filters.add(filter);
    }

    public Iterator<SDDocument> iterator() {
        init();
        return (Iterator)docs.iterator();
    }

    /**
     * Gets the {@link SDDocumentImpl} whose {@link SDDocumentImpl#getURL()}
     * returns the specified value.
     *
     * @return
     *      null if none is found.
     */
    public SDDocument resolve(String systemId) {
        init();
        return bySystemId.get(systemId);
    }
}
