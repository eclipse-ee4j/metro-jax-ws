/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.http.servlet;

/**
 * @author Rama Pulavarthi
 */
public class ServletUtil {
    public static boolean isServlet30Based() {
        try {
            Class servletRequestClazz = Class.forName("jakarta.servlet.ServletRequest");
            servletRequestClazz.getDeclaredMethod("getServletContext");
            //no exception
            return true;
        } catch (Throwable t) {
            // Not Servlet 3.0 API 
        }
        return false;        
    }
}
