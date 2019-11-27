/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.httpspi.servlet;


import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.ws.Endpoint;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.spi.Provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jitendra Kotamraju
 */
public final class EndpointAdapterFactory implements DeploymentDescriptorParser.AdapterFactory<EndpointAdapter> {
    private static final Logger LOGGER = Logger.getLogger(EndpointAdapterFactory.class.getName());

    private final EndpointContextImpl appContext;

    public EndpointAdapterFactory() {
        this.appContext = new EndpointContextImpl();
    }

    @Override
    public EndpointAdapter createAdapter(String name, String urlPattern, Class implType,
        QName serviceName, QName portName, String bindingId,
        List<Source> metadata, WebServiceFeature... features) {

        LOGGER.info("Creating Endpoint using JAX-WS 2.2 HTTP SPI");
        InvokerImpl endpointInvoker = new InvokerImpl(implType);
        Endpoint endpoint = Provider.provider().createEndpoint(bindingId, implType, endpointInvoker, features);
        appContext.add(endpoint);
        endpoint.setEndpointContext(appContext);

        // Use DD's service name, port names as WSDL_SERVICE and WSDL_PORT
        if (portName != null || serviceName != null) {
            Map<String, Object> props = new HashMap<String, Object>();
            if (portName != null) {
                props.put(Endpoint.WSDL_PORT, portName);
            }
            if (serviceName != null) {
                props.put(Endpoint.WSDL_SERVICE, serviceName);
            }
            if (LOGGER.isLoggable(Level.INFO)) {
                LOGGER.log(Level.INFO, "Setting Endpoint Properties={0}", props);
            }
            endpoint.setProperties(props);
        }

        // Set bundle's wsdl, xsd docs as metadata
        if (metadata != null) {
            endpoint.setMetadata(metadata);
            List<String> docId = new ArrayList<String>();
            for(Source source : metadata) {
                docId.add(source.getSystemId());
            }
            if (LOGGER.isLoggable(Level.INFO)) {
                LOGGER.log(Level.INFO, "Setting metadata={0}", docId);
            }
        }

        // Set DD's handlers
        // endpoint.getBinding().setHandlerChain(binding.getHandlerChain());

        return new EndpointAdapter(endpoint, urlPattern);
    }

}
