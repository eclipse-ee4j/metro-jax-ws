/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.message.jaxb;

import com.sun.xml.bind.api.Bridge;
import com.sun.xml.bind.api.JAXBRIContext;
import com.sun.xml.bind.api.TypeReference;
import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
import com.sun.xml.bind.v2.runtime.MarshallerImpl;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Used to adapt {@link Marshaller} into a {@link Bridge}.
 * @deprecated
 * @author Kohsuke Kawaguchi
 */
final class MarshallerBridge extends Bridge {
    public MarshallerBridge(JAXBRIContext context) {
        super((JAXBContextImpl)context);
    }

    public void marshal(Marshaller m, Object object, XMLStreamWriter output) throws JAXBException {
        m.setProperty(Marshaller.JAXB_FRAGMENT,true);
        try {
            m.marshal(object,output);
        } finally {
            m.setProperty(Marshaller.JAXB_FRAGMENT,false);
        }
    }

    public void marshal(Marshaller m, Object object, OutputStream output, NamespaceContext nsContext) throws JAXBException {
        m.setProperty(Marshaller.JAXB_FRAGMENT,true);
        try {
            ((MarshallerImpl)m).marshal(object,output,nsContext);
        } finally {
            m.setProperty(Marshaller.JAXB_FRAGMENT,false);
        }
    }

    public void marshal(Marshaller m, Object object, Node output) throws JAXBException {
        m.setProperty(Marshaller.JAXB_FRAGMENT,true);
        try {
            m.marshal(object,output);
        } finally {
            m.setProperty(Marshaller.JAXB_FRAGMENT,false);
        }
    }

    public void marshal(Marshaller m, Object object, ContentHandler contentHandler) throws JAXBException {
        m.setProperty(Marshaller.JAXB_FRAGMENT,true);
        try {
            m.marshal(object,contentHandler);
        } finally {
            m.setProperty(Marshaller.JAXB_FRAGMENT,false);
        }
    }

    public void marshal(Marshaller m, Object object, Result result) throws JAXBException {
        m.setProperty(Marshaller.JAXB_FRAGMENT,true);
        try {
            m.marshal(object,result);
        } finally {
            m.setProperty(Marshaller.JAXB_FRAGMENT,false);
        }
    }

    public Object unmarshal(Unmarshaller u, XMLStreamReader in) {
        throw new UnsupportedOperationException();
    }

    public Object unmarshal(Unmarshaller u, Source in) {
        throw new UnsupportedOperationException();
    }

    public Object unmarshal(Unmarshaller u, InputStream in) {
        throw new UnsupportedOperationException();
    }

    public Object unmarshal(Unmarshaller u, Node n) {
        throw new UnsupportedOperationException();
    }

    public TypeReference getTypeReference() {
        throw new UnsupportedOperationException();
    }
}
