/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.webfault.messageName.server;

import javax.jws.WebService;

/**
 * @author Rama Pulavarthi
 */
@WebService
public class Calculator {
    public int add(int i, int j) throws InvalidParametersException, TimeOutException {
        if(i < 0 || j <0)
            throw new InvalidParametersException("add","In put is invalid as One of the input parameters is less than Zero.");
        if(i > 100 || j >100) {
            try {
            Thread.sleep(3000);
            } catch(InterruptedException e) {
                //ignore
            }
            throw new TimeOutException("Operation Exceeded time limit", 3);
        }
        return i+j;

    }

}
