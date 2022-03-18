/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.nosei_apt.server;

@jakarta.xml.ws.WebFault(name="BarException",
    targetNamespace="urn:test:types")
public class WSDLBarException extends Exception {
    Bar bar;

    public WSDLBarException(String message, Bar bar) {
	  super(message);
        this.bar = bar;
    }

    public Bar getFaultInfo() {
        return bar;
    }
}
