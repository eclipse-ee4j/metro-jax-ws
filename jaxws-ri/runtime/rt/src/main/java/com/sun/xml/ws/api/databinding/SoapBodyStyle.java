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

/**
 * The SoapBodyStyle represents the possible choices of the mapping styles
 * between the SOAP body of a wsdl:operation input/output messages and JAVA
 * method parameters and return/output values.
 * 
 * @author shih-chang.chen@oracle.com
 */
public enum SoapBodyStyle {

	/**
	 * Bare style mapping of a document style with literal use wsdl:operation
	 */
	DocumentBare,

	/**
	 * Wrapper style mapping of a document style with literal use wsdl:operation
	 */
	DocumentWrapper,

	/**
	 * The mapping style of a RPC style with literal use wsdl:operation
	 */
	RpcLiteral,

	/**
	 * The mapping style of a RPC style with encoded use wsdl:operation.
	 * 
	 * Note: this is not offically supported in JAX-WS.
	 */
	RpcEncoded,

	/**
	 * The mapping style is not specified.
	 */
	Unspecificed;

	public boolean isDocument() {
		return (this.equals(DocumentBare) || this.equals(DocumentWrapper));
	}

	public boolean isRpc() {
		return (this.equals(RpcLiteral) || this.equals(RpcEncoded));
	}

	public boolean isLiteral() {
		return (this.equals(RpcLiteral) || this.isDocument());
	}

	public boolean isBare() {
		return (this.equals(DocumentBare));
	}

	public boolean isDocumentWrapper() {
		return (this.equals(DocumentWrapper));
	}
}
