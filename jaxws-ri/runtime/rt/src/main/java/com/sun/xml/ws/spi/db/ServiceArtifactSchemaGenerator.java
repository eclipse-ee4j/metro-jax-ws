/*
 * Copyright (c) 2014, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.spi.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sun.xml.ws.model.RuntimeModeler;
import com.sun.xml.ws.wsdl.writer.WSDLGenerator;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.SchemaOutputResolver;
import javax.xml.namespace.QName;
import javax.xml.transform.Result;
import jakarta.xml.ws.WebServiceException;

import org.glassfish.jaxb.runtime.v2.schemagen.xmlschema.ComplexType;
import org.glassfish.jaxb.runtime.v2.schemagen.xmlschema.Element;
import org.glassfish.jaxb.runtime.v2.schemagen.xmlschema.ExplicitGroup;
import org.glassfish.jaxb.runtime.v2.schemagen.xmlschema.LocalElement;
import org.glassfish.jaxb.runtime.v2.schemagen.xmlschema.Occurs;
import com.sun.xml.txw2.TXW;
import com.sun.xml.txw2.output.ResultFactory;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.model.AbstractSEIModelImpl;
import com.sun.xml.ws.model.JavaMethodImpl;
import com.sun.xml.ws.model.ParameterImpl;
import com.sun.xml.ws.model.WrapperParameter;
import com.sun.xml.ws.wsdl.writer.document.xsd.Schema;

/**
 * ServiceArtifactSchemaGenerator generates XML schema for service artifacts including the wrapper types of 
 * document-literal stype operation and exceptions.
 * 
 * @author shih-chang.chen@oracle.com
 */
public class ServiceArtifactSchemaGenerator {

    protected AbstractSEIModelImpl model;
    protected SchemaOutputResolver xsdResolver;
    
    public ServiceArtifactSchemaGenerator(SEIModel model) {
        this.model = (AbstractSEIModelImpl)model;
    }

    static final String FilePrefix = "jaxwsGen";
    protected int fileIndex = 0;
    protected Schema create(String tns) {
        try {
            Result res = xsdResolver.createOutput(tns, FilePrefix + (fileIndex++) + ".xsd"); 
            return TXW.create(Schema.class, ResultFactory.createSerializer(res));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new WebServiceException(e);
        }
    }
    
    public void generate(SchemaOutputResolver resolver) {
        xsdResolver = resolver;
        List<WrapperParameter> wrappers = new ArrayList<>();
        for (JavaMethodImpl method : model.getJavaMethods()) {
            if(method.getBinding().isRpcLit()) continue; 
            for (ParameterImpl p : method.getRequestParameters()) {
                if (p instanceof WrapperParameter) {
                    if (WrapperComposite.class.equals((p.getTypeInfo().type))) {
                        wrappers.add((WrapperParameter)p);
                    }
                }
            }
            for (ParameterImpl p : method.getResponseParameters()) {
                if (p instanceof WrapperParameter) {
                    if (WrapperComposite.class.equals((p.getTypeInfo().type))) {
                        wrappers.add((WrapperParameter)p);
                    }
                }
            }
        }        
        if (wrappers.isEmpty()) return;
        HashMap<String, Schema> xsds = initWrappersSchemaWithImports(wrappers); 
        postInit(xsds);
        for(WrapperParameter wp : wrappers) {
            String tns = wp.getName().getNamespaceURI();
            Schema xsd = xsds.get(tns);            
            Element e =  xsd._element(Element.class);
            e._attribute("name", wp.getName().getLocalPart());
            e.type(wp.getName());
            ComplexType ct =  xsd._element(ComplexType.class);
            ct._attribute("name", wp.getName().getLocalPart());
            ExplicitGroup sq = ct.sequence();            
            for (ParameterImpl p : wp.getWrapperChildren() ) if (p.getBinding().isBody()) addChild(sq, p); 
        }
        for(Schema xsd: xsds.values()) xsd.commit();
    }

    protected void postInit(HashMap<String, Schema> xsds) {        
    }

    protected void addChild(ExplicitGroup sq, ParameterImpl param) {
        TypeInfo typeInfo = param.getItemType();
        boolean repeatedElement = false;
        if (typeInfo == null) {
            typeInfo = param.getTypeInfo();
        } else {
            if (typeInfo.getWrapperType() != null) typeInfo = param.getTypeInfo();
            else repeatedElement = true;
        }
        Occurs child = addChild(sq, param.getName(), typeInfo);
        if (repeatedElement && child != null) {
            child.maxOccurs("unbounded");
        }
    }
    
    protected Occurs addChild(ExplicitGroup sq, QName name, TypeInfo typeInfo) {
        LocalElement le = null;
        QName type = model.getBindingContext().getTypeName(typeInfo); 
        if (type != null) {
            le = sq.element();
            le._attribute("name", name.getLocalPart());
            le.type(type);
        } else {
            if (typeInfo.type instanceof Class) {
                try {
                    QName elemName = model.getBindingContext().getElementName((Class)typeInfo.type);
                    if (elemName.getLocalPart().equals("any") && elemName.getNamespaceURI().equals(WSDLGenerator.XsdNs)) {
                        return sq.any();
                    } else {
                        le = sq.element();
                        le.ref(elemName);
                    }
                } catch (JAXBException je) {
                    throw new WebServiceException(je.getMessage(), je);
                }
            }
        }
        return le;
    }
    
    //All the imports have to go first ...
    private HashMap<String, Schema> initWrappersSchemaWithImports(List<WrapperParameter> wrappers) {
        Object o = model.databindingInfo().properties().get(RuntimeModeler.DocWrappeeNamespapceQualified);
        boolean wrappeeQualified = (o instanceof Boolean) ? ((Boolean) o) : false;
        HashMap<String, Schema> xsds = new HashMap<>();
        HashMap<String, Set<String>> imports = new HashMap<>();
        for(WrapperParameter wp : wrappers) {
            String tns = wp.getName().getNamespaceURI();
            Schema xsd = xsds.get(tns);
            if (xsd == null) {
                xsd = create(tns);
                xsd.targetNamespace(tns);
                if (wrappeeQualified) xsd._attribute("elementFormDefault", "qualified");
                xsds.put(tns, xsd);
            }          
            for (ParameterImpl p : wp.getWrapperChildren() ) {
                String nsToImport = (p.getBinding().isBody())? bodyParamNS(p): null;
                if (nsToImport != null && !nsToImport.equals(tns) && !nsToImport.equals("http://www.w3.org/2001/XMLSchema")) {
                    Set<String> importSet = imports.computeIfAbsent(tns, k -> new HashSet<>());
                    importSet.add(nsToImport);
                }
            }
        }
        for(Entry<String, Set<String>> entry: imports.entrySet()) {
            String tns = entry.getKey();
            Set<String> importSet = entry.getValue();
            Schema xsd = xsds.get(tns);
            for(String nsToImport : importSet) xsd._namespace(nsToImport, true);
            for(String nsToImport : importSet) {
                com.sun.xml.ws.wsdl.writer.document.xsd.Import imp = xsd._import();
                imp.namespace(nsToImport);
            }                       
        }
        return xsds;
    }

    protected String bodyParamNS(ParameterImpl p) {
        String nsToImport = null;
        TypeInfo typeInfo = p.getItemType();
        if (typeInfo == null) typeInfo = p.getTypeInfo();
        QName type = model.getBindingContext().getTypeName(typeInfo); 
        if (type != null) {
            nsToImport = type.getNamespaceURI();
        } else {
            if (typeInfo.type instanceof Class) {
                try {
                    QName elemRef = model.getBindingContext().getElementName((Class)typeInfo.type);
                    if (elemRef != null) nsToImport = elemRef.getNamespaceURI();
                } catch (JAXBException je) {
                    throw new WebServiceException(je.getMessage(), je);
                }
            }
        }
        return nsToImport;
    }
}
