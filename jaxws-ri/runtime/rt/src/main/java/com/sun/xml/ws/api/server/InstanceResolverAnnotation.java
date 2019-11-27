/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.server;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * Meta annotation for selecting instance resolver.
 *
 * <p>
 * When service class is annotated with an annotation that has
 * {@link InstanceResolverAnnotation} as meta annotation, the JAX-WS RI
 * runtime will use the instance resolver class specified on {@link #value()}.
 *
 * <p>
 * The {@link InstanceResolver} class must have the public constructor that
 * takes {@link Class}, which represents the type of the service.
 *
 * <p>
 * See {@link com.sun.xml.ws.developer.Stateful} for a real example. This annotation is only for
 * advanced users of the JAX-WS RI. 
 *
 * @since JAX-WS 2.1
 * @see com.sun.xml.ws.developer.Stateful
 * @author Kohsuke Kawaguchi
 */
@Target(ANNOTATION_TYPE)
@Retention(RUNTIME)
@Documented
public @interface InstanceResolverAnnotation {
    Class<? extends InstanceResolver> value();
}
