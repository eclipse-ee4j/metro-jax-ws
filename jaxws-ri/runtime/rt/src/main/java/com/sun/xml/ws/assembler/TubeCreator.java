/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.assembler;

import com.sun.istack.logging.Logger;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.assembler.dev.ClientTubelineAssemblyContext;
import com.sun.xml.ws.assembler.dev.ServerTubelineAssemblyContext;
import com.sun.xml.ws.assembler.dev.TubeFactory;
import com.sun.xml.ws.assembler.dev.TubelineAssemblyContextUpdater;
import com.sun.xml.ws.resources.TubelineassemblyMessages;
import com.sun.xml.ws.runtime.config.TubeFactoryConfig;

/**
 * Utility class that encapsulates logic of loading TubeFactory
 * instances and creating Tube instances.
 *
 * @author m_potociar
 */
final class TubeCreator {
    private static final Logger LOGGER = Logger.getLogger(TubeCreator.class);
    private final TubeFactory factory;
    private final String msgDumpPropertyBase;

    TubeCreator(TubeFactoryConfig config, ClassLoader tubeFactoryClassLoader) {
        String className = config.getClassName();
        try {
            Class<?> factoryClass;
            if (isJDKInternal(className)) {
                factoryClass = Class.forName(className, true, TubeCreator.class.getClassLoader());
            } else {
                factoryClass = Class.forName(className, true, tubeFactoryClassLoader);
            }
            if (TubeFactory.class.isAssignableFrom(factoryClass)) {
                // We can suppress "unchecked" warning here as we are checking for the correct type in the if statement above
                @SuppressWarnings("unchecked")
                Class<TubeFactory> typedClass = (Class<TubeFactory>) factoryClass;
                this.factory = typedClass.getConstructor().newInstance();
                this.msgDumpPropertyBase = this.factory.getClass().getName() + ".dump";
            } else {
                throw new RuntimeException(TubelineassemblyMessages.MASM_0015_CLASS_DOES_NOT_IMPLEMENT_INTERFACE(factoryClass.getName(), TubeFactory.class.getName()));
            }
        } catch (ReflectiveOperationException ex) {
            throw LOGGER.logSevereException(new RuntimeException(TubelineassemblyMessages.MASM_0016_UNABLE_TO_INSTANTIATE_TUBE_FACTORY(className), ex), true);
        }
    }

    Tube createTube(ClientTubelineAssemblyContext context) {
        // TODO implement passing init parameters (if any) to the factory
        return factory.createTube(context);
    }

    Tube createTube(ServerTubelineAssemblyContext context) {
        // TODO implement passing init parameters (if any) to the factory
        return factory.createTube(context);
    }

    void updateContext(ClientTubelineAssemblyContext context) {
        if (factory instanceof TubelineAssemblyContextUpdater) {
            ((TubelineAssemblyContextUpdater) factory).prepareContext(context);
        }
    }

    void updateContext(ServerTubelineAssemblyContext context) {
        if (factory instanceof TubelineAssemblyContextUpdater) {
            ((TubelineAssemblyContextUpdater) factory).prepareContext(context);
        }
    }

    String getMessageDumpPropertyBase() {
        return msgDumpPropertyBase;
    }

    private boolean isJDKInternal(String className) {
        // avoid repackaging
        return className.startsWith("com." + "sun.xml.internal.ws");
    }

}
