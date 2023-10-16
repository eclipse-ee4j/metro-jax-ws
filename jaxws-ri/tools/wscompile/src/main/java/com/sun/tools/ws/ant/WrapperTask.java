/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.ant;

import com.sun.istack.tools.ProtectedTask;
import com.sun.tools.ws.resources.WscompileMessages;
import com.sun.tools.ws.wscompile.Options;
import jakarta.xml.ws.Service;
import org.apache.tools.ant.BuildException;
import org.glassfish.jaxb.core.util.Which;

import java.io.IOException;

/**
 * Wrapper task to launch real implementations of the task in a classloader that can work
 * even in JavaSE 6.
 *
 * @author Kohsuke Kawaguchi
 */
public abstract class WrapperTask extends ProtectedTask {

    /**
     * Default constructor.
     */
    protected WrapperTask() {}

    @Override
    protected String getCoreClassName() {
        return getClass().getName()+'2';
    }

    private String targetVersionAttribute;


    @Override
    @SuppressWarnings({"exports"})
    public void setDynamicAttribute(String name, String value) throws BuildException {
        super.setDynamicAttribute(name,value);
        if(name.equals("target"))
            targetVersionAttribute = value;

    }

    @Override
    protected ClassLoader createClassLoader() throws ClassNotFoundException, IOException {
        ClassLoader cl = getClass().getClassLoader();
        Options.Target targetVersion;
        if (targetVersionAttribute != null) {
            targetVersion = Options.Target.parse(targetVersionAttribute);
        } else {
            targetVersion = Options.Target.getDefault();
        }
        Options.Target loadedVersion = Options.Target.getLoadedAPIVersion();
        //Check if the target version is supported by the loaded API version
        if (loadedVersion.isLaterThan(targetVersion)) {
            return cl;
        } else {
            throw new BuildException(WscompileMessages.WRAPPER_TASK_LOADING_INCORRECT_API(loadedVersion.getVersion(), Which.which(Service.class), targetVersion.getVersion()));
        }
    }
}
