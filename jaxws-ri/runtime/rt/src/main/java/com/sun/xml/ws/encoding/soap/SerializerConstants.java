/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.encoding.soap;

/**
 *
 * @author WS Development Team
 */
public interface SerializerConstants {
    public static final boolean ENCODE_TYPE             = true;
    public static final boolean DONT_ENCODE_TYPE        = false;
    public static final boolean SERIALIZE_AS_REF        = true;
    public static final boolean DONT_SERIALIZE_AS_REF   = false;
    public static final boolean REFERENCEABLE           = true;
    public static final boolean NOT_REFERENCEABLE       = false;
    public static final boolean NULLABLE                = true;
    public static final boolean NOT_NULLABLE            = false;
    public static final boolean REFERENCED_INSTANCE     = true;
    public static final boolean UNREFERENCED_INSTANCE   = false;
}
