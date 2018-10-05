/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.nosei_apt.server;


public class Exception1 extends Exception {
    String faultString;

    public Exception1(String faultString) {
        super(faultString+" Message");
        this.faultString = faultString;
    }

    public String getFaultString() {
        return faultString;
    }

    public boolean isValid() {
        return true;
    }
}
