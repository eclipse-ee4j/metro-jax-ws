/*
 * Copyright (c) 2011, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wscompile.plugin.at_generated;

import com.sun.codemodel.*;
import com.sun.tools.ws.ToolVersion;
import com.sun.tools.ws.processor.model.Model;
import com.sun.tools.ws.wscompile.ErrorReceiver;
import com.sun.tools.ws.wscompile.Plugin;
import com.sun.tools.ws.wscompile.WsimportOptions;
import com.sun.tools.ws.wscompile.WsimportTool;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.xml.sax.SAXException;

/**
 * {@link Plugin} that marks the generated code by using JSR-250's '@Generated'.
 * It is based on a similar plugin in JAXB RI.
 *
 * @author Lukas Jungmann
 * @since 2.2.6
 */
public final class PluginImpl extends Plugin {

    private JClass annotation;

    // cache the timestamp so that all the @Generated annotations match
    private String date = null;

    @Override
    public String getOptionName() {
        return "mark-generated";
    }

    @Override
    public String getUsage() {
        return "  -mark-generated    :  mark the generated code as @jakarta.annotation.Generated";
    }

    @Override
    public boolean run(Model model, WsimportOptions wo, ErrorReceiver er) throws SAXException {
        JCodeModel cm = wo.getCodeModel();
        // we want this to work without requiring JSR-250 jar.
        annotation = cm.ref("jakarta.annotation.Generated");

        for (Iterator<JPackage> i = cm.packages(); i.hasNext();) {
            for (Iterator<JDefinedClass> j = i.next().classes(); j.hasNext();) {
                annotate(j.next());
            }
        }
        
        return true;
    }

    private void annotate(JAnnotatable m) {
        m.annotate(annotation)
                .param("value", WsimportTool.class.getName())
                .param("date", getISO8601Date())
                .param("comments", ToolVersion.VERSION.BUILD_VERSION);
    }

    /**
     * calculate the date value in ISO8601 format for the @Generated annotation
     * @return the date value
     */
    private String getISO8601Date() {
        if(date==null) {
            StringBuilder tstamp = new StringBuilder();
            tstamp.append((new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ")).format(new Date()));
            // hack to get ISO 8601 style timezone - is there a better way that doesn't require
            // a bunch of timezone offset calculations?
            tstamp.insert(tstamp.length()-2, ':');
            date = tstamp.toString();
        }
        return date;
    }
}
