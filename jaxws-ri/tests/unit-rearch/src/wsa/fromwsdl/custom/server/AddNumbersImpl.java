/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromwsdl.custom.server;

import jakarta.jws.WebService;

/**
 * @author Arun Gupta
 */
@WebService(endpointInterface="wsa.fromwsdl.custom.server.AddNumbersPortType")
public class AddNumbersImpl implements AddNumbersPortType {
    public int addNumbers(int number1, int number2)
            throws AddNumbersFault_Exception {
        return doStuff(number1, number2);
    }

    public int addNumbers2(int number1, int number2)
            throws AddNumbersFault_Exception {
        return doStuff(number1, number2);
    }

    public int addNumbers3(int number1, int number2)
            throws AddNumbersFault_Exception {
        return doStuff(number1, number2);
    }

    public int addNumbers4(int number1, int number2)
            throws AddNumbersFault_Exception {
        return doStuff(number1, number2);
    }

    public void addNumbers5(int number1, int number2) {
        System.out.println("addNumbers5");
    }

    int doStuff(int number1, int number2) throws AddNumbersFault_Exception {
        if (number1 < 0 || number2 < 0) {
            ObjectFactory of = new ObjectFactory();
            AddNumbersFault fb = of.createAddNumbersFault();
            fb.setDetail("Negative numbers cant be added!");
            fb.setMessage("Numbers: " + number1 + ", " + number2);

            throw new AddNumbersFault_Exception(fb.getMessage(), fb);
        }
        return number1 + number2;
    }
}
