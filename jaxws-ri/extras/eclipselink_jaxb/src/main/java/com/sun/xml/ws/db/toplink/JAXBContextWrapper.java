/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.db.toplink;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.spi.db.BindingContext;
import com.sun.xml.ws.spi.db.DatabindingException;
import com.sun.xml.ws.spi.db.ServiceArtifactSchemaGenerator;
import com.sun.xml.ws.spi.db.XMLBridge;
import com.sun.xml.ws.spi.db.JAXBWrapperAccessor;
import com.sun.xml.ws.spi.db.PropertyAccessor;
import com.sun.xml.ws.spi.db.TypeInfo;
import com.sun.xml.ws.spi.db.WrapperComposite;

import org.eclipse.persistence.Version;
import org.eclipse.persistence.jaxb.JAXBMarshaller;
import org.eclipse.persistence.jaxb.JAXBUnmarshaller;
import org.eclipse.persistence.jaxb.TypeMappingInfo;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class JAXBContextWrapper implements BindingContext {
	org.eclipse.persistence.jaxb.JAXBContext jaxbContext;	  
	ObjectPool<JAXBMarshaller>   mpool;	  
    ObjectPool<JAXBUnmarshaller> upool;	
    Map<TypeInfo, TypeMappingInfo> infoMap;
    Map<TypeMappingInfo, QName> typeNames;
    Map<Class<?>, JAXBWrapperAccessor> wrapperAccessors;
    SEIModel seiModel;

    private boolean hasSwaRef = false;

	JAXBContextWrapper(javax.xml.bind.JAXBContext cxt, Map<TypeInfo, TypeMappingInfo> map, SEIModel model) {
		jaxbContext = (org.eclipse.persistence.jaxb.JAXBContext) cxt;
		infoMap = map;
	    mpool = new ObjectPool<JAXBMarshaller>() {
			protected JAXBMarshaller newInstance() {
				try {
                    return (JAXBMarshaller) jaxbContext.createMarshaller();
                } catch (JAXBException e) {
                    e.printStackTrace();
                    throw new DatabindingException(e);
                }
			}
		};
	    upool = new ObjectPool<JAXBUnmarshaller>() {
			protected JAXBUnmarshaller newInstance() {
                try {
				    return (JAXBUnmarshaller) jaxbContext.createUnmarshaller();
                } catch (JAXBException e) {
                    e.printStackTrace();
                    throw new DatabindingException(e);
                }
			}
		};
		wrapperAccessors = new HashMap<Class<?>, JAXBWrapperAccessor>();
		hasSwaRef = jaxbContext.hasSwaRef();
        seiModel = model;
	}
	
	public String getBuildId() {
		return Version.getBuildRevision();
	}

	public XMLBridge createBridge(TypeInfo ref) {
		return (XMLBridge) (WrapperComposite.class.equals(ref.type) ? new com.sun.xml.ws.spi.db.WrapperBridge(this, ref) : new JAXBBond(this, ref));
	}

	public XMLBridge createFragmentBridge() {
		return new JAXBBond(this, null);
	}

	public Marshaller createMarshaller() throws JAXBException {
		return jaxbContext.createMarshaller();
	}

	public Unmarshaller createUnmarshaller() throws JAXBException {
		return jaxbContext.createUnmarshaller();
	}

	public void generateSchema(SchemaOutputResolver outputResolver)
			throws IOException {
		jaxbContext.generateSchema(outputResolver);
        if (seiModel != null) {
            ServiceArtifactSchemaGenerator xsdgen = new ServiceArtifactSchemaGenerator(seiModel);
            xsdgen.generate(outputResolver);
        }
	}

	public QName getElementName(Object o) throws JAXBException {
		// TODO Auto-generated method stub
		return null;
	}

    public QName getElementName(Class cls) throws JAXBException {
        XmlRootElement xre = (XmlRootElement) cls.getAnnotation(XmlRootElement.class);
        XmlType xt = (XmlType) cls.getAnnotation(XmlType.class);
        if (xt != null && xt.name() != null && !"".equals(xt.name())) return null;
        if (xre != null) {
            String lp = xre.name();
            String ns = xre.namespace();
            if (ns.equals("##default")) {
                XmlSchema xs = cls.getPackage().getAnnotation(XmlSchema.class);
                ns = (xs != null) ? xs.namespace() : "";
          }
          return new QName(ns, lp);
      }
      return null;
    }

	public <B, V> PropertyAccessor<B, V> getElementPropertyAccessor(Class<B> wrapperBean, String ns, String name) throws JAXBException {
		JAXBWrapperAccessor wa = wrapperAccessors.get(wrapperBean);
		if (wa == null) {
			wa = new JAXBWrapperAccessor(wrapperBean);
			wrapperAccessors.put(wrapperBean, wa);
		}
		return wa.getPropertyAccessor(ns, name);
	}

	public JAXBContext getJAXBContext() {
		return jaxbContext;
	}

	public List<String> getKnownNamespaceURIs() {
		// TODO
		return new ArrayList<String>();
	}

	public QName getTypeName(TypeInfo tr) {
		if (typeNames == null) typeNames = jaxbContext.getTypeMappingInfoToSchemaType();
	    TypeMappingInfo tmi = infoMap.get(tr);
	    return typeNames.get(tmi);
	}

	public boolean hasSwaRef() {
		return hasSwaRef;
	}
	
    public Object newWrapperInstace(Class<?> wrapperType)
            throws InstantiationException, IllegalAccessException {
        return wrapperType.newInstance();
    }
}
