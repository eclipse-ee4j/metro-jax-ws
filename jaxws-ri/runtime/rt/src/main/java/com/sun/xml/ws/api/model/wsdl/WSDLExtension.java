/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.model.wsdl;

import javax.xml.namespace.QName;

/**
 * Represents a WSDL extensibility element or attribute.
 *
 * <p>
 * This interface can be implemented by the programs that build
 * on top of the JAX-WS RI, to hook additional information into
 * {@link WSDLModel}.
 *
 * @author Vivek Pandey
 */
public interface WSDLExtension {
    /**
     * Gets the qualified name of the WSDL extensibility element or attribute.
     *
     * @return
     *      must not be null.
     */
    QName getName();
}
