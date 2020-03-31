/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws1049.server;

import jakarta.jws.HandlerChain;
import jakarta.jws.WebService;

/**
 * Simple service to test http headers (bug JAX_WS-1049 - fixing client behaviour)
 *
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
@WebService
@HandlerChain(file = "handlers.xml")
public class EchoImpl {
    
    public void doSomething() {
        // doesn't need to do anything ...
    }
}
