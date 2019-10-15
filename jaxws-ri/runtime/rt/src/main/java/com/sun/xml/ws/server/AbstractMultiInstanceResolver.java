/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.server;

import com.sun.xml.ws.api.server.AbstractInstanceResolver;
import com.sun.xml.ws.api.server.InstanceResolver;
import com.sun.xml.ws.api.server.ResourceInjector;
import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.api.server.WSWebServiceContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.reflect.Method;

/**
 * Partial implementation of {@link InstanceResolver} with code
 * to handle multiple instances per server.
 *
 * @author Kohsuke Kawaguchi
 */
public abstract class AbstractMultiInstanceResolver<T> extends AbstractInstanceResolver<T> {
    protected final Class<T> clazz;

    // fields for resource injection.
    private /*almost final*/ WSWebServiceContext webServiceContext;
    protected /*almost final*/ WSEndpoint owner;
    private final Method postConstructMethod;
    private final Method preDestroyMethod;
    private /*almost final*/ ResourceInjector resourceInjector;

    public AbstractMultiInstanceResolver(Class<T> clazz) {
        this.clazz = clazz;

        postConstructMethod = findAnnotatedMethod(clazz, PostConstruct.class);
        preDestroyMethod = findAnnotatedMethod(clazz, PreDestroy.class);
    }

    /**
     * Perform resource injection on the given instance.
     */
    protected final void prepare(T t) {
        // we can only start creating new instances after the start method is invoked.
        assert webServiceContext!=null;

        resourceInjector.inject(webServiceContext,t);
        invokeMethod(postConstructMethod,t);
    }

    /**
     * Creates a new instance via the default constructor.
     */
    protected final T create() {
        T t = createNewInstance(clazz);
        prepare(t);
        return t;
    }

    @Override
    public void start(WSWebServiceContext wsc, WSEndpoint endpoint) {
        resourceInjector = getResourceInjector(endpoint);
        this.webServiceContext = wsc;
        this.owner = endpoint;
    }

    protected final void dispose(T instance) {
        invokeMethod(preDestroyMethod,instance);
    }
}
