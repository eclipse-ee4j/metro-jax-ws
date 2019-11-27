/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client;

import com.sun.xml.ws.api.model.wsdl.WSDLService;
import com.sun.xml.ws.api.server.Container;

import java.util.Map;

import javax.xml.namespace.QName;

import org.glassfish.gmbal.AMXMetadata;
import org.glassfish.gmbal.Description;
import org.glassfish.gmbal.ManagedAttribute;
import org.glassfish.gmbal.ManagedObject;

import java.net.URL;

/**
 * @author Harold Carr
 */
@ManagedObject
@Description("Metro Web Service client")
@AMXMetadata(type="WSClient")
public final class MonitorRootClient extends com.sun.xml.ws.server.MonitorBase {

    private final Stub stub;

    MonitorRootClient(final Stub stub) {
        this.stub = stub;
    }

    /*
    private static final Logger logger = Logger.getLogger(
        com.sun.xml.ws.util.Constants.LoggingDomain + ".client.stub");
    */

    //
    // From WSServiceDelegate
    //

    @ManagedAttribute
    private Container getContainer() { return stub.owner.getContainer(); }

    @ManagedAttribute        
    private Map<QName, PortInfo> qnameToPortInfoMap() { return stub.owner.getQNameToPortInfoMap(); }

    @ManagedAttribute
    private QName serviceName() { return stub.owner.getServiceName(); }
        
    @ManagedAttribute
    private Class serviceClass() { return stub.owner.getServiceClass(); }
        
    @ManagedAttribute
    private URL wsdlDocumentLocation() { return stub.owner.getWSDLDocumentLocation(); }

    @ManagedAttribute
    private WSDLService wsdlService() { return stub.owner.getWsdlService(); }

    
        
}
