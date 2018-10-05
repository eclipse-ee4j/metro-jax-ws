/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.async_client_transport;

import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.pipe.Engine;
import com.sun.xml.ws.api.pipe.Fiber;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.util.Pool;
import com.sun.istack.NotNull;

import javax.xml.ws.WebServiceException;
import java.io.Closeable;

/**
 * @author Rama.Pulavarthi@sun.com
 */
public class RequestSender implements Closeable {
    private final Tube masterTubeline;
    private Pool<Tube> tubelinePool;
    private volatile Engine engine;

    public RequestSender(String name, Tube tubeline) {
        this.masterTubeline = tubeline;
        this.engine = new Engine(name);
        this.tubelinePool = new Pool.TubePool(masterTubeline);

    }

    /**
     * Sends the request {@link com.sun.xml.ws.api.message.Packet} and returns the corresponding response {@link com.sun.xml.ws.api.message.Packet}.
     * This method should be used for Req-Resp MEP
     *
     * @param request {@link com.sun.xml.ws.api.message.Packet} containing the message to be send
     * @return response {@link com.sun.xml.ws.api.message.Message} wrapped in a response {@link com.sun.xml.ws.api.message.Packet} received
     */
    public Packet send(Packet request) {
        if (tubelinePool == null)
                        throw new WebServiceException("close method has already been invoked"); // TODO: i18n
        
        final Tube tubeline = tubelinePool.take();
        try {
            return engine.createFiber().runSync(tubeline, request);
        } finally {
            tubelinePool.recycle(tubeline);
        }
    }

    public void sendAsync(Packet request, final Fiber.CompletionCallback completionCallback) {
        if (tubelinePool == null)
                    throw new WebServiceException("close method has already been invoked"); // TODO: i18n

        Fiber fiber = engine.createFiber();
        final Tube tube = tubelinePool.take();
        fiber.start(tube, request, new Fiber.CompletionCallback() {
            public void onCompletion(@NotNull Packet response) {
                tubelinePool.recycle(tube);
                completionCallback.onCompletion(response);
            }

            public void onCompletion(@NotNull Throwable error) {
                // let's not reuse tubes as they might be in a wrong state, so not
                // calling tubePool.recycle()
                completionCallback.onCompletion(error);
            }
        });
    }

    public void close() {
       if (tubelinePool != null) {
            // multi-thread safety of 'close' needs to be considered more carefully.
            // some calls might be pending while this method is invoked. Should we
            // block until they are complete, or should we abort them (but how?)
            Tube p = tubelinePool.take();
            tubelinePool = null;
            p.preDestroy();
        }
    }
}

