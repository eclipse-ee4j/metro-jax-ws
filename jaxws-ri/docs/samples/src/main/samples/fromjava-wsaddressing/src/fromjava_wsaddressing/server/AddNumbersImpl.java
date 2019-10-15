/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava_wsaddressing.server;

import javax.jws.WebService;
import javax.xml.ws.*;
import javax.xml.ws.soap.Addressing;

@Addressing
@WebService
public class AddNumbersImpl {

    @Action(
            input = "http://example.com/input",
            output = "http://example.com/output")
    public int addNumbers(int number1, int number2) throws AddNumbersException {
        return impl(number1, number2);
    }

    public int addNumbers2(int number1, int number2) throws AddNumbersException {
        return impl(number1, number2);
    }

    @Action(
            input = "http://example.com/input3",
            output = "http://example.com/output3",
            fault = {
            @FaultAction(className = AddNumbersException.class, value = "http://example.com/fault3")
                    }
    )
    public int addNumbers3(int number1, int number2) throws AddNumbersException {
        return impl(number1, number2);
    }

    int impl(int number1, int number2) throws AddNumbersException {
        if (number1 < 0 || number2 < 0) {
            throw new AddNumbersException("Negative numbers can't be added!",
                                          "Numbers: " + number1 + ", " + number2);
        }
        return number1 + number2;
    }
}

