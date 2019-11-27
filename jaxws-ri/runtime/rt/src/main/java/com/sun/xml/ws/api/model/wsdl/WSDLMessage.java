/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
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
 * Abstraction of wsdl:message.
 *
 * @author Vivek Pandey
 */
public interface WSDLMessage extends WSDLObject, WSDLExtensible {
    /**
     * Gives wsdl:message@name value.
     */
    QName getName();

    /**
     * Gets all the parts.
     */
    Iterable<? extends WSDLPart> parts();
}
