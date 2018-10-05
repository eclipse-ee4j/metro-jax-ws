/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package dual_binding.server;

import javax.jws.WebService;
import javax.jws.WebMethod;

/**
 * @author Jitendra Kotamraju
 */

@WebService
public class AddNumbersImpl {
    
    @WebMethod(operationName="add")
    public int addNumbers(int number1, int number2) {
        return number1 + number2;
    }

}
