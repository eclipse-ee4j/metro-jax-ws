/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxb274;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import static javax.jws.soap.SOAPBinding.ParameterStyle.WRAPPED;
import static javax.jws.soap.SOAPBinding.Style.DOCUMENT;
import static javax.jws.soap.SOAPBinding.Use.LITERAL;

@WebService(portName="DocLitWrappedPort")
@SOAPBinding(style=DOCUMENT, use=LITERAL ,parameterStyle=WRAPPED)
public class DocLitWrapped {
    public Person[] echo(Person[] people){
        System.out.println("Received array. length="+people.length);
        for (Person p : people) {
            System.out.println(p);
        }
        return people;
    }
}
