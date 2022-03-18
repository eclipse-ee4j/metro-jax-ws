/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.developer;

import com.sun.xml.ws.api.server.InstanceResolverAnnotation;
import com.sun.xml.ws.server.StatefulInstanceResolver;

import jakarta.jws.WebService;
import jakarta.xml.ws.spi.WebServiceFeatureAnnotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Designates a stateful {@link WebService}.
 *
 * <p>
 * A service class that has this feature on will behave as a stateful web service.
 * See {@link StatefulWebServiceManager} for more about stateful web service.
 *
 * @since 2.1
 * @see StatefulWebServiceManager
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@WebServiceFeatureAnnotation(id = StatefulFeature.ID, bean = StatefulFeature.class)
@InstanceResolverAnnotation(StatefulInstanceResolver.class)
public @interface Stateful {    
}
