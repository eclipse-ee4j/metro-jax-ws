/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws;

import com.sun.tools.ws.wscompile.WsimportTool;

/**
 * WsImport tool entry point.
 * 
 * @author Vivek Pandey
 * @author Kohsuke Kawaguchi
 */
public class WsImport {
    /**
     * CLI entry point. Use {@link Invoker} to load proper API version
     */
    public static void main(String[] args) throws Throwable {
        System.exit(Invoker.invoke("com.sun.tools.ws.wscompile.WsimportTool", args));
    }

    /**
     * Entry point for tool integration.
     *
     * <p>
     * This does the same as {@link #main(String[])} except
     * it doesn't invoke {@link System#exit(int)}. This method
     * also doesn't play with classloaders. It's the caller's
     * responsibility to set up the classloader to load all jars
     * needed to run the tool.
     *
     * @return
     *      0 if the tool runs successfully.
     */
    public static int doMain(String[] args) throws Throwable {
        return new WsimportTool(System.out).run(args) ? 0 : 1;
    }
}
