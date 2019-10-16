/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.developer;

/**
 * Represents the exception that has occurred on the server side.
 *
 * <p>
 * When an exception occurs on the server, JAX-WS RI sends the stack
 * trace of that exception to the client. On the client side,
 * instances of this class are used to represent such stack trace.
 *
 * @author Kohsuke Kawaguchi
 * @since 2.1
 */
public class ServerSideException extends Exception {
    private final String className;

    public ServerSideException(String className, String message) {
        super(message);
        this.className = className;
    }

    public String getMessage() {
        return "Client received an exception from server: "
                + super.getMessage()
                + " Please see the server log to find more detail regarding exact cause of the failure.";
    }

    public String toString() {
        String s = className;
        String message = getLocalizedMessage();
        return (message != null) ? (s + ": " + message) : s;
    }
}
