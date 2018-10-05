/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.inherited_annotations.server.base3;


import fromjava.inherited_annotations.server.base2.EchoBase2;

import javax.jws.*;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;


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
