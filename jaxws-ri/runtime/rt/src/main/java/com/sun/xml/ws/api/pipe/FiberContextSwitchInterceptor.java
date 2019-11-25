/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.pipe;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Interception for {@link Fiber} context switch.
 *
 * <p>
 * Even though pipeline runs asynchronously, sometimes it's desirable
 * to bind some state to the current thread running a fiber. Such state
 * may include security subject (in terms of {@link AccessController#doPrivileged}),
 * or a transaction.
 *
 * <p>
 * This mechanism makes it possible to do such things, by allowing
 * some code to be executed before and after a thread executes a fiber.
 *
 * <p>
 * The design also encapsulates the entire fiber execution in a single
 * opaque method invocation {@link Work#execute}, allowing the use of
 * {@code finally} block.
 *
 * @author Kohsuke Kawaguchi
 */
public interface FiberContextSwitchInterceptor {
    /**
     * Allows the interception of the fiber execution.
     *
     * <p>
     * This method needs to be implemented like this:
     *
     * <pre>
     * &lt;R,P> R execute( Fiber f, P p, Work&lt;R,P> work ) {
     *   // do some preparation work
     *   ...
     *   try {
     *     // invoke
     *     return work.execute(p);
     *   } finally {
     *     // do some clean up work
     *     ...
     *   }
     * }
     * </pre>
     *
     * <p>
     * While somewhat unintuitive,
     * this interception mechanism enables the interceptor to wrap
     * the whole fiber execution into a {@link AccessController#doPrivileged(PrivilegedAction)},
     * for example.
     *
     * @param f
     *      {@link Fiber} to be executed.
     * @param p
     *      The opaque parameter value for {@link Work}. Simply pass this value to
     *      {@link Work#execute(Object)}.
     * @return
     *      The opaque return value from the the {@link Work}. Simply return
     *      the value from {@link Work#execute(Object)}.
     */
    <R,P> R execute( Fiber f, P p, Work<R,P> work );

    /**
     * Abstraction of the execution that happens inside the interceptor.
     */
    interface Work<R,P> {
        /**
         * Have the current thread executes the current fiber,
         * and returns when it stops doing so.
         *
         * <p>
         * The parameter and the return value is controlled by the
         * JAX-WS runtime, and interceptors should simply treat
         * them as opaque values.
         */
        R execute(P param);
    }
}
