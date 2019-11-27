/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.model.wsdl;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.model.wsdl.WSDLService;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLModel;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLPort;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLService;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementation of {@link WSDLService}
 *
 * @author Vivek Pandey
 */
public final class WSDLServiceImpl extends AbstractExtensibleImpl implements EditableWSDLService {
    private final QName name;
    private final Map<QName, EditableWSDLPort> ports;
    private final EditableWSDLModel parent;

    public WSDLServiceImpl(XMLStreamReader xsr, EditableWSDLModel parent, QName name) {
        super(xsr);
        this.parent = parent;
        this.name = name;
        ports = new LinkedHashMap<QName, EditableWSDLPort>();
    }

    public @NotNull
    EditableWSDLModel getParent() {
        return parent;
    }

    public QName getName() {
        return name;
    }

    public EditableWSDLPort get(QName portName) {
        return ports.get(portName);
    }

    public EditableWSDLPort getFirstPort() {
        if(ports.isEmpty())
            return null;
        else
            return ports.values().iterator().next();
    }

    public Iterable<EditableWSDLPort> getPorts(){
        return ports.values();
    }

    /**
    * gets the first port in this service which matches the portType
    */
    public @Nullable
    EditableWSDLPort getMatchingPort(QName portTypeName){
        for(EditableWSDLPort port : getPorts()){
            QName ptName = port.getBinding().getPortTypeName();
            assert (ptName != null);
            if(ptName.equals(portTypeName))
                return port;
        }
        return null;
    }

    /**
     * Populates the Map that holds port name as key and {@link WSDLPort} as the value.
     *
     * @param portName Must be non-null
     * @param port     Must be non-null
     * @throws NullPointerException if either opName or ptOp is null
     */
    public void put(QName portName, EditableWSDLPort port) {
        if (portName == null || port == null)
            throw new NullPointerException();
        ports.put(portName, port);
    }

    public void freeze(EditableWSDLModel root) {
        for (EditableWSDLPort port : ports.values()) {
            port.freeze(root);
        }
    }
}
