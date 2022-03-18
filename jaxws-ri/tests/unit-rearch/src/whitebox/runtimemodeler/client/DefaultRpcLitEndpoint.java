/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/*
 * $Id: DefaultRpcLitEndpoint.java,v 1.1.2.1 2007/01/05 03:32:14 vivekp Exp $
 */

package whitebox.runtimemodeler.client;

import jakarta.jws.*;
import jakarta.jws.soap.SOAPBinding;

import java.rmi.RemoteException;

import jakarta.xml.ws.Holder;


@WebService
@SOAPBinding(style=SOAPBinding.Style.RPC)
public class DefaultRpcLitEndpoint {

    @WebMethod
    public Integer echoInteger(Integer param) {
        return param;
    }
}

