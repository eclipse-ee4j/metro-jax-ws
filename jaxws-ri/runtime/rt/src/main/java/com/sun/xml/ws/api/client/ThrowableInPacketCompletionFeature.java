/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.client;

import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Dispatch;

import com.sun.xml.ws.api.pipe.ThrowableContainerPropertySet;

/**
 * When using {@link Dispatch}<{@link Packet}> and the invocation completes with a Throwable, it is
 * useful to be able to inspect the Packet in addition to the Throwable as the Packet contains 
 * meta-data about the request and/or response.  However, the default behavior is that the caller
 * only receives the Throwable.
 * 
 * When an instance of this feature is enabled on the binding, any Throwable generated will be available
 * on the Packet on the satellite {@link ThrowableContainerPropertySet}.
 * 
 * @see ThrowableContainerPropertySet
 */
public class ThrowableInPacketCompletionFeature extends WebServiceFeature {

    public ThrowableInPacketCompletionFeature() {
        this.enabled = true;
    }
    
    @Override
    public String getID() {
        return ThrowableInPacketCompletionFeature.class.getName();
    }

}
