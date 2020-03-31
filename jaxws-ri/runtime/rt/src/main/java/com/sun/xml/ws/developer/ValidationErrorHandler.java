/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.developer;

import com.sun.xml.ws.api.message.Packet;
import org.xml.sax.ErrorHandler;

import jakarta.xml.ws.handler.MessageContext;
import javax.xml.validation.Validator;

/**
 * An {@link ErrorHandler} to receive errors encountered during the
 * {@link Validator#validate} method invocation. Specify
 * a custom handler in {@link SchemaValidation}, {@link SchemaValidationFeature}
 * to customize the error handling process during validation.
 *
 * @see SchemaValidation
 * @author Jitendra Kotamraju
 */
public abstract class ValidationErrorHandler implements ErrorHandler {
    protected Packet packet;

    /**
     * Use it to communicate validation errors with the application.
     *
     * For e.g validation exceptions can be stored in {@link Packet#invocationProperties}
     * during request processing and can be accessed in the endpoint
     * via {@link MessageContext}
     *
     * @param packet for request or response message
     */
    public void setPacket(Packet packet) {
        this.packet = packet;
    }

}
