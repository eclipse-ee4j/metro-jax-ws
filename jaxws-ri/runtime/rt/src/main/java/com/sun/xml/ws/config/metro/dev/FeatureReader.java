/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.config.metro.dev;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.WebServiceFeature;

/**
 * Parses a XML fragment and is expected to return a corresponding WebServiceFeature.
 *
 * @author Fabian Ritzmann
 */
public interface FeatureReader<T extends WebServiceFeature> {

    QName ENABLED_ATTRIBUTE_NAME = new QName("enabled");

    /**
     * Parse an XML stream and return the corresponding WebServiceFeature instance.
     */
    T parse(XMLEventReader reader) throws WebServiceException;

}
