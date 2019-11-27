/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client.sei;

import com.sun.xml.ws.api.databinding.ClientCallBridge;
import com.sun.xml.ws.model.JavaMethodImpl;
import javax.xml.ws.AsyncHandler;
import javax.xml.ws.WebServiceException;

import java.lang.reflect.Method;
import java.util.concurrent.Future;

/**
 * {@link MethodHandler} that uses {@link AsyncHandler}.
 *
 * @author Kohsuke Kawaguchi
 */
final class CallbackMethodHandler extends AsyncMethodHandler {

    /**
     * Position of the argument that takes {@link AsyncHandler}.
     */
    private final int handlerPos;

    CallbackMethodHandler(SEIStub owner, Method m, int handlerPos) {
        super(owner, m);
        this.handlerPos = handlerPos;
    }

//    CallbackMethodHandler(SEIStub owner, JavaMethodImpl jm, JavaMethodImpl core, int handlerPos) {
//        super(owner,jm,core);
//        this.handlerPos = handlerPos;
//    }

    Future<?> invoke(Object proxy, Object[] args) throws WebServiceException {
        // the spec requires the last argument
        final AsyncHandler handler = (AsyncHandler)args[handlerPos];

        return doInvoke(proxy, args, handler);
    }
}
