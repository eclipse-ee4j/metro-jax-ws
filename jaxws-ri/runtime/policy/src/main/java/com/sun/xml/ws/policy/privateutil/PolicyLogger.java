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

import com.sun.istack.logging.Logger;
import com.sun.xml.ws.policy.spi.LoggingProvider;
import java.util.ServiceLoader;

/**
 * This is a helper class that provides some convenience methods wrapped around the
 * standard {@link java.util.logging.Logger} interface.
 *
 * @author Marek Potociar
 * @author Fabian Ritzmann
 */
public final class PolicyLogger extends Logger {

    /**
     * If we run with JAX-WS, we are using its logging domain (appended with ".wspolicy").
     * Otherwise we default to "wspolicy".
     */
    private static final String POLICY_PACKAGE_ROOT = "com.sun.xml.ws.policy";

    /**
     * Make sure this class cannot be instantiated by client code.
     *
     * @param policyLoggerName The name of the subsystem to be logged.
     * @param className The fully qualified class name.
     */
    private PolicyLogger(final String policyLoggerName, final String className) {
        super(policyLoggerName, className);
    }

    /**
     * The factory method returns preconfigured PolicyLogger wrapper for the class. Since there is no caching implemented,
     * it is advised that the method is called only once per a class in order to initialize a final static logger variable,
     * which is then used through the class to perform actual logging tasks.
     *
     * @param componentClass class of the component that will use the logger instance. Must not be {@code null}.
     * @return logger instance preconfigured for use with the component
     * @throws NullPointerException if the componentClass parameter is {@code null}.
     */
    public static PolicyLogger getLogger(final Class<?> componentClass) {
        final String componentClassName = componentClass.getName();

        if (componentClassName.startsWith(POLICY_PACKAGE_ROOT)) {
            return new PolicyLogger(getLoggingSubsystemName() + componentClassName.substring(POLICY_PACKAGE_ROOT.length()),
                    componentClassName);
        } else {
            return new PolicyLogger(getLoggingSubsystemName() + "." + componentClassName, componentClassName);
        }
    }

    private static String getLoggingSubsystemName() {
        for (LoggingProvider p : ServiceLoader.load(LoggingProvider.class)) {
            return p.getLoggingSubsystemName().concat(".wspolicy");
        }
        return "wspolicy";
    }

}
