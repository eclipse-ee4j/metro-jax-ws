/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client.sei;

import com.sun.xml.ws.model.ParameterImpl;

import jakarta.xml.ws.WebServiceException;

/**
 * {@link ValueSetterFactory} is used to create {@link ValueSetter}.
 *
 * @author Jitendra Kotamraju
 */
public abstract class ValueSetterFactory {

    public abstract ValueSetter get(ParameterImpl p);

    public static final ValueSetterFactory SYNC = new ValueSetterFactory() {
        @Override
        public ValueSetter get(ParameterImpl p) {
            return ValueSetter.getSync(p);
        }
    };

    public static final ValueSetterFactory NONE = new ValueSetterFactory() {
        @Override
        public ValueSetter get(ParameterImpl p) {
            throw new WebServiceException("This shouldn't happen. No response parameters.");
        }
    };

    public static final ValueSetterFactory SINGLE = new ValueSetterFactory() {
        @Override
        public ValueSetter get(ParameterImpl p) {
            return ValueSetter.SINGLE_VALUE;
        }
    };

    public static final class AsyncBeanValueSetterFactory extends ValueSetterFactory {
        private Class asyncBean;

        public AsyncBeanValueSetterFactory(Class asyncBean) {
            this.asyncBean = asyncBean;
        }

        @Override
        public ValueSetter get(ParameterImpl p) {
            return new ValueSetter.AsyncBeanValueSetter(p, asyncBean);
        }
    }

}
