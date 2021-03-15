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

import com.sun.xml.ws.api.BindingID;
import com.sun.xml.ws.api.pipe.TubelineAssembler;
import com.sun.xml.ws.api.pipe.TubelineAssemblerFactory;
import com.sun.xml.ws.resources.TubelineassemblyMessages;
import java.util.logging.Logger;

/**
 *
 * @deprecated Use {@code com.sun.xml.ws.assembler.metro.jaxws.TransportTubeFactory} provided by metro-wsit instead.
 */
@Deprecated
public final class TransportTubeFactory extends TubelineAssemblerFactory {

    private static final Logger LOG = Logger.getLogger(TransportTubeFactory.class.getName());

    private final TubelineAssemblerFactory taf;

    public TransportTubeFactory() {
        super();
            LOG.warning(TubelineassemblyMessages.MASM_0050_DEPRECATED_TUBE(TransportTubeFactory.class.getName(), "com.sun.xml.ws.assembler.metro.jaxws.TransportTubeFactory"));
        try {
            taf = (TubelineAssemblerFactory) Class.forName("com.sun.xml.ws.assembler.metro.jaxws.TransportTubeFactory").getConstructor().newInstance();
        } catch (ReflectiveOperationException | SecurityException ex) {
            LOG.fine(TubelineassemblyMessages.MASM_0014_UNABLE_TO_LOAD_CLASS("com.sun.xml.ws.assembler.metro.jaxws.TransportTubeFactory"));
            // cannot work without this
            throw new RuntimeException(ex);
        }
    }


    @Override
    public TubelineAssembler doCreate(BindingID bindingId) {
        return taf.doCreate(bindingId);
    }

}
