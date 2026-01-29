/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.oracle.webservices.api.message;

import java.util.Map;

import com.sun.istack.Nullable;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.client.RequestContext;
import com.sun.xml.ws.client.ResponseContext;
import jakarta.xml.ws.WebServiceContext;

/**
 * {@link PropertySet} that combines properties exposed from multiple
 * {@link PropertySet}s into one.
 *
 * <p>
 * This implementation allows one {@link PropertySet} to assemble
 * all properties exposed from other "satellite" {@link PropertySet}s.
 * (A satellite may itself be a {@link DistributedPropertySet}, so
 * in general this can form a tree.)
 *
 * <p>
 * This is useful for JAX-WS because the properties we expose to the application
 * are contributed by different pieces, and therefore we'd like each of them
 * to have a separate {@link PropertySet} implementation that backs up
 * the properties. For example, this allows FastInfoset to expose its
 * set of properties to {@link RequestContext} by using a strongly-typed fields.
 *
 * <p>
 * This is also useful for a client-side transport to expose a bunch of properties
 * into {@link ResponseContext}. It simply needs to create a {@link PropertySet}
 * object with methods for each property it wants to expose, and then add that
 * {@link PropertySet} to {@link Packet}. This allows property values to be
 * lazily computed (when actually asked by users), thus improving the performance
 * of the typical case where property values are not asked.
 *
 * <p>
 * A similar benefit applies on the server-side, for a transport to expose
 * a bunch of properties to {@link WebServiceContext}.
 *
 * <p>
 * To achieve these benefits, access to {@link DistributedPropertySet} is slower
 * compared to {@link PropertySet} (such as get/set), while adding a satellite
 * object is relatively fast.
 *
 * @author Kohsuke Kawaguchi
 */
public interface DistributedPropertySet extends com.oracle.webservices.api.message.PropertySet {

    @Nullable <T extends com.oracle.webservices.api.message.PropertySet> T getSatellite(Class<T> satelliteClass);

    Map<Class<? extends com.oracle.webservices.api.message.PropertySet>, com.oracle.webservices.api.message.PropertySet> getSatellites();
    
    void addSatellite(com.oracle.webservices.api.message.PropertySet satellite);

    void addSatellite(Class<? extends com.oracle.webservices.api.message.PropertySet> keyClass, com.oracle.webservices.api.message.PropertySet satellite);

    void removeSatellite(com.oracle.webservices.api.message.PropertySet satellite);

    void copySatelliteInto(com.oracle.webservices.api.message.MessageContext r);
}
