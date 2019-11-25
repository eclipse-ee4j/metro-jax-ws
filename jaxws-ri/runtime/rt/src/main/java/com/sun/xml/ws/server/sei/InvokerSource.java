/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.server.sei;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.message.Packet;

/**
 * Interface for determining Invoker for a given request
 *
 * @since 2.2.6
 */
public interface InvokerSource<T extends Invoker> {
	/**
	 * Returns Invoker for the given request
	 * @param request Packet for request
	 * @return Selected invoker
	 */
	public @NotNull T getInvoker(Packet request);
}
