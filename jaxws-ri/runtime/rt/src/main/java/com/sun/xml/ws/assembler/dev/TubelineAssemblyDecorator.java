/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.assembler.dev;

import java.util.ArrayList;
import java.util.Collection;

import com.sun.xml.ws.api.pipe.Tube;

/**
 * Decorate Tubes during tubeline assembly
 *
 * @since 2.2.7
 */
public class TubelineAssemblyDecorator {
    /**
     * Composite decorator
     * @param decorators decorators
     * @return composite that delegates to a list of decorators
     */
    public static TubelineAssemblyDecorator composite(Iterable<TubelineAssemblyDecorator> decorators) {
        return new CompositeTubelineAssemblyDecorator(decorators);
    }
    
    /**
     * Decorate client tube
     * @param tube tube
     * @param context client context
     * @return updated tube for tubeline or return tube parameter to no-op
     */
    public Tube decorateClient(Tube tube, ClientTubelineAssemblyContext context) {
        return tube;
    }
    
    /**
     * Decorate client head tube.  The decorateClient method will have been called first.
     * @param tube tube
     * @param context client context
     * @return updated tube for tubeline or return tube parameter to no-op
     */
    public Tube decorateClientHead(
            Tube tube, ClientTubelineAssemblyContext context) {
        return tube;
    }

    /**
     * Decorate client tail tube.  The decorateClient method will have been called first.
     * @param tube tube
     * @param context client context
     * @return updated tube for tubeline or return tube parameter to no-op
     */
    public Tube decorateClientTail(
            Tube tube,
            ClientTubelineAssemblyContext context) {
        return tube;
    }
    
    /**
     * Decorate server tube
     * @param tube tube
     * @param context server context
     * @return updated tube for tubeline or return tube parameter to no-op
     */
    public Tube decorateServer(Tube tube, ServerTubelineAssemblyContext context) {
        return tube;
    }
    
    /**
     * Decorate server tail tube.  The decorateServer method will have been called first.
     * @param tube tube
     * @param context server context
     * @return updated tube for tubeline or return tube parameter to no-op
     */
    public Tube decorateServerTail(
            Tube tube, ServerTubelineAssemblyContext context) {
        return tube;
    }

    /**
     * Decorate server head tube.  The decorateServer method will have been called first
     * @param tube tube
     * @param context server context
     * @return updated tube for tubeline or return tube parameter to no-op
     */
    public Tube decorateServerHead(
            Tube tube,
            ServerTubelineAssemblyContext context) {
        return tube;
    }
    
    private static class CompositeTubelineAssemblyDecorator extends TubelineAssemblyDecorator {
        private Collection<TubelineAssemblyDecorator> decorators = new ArrayList<TubelineAssemblyDecorator>();
        
        public CompositeTubelineAssemblyDecorator(Iterable<TubelineAssemblyDecorator> decorators) {
            for (TubelineAssemblyDecorator decorator : decorators) {
                this.decorators.add(decorator);
            }
        }
        
        @Override
        public Tube decorateClient(Tube tube, ClientTubelineAssemblyContext context) {
            for (TubelineAssemblyDecorator decorator : decorators) {
                tube = decorator.decorateClient(tube, context);
            }
            return tube;
        }

        @Override
        public Tube decorateClientHead(
                Tube tube, ClientTubelineAssemblyContext context) {
            for (TubelineAssemblyDecorator decorator : decorators) {
                tube = decorator.decorateClientHead(tube, context);
            }
            return tube;
        }

        @Override
        public Tube decorateClientTail(
                Tube tube,
                ClientTubelineAssemblyContext context) {
            for (TubelineAssemblyDecorator decorator : decorators) {
                tube = decorator.decorateClientTail(tube, context);
            }
            return tube;
        }
        
        public Tube decorateServer(Tube tube, ServerTubelineAssemblyContext context) {
            for (TubelineAssemblyDecorator decorator : decorators) {
                tube = decorator.decorateServer(tube, context);
            }
            return tube;
        }
        
        @Override
        public Tube decorateServerTail(
                Tube tube, ServerTubelineAssemblyContext context) {
            for (TubelineAssemblyDecorator decorator : decorators) {
                tube = decorator.decorateServerTail(tube, context);
            }
            return tube;
        }

        @Override
        public Tube decorateServerHead(
                Tube tube,
                ServerTubelineAssemblyContext context) {
            for (TubelineAssemblyDecorator decorator : decorators) {
                tube = decorator.decorateServerHead(tube, context);
            }
            return tube;
        }
    }
}
