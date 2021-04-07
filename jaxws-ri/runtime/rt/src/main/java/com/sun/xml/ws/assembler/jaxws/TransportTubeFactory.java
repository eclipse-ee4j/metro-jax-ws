/*
 * Copyright (c) 2021 Oracle and/or its affiliates. All rights reserved.
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
import com.sun.xml.ws.resources.TubelineassemblyMessages;
import jakarta.xml.ws.WebServiceException;
import java.util.logging.Logger;

/**
 *
 * @deprecated Use {@code com.sun.xml.ws.assembler.metro.jaxws.TransportTubeFactory} provided by metro-wsit instead.
 */
@Deprecated
public final class TransportTubeFactory implements TubeFactory {

    private static final Logger LOG = Logger.getLogger(TransportTubeFactory.class.getName());

    private final TubeFactory taf;

    public TransportTubeFactory() {
        super();
            LOG.warning(TubelineassemblyMessages.MASM_0050_DEPRECATED_TUBE(TransportTubeFactory.class.getName(), "com.sun.xml.ws.assembler.metro.jaxws.TransportTubeFactory"));
        try {
            taf = (TubeFactory) Class.forName("com.sun.xml.ws.assembler.metro.jaxws.TransportTubeFactory").getConstructor().newInstance();
        } catch (ReflectiveOperationException | SecurityException ex) {
            LOG.fine(TubelineassemblyMessages.MASM_0014_UNABLE_TO_LOAD_CLASS("com.sun.xml.ws.assembler.metro.jaxws.TransportTubeFactory"));
            // cannot work without this
            throw new RuntimeException(ex);
        }
    }


    @Override
    public Tube createTube(ClientTubelineAssemblyContext context) throws WebServiceException {
        return taf.createTube(context);
    }

    @Override
    public Tube createTube(ServerTubelineAssemblyContext context) throws WebServiceException {
        return taf.createTube(context);
    }

}
