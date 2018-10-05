/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.model.exporter;

import org.xml.sax.ContentHandler;

/**
 * "Opaque" object in the object graph that knows how
 * to persist itself to XML.
 *
 * TODO: ExternalObjectReader
 *
 */
public interface ExternalObject {
    /**
     * Type name of this object. This will be used
     * when loading the object back from XML.
     */
    String getType();

    /**
     * Saves the object into XML.
     */
    void saveTo(ContentHandler receiver);
}
