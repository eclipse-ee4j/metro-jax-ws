/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.assembler.dev;

import com.sun.xml.ws.api.pipe.Tube;

import jakarta.xml.ws.WebServiceException;

/**
 * @author Marek Potociar (marek.potociar at sun.com)
 */
public interface TubeFactory {
    /**
     * Adds RM tube to the client-side tubeline, depending on whether RM is enabled or not.
     *
     * @param context wsit client tubeline assembler context
     * @return new tail of the client-side tubeline
     */
    Tube createTube(ClientTubelineAssemblyContext context) throws WebServiceException;

    /**
     * Adds RM tube to the service-side tubeline, depending on whether RM is enabled or not.
     *
     * @param context wsit service tubeline assembler context
     * @return new head of the service-side tubeline
     */
    Tube createTube(ServerTubelineAssemblyContext context) throws WebServiceException;

}
