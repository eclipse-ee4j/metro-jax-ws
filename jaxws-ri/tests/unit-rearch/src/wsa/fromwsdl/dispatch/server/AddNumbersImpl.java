/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromwsdl.dispatch.server;

import javax.jws.WebService;

/**
 * @author Rama Pulavarthi
 */
@WebService(endpointInterface="wsa.fromwsdl.dispatch.server.AddNumbersPortType")
public class AddNumbersImpl implements AddNumbersPortType {
    public AddNumbersResponse addNumbers1(AddNumbers parameters)
            throws AddNumbersFault_Exception {
        return doStuff(parameters);
    }

    public AddNumbersResponse addNumbers2(AddNumbers parameters)
            throws AddNumbersFault_Exception {
        return doStuff(parameters);
    }

    public AddNumbersResponse addNumbers3(AddNumbers parameters)
            throws AddNumbersFault_Exception {
        return doStuff(parameters);
    }

    public AddNumbersResponse addNumbers4(AddNumbers parameters)
            throws AddNumbersFault_Exception {
        return doStuff(parameters);
    }

    public void addNumbers5(AddNumbers parameters) {
        System.out.println("addNumbers5");
    }

    AddNumbersResponse doStuff(AddNumbers numbers) throws AddNumbersFault_Exception {
        int number1 = numbers.getNumber1();
        int number2 = numbers.getNumber2();
         if (number1 < 0 || number2 < 0) {
            ObjectFactory of = new ObjectFactory();
            AddNumbersFault fb = of.createAddNumbersFault();
            fb.setDetail("Negative numbers cant be added!");
            fb.setMessage("Numbers: " + number1 + ", " + number2);

            throw new AddNumbersFault_Exception(fb.getMessage(), fb);
        }
        AddNumbersResponse response =  new AddNumbersResponse();
        response.setReturn(number1 + number2);
        return response;
    }
}
