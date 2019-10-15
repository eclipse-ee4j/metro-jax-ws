/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.db.sdo;

/* $Header: webservices/src/orajaxrpc/oracle/j2ee/ws/common/wsdl/SchemaFileMapping.java /main/1 2010/04/14 10:30:39 bnaugle Exp $ */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    bnaugle    Mar 29, 2010 - Creation
 */


public class SchemaFileMapping {
    private String advertisedName;
    private String path;

    public SchemaFileMapping(String advertisedName, String path) {
        this.advertisedName = advertisedName;
        this.path = path;
    }

    public String getAdvertisedName() {
        return advertisedName;
    }

    public String getPath() {
        return path;
    }
}
