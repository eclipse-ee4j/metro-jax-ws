/*
 * Copyright (c) 2010, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.http.servlet;

import org.glassfish.external.probe.provider.annotations.ProbeProvider;
import org.glassfish.external.probe.provider.annotations.Probe;
import org.glassfish.external.probe.provider.annotations.ProbeParam;

/**
 * sun-jaxws.xml deployment probe. A registered listener get to listen the emited
 * sun-jaxws.xml deployment/undepolyment events.
 *
 * @author Jitendra Kotamraju
 */
@ProbeProvider(moduleProviderName="glassfish", moduleName="webservices", probeProviderName="deployment-ri")
public class JAXWSRIDeploymentProbeProvider {

    /**
     * Default constructor.
     */
    public JAXWSRIDeploymentProbeProvider() {}

    @Probe(name="deploy", hidden=true)
    public void deploy(@ProbeParam("adapter")ServletAdapter adpater) {
        // intentionally left empty.
    }

    @Probe(name="undeploy", hidden=true)
    public void undeploy(@ProbeParam("adapter")ServletAdapter adapter) {
        // intentionally left empty.
    }
    
}
