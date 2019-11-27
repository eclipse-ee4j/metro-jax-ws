/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.wsdl.parser;

import com.sun.istack.NotNull;

import javax.xml.transform.Source;
import java.util.List;

/**
 * Abstraction over WSDL and Schema metadata
 *
 * @author Vivek Pandey
 */
public abstract class ServiceDescriptor {
    /**
     * Gives list of wsdls
     * @return List of WSDL documents as {@link Source}.
     * {@link javax.xml.transform.Source#getSystemId()} must be Non-null
     */
    public abstract @NotNull List<? extends Source> getWSDLs();

    /**
     * Gives list of schemas.
     * @return List of XML schema documents as {@link Source}. {@link javax.xml.transform.Source#getSystemId()} must be Non-null.
     * 
     */
    public abstract @NotNull List<? extends Source> getSchemas();
}
