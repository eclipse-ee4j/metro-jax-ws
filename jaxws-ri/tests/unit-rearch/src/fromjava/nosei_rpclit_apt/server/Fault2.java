/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.nosei_rpclit_apt.server;


public class Fault2 extends Exception {
    
    int age;

    public Fault2 (String message, int age) {
	  super(message);
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
