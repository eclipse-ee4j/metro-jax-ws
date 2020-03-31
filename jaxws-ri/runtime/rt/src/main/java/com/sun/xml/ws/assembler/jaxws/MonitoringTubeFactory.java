/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.assembler.jaxws;

import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.assembler.dev.ClientTubelineAssemblyContext;
import com.sun.xml.ws.assembler.dev.ServerTubelineAssemblyContext;
import com.sun.xml.ws.assembler.dev.TubeFactory;

import jakarta.xml.ws.WebServiceException;

/**
 * TubeFactory implementation creating one of the standard JAX-WS RI tubes
 *
 * @author Marek Potociar (marek.potociar at sun.com)
 */
public final class MonitoringTubeFactory implements TubeFactory {

    public Tube createTube(ClientTubelineAssemblyContext context) throws WebServiceException {
        return context.getTubelineHead();
    }

    public Tube createTube(ServerTubelineAssemblyContext context) throws WebServiceException {
        return context.getWrappedContext().createMonitoringTube(context.getTubelineHead());
    }
}
