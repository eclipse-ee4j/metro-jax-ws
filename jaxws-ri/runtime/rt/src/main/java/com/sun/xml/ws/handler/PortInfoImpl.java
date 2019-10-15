/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.handler;

import com.sun.xml.ws.api.BindingID;
import com.sun.xml.ws.client.WSServiceDelegate;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.PortInfo;

/**
 * <p>Implementation of the PortInfo interface. This is just a simple
 * class used to hold the info necessary to uniquely identify a port,
 * including the port name, service name, and binding ID. This class
 * is only used on the client side.
 *
 * <p>An instance is created by
 * {@link WSServiceDelegate} when used to
 * place a handler chain into the HandlerResolver map. Another is
 * created later by
 * {@link com.sun.xml.ws.client.WSServiceDelegate} to retrieve the
 * necessary handler chain to set on a binding instance.
 *
 * @see WSServiceDelegate
 * @see com.sun.xml.ws.client.HandlerResolverImpl
 *
 * @author WS Development Team
 */
public class PortInfoImpl implements PortInfo {

    private BindingID bindingId;
    private QName portName;
    private QName serviceName;

    /**
     * The class is constructed with the information needed to identify
     * a port. This information cannot be changed later.
     *
     * @param bindingId The binding ID string.
     * @param portName The QName of the port.
     * @param serviceName The QName of the service.
     */
    public PortInfoImpl(BindingID bindingId, QName portName, QName serviceName) {
        if (bindingId == null) {
            throw new RuntimeException("bindingId cannot be null");
        }
        if (portName == null) {
            throw new RuntimeException("portName cannot be null");
        }
        if (serviceName == null) {
            throw new RuntimeException("serviceName cannot be null");
        }
        this.bindingId = bindingId;
        this.portName = portName;
        this.serviceName = serviceName;
    }

    public String getBindingID() {
        return bindingId.toString();
    }

    public QName getPortName() {
        return portName;
    }

    public QName getServiceName() {
        return serviceName;
    }

    /**
     * Object.equals is overridden here so that PortInfo objects
     * can be compared when using them as keys in the map in
     * HandlerResolverImpl. This method relies on the equals()
     * methods of java.lang.String and javax.xml.namespace.QName.
     *
     * @param obj The PortInfo object to test for equality.
     * @return True if they match, and false if they do not or
     * if the object passed in is not a PortInfo.
     */
    public boolean equals(Object obj) {
        if (obj instanceof PortInfo) {
            PortInfo info = (PortInfo) obj;
            if (bindingId.toString().equals(info.getBindingID()) &&
                portName.equals(info.getPortName()) &&
                serviceName.equals(info.getServiceName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Needed so PortInfoImpl can be used as a key in a map. This
     * method just delegates to the hashCode method of java.lang.String.
     */
    public int hashCode() {
        return bindingId.hashCode();
    }
    
}
