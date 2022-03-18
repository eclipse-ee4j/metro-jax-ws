/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.jaxws991.server;

import jakarta.jws.WebService;
import jakarta.xml.ws.Action;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.Addressing;
import jakarta.xml.ws.soap.SOAPBinding;

/**
 * Simple WS to reproduce bug JAXWS-991 - simple Echo with WS-A
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
@Addressing(required = true)
@WebService
@BindingType(value = SOAPBinding.SOAP11HTTP_BINDING)
public class Echo {

    @Action(input = "echoInput", output = "echoOutput")
    public String echoString(String str) {
        return str;
    }

}
