/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.pipe.helper;

import com.sun.xml.ws.api.pipe.Pipe;
import com.sun.xml.ws.api.pipe.PipeCloner;
import com.sun.xml.ws.api.message.Packet;

/**
 * Default implementation of {@link Pipe} that is used as a filter.
 *
 * <p>
 * A filter pipe works on a {@link Packet}, then pass it onto the next pipe.
 *
 *
 * <h2>How do I implement a filter?</h2>
 * <p>
 * Filter {@link Pipe}s are ideal for those components that wish to
 * do some of the followings:
 *
 * <dl>
 * <dt><b>
 * To read an incoming message and perform some work before the
 * application (or more precisely the next pipe sees it)
 * </b>
 * <dd>
 * Implement the {@link #process} method and do some processing before
 * you pass the packet to the next pipe:
 * <pre>
 * process(request) {
 *   doSomethingWith(request);
 *   return next.process(request);
 * }
 * </pre>
 *
 *
 * <dt><b>
 * To intercept an incoming message and prevent the next pipe from seeing it.
 * </b>
 * <dd>
 * Implement the {@link #process} method and do some processing,
 * then do NOT pass the request onto the next pipe.
 * <pre>
 * process(request) {
 *   if(isSomethingWrongWith(request))
 *     return createErrorMessage();
 *   else
 *     return next.proces(request);
 * }
 * </pre>
 *
 * <dt><b>
 * To post process a reply and possibly modify a message:
 * </b>
 * <dd>
 * Implement the {@link #process} method and do some processing,
 * then do NOT pass the request onto the next pipe.
 * <pre>
 * process(request) {
 *   op = request.getMessage().getOperation();
 *   reply = next.proces(request);
 *   if(op is something I care) {
 *     reply = playWith(reply);
 *   }
 *   return reply;
 * }
 * </pre>
 *
 * </dl>
 *
 * @author Kohsuke Kawaguchi
 */
public abstract class AbstractFilterPipeImpl extends AbstractPipeImpl {
    /**
     * Next pipe to call.
     */
    protected final Pipe next;

    protected AbstractFilterPipeImpl(Pipe next) {
        this.next = next;
        assert next!=null;
    }

    protected AbstractFilterPipeImpl(AbstractFilterPipeImpl that, PipeCloner cloner) {
        super(that, cloner);
        this.next = cloner.copy(that.next);
        assert next!=null;
    }

    public Packet process(Packet packet) {
        return next.process(packet);
    }

    @Override
    public void preDestroy() {
        next.preDestroy();
    }
}
