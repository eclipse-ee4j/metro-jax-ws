/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package deployment.partial_webxml_multi.server;

import javax.jws.WebService;

/**
 * @author Rama Pulavarthi
 */
@WebService(targetNamespace = "http://com.example.calculator", portName = "CalculatorPort")
public class CalculatorImpl {
    public int add(int i, int j) {
        return i+j;    
    }
}
