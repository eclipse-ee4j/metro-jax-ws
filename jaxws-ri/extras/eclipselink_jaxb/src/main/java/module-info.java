/*
 * Copyright (c) 2019, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

module com.sun.xml.ws.eclipselink {

    requires java.desktop;
    requires jakarta.mail;

    requires transitive com.sun.xml.ws;
    requires org.eclipse.persistence.moxy;

    exports com.sun.xml.ws.db.toplink;

    provides com.sun.xml.ws.spi.db.BindingContextFactory with
            com.sun.xml.ws.db.toplink.JAXBContextFactory;
}
