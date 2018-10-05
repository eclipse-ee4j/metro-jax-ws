/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl_soap12.client;

public class AddNumbersClient {
    public static void main (String[] args) {
        try {
            AddNumbersPortType port = new AddNumbersService().getAddNumbersPort ();
            
            int number1 = 10;
            int number2 = 20;
            
            System.out.printf ("Invoking addNumbers(%d, %d)\n", number1, number2);
            int result = port.addNumbers (number1, number2);
            System.out.printf ("The result of adding %d and %d is %d.\n\n", number1, number2, result);
            
            number1 = -10;
            System.out.printf ("Invoking addNumbers(%d, %d)\n", number1, number2);
            result = port.addNumbers (number1, number2);
            System.out.printf ("The result of adding %d and %d is %d.\n", number1, number2, result);
            
        } catch (AddNumbersFault_Exception ex) {
            System.out.printf ("Caught AddNumbersFault_Exception: %s\n", ex.getFaultInfo().getFaultInfo ());
        }
    }
}
