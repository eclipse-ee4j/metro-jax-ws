/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package handler.handler_processing.common;

import javax.xml.ws.ProtocolException;

/**
 * This exception class is used by some tests to make sure that
 * the 
 */
public class TestProtocolException extends ProtocolException {

    public TestProtocolException() {
        super();
    }
    
    public TestProtocolException(String message) {
        super(message);
    }
    
}
