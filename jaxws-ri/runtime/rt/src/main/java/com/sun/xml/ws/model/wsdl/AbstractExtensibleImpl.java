/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.model.wsdl;

import com.sun.xml.ws.api.model.wsdl.WSDLExtensible;
import com.sun.xml.ws.api.model.wsdl.WSDLExtension;
import com.sun.xml.ws.api.model.wsdl.WSDLObject;
import com.sun.xml.ws.resources.UtilMessages;
import com.sun.istack.NotNull;

import javax.xml.stream.XMLStreamReader;
import javax.xml.namespace.QName;
import jakarta.xml.ws.WebServiceException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.xml.sax.Locator;

/**
 * All the WSDL 1.1 elements that are extensible should subclass from this abstract implementation of
 * {@link WSDLExtensible} interface.
 *
 * @author Vivek Pandey
 * @author Kohsuke Kawaguchi
 */
abstract class AbstractExtensibleImpl extends AbstractObjectImpl implements WSDLExtensible {
    protected final Set<WSDLExtension> extensions = new HashSet<>();
    // this captures any wsdl extensions that are not understood by WSDLExtensionParsers
    // and have wsdl:required=true
    protected List<UnknownWSDLExtension> notUnderstoodExtensions =
            new ArrayList<>();

    protected AbstractExtensibleImpl(XMLStreamReader xsr) {
        super(xsr);
    }

    protected AbstractExtensibleImpl(String systemId, int lineNumber) {
        super(systemId, lineNumber);
    }

    public final Iterable<WSDLExtension> getExtensions() {
        return extensions;
    }

    public final <T extends WSDLExtension> Iterable<T> getExtensions(Class<T> type) {
        // TODO: this is a rather stupid implementation
        List<T> r = new ArrayList<>(extensions.size());
        for (WSDLExtension e : extensions) {
            if(type.isInstance(e))
                r.add(type.cast(e));
        }
        return r;
    }

    public <T extends WSDLExtension> T getExtension(Class<T> type) {
        for (WSDLExtension e : extensions) {
            if(type.isInstance(e))
                return type.cast(e);
        }
        return null;
    }
    
    public void addExtension(WSDLExtension ex) {
        if(ex==null)
            // I don't trust plugins. So let's always check it, instead of making this an assertion
            throw new IllegalArgumentException();
        extensions.add(ex);
    }

    public List<? extends UnknownWSDLExtension> getNotUnderstoodExtensions() {
    	return notUnderstoodExtensions;
    }
    
    /**
     * This can be used if a WSDL extension element that has wsdl:required=true
     * is not understood
     */
    public void addNotUnderstoodExtension(QName extnEl, Locator locator) {
        notUnderstoodExtensions.add(new UnknownWSDLExtension(extnEl, locator));
    }

    protected static class UnknownWSDLExtension implements WSDLExtension, WSDLObject {
        private final QName extnEl;
        private final Locator locator;
        public UnknownWSDLExtension(QName extnEl, Locator locator) {
            this.extnEl = extnEl;
            this.locator = locator;
        }
        public QName getName() {
            return extnEl;
        }
        @NotNull public Locator getLocation() {
            return locator;
        }
        public String toString(){
           return extnEl + " "+ UtilMessages.UTIL_LOCATION( locator.getLineNumber(), locator.getSystemId());
       }
    }

    /**
     * This method should be called after freezing the WSDLModel
     * @return true if all wsdl required extensions on Port and Binding are understood
     */
    public boolean areRequiredExtensionsUnderstood() {
        if (notUnderstoodExtensions.size() != 0) {
            StringBuilder buf = new StringBuilder("Unknown WSDL extensibility elements:");
            for (UnknownWSDLExtension extn : notUnderstoodExtensions)
                buf.append('\n').append(extn.toString());
            throw new WebServiceException(buf.toString());
        }
        return true;
    }    
}
