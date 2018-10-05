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

/**
 * An interface implemented by entities which can be defined in a target namespace.
 *
 * @author WS Development Team
 */
public interface GloballyKnown extends Elemental {
    public String getName();
    public Kind getKind();
    public Defining getDefining();
}
