/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.sun.tools.ws.ant;

import com.sun.tools.ws.processor.modeler.annotation.WebServiceAp;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Javac;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * annotation processing task for use with the JAXWS project.
 */
@SuppressWarnings({"exports"})
public class AnnotationProcessingTask extends Javac {

    private boolean procOnly = false;
    private File sourceDestDir;

    private static final Pattern VERSION_PATTERN = Pattern.compile("^\\D+(\\d+(\\.?\\d+)?)$");

    /**
     * Default constructor.
     */
    public AnnotationProcessingTask() {}

    /**
     * Get the sourceDestDir attribute (-s javac parameter)
     * The default value is null.
     *
     * @return directory where to place generated source files.
     */
    public File getSourceDestDir() {
        return sourceDestDir;
    }

    /**
     * Set the sourceDestDir attribute. (-s javac parameter)
     *
     * @param sourceDestDir directory where to place processor generated source files.
     */
    public void setSourceDestDir(File sourceDestDir) {
        this.sourceDestDir = sourceDestDir;
    }

    /**
     * Get the compile option for the ap compiler.
     * If this is true the "-proc:only" argument will be used.
     *
     * @return the value of the compile option.
     */
    public boolean isProcOnly() {
        return procOnly;
    }

    /**
     * Set the compile option for the ap compiler.
     * Default value is false.
     *
     * @param procOnly if true set the compile option.
     */
    public void setProcOnly(boolean procOnly) {
        this.procOnly = procOnly;
    }

    @Override
    protected void checkParameters() throws BuildException {
        super.checkParameters();
        if (sourceDestDir == null) {
            throw new BuildException("destination source directory must be set", getLocation());
        }
        if (!sourceDestDir.isDirectory()) {
            throw new BuildException("destination source directory \"" + sourceDestDir + "\" does not exist or is not a directory",
                    getLocation());
        }
        try {
            Matcher matcher = VERSION_PATTERN.matcher(super.getCompilerVersion());
            if (matcher.find()) {
                float version = Float.parseFloat(matcher.group(1));
                if (version < 1.6) {
                    throw new BuildException("Annotation processing task requires Java 1.6+", getLocation());
                }
            }
        } catch (NumberFormatException | NullPointerException e) {
            log("Can't check version for annotation processing task");
        }
    }

    /**
     * Performs a compile using the Javac externally.
     *
     * @throws BuildException if there is a problem.
     */
    @Override
    public void execute() throws BuildException {
        ImplementationSpecificArgument argument = super.createCompilerArg();
        argument.setLine("-processor " + WebServiceAp.class.getName());
        argument = super.createCompilerArg();
        argument.setLine("-s " + sourceDestDir);
        if (procOnly) {
            argument = super.createCompilerArg();
            argument.setLine("-proc:only");
        }
        super.execute();
    }
}
