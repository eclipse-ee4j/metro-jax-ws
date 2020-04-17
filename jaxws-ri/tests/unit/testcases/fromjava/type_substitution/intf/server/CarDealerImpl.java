/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.type_substitution.intf.server;

import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;

/**
 * Tests the type substitution.
 */
@WebService(name="CarDealer")
@XmlSeeAlso({Car.class, Toyota.class})
public class CarDealerImpl {

    public Car[] getSedans(){
        Car[] cars = new Toyota[2];
        cars[0] = new Toyota("Camry", "1998", "white");
        cars[1] = new Toyota("Corolla", "1999", "red");
        return cars;
    }
}
