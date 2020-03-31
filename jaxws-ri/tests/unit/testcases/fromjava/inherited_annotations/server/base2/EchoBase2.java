/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.inherited_annotations.server.base2;

import fromjava.inherited_annotations.server.base.*;

import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;

@WebService
public class EchoBase2 extends EchoBase {
    public String echoA(String str) {
        return "EchoBase2:"+str;
    }

    public String echoD(String str) {
        return "EchoBase2:"+str;
    }

    public String echoE(String str) {
        return "EchoBase2:"+str;
    }
}
