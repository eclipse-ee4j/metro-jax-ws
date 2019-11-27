/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.ha;

import com.sun.xml.ws.api.message.Packet;

/**
 * This class has HA information
 * <p>
 *
 * This would help a loadbalancer to put the request(in case of a fail-over)
 * on a replica instance that has all the related data. Even if there is no
 * loadbalancer, a backing store could locate the information by directly
 * going to the correct replica instance. This would also help any part of
 * the runtime to know about failover case(and in-turn may invalidate
 * local caches).
 *
 * <p>
 * To achieve this functionality, it carries two pieces of information:
 * <ol>
 * <li>key - Related {@link org.glassfish.ha.store.api.BackingStore} keys can
 * use this info for their HashableKey impl. First store creates this object,
 * and subsequent related stores use the same key.
 * <li>replicaInstance - where the related info is replicated
 * </ol>
 *
 * <p>
 * This can be accessed from {@link Packet} using {@link Packet#HA_INFO}
 * property by the runtime. This object is created typically
 * <ul>
 * <li> When a store happens for the first time
 * <li> A subsequent inbound transport creates from cookies
 * <li> A fail-over request stores the data to a different replica
 * </ul>
 *
 * @author Jitendra Kotamraju
 * @since JAX-WS RI 2.2.2
 */
public class HaInfo {
    private final String replicaInstance;
    private final String key;
    private final boolean failOver;

    public HaInfo(String key, String replicaInstance, boolean failOver) {
        this.key = key;
        this.replicaInstance = replicaInstance;
        this.failOver = failOver;
    }

    public String getReplicaInstance() {
        return replicaInstance;
    }

    public String getKey() {
        return key;
    }

    public boolean isFailOver() {
        return failOver;
    }
}
