/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client.sei;

import java.lang.reflect.Method;

import com.sun.xml.ws.api.databinding.ClientCallBridge;
import com.sun.xml.ws.model.JavaMethodImpl;
import jakarta.xml.ws.Response;
import jakarta.xml.ws.WebServiceException;

/**
 * {@link MethodHandler} that handles asynchronous invocations through {@link Response}.
 * @author Kohsuke Kawaguchi
 */
final class PollingMethodHandler extends AsyncMethodHandler {

//    PollingMethodHandler(SEIStub owner, JavaMethodImpl jm, JavaMethodImpl core) {
//        super(owner, jm, core);
//    }    
    
    PollingMethodHandler(SEIStub owner, Method m) {
        super(owner, m);
    }

    Response<?> invoke(Object proxy, Object[] args) throws WebServiceException {
        return doInvoke(proxy,args,null);
    }
}
