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

public class Toyota extends Car {
    private String color;
    private final String make="Toyota";

    public Toyota() {
        setMake("Toyota");
    }

    public String getMake() {
        return make;
    }

    public Toyota(String model, String year, String color) {
        setModel(model);
        setYear(year);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String toString(){
        return getMake()+":"+getModel()+":"+getYear()+":"+color;
    }

}
