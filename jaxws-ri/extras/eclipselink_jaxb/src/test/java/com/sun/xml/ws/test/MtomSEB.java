/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.test;

import static javax.jws.soap.SOAPBinding.ParameterStyle.BARE;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.soap.MTOM;


@WebService(targetNamespace="http://www.oracle.com/j2ee.ws.jaxws.test/")
@SOAPBinding(parameterStyle = BARE)
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
