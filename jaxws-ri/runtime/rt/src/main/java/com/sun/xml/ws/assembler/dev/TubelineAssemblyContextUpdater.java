/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.assembler.dev;

import javax.xml.ws.WebServiceException;

/**
 *
 * @author Marek Potociar (marek.potociar at sun.com)
 */
public interface TubelineAssemblyContextUpdater {
    /**
     * TODO javadoc
     * 
     * @param context
     * @throws javax.xml.ws.WebServiceException
     */
    void prepareContext(ClientTubelineAssemblyContext context) throws WebServiceException;
    
    /**
     * TODO javadoc
     * 
     * @param context
     * @throws javax.xml.ws.WebServiceException
     */
    void prepareContext(ServerTubelineAssemblyContext context) throws WebServiceException;
}
