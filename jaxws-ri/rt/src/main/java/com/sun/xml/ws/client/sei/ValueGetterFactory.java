/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client.sei;

import com.sun.xml.ws.model.ParameterImpl;

import javax.jws.WebParam;

/**
 * {@link ValueGetterFactory} is used to create {@link ValueGetter} objects.
 *
 * @author Jitendra Kotamraju
 */
abstract class ValueGetterFactory {

    abstract ValueGetter get(ParameterImpl p);

    static final ValueGetterFactory SYNC = new ValueGetterFactory() {
        ValueGetter get(ParameterImpl p) {
            return (p.getMode()== WebParam.Mode.IN || p.getIndex() == -1)
                    ? ValueGetter.PLAIN : ValueGetter.HOLDER;
        }
    };

    /**
     * In case of SEI async signatures, there are no holders. The OUT
     * parameters go in async bean class
     */
    static final ValueGetterFactory ASYNC = new ValueGetterFactory() {
        ValueGetter get(ParameterImpl p) {
            return ValueGetter.PLAIN;
        }
    };

}
