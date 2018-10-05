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

import com.sun.tools.ws.api.wsdl.TWSDLExtension;

/**
 * A visitor working on extension entities.
 *
 * @author WS Development Team
 */
public interface ExtensionVisitor {
    public void preVisit(TWSDLExtension extension) throws Exception;
    public void postVisit(TWSDLExtension extension) throws Exception;
}
