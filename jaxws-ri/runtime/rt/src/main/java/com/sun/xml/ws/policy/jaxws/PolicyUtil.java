/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.jaxws;

import com.sun.xml.ws.addressing.policy.AddressingFeatureConfigurator;
import com.sun.xml.ws.api.model.wsdl.WSDLModel;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.model.wsdl.WSDLService;
import com.sun.xml.ws.encoding.policy.FastInfosetFeatureConfigurator;
import com.sun.xml.ws.encoding.policy.MtomFeatureConfigurator;
import com.sun.xml.ws.encoding.policy.SelectOptimalEncodingFeatureConfigurator;
import com.sun.xml.ws.policy.PolicyException;
import com.sun.xml.ws.policy.PolicyMap;
import com.sun.xml.ws.policy.PolicyMapKey;
import com.sun.xml.ws.policy.jaxws.spi.PolicyFeatureConfigurator;
import com.sun.xml.ws.policy.privateutil.PolicyLogger;
import com.sun.xml.ws.util.ServiceFinder;

import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.WebServiceException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Rama Pulavarthi
 * @author Fabian Ritzmann
 */
public class PolicyUtil {

    private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyUtil.class);
    private static final Collection<PolicyFeatureConfigurator> CONFIGURATORS =
            new LinkedList<PolicyFeatureConfigurator>();

    static {
        // Add feature configurators that are already built into JAX-WS
        CONFIGURATORS.add(new AddressingFeatureConfigurator());
        CONFIGURATORS.add(new MtomFeatureConfigurator());
        CONFIGURATORS.add(new FastInfosetFeatureConfigurator());
        CONFIGURATORS.add(new SelectOptimalEncodingFeatureConfigurator());

        // Dynamically discover remaining feature configurators
        addServiceProviders(CONFIGURATORS, PolicyFeatureConfigurator.class);
    }

    /**
     * Adds the dynamically discovered implementations for the given service class
     * to the given collection.
     *
     * @param <T> The type of the service class.
     * @param providers The discovered implementations are added to this collection.
     * @param service The service interface.
     */
    public static <T> void addServiceProviders(Collection<T> providers, Class<T> service) {
        final Iterator<T> foundProviders = ServiceFinder.find(service).iterator();
        while (foundProviders.hasNext()) {
            providers.add(foundProviders.next());
        }
    }

    /**
     * Iterates through the ports in the WSDL model, for each policy in the policy
     * map that is attached at endpoint scope computes a list of corresponding
     * WebServiceFeatures and sets them on the port.
     *
     * @param model The WSDL model
     * @param policyMap The policy map
     * @throws PolicyException If the list of WebServiceFeatures could not be computed
     */
    public static void configureModel(final WSDLModel model, PolicyMap policyMap) throws PolicyException {
        LOGGER.entering(model, policyMap);
        for (WSDLService service : model.getServices().values()) {
            for (WSDLPort port : service.getPorts()) {
                final Collection<WebServiceFeature> features = getPortScopedFeatures(policyMap, service.getName(), port.getName());
                for (WebServiceFeature feature : features) {
                    port.addFeature(feature);
                    port.getBinding().addFeature(feature);
                }
            }
        }
        LOGGER.exiting();
    }

    /**
     * Returns the list of features that correspond to the policies in the policy
     * map for a give port
     *
     * @param policyMap The service policies
     * @param serviceName The service name
     * @param portName The service port name
     * @return List of features for the given port corresponding to the policies in the map
     */
    public static Collection<WebServiceFeature> getPortScopedFeatures(PolicyMap policyMap, QName serviceName, QName portName) {
        LOGGER.entering(policyMap, serviceName, portName);
        Collection<WebServiceFeature> features = new ArrayList<WebServiceFeature>();
        try {
            final PolicyMapKey key = PolicyMap.createWsdlEndpointScopeKey(serviceName, portName);
            for (PolicyFeatureConfigurator configurator : CONFIGURATORS) {
                Collection<WebServiceFeature> additionalFeatures = configurator.getFeatures(key, policyMap);
                if (additionalFeatures != null) {
                    features.addAll(additionalFeatures);
                }
            }
        } catch (PolicyException e) {
            throw new WebServiceException(e);
        }
        LOGGER.exiting(features);
        return features;
    }

}
