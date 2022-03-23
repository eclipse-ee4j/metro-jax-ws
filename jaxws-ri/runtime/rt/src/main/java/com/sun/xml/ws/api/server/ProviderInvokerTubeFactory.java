/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.server;


import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.server.provider.AsyncProviderInvokerTube;
import com.sun.xml.ws.server.provider.ProviderArgumentsBuilder;
import com.sun.xml.ws.server.provider.ProviderInvokerTube;
import com.sun.xml.ws.server.provider.SyncProviderInvokerTube;
import com.sun.xml.ws.util.ServiceFinder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Factory for Provider invoker tubes that know how to handle specific
 * types of Providers (i.e., jakarta.xml.ws.Provider).
 *
 */

public abstract class ProviderInvokerTubeFactory<T> {

    /**
     * Default constructor.
     */
    protected ProviderInvokerTubeFactory() {}

    protected abstract ProviderInvokerTube<T> doCreate(@NotNull final Class<T> implType,
                                                       @NotNull final Invoker invoker,
                                                       @NotNull final ProviderArgumentsBuilder<?> argsBuilder,
                                                                final boolean isAsync);

    private static final ProviderInvokerTubeFactory DEFAULT = new DefaultProviderInvokerTubeFactory();

    private static class DefaultProviderInvokerTubeFactory<T> extends ProviderInvokerTubeFactory<T> {
        @Override
        public ProviderInvokerTube<T> doCreate(@NotNull final Class<T> implType,
                                               @NotNull final Invoker invoker,
                                               @NotNull final ProviderArgumentsBuilder<?> argsBuilder,
                                                        final boolean isAsync)
        {
            return createDefault(implType, invoker, argsBuilder, isAsync);
        }
    }

    /**
     */
    public static <T> ProviderInvokerTube<T> create(@Nullable final ClassLoader classLoader,
                                                    @NotNull  final Container container,
                                                    @NotNull  final Class<T> implType,
                                                    @NotNull  final Invoker invoker,
                                                    @NotNull  final ProviderArgumentsBuilder<?> argsBuilder,
                                                              final boolean isAsync)
    {
        for (ProviderInvokerTubeFactory factory : ServiceFinder.find(ProviderInvokerTubeFactory.class,
                                                                     classLoader, container))
        {
            ProviderInvokerTube<T> tube = factory.doCreate(implType, invoker, argsBuilder, isAsync);
            if (tube != null) {
                if (logger.isLoggable(Level.FINE)) {
                    ProviderInvokerTubeFactory.logger.log(Level.FINE, "{0} successfully created {1}", new Object[]{factory.getClass(), tube});
                }
                return tube;
            }
        }
        return DEFAULT.createDefault(implType, invoker, argsBuilder, isAsync);
    }

    protected ProviderInvokerTube<T> createDefault(@NotNull final Class<T> implType,
                                                   @NotNull final Invoker invoker,
                                                   @NotNull final ProviderArgumentsBuilder<?> argsBuilder,
                                                            final boolean isAsync)
    {
        return
            isAsync
            ? new AsyncProviderInvokerTube(invoker, argsBuilder)
            : new SyncProviderInvokerTube (invoker, argsBuilder);
    }

    private static final Logger logger = Logger.getLogger(ProviderInvokerTubeFactory.class.getName());
}
