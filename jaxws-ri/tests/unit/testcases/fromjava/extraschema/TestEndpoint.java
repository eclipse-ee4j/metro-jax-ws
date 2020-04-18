/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.extraschema;

import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService(targetNamespace = "http://echo.org/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class TestEndpoint {
    public String echoString(String param){
        return param;
    }

    public int echoInt(int param){
        return param;
    }
}
