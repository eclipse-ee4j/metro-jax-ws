/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.spi;

import com.sun.xml.ws.policy.PolicyException;
import com.sun.xml.ws.policy.sourcemodel.AssertionData;

/**
 * Exception thrown in case of assertion creation failure.
 *
 * @author Marek Potociar
 */
public final class AssertionCreationException extends PolicyException {
    
    private final AssertionData assertionData; 
    
    /**
     * Constructs a new assertion creation exception with the specified detail message and cause.  
     * <br>
     * Note that the detail message associated with {@code cause} is <strong>not</strong> automatically incorporated in
     * this exception's detail message.
     *
     * @param assertionData the data provided for assertion creation
     * @param  message the detail message.
     */
    public AssertionCreationException(final AssertionData assertionData, final String message) {
        super(message);
        this.assertionData = assertionData;
    }
    
    /**
     * Constructs a new assertion creation exception with the specified detail message and cause.  
     * <br>
     * Note that the detail message associated with {@code cause} is <strong>not</strong> automatically incorporated in
     * this exception's detail message.
     *
     * @param assertionData the data provided for assertion creation
     * @param  message the detail message.
     * @param  cause the cause.  (A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public AssertionCreationException(final AssertionData assertionData, final String message, final Throwable cause) {
        super(message, cause);
        this.assertionData = assertionData;
    }
    
    /**
     * Constructs a new assertion creation exception with the specified detail message and cause.  
     *
     * @param assertionData the data provided for assertion creation
     * @param  cause the cause.  (A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public AssertionCreationException(AssertionData assertionData, Throwable cause) {
        super(cause);
        this.assertionData = assertionData;
    }
    
    /**
     * Retrieves assertion data associated with the exception.
     *
     * @return associated assertion data (present when assertion creation failed raising this exception).
     */
    public AssertionData getAssertionData() {
        return this.assertionData;
    }
}
