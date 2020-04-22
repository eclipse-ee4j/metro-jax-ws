/*
 * Copyright (c) 2019, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.spi;

/**
 * Policy extensions may implement this provider interface
 * in order to change the name of logging subsystem
 * used by the core Policy module.
 *
 * @author lukas
 */
public interface LoggingProvider {

    /**
     * Policy will append {@code .wspolicy} to the returned string.
     *
     * @return a logging subsystem name to use by loggers
     */
    String getLoggingSubsystemName();
}
