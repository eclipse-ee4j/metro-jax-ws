/*
 * Copyright (c) 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wscompile.plugin.javax;

import java.io.File;
import java.io.IOException;

import com.sun.tools.ws.processor.model.Model;
import com.sun.tools.ws.wscompile.BadCommandLineException;
import com.sun.tools.ws.wscompile.ErrorReceiver;
import com.sun.tools.ws.wscompile.Options;
import com.sun.tools.ws.wscompile.Options.Target;
import com.sun.tools.ws.wscompile.Plugin;
import com.sun.tools.ws.wscompile.WsimportOptions;

import org.xml.sax.SAXException;

/**
 * {@link Plugin} that generate and compile sources with javax.xml.bind
 * packages.
 *
 * @author jbescos
 */
public class PluginImpl extends Plugin {
    
    private String javaxLibPath;

    @Override
    public String getOptionName() {
        return "generateJavax";
    }

    @Override
    public String getUsage() {
        return "  -generateJavax                  mark the generated code as javax.xml.bind\n"
                + "              -javaxLib <javax.xml.bind-api dependency path>   Path where javax.xml.bind-api jar file exists. This is necessary for JDK11+.";
    }

    @Override
    public int parseArgument(Options opt, String[] args, int i) throws BadCommandLineException, IOException {
        if ("-javaxLib".equals(args[i])) {
            javaxLibPath = opt.requireArgument("-javaxLib", args, ++i);
            File file = new File(javaxLibPath);
            if (!file.exists()) {
                throw new BadCommandLineException("File " + javaxLibPath + " does not exist.");
            }
            return 2;
        }
        return 0;
    }

    @Override
    public boolean run(Model wsdlModel, WsimportOptions options, ErrorReceiver errorReceiver) throws SAXException {
        Target target = options.target;
        // Javax applies when -target version is lower than 3.0
        if (target.ordinal() < Target.V3_0.ordinal()) {
            System.setProperty("javax.xml.bind.conversion", Boolean.TRUE.toString());
            if (javaxLibPath != null) {
                options.cmdlineJars.add(javaxLibPath);
            }
            // Otherwise it is still possible to make it work if JDK contains it
        }
        return true;
    }

}
