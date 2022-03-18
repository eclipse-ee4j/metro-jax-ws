/*
 * Copyright (c) 2013, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.test;

import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.xml.ws.soap.MTOM;


@WebService(targetNamespace="http://www.oracle.com/j2ee.ws.jaxws.test/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@MTOM
public class MtomSEB {
    public byte[] echoByteArray(byte[] b) {
        return b;
    }
    public MtomBean echoBean(MtomBean b){
        return b;
    }
    public MtomBean header(@WebParam(header=true, name="mtom-header")MtomBean h, MtomBean b) {
        return h;
    }
}
