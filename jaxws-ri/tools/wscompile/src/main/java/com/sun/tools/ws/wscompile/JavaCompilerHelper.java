/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wscompile;

import com.sun.istack.tools.ParallelWorldClassLoader;
import com.sun.tools.ws.resources.JavacompilerMessages;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * A helper class to invoke javac.
 *
 * @author WS Development Team
 */
class JavaCompilerHelper{
    static File getJarFile(Class<?> clazz) {
        URL url = null;
        try {
            url = ParallelWorldClassLoader.toJarUrl(clazz.getResource('/'+clazz.getName().replace('.','/')+".class"));
            return new File(url.toURI());
        } catch (ClassNotFoundException | MalformedURLException e) {
            // if we can't figure out where JAXB/JAX-WS API are, we couldn't have been executing this code.
            throw new Error(e);
        } catch (URISyntaxException e) {
            // url.toURI() is picky and doesn't like ' ' in URL, so this is the fallback
            return new File(url.getPath());
        }
    }

    static boolean compile(String[] args, OutputStream out, ErrorReceiver receiver){
        try {
            JavaCompiler comp = ToolProvider.getSystemJavaCompiler();
            if (comp == null) {
                receiver.error(JavacompilerMessages.NO_JAVACOMPILER_ERROR(), null);
                return false;
            }
            return 0 == comp.run(null, out, out, args);
        } catch (SecurityException e) {
            receiver.error(e);
        }
        return false;
    }
    
}
