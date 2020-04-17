/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.inherited_soapbindings.server;

import fromjava.inherited_soapbindings.server.base3.*;
import jakarta.jws.*;

public class EchoBase4 extends EchoBase3 {
    @WebMethod
    public String echoJ(String str) {
        return "EchoBase4:"+str;
    }
}
