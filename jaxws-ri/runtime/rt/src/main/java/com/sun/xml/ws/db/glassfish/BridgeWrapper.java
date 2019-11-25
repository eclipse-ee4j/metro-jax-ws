/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.attachment.AttachmentMarshaller;
import javax.xml.bind.attachment.AttachmentUnmarshaller;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;

import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;

import com.sun.xml.bind.api.JAXBRIContext;
import com.sun.xml.ws.spi.db.BindingContext;
import com.sun.xml.ws.spi.db.XMLBridge;
import com.sun.xml.ws.spi.db.DatabindingException;
import com.sun.xml.ws.spi.db.TypeInfo;

public class BridgeWrapper<T> implements XMLBridge<T> {

    private JAXBRIContextWrapper parent;
    private com.sun.xml.bind.api.Bridge<T> bridge;

    public BridgeWrapper(JAXBRIContextWrapper p, com.sun.xml.bind.api.Bridge<T> b) {
        parent = p;
        bridge = b;
    }

    @Override
    public BindingContext context() {
        return parent;
    }

    com.sun.xml.bind.api.Bridge getBridge() {
        return bridge;
    }

    @Override
    public boolean equals(Object obj) {
        return bridge.equals(obj);
    }

    public JAXBRIContext getContext() {
        return bridge.getContext();
    }

    @Override
    public TypeInfo getTypeInfo() {
        return parent.typeInfo(bridge.getTypeReference());
    }

    @Override
    public int hashCode() {
        return bridge.hashCode();
    }

//	public final void marshal(BridgeContext context, T object, ContentHandler contentHandler) throws JAXBException {
//		bridge.marshal(context, object, contentHandler);
//	}
//
//	public final void marshal(BridgeContext context, T object, Node output) throws JAXBException {
//		bridge.marshal(context, object, output);
//	}
//
//	public final void marshal(BridgeContext context, T object, OutputStream output, NamespaceContext nsContext) throws JAXBException {
//		bridge.marshal(context, object, output, nsContext);
//	}
//
//	public final void marshal(BridgeContext context, T object, Result result) throws JAXBException {
//		bridge.marshal(context, object, result);
//	}
//
//	public final void marshal(BridgeContext context, T object, XMLStreamWriter output) throws JAXBException {
//		bridge.marshal(context, object, output);
//	}
    public void marshal(Marshaller m, T object, ContentHandler contentHandler) throws JAXBException {
        bridge.marshal(m, object, contentHandler);
    }

    public void marshal(Marshaller m, T object, Node output) throws JAXBException {
        bridge.marshal(m, object, output);
    }

    public void marshal(Marshaller m, T object, OutputStream output, NamespaceContext nsContext) throws JAXBException {
        bridge.marshal(m, object, output, nsContext);
    }

    public void marshal(Marshaller m, T object, Result result) throws JAXBException {
        bridge.marshal(m, object, result);
    }

    public void marshal(Marshaller m, T object, XMLStreamWriter output) throws JAXBException {
        bridge.marshal(m, object, output);
//		bridge.marshal(m, (T) convert(object), output);
    }

    @Override
    public final void marshal(T object, ContentHandler contentHandler, AttachmentMarshaller am) throws JAXBException {
//		bridge.marshal((T) convert(object), contentHandler, am);
        bridge.marshal(object, contentHandler, am);
    }

//	Object convert(Object o) {
//		return (o instanceof WrapperComposite)? convertWrapper((WrapperComposite)o) : o;
//	}
//	
//	static CompositeStructure convertWrapper(WrapperComposite w) {
//		CompositeStructure cs = new CompositeStructure();
//		cs.values = w.values;
//		cs.bridges = new Bridge[w.bridges.length];
//		for (int i = 0; i < cs.bridges.length; i++) 
//		    cs.bridges[i] = ((BridgeWrapper)w.bridges[i]).getBridge();
//		return cs;
//	}
    public void marshal(T object, ContentHandler contentHandler) throws JAXBException {
        bridge.marshal(object, contentHandler);
//		bridge.marshal((T) convert(object), contentHandler);
    }

    @Override
    public void marshal(T object, Node output) throws JAXBException {
        bridge.marshal(object, output);
//		bridge.marshal((T) convert(object), output);
    }

    @Override
    public void marshal(T object, OutputStream output, NamespaceContext nsContext, AttachmentMarshaller am) throws JAXBException {
//		bridge.marshal((T) convert(object), output, nsContext, am);
        bridge.marshal(object, output, nsContext, am);
    }

    public void marshal(T object, OutputStream output, NamespaceContext nsContext) throws JAXBException {
        bridge.marshal(object, output, nsContext);
//		bridge.marshal((T) convert(object), output, nsContext);
    }

    @Override
    public final void marshal(T object, Result result) throws JAXBException {
        bridge.marshal(object, result);
    }

    @Override
    public final void marshal(T object, XMLStreamWriter output,
            AttachmentMarshaller am) throws JAXBException {
        bridge.marshal(object, output, am);
    }

    public final void marshal(T object, XMLStreamWriter output)
            throws JAXBException {
        bridge.marshal(object, output);
    }

    @Override
    public String toString() {
        return BridgeWrapper.class.getName() + " : " + bridge.toString();
    }

//	public final T unmarshal(BridgeContext context, InputStream in)
//			throws JAXBException {
//		return bridge.unmarshal(context, in);
//	}
//
//	public final T unmarshal(BridgeContext context, Node n)
//			throws JAXBException {
//		return bridge.unmarshal(context, n);
//	}
//
//	public final T unmarshal(BridgeContext context, Source in)
//			throws JAXBException {
//		return bridge.unmarshal(context, in);
//	}
//
//	public final T unmarshal(BridgeContext context, XMLStreamReader in)
//			throws JAXBException {
//		return bridge.unmarshal(context, in);
//	}
    @Override
    public final T unmarshal(InputStream in) throws JAXBException {
        return bridge.unmarshal(in);
    }

    @Override
    public final T unmarshal(Node n, AttachmentUnmarshaller au)
            throws JAXBException {
        return bridge.unmarshal(n, au);
    }

    public final T unmarshal(Node n) throws JAXBException {
        return bridge.unmarshal(n);
    }

    @Override
    public final T unmarshal(Source in, AttachmentUnmarshaller au)
            throws JAXBException {
        return bridge.unmarshal(in, au);
    }

    public final T unmarshal(Source in) throws DatabindingException {
        try {
            return bridge.unmarshal(in);
        } catch (JAXBException e) {
            throw new DatabindingException(e);
        }
    }

    public T unmarshal(Unmarshaller u, InputStream in) throws JAXBException {
        return bridge.unmarshal(u, in);
    }

    public T unmarshal(Unmarshaller context, Node n) throws JAXBException {
        return bridge.unmarshal(context, n);
    }

    public T unmarshal(Unmarshaller u, Source in) throws JAXBException {
        return bridge.unmarshal(u, in);
    }

    public T unmarshal(Unmarshaller u, XMLStreamReader in) throws JAXBException {
        return bridge.unmarshal(u, in);
    }

    @Override
    public final T unmarshal(XMLStreamReader in, AttachmentUnmarshaller au)
            throws JAXBException {
        return bridge.unmarshal(in, au);
    }

    public final T unmarshal(XMLStreamReader in) throws JAXBException {
        return bridge.unmarshal(in);
    }

    @Override
    public boolean supportOutputStream() {
        return true;
    }
}
