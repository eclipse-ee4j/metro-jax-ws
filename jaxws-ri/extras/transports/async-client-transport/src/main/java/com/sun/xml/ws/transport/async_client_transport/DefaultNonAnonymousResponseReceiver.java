/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.async_client_transport;

import com.sun.xml.ws.api.message.Message;
import java.util.logging.Level;
import jakarta.xml.ws.*;
import java.util.logging.Logger;

/**
 * @author Rama.Pulavarthi@sun.com
 */
public class DefaultNonAnonymousResponseReceiver implements NonAnonymousResponsesReceiver<Message>{
    private Endpoint e;
    private String bindingId;
    private String nonanonAddress;
    public DefaultNonAnonymousResponseReceiver(String nonanonAddress, String bindingId) {
        this.bindingId = bindingId;
        this.nonanonAddress = nonanonAddress;
    }

    @Override
    public void register(NonAnonymousResponseHandler<Message> nonAnonymousResponseHandler) {
        e = Endpoint.create(bindingId, new DefaultNonAnonymousEndpoint(nonAnonymousResponseHandler));
        if(nonanonAddress == null) {
            nonanonAddress = NonAnonymousAddressAllocator.getInstance().createNonAnonymousAddress();

        }
        LOGGER.log(Level.INFO, "Starting NonAnonymousResponseReceiver on:{0}", nonanonAddress);
        try {
        e.publish(nonanonAddress);
        } catch (Exception ex) {
//           ex.printStackTrace();
           throw new WebServiceException(ex);
        }
    }

    @Override
    public void unregister(NonAnonymousResponseHandler<Message> nonAnonymousResponseHandler) {
        if (e != null) {
            e.stop();
        }
    }

    @Override
    public String getAddress() {
        return nonanonAddress;
    }

    @ServiceMode(value= Service.Mode.MESSAGE)
    @WebServiceProvider(serviceName ="RINonAnonService", portName ="RINonAnonPort",targetNamespace ="http://jax-ws//foo")
    private static class DefaultNonAnonymousEndpoint implements Provider<Message> {
        private NonAnonymousResponseHandler<Message> handler;

        private DefaultNonAnonymousEndpoint(NonAnonymousResponseHandler<Message> handler) {
            this.handler = handler;
        }

        @Override
        public Message invoke(Message m) {
            LOGGER.log(Level.FINE, "Message receieved by{0}", this.getClass());
            if (handler != null) {
                handler.onReceive(m);
            }
            return null;
        }
    }
    private static final Logger LOGGER = Logger.getLogger(DefaultNonAnonymousResponseReceiver.class.getName());
}
