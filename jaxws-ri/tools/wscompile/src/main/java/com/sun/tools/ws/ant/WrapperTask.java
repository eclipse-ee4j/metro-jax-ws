/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.ant;

import com.sun.istack.tools.ProtectedTask;
import com.sun.tools.ws.Invoker;
import com.sun.tools.ws.wscompile.Options;
import com.sun.tools.ws.resources.WscompileMessages;
import org.glassfish.jaxb.core.util.Which;
import org.apache.tools.ant.BuildException;

import jakarta.xml.ws.Service;
import java.io.IOException;

/**
 * Wrapper task to launch real implementations of the task in a classloader that can work
 * even in JavaSE 6.
 *
 * @author Kohsuke Kawaguchi
 */
public abstract class WrapperTask extends ProtectedTask {

    /**
     * Set to true to perform the endorsed directory override so that
     * Ant tasks can run on JavaSE 6.
     */
    private boolean doEndorsedMagic = false;

    protected String getCoreClassName() {
        return getClass().getName()+'2';
    }

    private String targetVersionAttribute;


    @Override
    public void setDynamicAttribute(String name, String value) throws BuildException {
        super.setDynamicAttribute(name,value);
        if(name.equals("target"))
            targetVersionAttribute = value;
        else if(name.equals("xendorsed"))
            this.doEndorsedMagic = Boolean.valueOf(value);

    }

    protected ClassLoader createClassLoader() throws ClassNotFoundException, IOException {
        ClassLoader cl = getClass().getClassLoader();
        if (doEndorsedMagic) {
            return Invoker.createClassLoader(cl);
        } else {
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
                if (Service.class.getClassLoader() == null)
                    throw new BuildException(WscompileMessages.WRAPPER_TASK_NEED_ENDORSED(loadedVersion.getVersion(), targetVersion.getVersion()));
                else {
                    throw new BuildException(WscompileMessages.WRAPPER_TASK_LOADING_INCORRECT_API(loadedVersion.getVersion(), Which.which(Service.class), targetVersion.getVersion()));
                }
            }
        }
    }
}
