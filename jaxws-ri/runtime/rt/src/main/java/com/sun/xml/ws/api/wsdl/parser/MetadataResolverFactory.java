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
import org.xml.sax.EntityResolver;

/**
 * Extension point for resolving metadata using wsimport.
 * <br>
 * wsimport would get a {@link MetaDataResolver} using this factory and from it will resolve all the wsdl/schema
 * metadata.
 *
 * Implementor of this class must provide a zero argument constructor so that
 * it can be loaded during service lookup mechanism.
 *
 * @author Vivek Pandey
 * @see com.sun.xml.ws.api.wsdl.parser.MetaDataResolver#resolve(java.net.URI)
 */
public abstract class MetadataResolverFactory {

    /**
     * Default constructor.
     */
    protected MetadataResolverFactory() {}

    /**
     * Gets a {@link com.sun.xml.ws.api.wsdl.parser.MetaDataResolver}
     *
     */
    public abstract @NotNull MetaDataResolver metadataResolver(@Nullable EntityResolver resolver);
}
