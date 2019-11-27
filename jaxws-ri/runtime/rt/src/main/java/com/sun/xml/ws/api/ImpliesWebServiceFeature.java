/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api;

/**
 * Features, Providers, and JWS implementations can implement this interface to
 * receive a callback allowing them to modify the features enabled for a client
 * or endpoint binding.
 * 
 * Implementations of this interface can make any changes they like to the set of 
 * features; however, general best practice is that implementations should not 
 * override features specified by the developer.  For instance, a Feature object
 * for WS-ReliableMessaging might use this interface to automatically enable
 * WS-Addressing (by adding the AddressingFeature), but not modify addressing if the 
 * user had already specified a different addressing version.
 * 
 * @since 2.2.6
 * @deprecated use {@link FeatureListValidatorAnnotation}
 */
public interface ImpliesWebServiceFeature {
	/**
	 * Callback that may inspect the current feature list and add additional features
	 * @param list Feature list
	 */
	public void implyFeatures(WSFeatureList list);
}
