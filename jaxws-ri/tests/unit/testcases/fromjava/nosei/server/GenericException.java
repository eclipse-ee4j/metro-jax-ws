/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.nosei.server;

import java.util.List;

public class GenericException extends Exception {
    GenericValue<Integer> age;
    Bar object;
    List<Bar> barList;
    
    public GenericException (String message, GenericValue<Integer> age, Bar object, List<Bar> barList) {
	  super(message);
        this.age = age;
        this.object = object;
        this.barList = barList;
    }

    public GenericValue<Integer> getAge() {
        return age;
    }
    
    public Bar getObject() {
        return object;
    }
    
    public List<Bar> getBarList() {
        return barList;
    }
}
