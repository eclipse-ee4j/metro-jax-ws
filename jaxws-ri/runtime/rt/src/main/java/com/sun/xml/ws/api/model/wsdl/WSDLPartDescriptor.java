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
 * Abstracts wsdl:part descriptor that is defined using element or type attribute.
 *
 * @author Vivek Pandey
 */
public interface WSDLPartDescriptor extends WSDLObject {
    /**
     * Gives Qualified name of the XML Schema element or type
     */
    QName name();

    /**
     * Gives whether wsdl:part references a schema type or a global element.
     */
    WSDLDescriptorKind type();

}
