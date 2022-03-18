/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy;

/**
 * This is a base exception class and thrown when there is an error in the policy processing
 */
public class PolicyException extends Exception {

    private static final long serialVersionUID = 820901813457428163L;

    public PolicyException(String message) {
        super(message);
    }
    
    
    public PolicyException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
    public PolicyException(Throwable cause) {
        super(cause);
    }
    
}
