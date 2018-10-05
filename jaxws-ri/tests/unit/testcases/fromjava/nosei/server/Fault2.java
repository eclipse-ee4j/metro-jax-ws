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


@javax.xml.ws.WebFault(faultBean="fromjava.nosei.server.MyFault2Bean", name="MyFault2", targetNamespace="http://mynamespace")
public class Fault2 extends Exception {
    
    String zing;
    int age;

    public Fault2 (String message, int age, String zing) {
	  super(message);
        this.age = age;
	  this.zing = zing;
    }

    public String getZing() {
        return zing;
    }

    public int getAge() {
        return age;
    }
}
