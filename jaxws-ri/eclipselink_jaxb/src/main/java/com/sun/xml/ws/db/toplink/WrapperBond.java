/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.db.toplink;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBException;
import javax.xml.bind.attachment.AttachmentMarshaller;
import javax.xml.bind.attachment.AttachmentUnmarshaller;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;

//import org.eclipse.persistence.jaxb.TypeMappingInfo;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;


import com.sun.xml.ws.spi.db.BindingContext;
import com.sun.xml.ws.spi.db.XMLBridge;
import com.sun.xml.ws.spi.db.DatabindingException;
import com.sun.xml.ws.spi.db.TypeInfo;
import com.sun.xml.ws.spi.db.WrapperComposite;

public class WrapperBond<T> implements XMLBridge<T> {

    JAXBContextWrapper parent;
    TypeInfo typeInfo;
//	TypeMappingInfo mappingInfo;
    static final String WrapperPrefix = "W";
    static final String WrapperPrefixColon = WrapperPrefix + ":";

    public WrapperBond(JAXBContextWrapper p, TypeInfo ti) {
        this.parent = p;
        this.typeInfo = ti;
//		if (parent.infoMap != null) mappingInfo = parent.infoMap.get(ti); 
    }

    @Override
    public BindingContext context() {
        return parent;
    }

    @Override
    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    @Override
    public final void marshal(T object, ContentHandler contentHandler, AttachmentMarshaller am) throws JAXBException {
        WrapperComposite w = (WrapperComposite) object;
        Attributes att = new Attributes() {
			@Override public int getLength() { return 0; }
			@Override public String getURI(int index) { return null; }
			@Override public String getLocalName(int index)  { return null; }
			@Override public String getQName(int index) { return null; }
			@Override public String getType(int index) { return null; }
			@Override public String getValue(int index)  { return null; }
			@Override public int getIndex(String uri, String localName)  { return 0; }
			@Override public int getIndex(String qName) {  return 0; }
			@Override public String getType(String uri, String localName)  { return null; }
			@Override public String getType(String qName)  { return null; }
			@Override public String getValue(String uri, String localName)  { return null; }
			@Override public String getValue(String qName)  { return null; }
        };
        try {
            contentHandler.startPrefixMapping(WrapperPrefix, typeInfo.tagName.getNamespaceURI());
            contentHandler.startElement(typeInfo.tagName.getNamespaceURI(), typeInfo.tagName.getLocalPart(), WrapperPrefixColon + typeInfo.tagName.getLocalPart(), att);
        } catch (SAXException e) {
            throw new JAXBException(e);
        }
        if (w.bridges != null) {
            for (int i = 0; i < w.bridges.length; i++) {
                w.bridges[i].marshal(w.values[i], contentHandler, am);
            }
        }
        try {
            contentHandler.endElement(typeInfo.tagName.getNamespaceURI(), typeInfo.tagName.getLocalPart(), null);
            contentHandler.endPrefixMapping(WrapperPrefix);
        } catch (SAXException e) {
            throw new JAXBException(e);
        }
//		bridge.marshal(object, contentHandler, am);
    }

    @Override
    public void marshal(T object, Node output) throws JAXBException {
        throw new UnsupportedOperationException();
//		bridge.marshal(object, output);
//		bridge.marshal((T) convert(object), output);
    }

    @Override
    public void marshal(T object, OutputStream output, NamespaceContext nsContext, AttachmentMarshaller am) throws JAXBException {
//		bridge.marshal((T) convert(object), output, nsContext, am);
    }

    @Override
    public final void marshal(T object, Result result) throws JAXBException {
        throw new UnsupportedOperationException();
//		bridge.marshal(object, result);
    }

    @Override
    public final void marshal(T object, XMLStreamWriter output, AttachmentMarshaller am) throws JAXBException {
        WrapperComposite w = (WrapperComposite) object;
        try {
//			output.writeStartElement(typeInfo.tagName.getNamespaceURI(), typeInfo.tagName.getLocalPart());
//			System.out.println(typeInfo.tagName.getNamespaceURI());

            //The prefix is to workaround an eclipselink bug
            output.writeStartElement(WrapperPrefix, typeInfo.tagName.getLocalPart(), typeInfo.tagName.getNamespaceURI());
            output.writeNamespace(WrapperPrefix, typeInfo.tagName.getNamespaceURI());

//			output.writeStartElement("", typeInfo.tagName.getLocalPart(), typeInfo.tagName.getNamespaceURI());
//			output.writeDefaultNamespace(typeInfo.tagName.getNamespaceURI());
//			System.out.println("======== " + output.getPrefix(typeInfo.tagName.getNamespaceURI()));
//			System.out.println("======== " + output.getNamespaceContext().getPrefix(typeInfo.tagName.getNamespaceURI()));
//			System.out.println("======== " + output.getNamespaceContext().getNamespaceURI(""));
        } catch (XMLStreamException e) {
//			e.printStackTrace();
            throw new DatabindingException(e);
        }
        if (w.bridges != null) {
            for (int i = 0; i < w.bridges.length; i++) {
//			System.out.println("======== bond " + w.bridges[i].getTypeInfo().tagName);
                w.bridges[i].marshal(w.values[i], output, am);
            }
        }
        try {
            output.writeEndElement();
        } catch (XMLStreamException e) {
            throw new DatabindingException(e);
        }
    }

    @Override
    public final T unmarshal(InputStream in) throws JAXBException {
        //EndpointArgumentsBuilder.RpcLit.readRequest
        throw new UnsupportedOperationException();
//		return bridge.unmarshal(in);
    }

    @Override
    public final T unmarshal(Node n, AttachmentUnmarshaller au) throws JAXBException {
        //EndpointArgumentsBuilder.RpcLit.readRequest
        throw new UnsupportedOperationException();
//		return bridge.unmarshal(n, au);
    }

    @Override
    public final T unmarshal(Source in, AttachmentUnmarshaller au) throws JAXBException {
        //EndpointArgumentsBuilder.RpcLit.readRequest
        throw new UnsupportedOperationException();
//		return bridge.unmarshal(in, au);
    }

    @Override
    public final T unmarshal(XMLStreamReader in, AttachmentUnmarshaller au) throws JAXBException {
        //EndpointArgumentsBuilder.RpcLit.readRequest
        throw new UnsupportedOperationException();
//		return bridge.unmarshal(in, au);
    }

    @Override
    public boolean supportOutputStream() {
        return false;
    }
}
