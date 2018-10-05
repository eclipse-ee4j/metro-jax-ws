/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.type_substitution.server;

import java.util.ArrayList;
import java.util.List;

@javax.jws.WebService (endpointInterface="fromwsdl.type_substitution.server.CarDealer")
public class CarDealerImpl {
    public List<Car> getSedans(){
        List<Car> cars = new ArrayList<Car>();
        Toyota camry = new Toyota();

        camry.setMake("Toyota");
        camry.setModel("Camry");
        camry.setYear("1998");
        camry.setColor("white");

        cars.add(camry);

        Toyota corolla = new Toyota();

        corolla.setMake("Toyota");
        corolla.setModel("Corolla");
        corolla.setYear("1999");
        corolla.setColor("red");
        cars.add(corolla);
        return cars;
    }
}
