/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.privateutil;

/**
 *
 * @author Marek Potociar (marek.potociar at sun.com)
 */
public final class RuntimePolicyUtilsException extends RuntimeException {
    
    RuntimePolicyUtilsException(final String message) {
        super(message);
    }
    
    RuntimePolicyUtilsException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
