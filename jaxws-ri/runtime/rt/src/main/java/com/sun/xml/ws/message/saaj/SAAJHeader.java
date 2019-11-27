/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.message.saaj;

import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.message.DOMHeader;
import com.sun.istack.NotNull;

import javax.xml.soap.SOAPHeaderElement;

/**
 * {@link Header} for {@link SOAPHeaderElement}.
 *
 * @author Vivek Pandey
 */
public final class SAAJHeader extends DOMHeader<SOAPHeaderElement> {
    public SAAJHeader(SOAPHeaderElement header) {
        // we won't rely on any of the super class method that uses SOAPVersion,
        // so we can just pass in a dummy version
        super(header);
    }

    public
    @NotNull
    String getRole(@NotNull SOAPVersion soapVersion) {
        String v = getAttribute(soapVersion.nsUri, soapVersion.roleAttributeName);
        if(v==null || v.equals(""))
            v = soapVersion.implicitRole;
        return v;
    }
}
