/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/**
 * Convenience test driver for WsGen.
 *
 * This allows us to run "java WsImport ..." from the command line.
 * See {@linkplain http://parse-ipr.dev.java.net/} for how to set up your classpath
 * in your shell easily.
 */
public class WsImport {
    public static void main(String[] args) throws Throwable {
        com.sun.tools.ws.WsImport.main(args);
    }
}
