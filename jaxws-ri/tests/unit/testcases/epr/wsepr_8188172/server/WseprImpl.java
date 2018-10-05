/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package epr.wsepr_8188172.server;

import java.util.List;

import javax.jws.WebService;
import javax.jws.WebMethod;

/**
 * @author lingling guo
 */
@WebService(serviceName = "WseprService",portName = "WseprPort",name = "Wsepr",targetNamespace = "http://wseprservice.org/wsdl")
public class WseprImpl{
    @WebMethod
    public String get() {
        return "";
    }
}
