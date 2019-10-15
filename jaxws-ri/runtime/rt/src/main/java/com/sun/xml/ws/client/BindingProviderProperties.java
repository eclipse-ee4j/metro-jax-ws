/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client;

import com.sun.xml.ws.developer.JAXWSProperties;

public interface BindingProviderProperties extends JAXWSProperties{

    //legacy properties
    @Deprecated
    public static final String HOSTNAME_VERIFICATION_PROPERTY =
        "com.sun.xml.ws.client.http.HostnameVerificationProperty";
    public static final String HTTP_COOKIE_JAR =
        "com.sun.xml.ws.client.http.CookieJar";

    public static final String REDIRECT_REQUEST_PROPERTY =
        "com.sun.xml.ws.client.http.RedirectRequestProperty";
    public static final String ONE_WAY_OPERATION =
        "com.sun.xml.ws.server.OneWayOperation";

    
    //JAXWS 2.0
    public static final String JAXWS_HANDLER_CONFIG =
        "com.sun.xml.ws.handler.config";
    public static final String JAXWS_CLIENT_HANDLE_PROPERTY =
        "com.sun.xml.ws.client.handle";

}
