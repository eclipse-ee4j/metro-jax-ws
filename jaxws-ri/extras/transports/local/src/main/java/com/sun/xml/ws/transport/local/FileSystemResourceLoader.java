/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.local;

import com.sun.xml.ws.transport.http.ResourceLoader;

import javax.xml.ws.WebServiceException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link ResourceLoader} that deals with the expanded image of a war file
 * on a file system.
 */
public final class FileSystemResourceLoader implements ResourceLoader {
    /**
     * The root of the exploded war file.
     */
    private final File root;

    public FileSystemResourceLoader(File root) {
        this.root = root;
    }

    public URL getResource(String path) throws MalformedURLException {
        try {
            return new File(root+path).getCanonicalFile().toURL();
        } catch(IOException ioe) {
            throw new WebServiceException(ioe);
        }
    }

    public URL getCatalogFile() throws MalformedURLException {
        return getResource("/WEB-INF/jax-ws-catalog.xml");
    }

    public Set<String> getResourcePaths(String path) {
        Set<String> r = new HashSet<String>();
        File[] files = new File(root+path).listFiles();
        if (files == null) {
            return null;
        }
        for( File f : files) {
            if(f.isDirectory()) {
                r.add(path+f.getName()+'/');
            } else {
                r.add(path+f.getName());
            }
        }
        return r;
    }
}
