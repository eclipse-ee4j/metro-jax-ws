/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api;

import jakarta.xml.ws.WebServiceFeature;

/**
 * Marker interface for {@link WebServiceFeature} derived classes that when instances are specified in the feature list to a service delegate must be
 * added to the feature list of any Stubs created by that delegate.  WebServiceFeature instances passed directly in the parameters of get...() or createDispatch()
 * must take precedence over feature instances passed during service delegate initialization.
 * 
 *  @since 2.2.6 
 */
public interface ServiceSharedFeatureMarker {

}
