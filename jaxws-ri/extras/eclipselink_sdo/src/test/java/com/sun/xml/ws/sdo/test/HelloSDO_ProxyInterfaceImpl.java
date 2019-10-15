/*
 * Copyright (c) 2012, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.sdo.test;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.sun.xml.ws.sdo.test.helloSDO.MySDO;

@WebService(targetNamespace="http://oracle.j2ee.ws.jaxws.test/")

public class HelloSDO_ProxyInterfaceImpl implements HelloSDO_ProxyInterface {

    @SOAPBinding(parameterStyle= SOAPBinding.ParameterStyle.BARE)
    public MySDO echoSDO(MySDO coo) {
        coo.setIntPart(coo.getIntPart() + 1);
        coo.setStringPart("Gary");
        return coo;

    }
}
