/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.model;



/**
 * @author Vivek Pandey
 *
 * Async WSDLOperation type
 */
public final class AsyncOperationType {

    public static final AsyncOperationType POLLING = new AsyncOperationType();
    public static final AsyncOperationType CALLBACK = new AsyncOperationType();

    private AsyncOperationType() {
    }

}
