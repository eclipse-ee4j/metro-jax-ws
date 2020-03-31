/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.w3c.fromwsdl.w3cepr.server;

import jakarta.jws.WebService;

/**
 * @author Rama Pulavarthi
 */

@WebService(serviceName="AddNumbersService", portName="AddNumbersPort",
        endpointInterface = "wsa.w3c.fromwsdl.w3cepr.server.AddNumbersPortType", targetNamespace = "http://example.com/")
public class AddNumbersImpl implements AddNumbersPortType {
    public int addNumbers( int number1, int number2) throws AddNumbersFault_Exception {
        return number1 + number2;
    }


}
