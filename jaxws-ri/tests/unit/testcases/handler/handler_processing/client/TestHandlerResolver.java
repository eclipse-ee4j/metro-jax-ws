/*
 * Copyright (c) 2005, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package handler.handler_processing.client;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.handler.HandlerResolver;
import jakarta.xml.ws.handler.PortInfo;

/**
 * Simple Handlerresolver
 */
public class TestHandlerResolver implements HandlerResolver {

    private List<PortInfo> portInfos;
    
    public TestHandlerResolver() {
        portInfos = new ArrayList<PortInfo>();
    }
    
    public List<Handler> getHandlerChain(PortInfo portInfo) {
        portInfos.add(portInfo);
        return new ArrayList<Handler>();
    }
    
    // returns a copy so it won't get clobbered
    List<PortInfo> getPortInfos() {
        return new ArrayList(portInfos);
    }
    
    void clearPortInfos() {
        portInfos.clear();
    }
}
