/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.db.glassfish;

import java.io.InputStream;
import java.io.OutputStream;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;

import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;

import org.glassfish.jaxb.runtime.api.TypeReference;
import org.glassfish.jaxb.runtime.v2.runtime.JAXBContextImpl;
import org.glassfish.jaxb.runtime.v2.runtime.MarshallerImpl;
import com.sun.xml.ws.spi.db.BindingContext;
import com.sun.xml.ws.spi.db.TypeInfo;

public class MarshallerBridge 
   extends org.glassfish.jaxb.runtime.api.Bridge
implements com.sun.xml.ws.spi.db.XMLBridge {

    protected MarshallerBridge(JAXBContextImpl context) {
		super(context);
	}
    
	@Override
    public void marshal(Marshaller m, Object object, XMLStreamWriter output) throws JAXBException {
        m.setProperty(Marshaller.JAXB_FRAGMENT,true);
        try {
            m.marshal(object,output);
        } finally {
            m.setProperty(Marshaller.JAXB_FRAGMENT,false);
        }
    }

    @Override
    public void marshal(Marshaller m, Object object, OutputStream output, NamespaceContext nsContext) throws JAXBException {
        m.setProperty(Marshaller.JAXB_FRAGMENT,true);
        try {
            ((MarshallerImpl)m).marshal(object,output,nsContext);
        } finally {
            m.setProperty(Marshaller.JAXB_FRAGMENT,false);
        }
    }

    @Override
    public void marshal(Marshaller m, Object object, Node output) throws JAXBException {
        m.setProperty(Marshaller.JAXB_FRAGMENT,true);
        try {
            m.marshal(object,output);
        } finally {
            m.setProperty(Marshaller.JAXB_FRAGMENT,false);
        }
    }

    @Override
    public void marshal(Marshaller m, Object object, ContentHandler contentHandler) throws JAXBException {
        m.setProperty(Marshaller.JAXB_FRAGMENT,true);
        try {
            m.marshal(object,contentHandler);
        } finally {
            m.setProperty(Marshaller.JAXB_FRAGMENT,false);
        }
    }

    @Override
    public void marshal(Marshaller m, Object object, Result result) throws JAXBException {
        m.setProperty(Marshaller.JAXB_FRAGMENT,true);
        try {
            m.marshal(object,result);
        } finally {
            m.setProperty(Marshaller.JAXB_FRAGMENT,false);
        }
    }

    @Override
    public Object unmarshal(Unmarshaller u, XMLStreamReader in) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object unmarshal(Unmarshaller u, Source in) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object unmarshal(Unmarshaller u, InputStream in) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object unmarshal(Unmarshaller u, Node n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TypeInfo getTypeInfo() {
        throw new UnsupportedOperationException();
    }
	@Override
    public TypeReference getTypeReference() {
        throw new UnsupportedOperationException();
	}
	@Override
    public BindingContext context() {
        throw new UnsupportedOperationException();
	}
    @Override
    public boolean supportOutputStream() {
    	return true;
    }
}
