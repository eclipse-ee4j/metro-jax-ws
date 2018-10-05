/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.wsdl;

import com.sun.istack.NotNull;

import javax.xml.namespace.QName;

/**
 * This class models the Operation Signature as defined by WS-I BP ( 1.2 or 2.0) to use wsa:Action and payload QName to
 * identify the corresponding wsdl operation from a request SOAP Message. 
 *
 * @author Rama Pulavarthi
 */
public class ActionBasedOperationSignature {
    private final String action;
    private final QName payloadQName;
    public ActionBasedOperationSignature(@NotNull String action, @NotNull QName payloadQName) {
        this.action = action;
        this.payloadQName = payloadQName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActionBasedOperationSignature that = (ActionBasedOperationSignature) o;

        if (!action.equals(that.action)) return false;
        if (!payloadQName.equals(that.payloadQName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = action.hashCode();
        result = 31 * result + payloadQName.hashCode();
        return result;
    }



}
