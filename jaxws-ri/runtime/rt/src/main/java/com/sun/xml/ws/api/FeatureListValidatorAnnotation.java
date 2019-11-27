/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.xml.ws.WebServiceFeature;

/**
 * This annotation should be used on classes that extend {@link WebServiceFeature} in
 * order to specify the type of {@link FeatureListValidator} bean that will be invoked when 
 * instances of the {@link WebServiceFeature} class are included in the list of features
 * that are added to a client or service binding.
 * 
 * @since 2.2.8
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FeatureListValidatorAnnotation {
    /**
     * The <code>FeatureListValidator</code> bean that is associated
     * with the <code>FeatureListValidator</code> annotation
     */
    Class<? extends FeatureListValidator> bean();
}
