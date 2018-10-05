/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.spi.db;

/**
 * Signals an error in Databinding.
 * 
 * @author shih-chang.chen@oracle.com
 */
public class DatabindingException extends RuntimeException {
	public DatabindingException() {}
    public DatabindingException(String message) { super(message); }
    public DatabindingException(Throwable cause) { super(cause); }
    public DatabindingException(String message, Throwable cause) { super(message, cause); }
}
