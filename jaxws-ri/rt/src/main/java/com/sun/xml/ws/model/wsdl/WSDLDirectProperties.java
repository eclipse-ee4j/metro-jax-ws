/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.model.wsdl;

import com.sun.xml.ws.api.model.SEIModel;

import javax.xml.namespace.QName;

/**
 * Replacement for {@link WSDLPortProperties} for when elements from the WSDL are known,
 * but the full WSDL is not available.
 *
 */
public final class WSDLDirectProperties extends WSDLProperties {
	private final QName serviceName;
	private final QName portName;

    public WSDLDirectProperties(QName serviceName, QName portName) {
    	this(serviceName, portName, null);
    }
    
    public WSDLDirectProperties(QName serviceName, QName portName, SEIModel seiModel) {
    	super(seiModel);
        this.serviceName = serviceName;
        this.portName = portName;
    }

    public QName getWSDLService() {
        return serviceName;
    }

    public QName getWSDLPort() {
        return portName;
    }

    public QName getWSDLPortType() {
        return null;
    }
}
