/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.generics;

import jakarta.jws.WebService;

/**
 * @author JAX-RPC Development Team
 */
@WebService(targetNamespace="portypeNamespace")
public interface EchoIF<T extends Bar>  {
    public T echoBar(T bar);
    public String echoString(String str);
}
