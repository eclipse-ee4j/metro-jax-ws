/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.dump;


import jakarta.xml.ws.spi.WebServiceFeatureAnnotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Marek Potociar (marek.potociar at sun.com)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@WebServiceFeatureAnnotation(id = MessageDumpingFeature.ID, bean = MessageDumpingFeature.class)
public @interface MessageDumping {
    /**
     * Specifies if this feature is enabled or disabled.
     */
    boolean enabled() default true;

    /**
     * Message logging root
     */
    String messageLoggingRoot() default MessageDumpingTube.DEFAULT_MSGDUMP_LOGGING_ROOT;
    
    /**
     * Message logging level
     */
    String messageLoggingLevel() default "FINE";
    
    /**
     * Turns on or off storing messages
     */
    boolean storeMessages() default false; 
}
