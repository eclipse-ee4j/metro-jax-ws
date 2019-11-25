/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.server;

import com.sun.istack.NotNull;

import javax.xml.ws.Provider;
import javax.xml.ws.WebServiceContext;
import java.util.concurrent.Executor;

/**
 * Asynchronous version of {@link Provider}.
 *
 * <p>
 * Applications that use the JAX-WS RI can implement this interface instead of
 * {@link Provider} to implement asynchronous web services (AWS.) AWS enables
 * applications to perform operations with long latency without blocking a thread,
 * and thus particularly suitable for highly scalable service implementation,
 * at the expesnce of implementation complexity. 
 *
 * <h2>Programming Model</h2>
 * <p>
 * Whenever a new reuqest arrives, the JAX-WS RI invokes the {@link #invoke} method
 * to notify the application. Normally, the application then schedules an execution
 * of this request, and exit from this method immediately (the point of AWS is not
 * to use this calling thread for request processing.)
 *
 * <p>
 * Unlike the synchronous version, which requires the response to be given as the return value,
 * with AWS the JAX-WS RI will keep the connection with client open, until the application
 * eventually notifies the JAX-WS RI via {@link AsyncProviderCallback}. When that
 * happens that causes the JAX-WS RI to send back a response to the client.
 *
 * <p>
 * The following code shows a very simple AWS example:
 *
 * <pre>
 * &#64;WebService
 * class MyAsyncEchoService implements AsyncProvider&lt;Source> {
 *     private static final {@link Executor} exec = ...;
 *
 *     public void invoke( final Source request, final AsyncProviderCallback&lt;Source> callback, final WebServiceContext context) {
 *         exec.execute(new {@link Runnable}() {
 *             public void run() {
 *                 Thread.sleep(1000);     // kill time.
 *                 callback.send(request); // just echo back
 *             }
 *         });
 *     }
 * }
 * </pre>
 *
 * <p>
 * Please also check the {@link Provider} and its programming model for general
 * provider programming model.
 *
 *
 * <h2>WebServiceContext</h2>
 * <p>
 * In synchronous web services, the injected {@link WebServiceContext} instance uses
 * the calling {@link Thread} to determine which request it should return information about.
 * This no longer works with AWS, as you may need to call {@link WebServiceContext}
 * much later, possibly from entirely different thread.
 *
 * <p>
 * For this reason, {@link AsyncProvider} passes in {@link WebServiceContext} as
 * a parameter. This object remains usable until you invoke {@link AsyncProviderCallback},
 * and it can be invoked from any thread, even concurrently. AWS must not use the injected
 * {@link WebServiceContext}, as its behavior is undefined.
 *
 * @see Provider
 * @author Jitendra Kotamraju
 * @author Kohsuke Kawaguchi
 * @since 2.1
 */
public interface AsyncProvider<T> {
    /**
     * Schedules an execution of a request.
     *
     * @param request
     *      Represents the request message or payload.
     * @param callback
     *      Application must notify this callback interface when the processing
     *      of a request is complete.
     * @param context
     *      The web service context instance that can be used to retrieve
     *      context information about the given request.
     */
    public void invoke(
        @NotNull T request,
        @NotNull AsyncProviderCallback<T> callback,
        @NotNull WebServiceContext context);
}
