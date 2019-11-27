/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client;

/**
 * Content negotiation enum.
 * <p>
 * A value of {@link #none} means no content negotation at level of the 
 * client transport will be performed to negotiate the encoding of XML infoset.
 * The default encoding will always be used.
 * <p>
 * A value of {@link #pessimistic} means the client transport will assume
 * the default encoding of XML infoset for an outbound message unless informed
 * otherwise by a previously received inbound message.
 * (The client transport initially and pessimistically assumes that a service
 * does not support anything other than the default encoding of XML infoset.)
 * <p>
 * A value of {@link #optimistic} means the client transport will assume
 * a non-default encoding of XML infoset for an outbound message.
 * (The client transport optimistically assumes that a service
 * supports the non-default encoding of XML infoset.)
 *
 * @author Paul.Sandoz@Sun.Com
 */
public enum ContentNegotiation {
    none,
    pessimistic,
    optimistic;
  
    /**
     * Property name for content negotiation on {@link RequestContext}.
     */
    public static final String PROPERTY = "com.sun.xml.ws.client.ContentNegotiation";
    
    /**
     * Obtain the content negotiation value from a system property.
     * <p>
     * This method will never throw a runtime exception.
     *
     * @return the content negotiation value.
     */
    public static ContentNegotiation obtainFromSystemProperty() {
        try {
            String value = System.getProperty(PROPERTY);
            
            if (value == null) {
                return none;
            }
            
            return valueOf(value);
        } catch (Exception e) {
            // Default to none for any unrecognized value or any other
            // runtime exception thrown
            return none;
        }
    }
}
