/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.inherited_annotations.server;

import fromjava.inherited_annotations.server.base3.*;
import javax.jws.*;

public class EchoBase4 extends EchoBase3 {
    @WebMethod
    public String echoJ(String str) {
        return "EchoBase4:"+str;
    }
}
