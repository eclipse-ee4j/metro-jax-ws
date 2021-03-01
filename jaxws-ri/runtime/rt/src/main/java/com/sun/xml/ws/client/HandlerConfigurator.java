/*
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.client.WSPortInfo;
import com.sun.xml.ws.binding.BindingImpl;
import com.sun.xml.ws.handler.HandlerChainsModel;
import com.sun.xml.ws.util.HandlerAnnotationInfo;
import com.sun.xml.ws.util.HandlerAnnotationProcessor;

import jakarta.jws.HandlerChain;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.handler.HandlerResolver;
import jakarta.xml.ws.handler.PortInfo;
import jakarta.xml.ws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Used by {@link WSServiceDelegate} to configure {@link BindingImpl}
 * with handlers. The two mechanisms encapsulated by this abstraction
 * is {@link HandlerChain} annotaion and {@link HandlerResolver}
 * interface.
 *
 * @author Kohsuke Kawaguchi
 */
abstract class HandlerConfigurator {
    /**
     * Configures the given {@link BindingImpl} object by adding handlers to it.
     */
    abstract void configureHandlers(@NotNull WSPortInfo port, @NotNull BindingImpl binding);

    /**
     * Returns a {@link HandlerResolver}, if this object encapsulates any {@link HandlerResolver}.
     * Otherwise null.
     */
    abstract HandlerResolver getResolver();


    /**
     * Configures handlers by calling {@link HandlerResolver}.
     * <p>
     * When a null {@link HandlerResolver} is set by the user to
     * {@link Service#setHandlerResolver(HandlerResolver)}, we'll use this object
     * with null {@link #resolver}.
     */
    static final class HandlerResolverImpl extends HandlerConfigurator {
        private final @Nullable HandlerResolver resolver;

        public HandlerResolverImpl(HandlerResolver resolver) {
            this.resolver = resolver;
        }

        @Override
        void configureHandlers(@NotNull WSPortInfo port, @NotNull BindingImpl binding) {
            if (resolver!=null) {
                binding.setHandlerChain(resolver.getHandlerChain(port));
            }
        }


        @Override
        HandlerResolver getResolver() {
            return resolver;
        }
    }

    /**
     * Configures handlers from {@link HandlerChain} annotation.
     *
     * <p>
     * This class is a simple
     * map of PortInfo objects to handler chains. It is used by a
     * {@link WSServiceDelegate} object, and can
     * be replaced by user code with a different class implementing
     * HandlerResolver. This class is only used on the client side, and
     * it includes a lot of logging to help when there are issues since
     * it deals with port names, service names, and bindings. All three
     * must match when getting a handler chain from the map.
     *
     * <p>It is created by the {@link WSServiceDelegate}
     * class , which uses {@link HandlerAnnotationProcessor} to create
     * a handler chain and then it sets the chains on this class and they
     * are put into the map. The ServiceContext uses the map to set handler
     * chains on bindings when they are created.
     */
    static final class AnnotationConfigurator extends HandlerConfigurator {
        private final HandlerChainsModel handlerModel;
        private final Map<WSPortInfo,HandlerAnnotationInfo> chainMap = new HashMap<>();
        private static final Logger logger = Logger.getLogger(
            com.sun.xml.ws.util.Constants.LoggingDomain + ".handler");

        AnnotationConfigurator(WSServiceDelegate delegate) {
            handlerModel = HandlerAnnotationProcessor.buildHandlerChainsModel(delegate.getServiceClass());
            assert handlerModel!=null; // this class is suppeod to be called only when there's @HandlerCHain
        }


        @Override
        void configureHandlers(WSPortInfo port, BindingImpl binding) {
            //Check in cache first
            HandlerAnnotationInfo chain = chainMap.get(port);

            if(chain==null) {
                logGetChain(port);
                // Put it in cache
                chain = handlerModel.getHandlersForPortInfo(port);
                chainMap.put(port,chain);
            }

            if (binding instanceof SOAPBinding) {
                ((SOAPBinding) binding).setRoles(chain.getRoles());
            }

            logSetChain(port,chain);
            binding.setHandlerChain(chain.getHandlers());
        }

        @Override
        HandlerResolver getResolver() {
            return new HandlerResolver() {
                @Override
                public List<Handler> getHandlerChain(PortInfo portInfo) {
                    return new ArrayList<>(
                        handlerModel.getHandlersForPortInfo(portInfo).getHandlers());
                }
            };
        }

        // logged at finer level
        private void logSetChain(WSPortInfo info, HandlerAnnotationInfo chain) {
            if (logger.isLoggable(Level.FINER)) {
                logger.log(Level.FINER, "Setting chain of length {0} for port info", chain.getHandlers().size());
                logPortInfo(info, Level.FINER);
            }
        }

        // logged at fine level
        private void logGetChain(WSPortInfo info) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("No handler chain found for port info:");
                logPortInfo(info, Level.FINE);
                logger.fine("Existing handler chains:");
                if (chainMap.isEmpty()) {
                    logger.fine("none");
                } else {
                    for (Map.Entry<WSPortInfo, HandlerAnnotationInfo> entry : chainMap.entrySet()) {
                        logger.log(Level.FINE, "{0} handlers for port info ", entry.getValue().getHandlers().size());
                        logPortInfo(entry.getKey(), Level.FINE);
                    }
                }
            }
        }

        private void logPortInfo(WSPortInfo info, Level level) {
            logger.log(level, "binding: {0}\nservice: {1}\nport: {2}",
                    new Object[]{info.getBindingID(), info.getServiceName(), info.getPortName()});
        }
    }
}
