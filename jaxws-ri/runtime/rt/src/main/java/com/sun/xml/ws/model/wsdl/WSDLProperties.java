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

import com.oracle.webservices.api.message.BasePropertySet;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;

import javax.xml.namespace.QName;
import jakarta.xml.ws.handler.MessageContext;


import org.xml.sax.InputSource;

/**
 * Properties exposed from {@link WSDLPort} for {@link MessageContext}.
 * Donot add this satellite if {@link WSDLPort} is null.
 *
 * @author Jitendra Kotamraju
 */
public abstract class WSDLProperties extends BasePropertySet {

    private static final PropertyMap model;
    static {
        model = parse(WSDLProperties.class);
    }

    private final @Nullable SEIModel seiModel;

    protected WSDLProperties(@Nullable SEIModel seiModel) {
        this.seiModel = seiModel;
    }

    @Property(MessageContext.WSDL_SERVICE)
    public abstract QName getWSDLService();

    @Property(MessageContext.WSDL_PORT)
    public abstract QName getWSDLPort();

    @Property(MessageContext.WSDL_INTERFACE)
    public abstract QName getWSDLPortType();
    
    @Property(MessageContext.WSDL_DESCRIPTION)
    public InputSource getWSDLDescription() {
    	return seiModel != null ? new InputSource(seiModel.getWSDLLocation()) : null;
    }

    @Override
    protected PropertyMap getPropertyMap() {
        return model;
    }

}
