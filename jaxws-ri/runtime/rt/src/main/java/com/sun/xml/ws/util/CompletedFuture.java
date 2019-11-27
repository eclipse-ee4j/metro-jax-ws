/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * {@link Future} implementation that obtains an already available value.
 *
 * @author Kohsuke Kawaguchi
 * @author Jitendra Kotamraju
 */
public class CompletedFuture<T> implements Future<T> {
    private final T v;
    private final Throwable re;

    public CompletedFuture(T v, Throwable re) {
        this.v = v;
        this.re = re;
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    public boolean isCancelled() {
        return false;
    }

    public boolean isDone() {
        return true;
    }

    public T get() throws ExecutionException {
        if (re != null) {
            throw new ExecutionException(re);
        }
        return v;
    }

    public T get(long timeout, TimeUnit unit) throws ExecutionException {
        return get();
    }
}
