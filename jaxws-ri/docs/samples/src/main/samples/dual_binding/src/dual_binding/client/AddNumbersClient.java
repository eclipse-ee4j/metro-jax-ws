/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package dual_binding.client;

import javax.xml.ws.BindingProvider;

import com.sun.xml.ws.developer.BindingTypeFeature;
import com.sun.xml.ws.developer.JAXWSProperties;

/**
 * @author Jitendra Kotamraju
 */

public class AddNumbersClient {

    public static void main(String[] args) {
        // Uses SOAP binding
        AddNumbersImpl soap  = new AddNumbersImplService().getSoap();
        int number1 = 10;
        int number2 = 20;
        System.out.printf ("Invoking add(%d, %d)\n", number1, number2);
        int result = soap .add(number1, number2);
        System.out.printf ("The result of adding %d and %d is %d.\n\n", number1, number2, result);

        // Uses REST binding
        BindingTypeFeature bf = new BindingTypeFeature(JAXWSProperties.REST_BINDING);
        AddNumbersImpl rest = new AddNumbersImplService().getSoap(bf);
        ((BindingProvider)rest).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8080/jaxws-dual_binding/rest");
        System.out.printf ("Invoking add(%d, %d)\n", number1, number2);
        result = rest.add(number1, number2);
        System.out.printf("The result of adding %d and %d is %d.\n\n", number1, number2, result);
    }

}
