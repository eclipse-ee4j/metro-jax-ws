/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package type_substitution.server;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public abstract class Car implements Vehicle{
    private String model;
    private String year;
    private String make;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public abstract String getMake();

    static class Adapter extends XmlAdapter<Car, Vehicle> {
        public Vehicle unmarshal(Car v) throws Exception {
            return v;
        }

        public Car marshal(Vehicle v) throws Exception {
            return (Car)v;
        }
    }
}
