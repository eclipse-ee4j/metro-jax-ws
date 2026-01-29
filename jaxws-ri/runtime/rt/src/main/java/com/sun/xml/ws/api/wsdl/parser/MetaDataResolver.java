/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.wsdl.parser;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

import java.net.URI;

/**
 * Resolves metadata such as WSDL/schema. This serves as extensibile plugin point which a wsdl parser can use to
 * get the metadata from an endpoint.
 *
 * @author Vivek Pandey
 */
public abstract class MetaDataResolver {

    /**
     * Default constructor.
     */
    protected MetaDataResolver() {}

    /**
     * Gives {@link com.sun.xml.ws.api.wsdl.parser.ServiceDescriptor} resolved from the given location.
     *
     * TODO: Does this method need to propogate errors?
     *
     * @param location metadata location
     * @return {@link com.sun.xml.ws.api.wsdl.parser.ServiceDescriptor} resolved from the location. It may be null in the cases when MetadataResolver
     *         can get the metada associated with the metadata loction.
     */
    public abstract @Nullable ServiceDescriptor resolve(@NotNull URI location);
}
