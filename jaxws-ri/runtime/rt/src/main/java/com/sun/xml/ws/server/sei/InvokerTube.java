/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.server.sei;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.pipe.TubeCloner;
import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;

/**
 * Base class for {@link com.sun.xml.ws.server.InvokerTube} restored
 * to allow for containers to specify alternate implementations of SEI-based
 * invoking.
 */
public abstract class InvokerTube<T extends Invoker> 
	extends AbstractTubeImpl implements InvokerSource<T> {

    protected final T invoker;

    protected InvokerTube(T invoker) {
        this.invoker = invoker;
    }

    /**
     * Copy constructor.
     */
    protected InvokerTube(InvokerTube<T> that, TubeCloner cloner) {
        cloner.add(that,this);
        this.invoker = that.invoker;
    }

    /**
     * Returns the {@link Invoker} object that serves the request.
     */
    public @NotNull T getInvoker(Packet request) {
    	return invoker;
    }
}
