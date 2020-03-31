/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.server;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.server.ResourceInjector;
import com.sun.xml.ws.api.server.WSWebServiceContext;
import com.sun.xml.ws.util.InjectionPlan;

import jakarta.xml.ws.WebServiceContext;

/**
 * Default {@link ResourceInjector}.
 *
 * @see ResourceInjector#STANDALONE
 * @author Kohsuke Kawaguchi
 */
public final class DefaultResourceInjector extends ResourceInjector {
    public void inject(@NotNull WSWebServiceContext context, @NotNull Object instance) {
        InjectionPlan.<Object, WebServiceContext>buildInjectionPlan(
            instance.getClass(),WebServiceContext.class,false).inject(instance,context);
    }

}
