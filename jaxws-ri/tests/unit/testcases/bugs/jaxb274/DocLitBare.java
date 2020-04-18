/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxb274;

import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import static jakarta.jws.soap.SOAPBinding.ParameterStyle.BARE;
import static jakarta.jws.soap.SOAPBinding.Style.DOCUMENT;
import static jakarta.jws.soap.SOAPBinding.Use.LITERAL;

@WebService(portName="DocLitBarePort")
@SOAPBinding(style=DOCUMENT, use=LITERAL ,parameterStyle=BARE)
public class DocLitBare {
    public Person[] echo(Person[] people){
        System.out.println("Received array. length="+people.length);
        for (Person p : people) {
            System.out.println(p);
        }
        return people;
    }
}
