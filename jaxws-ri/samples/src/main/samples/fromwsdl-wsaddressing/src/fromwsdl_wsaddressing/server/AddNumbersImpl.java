/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl_wsaddressing.server;

import javax.jws.WebService;

@WebService(endpointInterface = "fromwsdl_wsaddressing.server.AddNumbersPortType")
public class AddNumbersImpl implements AddNumbersPortType {


    public int addNumbers(int number1, int number2)
            throws AddNumbersFault_Exception {
        return impl(number1, number2);
    }

    public int addNumbers2(int number1, int number2)
            throws AddNumbersFault_Exception {
        return impl(number1, number2);
    }

    public int addNumbers3(int number1, int number2)
            throws AddNumbersFault_Exception {
        return impl(number1, number2);
    }

    int impl(int number1, int number2) throws AddNumbersFault_Exception {
        if (number1 < 0 || number2 < 0) {
            AddNumbersFault fb = new AddNumbersFault();
            fb.setDetail("Negative numbers cant be added!");
            fb.setMessage("Numbers: " + number1 + ", " + number2);

            throw new AddNumbersFault_Exception(fb.getMessage(), fb);
        }

        return number1 + number2;
    }
}
