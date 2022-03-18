/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromwsdl.delimiter.server;

import jakarta.jws.WebService;

/**
 * @author Arun Gupta
 */
@WebService(endpointInterface="wsa.fromwsdl.delimiter.server.AddNumbersPortType")
public class AddNumbersImpl implements AddNumbersPortType {
    public int addNumbers(int number1, int number2)
            throws AddNumbersFault_Exception, TooBigNumbersFault_Exception {
        if (number1 < 0 || number2 < 0) {
            AddNumbersFault faultInfo = createAddNumbersFault(number1, number2);
            throw new AddNumbersFault_Exception(faultInfo.getMessage(), faultInfo);
        }

        if (number1 > 10 || number2 > 10) {
            TooBigNumbersFault faultInfo = createTooBigNumbersFaultInfo(number1, number2);
            throw new TooBigNumbersFault_Exception(faultInfo.getMessage(), faultInfo);
        }

        return number1 + number2;
    }

    public int addNumbers2(int number1, int number2)
            throws AddNumbersFault_Exception, TooBigNumbersFault_Exception {
        if (number1 < 0 || number2 < 0) {
            AddNumbersFault faultInfo = createAddNumbersFault(number1, number2);
            throw new AddNumbersFault_Exception(faultInfo.getMessage(), faultInfo);
        }

        if (number1 > 10 || number2 > 10) {
            TooBigNumbersFault faultInfo = createTooBigNumbersFaultInfo(number1, number2);
            throw new TooBigNumbersFault_Exception(faultInfo.getMessage(), faultInfo);
        }

        return number1 + number2;
    }

    AddNumbersFault createAddNumbersFault(int number1, int number2) {
        ObjectFactory of = new ObjectFactory();
        AddNumbersFault faultInfo = of.createAddNumbersFault();
        faultInfo.setDetail("Negative numbers cant be added!");
        faultInfo.setMessage("Numbers: " + number1 + ", " + number2);

        return faultInfo;
    }

    TooBigNumbersFault createTooBigNumbersFaultInfo(int number1, int number2) {
        ObjectFactory of = new ObjectFactory();
        TooBigNumbersFault faultInfo = of.createTooBigNumbersFault();
        faultInfo.setDetail("Too Big numbers cant be added!");
        faultInfo.setMessage("Numbers: " + number1 + ", " + number2);

        return faultInfo;
    }
}
