/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.util;

import com.sun.tools.ws.spi.WSToolsObjectFactory;
import com.sun.tools.ws.wscompile.WsgenTool;
import com.sun.tools.ws.wscompile.WsimportTool;
import com.sun.xml.ws.api.server.Container;

import java.io.OutputStream;

/**
 * Factory implementation class to instantiate concrete objects for JAX-WS tools.
 *
 * @author JAX-WS Development Team
 */
public class WSToolsObjectFactoryImpl extends WSToolsObjectFactory {

    /**
     * Default constructor.
     */
    public WSToolsObjectFactoryImpl() {}

    @Override
    public boolean wsimport(OutputStream logStream, Container container, String[] args) {
        WsimportTool tool = new WsimportTool(logStream, container);
        return tool.run(args);
    }
    
    @Override
    public boolean wsgen(OutputStream logStream, Container container, String[] args) {
        WsgenTool tool = new WsgenTool(logStream, container);
        return tool.run(args);
    }
}
