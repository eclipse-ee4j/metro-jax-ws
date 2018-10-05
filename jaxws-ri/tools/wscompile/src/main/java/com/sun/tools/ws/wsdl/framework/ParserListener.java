/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.framework;

import javax.xml.namespace.QName;

/**
 * A listener for parsing-related events.
 *
 * @author WS Development Team
 */
public interface ParserListener {
    public void ignoringExtension(Entity entity, QName name, QName parent);
    public void doneParsingEntity(QName element, Entity entity);
}
