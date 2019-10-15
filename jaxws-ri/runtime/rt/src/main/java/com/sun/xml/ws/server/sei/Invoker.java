/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.server.sei;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.message.Packet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Hides the detail of calling into application implementations.
 * 
 * @since 2.2.6
 */
public abstract class Invoker {
    /**
     * Wrapper for reflection invoke that allows containers to adapt
     */
    public abstract Object invoke( @NotNull Packet p, @NotNull Method m, @NotNull Object... args ) throws InvocationTargetException, IllegalAccessException;
}
