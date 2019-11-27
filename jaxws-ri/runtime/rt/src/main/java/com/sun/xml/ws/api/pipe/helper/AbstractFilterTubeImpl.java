/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.pipe.helper;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.pipe.NextAction;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.pipe.TubeCloner;

/**
 * Convenient default implementation for filtering {@link Tube}.
 *
 * <p>
 * In this prototype, this is not that convenient, but in the real production
 * code where we have {@code preDestroy()} and {@code clone()}, this
 * is fairly handy.
 * 
 * @author Kohsuke Kawaguchi
 */
public abstract class AbstractFilterTubeImpl extends AbstractTubeImpl {
    protected final Tube next;

    protected AbstractFilterTubeImpl(Tube next) {
        this.next = next;
    }

    protected AbstractFilterTubeImpl(AbstractFilterTubeImpl that, TubeCloner cloner) {
        super(that, cloner);
        if (that.next != null) {
            this.next = cloner.copy(that.next);
        } else {
            this.next = null;
        }        
    }

    /**
     * Default no-op implementation.
     */
    public @NotNull NextAction processRequest(Packet request) {
        return doInvoke(next,request);
    }

    /**
     * Default no-op implementation.
     */
    public @NotNull NextAction processResponse(Packet response) {
        return doReturnWith(response);
    }

    /**
     * Default no-op implementation.
     */
    public @NotNull NextAction processException(Throwable t) {
        return doThrow(t);
    }

    public void preDestroy() {
        if (next != null) {
          next.preDestroy();
        }
    }
}
