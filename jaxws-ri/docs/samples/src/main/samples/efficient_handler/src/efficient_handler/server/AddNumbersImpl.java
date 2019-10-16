/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package efficient_handler.server;

import javax.jws.HandlerChain;
import javax.jws.WebService;

@WebService
@HandlerChain( file="handlers.xml")
public class AddNumbersImpl {
    
    /**
     * @param number1
     * @param number2
     * @return The sum
     * @throws AddNumbersException
     *             if any of the numbers to be added is negative.
     */
    public int addNumbers(int number1, int number2) throws AddNumbersException {
        if (number1 < 0 || number2 < 0) {
            throw new AddNumbersException("Negative number cant be added!",
                "Numbers: " + number1 + ", " + number2);
        }
        return number1 + number2;
    }
    
}
