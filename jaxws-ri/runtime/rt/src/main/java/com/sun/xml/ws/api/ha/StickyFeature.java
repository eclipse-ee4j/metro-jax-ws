/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.ha;


/**
 * Provides a way to tell the runtime about stickiness of requests. In a
 * HA environment, a client's requests need to land on the same instance so
 * that a {@link org.glassfish.ha.store.api.BackingStore} entry for a key is
 * accessed/modified from the same instance.
 *
 * <p>
 * A web service feature may implement this interface. JAX-WS runtime
 * checks if any feature needs stickiness of requests, and if HA is configured
 * ({@link HighAvailabilityProvider#isHaEnvironmentConfigured()}), it will take
 * an appropriate action. For example, in servlet transport, it would create
 * JSESSIONID cookie so that a typical loadbalancer would stick the subsequent
 * client requests to the same instance.
 * 
 * @author Jitendra Kotamraju
 * @since JAX-WS RI 2.2.2
 */
public interface StickyFeature {
}
