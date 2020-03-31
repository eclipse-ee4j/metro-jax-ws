/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.header_diff_ns.server;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.Holder;

/**
 * @author Rama Pulavarthi
 */
@WebService
public class Calculator {

    @WebMethod
    public int addNumbers(int val, @WebParam(name="valueheader", header=true,targetNamespace = "urn:mycompany/headers")int header) {
        return val+header;
    }

}
