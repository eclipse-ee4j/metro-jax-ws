/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.multibytename.test;

import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

/**
 * @author Jitendra Kotamraju
 */
@WebService(name="Hello",serviceName="Hello\u00EEService", targetNamespace="http://example.com/Hello")
@SOAPBinding(style=SOAPBinding.Style.RPC)
public class HelloService {
	public int echo(int a) {
		return a;
	}
}

