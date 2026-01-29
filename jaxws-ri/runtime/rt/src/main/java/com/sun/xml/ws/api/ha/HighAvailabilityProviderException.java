/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.ha;

import jakarta.xml.ws.WebServiceException;

/**
 *
 * @author Marek Potociar
 */
public final class HighAvailabilityProviderException extends WebServiceException {

    private static final long serialVersionUID = 478158457185016835L;

    public HighAvailabilityProviderException(String message, Throwable cause) {
        super (message, cause);
    }

    public HighAvailabilityProviderException(String message) {
        super (message);
    }

}
