/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wscompile;

import com.sun.istack.NotNull;

import java.net.URL;
import java.util.regex.Pattern;

/**
 * Represents authorization information needed by {@link com.sun.istack.tools.DefaultAuthenticator} to
 * authenticate wsimport to access the wsdl.
 *
 * @author Vivek Pandey
 */

public final class AuthInfo {
    private final String user;
    private final String password;
    private final Pattern urlPattern;

    public AuthInfo(@NotNull URL url, @NotNull String user, @NotNull String password) {
        String u = url.toExternalForm().replaceFirst("\\?", "\\\\?");
        this.urlPattern = Pattern.compile(u.replace("*", ".*"), Pattern.CASE_INSENSITIVE);
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Returns if the requesting host and port are associated with this {@link AuthInfo}
     */
    public boolean matchingHost(@NotNull URL requestingURL) {
        return urlPattern.matcher(requestingURL.toExternalForm()).matches();
    }

}
