/*
 * Copyright (c) 2012, 2023 Oracle and/or its affiliates. All rights reserved.
 * Copyright 2006 Guillaume Nodet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jvnet.jax_ws_commons.jaxws;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.jws.WebService;
import java.util.List;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;

/**
 *
 *
 * @author gnodet (gnodet at apache.org)
 * @author dantran (dantran at apache.org)
 * @version $Id: WsGenMojo.java 3169 2007-01-22 02:51:29Z dantran $
 */
abstract class AbstractWsGenMojo extends AbstractJaxwsMojo {

    /**
     * Service endpoint implementation class names.
     * @since 3.0.1
     */
    @Parameter
    private List<String> seis;

    /**
     * Service endpoint implementation class name.
     */
    @Parameter
    private String sei;

    /**
     * Used in conjunction with <code>genWsdl</code> to specify the protocol to use in the
     * <code>wsdl:binding</code>. Valid values are "<code>soap1.1</code>" or "<code>Xsoap1.2</code>",
     * default is "<code>soap1.1</code>". "<code>Xsoap1.2</code>" is not standard
     * and can only be used in conjunction with the <code>extension</code> option.
     */
    @Parameter
    private String protocol;

    /**
     * Specify the Service name to use in the generated WSDL.
     * Used in conjunction with the <code>genWsdl</code> option.
     */
    @Parameter
    private String servicename;

    /**
     * Specify the Port name to use in the generated WSDL.
     * Used in conjunction with the <code>genWsdl</code> option.
     */
    @Parameter
    private String portname;

    /**
     * Inline schemas in the generated WSDL.
     * Used in conjunction with the <code>genWsdl</code> option.
     */
    @Parameter(defaultValue = "false")
    private boolean inlineSchemas;

    /**
     * Turn off compilation after code generation and let generated sources be
     * compiled by maven during compilation phase; keep is turned on with this option.
     */
    @Parameter(defaultValue = "false")
    private boolean xnocompile;

    /**
     */
    @Parameter(defaultValue = "false")
    private boolean xdonotoverwrite;

    /**
     * Metadata file for wsgen. See <a href="https://eclipse-ee4j.github.io/metro-jax-ws/3.0.0/docs/ch03.html#users-guide-external-metadata">the JAX-WS Guide</a>
     * for the description of this feature.
     * Unmatched files will be ignored.
     *
     * @since 2.3
     * @see <a href="https://eclipse-ee4j.github.io/metro-jax-ws/3.0.0/docs/ch03.html#users-guide-external-metadata">External Web Service Metadata</a>
     */
    @Parameter
    private File metadata;

    protected abstract File getResourceDestDir();

    protected abstract File getClassesDir();

    protected abstract boolean getGenWSDL();

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Set<String> allSeis = new HashSet<>();
        if (seis != null && !seis.isEmpty()) {
            allSeis.addAll(seis);
        }
        if (sei != null) {
            if (!allSeis.add(sei)) {
                getLog().warn("'" + sei + "' was already added");
            }
        }
        if (allSeis.isEmpty()) {
            //find all SEIs within current classes
            allSeis.addAll(getSEIs(getClassesDir()));
        }
        if (allSeis.isEmpty()) {
            throw new MojoFailureException("No @javax.jws.WebService found.");
        }
        for (String aSei : allSeis) {
            processSei(aSei);
        }
    }

    protected void processSei(String sei) throws MojoExecutionException {
        if (buildContext.hasDelta(new File(getClassesDir(), sei.replace('.', '/') + ".class"))
                || (metadata != null && buildContext.hasDelta(metadata))) {
            getLog().info("Processing: " + sei);
            List<String> args = getWsGenArgs(sei, true);
            getLog().info("jaxws:wsgen args: " + args);
            exec(args);
            if (metadata != null) {
                try {
                    FileUtils.copyFileToDirectory(metadata, getClassesDir());
                } catch (IOException ioe) {
                    throw new MojoExecutionException(ioe.getMessage(), ioe);
                }
            }
        }
    }

    @Override
    protected String getMain() {
        return "com.sun.tools.ws.wscompile.WsgenTool";
    }

    @Override
    @SuppressWarnings("unchecked")
    protected String getExtraClasspath() {
        StringBuilder buf = new StringBuilder();
        buf.append(getClassesDir().getAbsolutePath());
        for (Artifact a : project.getArtifacts()) {
            buf.append(File.pathSeparatorChar);
            buf.append(a.getFile().getAbsolutePath());
        }
        return buf.toString();
    }

    @Override
    protected boolean getXnocompile() {
        return xnocompile;
    }

    /**
     * Construct wsgen arguments
     * @param aSei web service
     * @param attachResources true to attach resources to the project
     * @return a list of arguments
     * @throws MojoExecutionException for errors
     */
    protected List<String> getWsGenArgs(String aSei, boolean attachResources) throws MojoExecutionException {
        List<String> args = new ArrayList<>(getCommonArgs());

        if (getGenWSDL()) {
            if (this.protocol != null) {
                args.add("-wsdl:" + this.protocol);
            } else {
                args.add("-wsdl");
            }

            if (inlineSchemas) {
                maybeUnsupportedOption("-inlineSchemas", null, args);
            }

            if (servicename != null) {
                args.add("-servicename");
                args.add(servicename);
            }

            if (portname != null) {
                args.add("-portname");
                args.add(portname);
            }

            File resourceDir = getResourceDestDir();
            if (!resourceDir.mkdirs() && !resourceDir.exists()) {
                getLog().warn("Cannot create directory: " + resourceDir.getAbsolutePath());
            }
            args.add("-r");
            args.add("'" + resourceDir.getAbsolutePath() + "'");
            if (attachResources && !"war".equals(project.getPackaging())) {
                Resource r = new Resource();
                r.setDirectory(getRelativePath(project.getBasedir(), getResourceDestDir()));
                project.addResource(r);
            }
        }

        if (xdonotoverwrite) {
            args.add("-Xdonotoverwrite");
        }

        if (metadata != null && isArgSupported("-x")) {
            maybeUnsupportedOption("-x", "'" + metadata.getAbsolutePath() + "'", args);
        }

        args.add(aSei);

        getLog().debug("jaxws:wsgen args: " + args);

        return args;
    }

    private String getRelativePath(File root, File f) {
        return root.toURI().relativize(f.toURI()).getPath();
    }

    private Set<String> getSEIs(File directory) throws MojoExecutionException {
        Set<String> seis = new HashSet<String>();
        if (!directory.exists() || directory.isFile()) {
            return seis;
        }
        ClassLoader cl = null;
        try {
            cl = new URLClassLoader(new URL[]{ directory.toURI().toURL()}, getClass().getClassLoader());
            for (String s : FileUtils.getFileAndDirectoryNames(directory, "**/*.class", null, false, true, true, false)) {
                try {
                    String clsName = s.replace(File.separator, ".");
                    Class<?> c = cl.loadClass(clsName.substring(0, clsName.length() - 6));
                    if (!c.isInterface() && c.isAnnotationPresent(WebService.class)) {
                        //more sophisticated checks are done by wsgen itself
                        seis.add(c.getName());
                    }
                } catch (ClassNotFoundException ex) {
                    throw new MojoExecutionException(ex.getMessage(), ex);
                }
            }
        } catch (IOException ex) {
            throw new MojoExecutionException(ex.getMessage(), ex);
        } finally {
            if (cl instanceof Closeable) {
                try {
                    ((Closeable) cl).close();
                } catch (IOException ex) {
                    //ignore
                }
            }
        }
        return seis;
    }
}
