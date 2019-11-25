/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Represents the version information.
 *
 * @author Kohsuke Kawaguchi
 */
public final class Version {
    /**
     * Represents the build id, which is a string like "b13" or "hudson-250".
     */
    public final String BUILD_ID;
    /**
     * Represents the complete version string, such as "JAX-WS RI 2.0-b19"
     */
    public final String BUILD_VERSION;
    /**
     * Represents the major JAX-WS version, such as "2.0".
     */
    public final String MAJOR_VERSION;

    /**
     * Represents the latest Subversion Reversion number.
     */
    public final String SVN_REVISION;

    /**
     * The Runtime Version.
     */
    public static final Version RUNTIME_VERSION = Version.create(Version.class.getResourceAsStream("version.properties"));

    private Version(String buildId, String buildVersion, String majorVersion, String svnRev) {
        this.BUILD_ID = fixNull(buildId);
        this.BUILD_VERSION = fixNull(buildVersion);
        this.MAJOR_VERSION = fixNull(majorVersion);
        this.SVN_REVISION = fixNull(svnRev);
    }

    public static Version create(InputStream is) {
        Properties props = new Properties();
        try {
            props.load(is);
        } catch (IOException e) {
            // ignore even if the property was not found. we'll treat everything as unknown
        } catch (Exception e) {
            //ignore even if property not found
        }

        return new Version(
            props.getProperty("build-id"),
            props.getProperty("build-version"),
            props.getProperty("major-version"),
            props.getProperty("svn-revision"));
    }

    private String fixNull(String v) {
        if(v==null) return "unknown";
        return v;
    }

    @Override
    public String toString() {
        return BUILD_VERSION + " git-revision#" + SVN_REVISION;
    }
}
