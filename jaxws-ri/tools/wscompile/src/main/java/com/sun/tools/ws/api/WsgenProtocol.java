/*
 * Copyright (c) 2009, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.api;

import java.lang.annotation.*;

/**
 * Allows to extend protocol for wsgen's wsdl[:protocol] switch.
 * This annotation must be specified on {@link WsgenExtension}
 * implementations.
 *
 * @author Jitendra Kotamraju
 * @since JAX-WS RI 2.1.6
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WsgenProtocol {
    /**
     * Token for wsgen -wsdl[:protocol]
     * @return Token for wsgen -wsdl[:protocol]
     */
    String token();

    /**
     * The corresponding lexical string used to create BindingID
     * @return lexical string used to create BindingID
     */
    String lexical();
}
