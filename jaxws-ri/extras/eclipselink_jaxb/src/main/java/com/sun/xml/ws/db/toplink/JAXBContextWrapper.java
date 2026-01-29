/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
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

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.SchemaOutputResolver;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchema;
import jakarta.xml.bind.annotation.XmlType;
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

	JAXBContextWrapper(jakarta.xml.bind.JAXBContext cxt, Map<TypeInfo, TypeMappingInfo> map, SEIModel model) {
		jaxbContext = (org.eclipse.persistence.jaxb.JAXBContext) cxt;
		infoMap = map;
	    mpool = new ObjectPool<>() {
			@Override
			protected JAXBMarshaller newInstance() {
				try {
					return jaxbContext.createMarshaller();
				} catch (JAXBException e) {
					e.printStackTrace();
					throw new DatabindingException(e);
				}
			}
		};
	    upool = new ObjectPool<>() {
			@Override
			protected JAXBUnmarshaller newInstance() {
				try {
					return jaxbContext.createUnmarshaller();
				} catch (JAXBException e) {
					e.printStackTrace();
					throw new DatabindingException(e);
				}
			}
		};
		wrapperAccessors = new HashMap<>();
		hasSwaRef = jaxbContext.hasSwaRef();
        seiModel = model;
	}
	
	@Override
	public String getBuildId() {
		return Version.getBuildRevision();
	}

	@Override
	public XMLBridge createBridge(TypeInfo ref) {
		return WrapperComposite.class.equals(ref.type) ? new com.sun.xml.ws.spi.db.WrapperBridge(this, ref) : new JAXBBond(this, ref);
	}

	@Override
	public XMLBridge createFragmentBridge() {
		return new JAXBBond(this, null);
	}

	@Override
	public Marshaller createMarshaller() throws JAXBException {
		return jaxbContext.createMarshaller();
	}

	@Override
	public Unmarshaller createUnmarshaller() throws JAXBException {
		return jaxbContext.createUnmarshaller();
	}

	@Override
	public void generateSchema(SchemaOutputResolver outputResolver)
			throws IOException {
		jaxbContext.generateSchema(outputResolver);
        if (seiModel != null) {
            ServiceArtifactSchemaGenerator xsdgen = new ServiceArtifactSchemaGenerator(seiModel);
            xsdgen.generate(outputResolver);
        }
	}

	@Override
	public QName getElementName(Object o) throws JAXBException {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
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

	@Override
	public <B, V> PropertyAccessor<B, V> getElementPropertyAccessor(Class<B> wrapperBean, String ns, String name) throws JAXBException {
		JAXBWrapperAccessor wa = wrapperAccessors.get(wrapperBean);
		if (wa == null) {
			wa = new JAXBWrapperAccessor(wrapperBean);
			wrapperAccessors.put(wrapperBean, wa);
		}
		return wa.getPropertyAccessor(ns, name);
	}

	@Override
	public JAXBContext getJAXBContext() {
		return jaxbContext;
	}

	@Override
	public List<String> getKnownNamespaceURIs() {
		// TODO
		return new ArrayList<>();
	}

	@Override
	public QName getTypeName(TypeInfo tr) {
		if (typeNames == null) typeNames = jaxbContext.getTypeMappingInfoToSchemaType();
	    TypeMappingInfo tmi = infoMap.get(tr);
	    return typeNames.get(tmi);
	}

	@Override
	public boolean hasSwaRef() {
		return hasSwaRef;
	}
	
    @Override
	public Object newWrapperInstace(Class<?> wrapperType)
			throws ReflectiveOperationException {
        return wrapperType.getConstructor().newInstance();
    }
}
