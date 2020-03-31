/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.model.soap;

import com.sun.xml.ws.api.model.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.Style;
import com.sun.xml.ws.api.SOAPVersion;

/**
 * A wsdl:opeartion binding object that represents soap:binding. This can be
 * the return of {@link com.sun.xml.ws.api.model.JavaMethod#getBinding()}.
 * <br>
 * the default values are always document/literal and SoapVersion is SOAP 1.1.
 *
 * @author Vivek Pandey
 */
public class SOAPBindingImpl extends SOAPBinding {
    public SOAPBindingImpl() {
    }

    public SOAPBindingImpl(SOAPBinding sb) {
        this.use = sb.getUse();
        this.style = sb.getStyle();
        this.soapVersion = sb.getSOAPVersion();
        this.soapAction = sb.getSOAPAction();
    }

    /**
     * @param style The style to set.
     */
    public void setStyle(Style style) {
        this.style = style;
    }

    /**
     * @param version
     */
    public void setSOAPVersion(SOAPVersion version) {
        this.soapVersion = version;
    }

    /**
     * @param soapAction The soapAction to set.
     */
    public void setSOAPAction(String soapAction) {
        this.soapAction = soapAction;
    }
}
