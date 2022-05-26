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

import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;

/**
 * ContainerResolver based on {@link ThreadLocal}.
 * <p>
 * The ThreadLocalContainerResolver is the default implementation available
 * from the ContainerResolver using {@link ContainerResolver#getDefault()}.  Code
 * sections that run with a Container must use the following pattern:
 * <pre>
 *   public void m() {
 *     Container old = ContainerResolver.getDefault().enterContainer(myContainer);
 *     try {
 *       // ... method body
 *     } finally {
 *       ContainerResolver.getDefault().exitContainer(old);
 *     }
 *   }
 * </pre>
 * @since 2.2.7
 */
public class ThreadLocalContainerResolver extends ContainerResolver {
    private ThreadLocal<WeakReference<Container>> containerThreadLocal = new ThreadLocal<>() {
        @Override
        protected WeakReference<Container> initialValue() {
            return new WeakReference<>(Container.NONE);
        }
    };

    /**
     * Default constructor.
     */
    public ThreadLocalContainerResolver() {}

    @Override
    public Container getContainer() {
        return containerThreadLocal.get().get();
    }
    
    /**
     * Enters container
     * @param container Container to set
     * @return Previous container; must be remembered and passed to exitContainer
     */
    public Container enterContainer(Container container) {
        Container old = containerThreadLocal.get().get();
        containerThreadLocal.set(new WeakReference<>(container));
        return old;
    }
    
    /**
     * Exits container
     * @param old Container returned from enterContainer
     */
    public void exitContainer(Container old) {
        containerThreadLocal.set(new WeakReference<>(old));
    }
    
    /**
     * Used by {@link com.sun.xml.ws.api.pipe.Engine} to wrap asynchronous {@link com.sun.xml.ws.api.pipe.Fiber} executions
     * @param container Container
     * @param ex Executor to wrap
     * @return an Executor that will set the container during executions of Runnables
     */
    public Executor wrapExecutor(final Container container, final Executor ex) {
        if (ex == null)
            return null;
        
        return new Executor() {
            @Override
            public void execute(final Runnable command) {
                ex.execute(new Runnable() {
                    @Override
                    public void run() {
                        Container old = enterContainer(container);
                        try {
                            command.run();
                        } finally {
                            exitContainer(old);
                        }
                    }
                });
            }
        };
    }
}
