/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.databinding;

import java.lang.reflect.Method;
import com.oracle.webservices.api.databinding.JavaCallInfo;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.model.JavaMethod;

public interface ClientCallBridge {
	
	Packet createRequestPacket(JavaCallInfo call);
	
	JavaCallInfo readResponse(Packet packet, JavaCallInfo call) throws Throwable;
	
	Method getMethod();
	
	JavaMethod getOperationModel();
}
