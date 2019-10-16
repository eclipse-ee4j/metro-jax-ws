/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util.xml;

import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;

import org.jvnet.staxex.NamespaceContextEx;

public class NamespaceContextExAdaper implements NamespaceContextEx {
    
    private final NamespaceContext nsContext;

    public NamespaceContextExAdaper(NamespaceContext nsContext) {
        this.nsContext = nsContext;
    }

    @Override //Who wants this?
    public Iterator<Binding> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getNamespaceURI(String prefix) {
        return nsContext.getNamespaceURI(prefix);
    }

    @Override
    public String getPrefix(String namespaceURI) {
        return nsContext.getPrefix(namespaceURI);
    }

    @Override
    public Iterator getPrefixes(String namespaceURI) {
        return nsContext.getPrefixes(namespaceURI);
    }
}
