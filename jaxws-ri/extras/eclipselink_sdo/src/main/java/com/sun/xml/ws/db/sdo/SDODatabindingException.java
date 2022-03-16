/*
 * Copyright (c) 2012, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.db.sdo;

/**
 * Created by IntelliJ IDEA.
 * User: giglee
 * Date: Apr 9, 2009
 * Time: 1:30:52 PM
 * To change this template use File | Settings | File Templates.
 */


public class SDODatabindingException extends RuntimeException {
    //constructors to defer to base class

    /**
     * Default constructor.
     */
    public SDODatabindingException() {
    }

    /**
     * Creates an exception with the provided message.
     *
     * @param message the exception message.
     */
    public SDODatabindingException(String message) {
        super(message);
    }

    /**
     * Creates an exception with the provided message and root cause.
     *
     * @param message the exception message.
     * @param cause   the root cause exception/throwable.
     */
    public SDODatabindingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an exception with the provided root cause throwable.
     *
     * @param throwable the root cause.
     */
    public SDODatabindingException(Throwable throwable) {
        super(throwable);
    }

    public String getMessage() {
        Throwable cause = getCause();
        if (cause != null && cause.getMessage() != null && !super.getMessage().equals(cause.getMessage())) {
            return super.getMessage() + ": "
                    + cause.getMessage();
        }
        return super.getMessage();
    }


}
