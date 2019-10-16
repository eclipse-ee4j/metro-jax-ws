/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package supplychain.server;

public class InvalidPOException extends Exception {
    String detail;
    
    public InvalidPOException (String message) {
        super (message);
        this.detail = message;
    }
    
    public InvalidPOException (String message, String detail) {
        super (message);
        this.detail = detail;
    }
    
    public String getDetail () {
        return detail;
    }
}
