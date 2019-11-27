/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.protocol.soap;

import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.pipe.*;
import com.sun.xml.ws.client.HandlerConfiguration;
import javax.xml.namespace.QName;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Rama Pulavarthi
 */

public class ServerMUTube extends MUTube {
    
    private ServerTubeAssemblerContext tubeContext;
    private final Set<String> roles;
    private final Set<QName> handlerKnownHeaders;
    private final Lock lock = new ReentrantLock();

    public ServerMUTube(ServerTubeAssemblerContext tubeContext, Tube next) {
        super(tubeContext.getEndpoint().getBinding(), next);

        this.tubeContext = tubeContext;

        //On Server, HandlerConfiguration does n't change after publish, so store locally
        HandlerConfiguration handlerConfig = binding.getHandlerConfig();
        roles = handlerConfig.getRoles();
        handlerKnownHeaders = binding.getKnownHeaders();
    }

    protected ServerMUTube(ServerMUTube that, TubeCloner cloner) {
        super(that,cloner);
        tubeContext = that.tubeContext;
        roles = that.roles;
        handlerKnownHeaders = that.handlerKnownHeaders;
    }

    /**
     * Do MU Header Processing on incoming message (request)
     * @return
     *      if all the headers in the packet are understood, returns action such that
     *      next pipe will be inovked.
     *      if all the headers in the packet are not understood, returns action such that
     *      SOAPFault Message is sent to previous pipes.
     */
    @Override
    public NextAction processRequest(Packet request) {
        Set<QName> misUnderstoodHeaders=null;
     	lock.lock();
        try{
            misUnderstoodHeaders = getMisUnderstoodHeaders(request.getMessage().getHeaders(),roles, handlerKnownHeaders);
 	} finally {
            lock.unlock();
        }
        if((misUnderstoodHeaders == null)  || misUnderstoodHeaders.isEmpty()) {
            return doInvoke(super.next, request);
        }
        return doReturnWith(request.createServerResponse(createMUSOAPFaultMessage(misUnderstoodHeaders),
                tubeContext.getWsdlModel(), tubeContext.getSEIModel(), tubeContext.getEndpoint().getBinding()));
    }

    public ServerMUTube copy(TubeCloner cloner) {
        return new ServerMUTube(this,cloner);
    }

}

