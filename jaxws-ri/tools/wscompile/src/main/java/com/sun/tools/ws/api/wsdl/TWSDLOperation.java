/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.api.wsdl;

import com.sun.codemodel.JClass;

import java.util.Map;

/**
 * Abstracts wsdl:portType/wsdl:operation
 *
 * @author Vivek Pandey
 * @deprecated This interface is deprecated, will be removed in JAX-WS 2.2 RI.
 */
@Deprecated
public interface TWSDLOperation extends TWSDLExtensible{
    /**
     * Gives a Map of fault name attribute value to the {@link JClass}
     */
    Map<String, JClass> getFaults();
}
