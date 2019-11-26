/*
 * Copyright (c) 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

module com.sun.xml.ws.transport.local {

    requires com.sun.xml.ws;

    exports com.sun.xml.ws.transport.local;

    opens com.sun.xml.ws.transport.local to com.sun.xml.ws;

    provides com.sun.xml.ws.api.pipe.TransportTubeFactory with
            com.sun.xml.ws.transport.local.LocalTransportFactory,
            com.sun.xml.ws.transport.local.InVmTransportFactory;

}
