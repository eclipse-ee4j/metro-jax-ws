/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
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
    boolean ENCODE_TYPE             = true;
    boolean DONT_ENCODE_TYPE        = false;
    boolean SERIALIZE_AS_REF        = true;
    boolean DONT_SERIALIZE_AS_REF   = false;
    boolean REFERENCEABLE           = true;
    boolean NOT_REFERENCEABLE       = false;
    boolean NULLABLE                = true;
    boolean NOT_NULLABLE            = false;
    boolean REFERENCED_INSTANCE     = true;
    boolean UNREFERENCED_INSTANCE   = false;
}
