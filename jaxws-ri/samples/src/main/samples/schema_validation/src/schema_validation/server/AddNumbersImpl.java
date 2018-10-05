/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package schema_validation.server;

import javax.jws.WebService;
import com.sun.xml.ws.developer.SchemaValidation;

/**
 * Schema Validation sample
 *
 * @author Jitendra Kotamraju
 */
@WebService (endpointInterface="schema_validation.server.AddNumbersPortType")
@SchemaValidation
public class AddNumbersImpl {
    
    public int addNumbers (int number1, int number2) {
        return number1 + number2;
    }
    
}
