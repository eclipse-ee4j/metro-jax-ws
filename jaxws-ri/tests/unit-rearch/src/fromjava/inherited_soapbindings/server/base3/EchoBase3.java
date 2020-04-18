/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.inherited_soapbindings.server.base3;

import fromjava.inherited_soapbindings.server.base2.*;

import jakarta.jws.*;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;


@WebService
@SOAPBinding(parameterStyle=ParameterStyle.BARE)
public class EchoBase3 extends EchoBase2 { 
    public String echoC(String str) {
        return "EchoBase3:"+str;
    }

    public String echoD(String str) {
        return "EchoBase3:"+str;
    }

    public String echoF(String str) {
        return "EchoBase3:"+str;
    }
    public String echoG(String str) {
        return "EchoBase3:"+str;
    }

    @WebMethod(exclude=true)
    public void badBase1(String str) {
    }
}
