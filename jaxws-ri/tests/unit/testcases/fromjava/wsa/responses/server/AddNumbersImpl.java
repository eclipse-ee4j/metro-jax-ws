/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.wsa.responses.server;

import jakarta.jws.WebMethod;
import jakarta.xml.bind.annotation.XmlElement;

/**
 * @author Rama Pulavarthi
 */
public class AddNumbersImpl {

    @WebMethod
    public int addNumbers(@XmlElement(name="number1")int number1, @XmlElement(name="number2")int number2) throws AddNumbersException {
        return doStuff(number1, number2);
    }

    int doStuff(int number1, int number2) throws AddNumbersException {
        if (number1 < 0 || number2 < 0) {
            throw new AddNumbersException("Negative numbers can't be added!",
                                          "Numbers: " + number1 + ", " + number2);
                }
        return number1 + number2;
    }
}
