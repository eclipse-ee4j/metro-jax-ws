/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.wsdl;

import com.sun.xml.ws.api.message.Message;

/**
 * {@link Exception} that demands a specific fault message to be sent back.
 *
 * TODO: think about a way to generalize it, as it seems to be useful
 * in other places.
 *
 * @author Kohsuke Kawaguchi
 */
public final class DispatchException extends Exception {

    private static final long serialVersionUID = 4853394475519450701L;

    public final Message fault;

    public DispatchException(Message fault) {
        this.fault = fault;
    }

}
