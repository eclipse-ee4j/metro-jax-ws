/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.rest.simple.server;

import jakarta.jws.*;

import jakarta.xml.ws.*;

import com.sun.xml.ws.developer.JAXWSProperties;

@WebService
@BindingType(JAXWSProperties.REST_BINDING)
public class EchoImpl {
    public int add(int num1, int num2) {
        return num1+num2;
    }
}
