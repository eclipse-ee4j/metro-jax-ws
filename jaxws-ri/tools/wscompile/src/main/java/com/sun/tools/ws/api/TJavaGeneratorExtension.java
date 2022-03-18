/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.api;

import com.sun.codemodel.JMethod;
import com.sun.tools.ws.api.wsdl.TWSDLOperation;
import com.sun.tools.ws.processor.generator.JavaGeneratorExtensionFacade;

/**
 * Provides Java SEI Code generation Extensiblity mechanism.
 *
 * @see JavaGeneratorExtensionFacade
 * @author Vivek Pandey
 * @deprecated This class is deprecated, will be removed in JAX-WS 2.2 RI.
 */
@Deprecated
public abstract class TJavaGeneratorExtension {
    /**
     * This method should be used to write annotations on {@link JMethod}.
     *
     * @param wsdlOperation non-null wsdl extensiblity element -  wsdl:portType/wsdl:operation.
     * @param jMethod non-null {@link JMethod}
     */
     public abstract void writeMethodAnnotations(TWSDLOperation wsdlOperation, JMethod jMethod);
}
