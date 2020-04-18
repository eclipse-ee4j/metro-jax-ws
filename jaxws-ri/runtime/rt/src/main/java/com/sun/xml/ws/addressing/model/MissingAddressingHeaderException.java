/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.addressing.model;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.resources.AddressingMessages;

import jakarta.xml.ws.WebServiceException;
import javax.xml.namespace.QName;

/**
 * This exception signals that a particular WS-Addressing header is missing in a SOAP message.
 *
 * @author Rama Pulavarthi
 */
public class MissingAddressingHeaderException extends WebServiceException {
    private final QName name;
    private transient final Packet packet;

    /**
     *
     * @param name QName of the missing WS-Addressing Header
     */
    public MissingAddressingHeaderException(@NotNull QName name) {
        this(name,null);
    }

    public MissingAddressingHeaderException(@NotNull QName name, @Nullable Packet p) {
        super(AddressingMessages.MISSING_HEADER_EXCEPTION(name));
        this.name = name;
        this.packet = p;
    }

    /**
     * Gets the QName of the missing WS-Addressing Header.
     *
     * @return
     *      never null.
     */
    public QName getMissingHeaderQName() {
        return name;
    }

    /**
     * The {@link Packet} in which a header was missing.
     *
     * <p>
     * This object can be used to deep-inspect the problematic SOAP message.
     */
    public Packet getPacket() {
        return packet;
    }
}
