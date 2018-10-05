/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.streaming;

/**
 * <p> Interface for prefix factories. </p>
 *
 * <p> A prefix factory is able to create a new prefix for a URI that
 * was encountered for the first time when writing a document
 * using an XMLWriter. </p>
 *
 * @author WS Development Team
 */
public interface PrefixFactory {
    /**
     * Return a brand new prefix for the given URI.
     */
    public String getPrefix(String uri);
}
