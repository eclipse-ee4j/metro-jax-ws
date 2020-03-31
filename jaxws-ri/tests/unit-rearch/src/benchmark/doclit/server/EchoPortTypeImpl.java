/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package benchmark.doclit.server;

import java.math.BigDecimal;
import java.util.List;

import jakarta.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author JAX-RPC RI Development Team
 */
@WebService(endpointInterface="benchmark.doclit.server.EchoPortType")
public class EchoPortTypeImpl implements EchoPortType {
	public void echoVoid() {
	}

	public boolean echoBoolean(boolean param) {
		return param;
	}

	public String echoString(String inputString) {
		return inputString;
	}

	public List<String> echoStringArray(List<String> param) {
		return param;
	}

	public int echoInteger(int param) {
		return param;
	}

	public List<Integer> echoIntegerArray(List<Integer> param) {
		return param;
	}

	public float echoFloat(float param) {
		return param;
	}

	public List<Float> echoFloatArray(List<Float> param) {
		return param;
	}

	public ComplexType echoComplexType(ComplexType param) {
		return param;
	}

	public List<ComplexType> echoComplexTypeArray(List<ComplexType> param) {
		return param;
	}

	public byte[] echoBase64(byte[] param) {
		return param;
	}

	public XMLGregorianCalendar echoDate(XMLGregorianCalendar param) {
		return param;
	}

	public BigDecimal echoDecimal(java.math.BigDecimal param) {
		return param;
	}

	public Enum echoEnum(Enum param) {
		return param;
	}

	public NestedComplexType echoNestedComplexType(NestedComplexType param) {
		return param;
	}
}
