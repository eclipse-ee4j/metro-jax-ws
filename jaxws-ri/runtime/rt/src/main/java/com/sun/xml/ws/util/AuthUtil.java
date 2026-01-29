/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package com.sun.xml.ws.util;

import java.net.HttpURLConnection;
import java.net.Authenticator;

/**
 * Utils for HttpURLConnection authentication.
 *
 * Version for {@code runtime >= 9}.
 *
 * @author Nancy Bosecker
 *
 */
public final class AuthUtil {

	private AuthUtil() {}

	/**
	 * Sets the authenticator on the {@link HttpURLConnection} by invoking {@link HttpURLConnection#setAuthenticator(Authenticator)}.
	 */
	public static void setAuthenticator(Authenticator authenticator, HttpURLConnection httpConnection) {
		httpConnection.setAuthenticator(authenticator);
	}
}