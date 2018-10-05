/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.webfault.messageName.client;

import junit.framework.TestCase;

/**
 * Tests for messageName customization using @WebFault #JAX-WS-727
 * @author Rama Pulavarthi
 */
public class WebFault_MessageName_Test extends TestCase {
    public void test1() throws InvalidInputException, TimedOutException {
        CalculatorService s = new CalculatorService();
        Calculator calculator =  s.getCalculatorPort();
        assertTrue(calculator.add(1,2)==3);
    }

    public void test2() throws TimedOutException {
        CalculatorService s = new CalculatorService();
        Calculator calculator =  s.getCalculatorPort();
        try {
            calculator.add(-1,-2);
            fail("Exception should have occured");
        } catch (InvalidInputException e) {
          //expected
        }
    }

    public void test3() throws InvalidInputException {
        CalculatorService s = new CalculatorService();
        Calculator calculator =  s.getCalculatorPort();
        try {
            calculator.add(150,5);
            fail("Exception should have occured");
        } catch (TimedOutException e) {
           //expected
        }

    }
}
