/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/*
 * $Id: EchoPortTypeImpl.java,v 1.1 2007-08-31 22:19:29 kohsuke Exp $
 */

package benchmark.rpclit;

import java.math.BigDecimal;
import java.util.Calendar;

import jakarta.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author JAX-RPC RI Development Team
 */
@WebService(endpointInterface="benchmark.rpclit.EchoPortType")
public class EchoPortTypeImpl implements EchoPortType {
	public void echoVoid() {
	}

	public boolean echoBoolean(boolean param) {
		return param;
	}

	public int echoInteger(int param) {
		return param;
	}

	public float echoFloat(float param) {
		return param;
	}

	public String echoString(String inputString) {
		return inputString;
	}

	public byte[] echoBase64(byte[] param) {
		return param;
	}

	public XMLGregorianCalendar echoDate(XMLGregorianCalendar param) {
		return param;
	}

	public Enum echoEnum(Enum param) {
		return param;
	}

	public BigDecimal echoDecimal(BigDecimal param) {
		return param;
	}

	public ComplexType echoComplexType(ComplexType param) {
		return param;
	}

	public NestedComplexType echoNestedComplexType(NestedComplexType param) {
		return param;
	}

	public IntegerArray echoIntegerArray(IntegerArray param) {
		return param;
	}

	public FloatArray echoFloatArray(FloatArray param) {
		return param;
	}

	public StringArray echoStringArray(StringArray param) {
		return param;
	}

	public ComplexTypeArray echoComplexTypeArray(ComplexTypeArray param) {
		return param;
	}
}
