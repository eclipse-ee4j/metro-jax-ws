/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package external_customize.client;

public class AddNumbersClient {
    private MathUtil port;
    
    public AddNumbersClient () {
        port = new MathUtilService().getMathUtil ();
    }
    
    public static void main (String[] args) {
        try {
            AddNumbersClient client = new AddNumbersClient ();
            
            //invoke synchronous method
            client.invoke ();
        } catch(MathUtilException e){
            System.out.println ("\tException detail: "+ e.getMessage ()+", "+e.getFaultInfo ());
        }
    }
    
    private void invoke () throws MathUtilException{
        int number1 = 10;
        int number2 = 20;
        
        System.out.printf ("Invoking addNumbers(%d, %d)\n", number1, number2);
        int result = port.add (number1, number2);
        System.out.printf ("The result of adding %d and %d is %d.\n\n", number1, number2, result);
        
        //lets make endpoint throw exception
        number1 = -10;
        System.out.printf ("Invoking addNumbers(%d, %d) and expect exception.\n", number1, number2);
        result = port.add (number1, number2);
    }
}
