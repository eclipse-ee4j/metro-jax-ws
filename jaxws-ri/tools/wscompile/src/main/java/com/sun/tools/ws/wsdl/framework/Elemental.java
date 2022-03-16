/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.framework;

import org.xml.sax.Locator;

import javax.xml.namespace.QName;

/**
 * Interface implemented by classes that are mappable to XML elements.
 *
 * @author WS Development Team
 */
public interface Elemental {
    QName getElementName();
    Locator getLocator();
}
