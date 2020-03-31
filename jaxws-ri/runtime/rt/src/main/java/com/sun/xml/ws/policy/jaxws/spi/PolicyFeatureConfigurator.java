/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.jaxws.spi;

import com.sun.xml.ws.policy.PolicyException;
import com.sun.xml.ws.policy.PolicyMap;
import com.sun.xml.ws.policy.PolicyMapKey;
import java.util.Collection;
import jakarta.xml.ws.WebServiceFeature;

/**
 * The service provider implementing this interface will be discovered and called to configure
 * wsdl model based on PolicyMap bound to it.
 *
 * @since JAX-WS 2.2
 *
 * @author japod
 * @author Fabian Ritzmann
 * @author Rama.Pulavarthi@sun.com
 */
public interface PolicyFeatureConfigurator {

    /**
     * A callback method that allows to retrieve policy related information from provided PolicyMap
     * and return a list of corresponding WebServiceFeatures.
     *
     * @param key Identifies the policy in the policy map
     * @param policyMap Provides policies as a source of information on proper configuration
     * @return A list of features that correspond to the policy identified by the policy map key. May be empty but not null.
     * @throws PolicyException If an error occurred
     */
    public Collection<WebServiceFeature> getFeatures(PolicyMapKey key, PolicyMap policyMap) throws PolicyException;
    
}
