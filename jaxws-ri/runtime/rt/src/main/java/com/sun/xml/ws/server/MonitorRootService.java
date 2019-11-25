/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.server;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.BindingID;
import com.sun.xml.ws.api.WSFeatureList;
import com.sun.xml.ws.api.EndpointAddress;
import com.sun.xml.ws.api.addressing.AddressingVersion;
import com.sun.xml.ws.api.server.*;
import com.sun.xml.ws.transport.http.HttpAdapter;
import com.sun.xml.ws.util.RuntimeVersion;
import org.glassfish.gmbal.AMXMetadata;
import org.glassfish.gmbal.Description;
import org.glassfish.gmbal.ManagedAttribute;
import org.glassfish.gmbal.ManagedObject;
import java.net.URL;
import javax.xml.namespace.QName;
import java.util.*;

/**
 * @author Harold Carr
 */
@ManagedObject
@Description("Metro Web Service endpoint")
@AMXMetadata(type="WSEndpoint")
public final class MonitorRootService extends MonitorBase {

    private final WSEndpoint endpoint;

    MonitorRootService(final WSEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    //
    // Items from WSEndpoint
    //

    @ManagedAttribute
    @Description("Policy associated with Endpoint")
    public String policy() {
        return endpoint.getPolicyMap() != null ?
               endpoint.getPolicyMap().toString() : null;
    }

    @ManagedAttribute
    @Description("Container")
    public @NotNull Container container() {
        return endpoint.getContainer();
    }


    @ManagedAttribute
    @Description("Port name")
    public @NotNull QName portName() {
        return endpoint.getPortName();
    }

    @ManagedAttribute
    @Description("Service name")
    public @NotNull QName serviceName() {
        return endpoint.getServiceName();
    }

    //
    // Items from WSBinding
    //

    @ManagedAttribute
    @Description("Binding SOAP Version")
    public String soapVersionHttpBindingId() {
        return endpoint.getBinding().getSOAPVersion().httpBindingId;
    }

    @ManagedAttribute
    @Description("Binding Addressing Version")
    public AddressingVersion addressingVersion() {
        return endpoint.getBinding().getAddressingVersion();
    }

    @ManagedAttribute
    @Description("Binding Identifier")
    public @NotNull BindingID bindingID() {
        return endpoint.getBinding().getBindingId();
    }

    @ManagedAttribute
    @Description("Binding features")
    public @NotNull WSFeatureList features() {
        return endpoint.getBinding().getFeatures();
    }

    //
    // Items from WSDLPort
    //

    @ManagedAttribute
    @Description("WSDLPort bound port type")
    public QName wsdlPortTypeName() {
        return endpoint.getPort() != null ?
               endpoint.getPort().getBinding().getPortTypeName() : null;
    }

    @ManagedAttribute
    @Description("Endpoint address")
    public EndpointAddress wsdlEndpointAddress() {
        return endpoint.getPort() != null ?
               endpoint.getPort().getAddress() : null;
    }

    //
    // Items from ServiceDefinition
    //

    @ManagedAttribute
    @Description("Documents referenced")
    public Set<String> serviceDefinitionImports() {
        return endpoint.getServiceDefinition() != null ?
               endpoint.getServiceDefinition().getPrimary().getImports() : null;
    }

    @ManagedAttribute
    @Description("System ID where document is taken from")
    public URL serviceDefinitionURL() {
        return endpoint.getServiceDefinition() != null ?
               endpoint.getServiceDefinition().getPrimary().getURL() : null;
    }

    //
    // Items from SEIModel
    //

    @ManagedAttribute
    @Description("SEI model WSDL location")
    public String seiModelWSDLLocation() {
        return endpoint.getSEIModel() != null ? 
               endpoint.getSEIModel().getWSDLLocation() : null;
    }

    //
    // Items from RuntimeVersion
    //

    @ManagedAttribute
    @Description("JAX-WS runtime version")
    public String jaxwsRuntimeVersion() {
        return RuntimeVersion.VERSION.toString();
    }

    //
    // Items from HttpAdapter
    //

    @ManagedAttribute
    @Description("If true: show what goes across HTTP transport")
    public boolean dumpHTTPMessages() { return HttpAdapter.dump; }


    @ManagedAttribute
    @Description("Show what goes across HTTP transport")
    public void dumpHTTPMessages(final boolean x) { HttpAdapter.setDump(x); }

}

// End of file.
