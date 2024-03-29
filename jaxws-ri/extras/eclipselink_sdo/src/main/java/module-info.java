/*
 * Copyright (c) 2019, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

module com.sun.xml.ws.sdo {

    requires java.logging;
    requires transitive com.sun.xml.ws;
    requires transitive org.eclipse.persistence.sdo;

    //TODO: why do we need this? sdo/moxy do have xjc as static dep
    requires com.sun.tools.xjc;

    exports com.sun.xml.ws.db.sdo;

    provides com.sun.xml.ws.spi.db.BindingContextFactory with
            com.sun.xml.ws.db.sdo.SDOContextFactory;

}
