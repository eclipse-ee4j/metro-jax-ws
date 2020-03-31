/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.model.wsdl;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;

import javax.xml.namespace.QName;
import jakarta.xml.ws.handler.MessageContext;

/**
 * Properties exposed from {@link WSDLPort} for {@link MessageContext}.
 * Donot add this satellite if {@link WSDLPort} is null.
 *
 * @author Jitendra Kotamraju
 */
public final class WSDLPortProperties extends WSDLProperties {

    private final @NotNull WSDLPort port;

    public WSDLPortProperties(@NotNull WSDLPort port) {
    	this(port, null);
    }
    
    public WSDLPortProperties(@NotNull WSDLPort port, @Nullable SEIModel seiModel) {
    	super(seiModel);
        this.port = port;
    }

    public QName getWSDLService() {
        return port.getOwner().getName();
    }

    public QName getWSDLPort() {
        return port.getName();
    }

    public QName getWSDLPortType() {
        return port.getBinding().getPortTypeName();
    }
}
