/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.wsdl.parser;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLModel;
import com.sun.xml.ws.api.server.Container;
import com.sun.xml.ws.api.policy.PolicyResolver;

/**
 * Provides contextual information for {@link WSDLParserExtension}s.
 * 
 * @author Vivek Pandey
 * @author Fabian Ritzmann
 */
public interface WSDLParserExtensionContext {
    /**
     * Returns true if the WSDL parsing is happening on the client side. Returns false means
     * its started on the server side.
     */
    boolean isClientSide();

    /**
     * Gives the {@link EditableWSDLModel}. The WSDLModel may not be complete until
     * {@link WSDLParserExtension#finished(WSDLParserExtensionContext)} is called.
     */
    EditableWSDLModel getWSDLModel();

    /**
     * Provides the {@link Container} in which this service or client is running.
     * May return null.
     *
     * @return The container in which this service or client is running.
     */
    @NotNull Container getContainer();


    /**
     * Provides the PolicyResolver
     */
    @NotNull
    PolicyResolver getPolicyResolver();
}
