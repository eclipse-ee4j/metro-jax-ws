/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.model;

import com.sun.tools.ws.processor.model.jaxb.JAXBModel;
import com.sun.tools.ws.wsdl.framework.Entity;

import javax.xml.namespace.QName;
import java.util.*;

/**
 * The model is used to represent the entire Web Service.  The JAX-WS ProcessorActions can process
 * this Model to generate Java artifacts such as the service interface.
 *
 * @author WS Development Team
 */
public class Model extends ModelObject {

    public Model(Entity entity) {
        super(entity);
    }

    public Model(QName name, Entity entity) {
        super(entity);
        this.name = name;
    }

    public QName getName() {
        return name;
    }

    public void setName(QName n) {
        name = n;
    }

    public String getTargetNamespaceURI() {
        return targetNamespace;
    }

    public void setTargetNamespaceURI(String s) {
        targetNamespace = s;
    }

    public void addService(Service service) {
        if (servicesByName.containsKey(service.getName())) {
            throw new ModelException("model.uniqueness");
        }
        services.add(service);
        servicesByName.put(service.getName(), service);
    }

    public Service getServiceByName(QName name) {
        if (servicesByName.size() != services.size()) {
            initializeServicesByName();
        }
        return servicesByName.get(name);
    }

    /* serialization */
    public List<Service> getServices() {
        return services;
    }

    /* serialization */
    public void setServices(List<Service> l) {
        services = l;
    }

    private void initializeServicesByName() {
        servicesByName = new HashMap();
        if (services != null) {
            for (Service service : services) {
                if (service.getName() != null &&
                    servicesByName.containsKey(service.getName())) {

                    throw new ModelException("model.uniqueness");
                }
                servicesByName.put(service.getName(), service);
            }
        }
    }

    public void addExtraType(AbstractType type) {
        extraTypes.add(type);
    }

    public Iterator getExtraTypes() {
        return extraTypes.iterator();
    }

    /* serialization */
    public Set<AbstractType> getExtraTypesSet() {
        return extraTypes;
    }

    /* serialization */
    public void setExtraTypesSet(Set<AbstractType> s) {
        extraTypes = s;
    }


    public void accept(ModelVisitor visitor) throws Exception {
        visitor.visit(this);
    }

    /**
     * @return the source version
     */
    public String getSource() {
        return source;
    }

    /**
     */
    public void setSource(String string) {
        source = string;
    }

    public void setJAXBModel(JAXBModel jaxBModel) {
        this.jaxBModel = jaxBModel;
    }

    public JAXBModel getJAXBModel() {
        return jaxBModel;
    }

    private QName name;
    private String targetNamespace;
    private List<Service> services = new ArrayList<>();
    private Map<QName, Service> servicesByName = new HashMap<>();
    private Set<AbstractType> extraTypes = new HashSet<>();
    private String source;
    private JAXBModel jaxBModel = null;
}
