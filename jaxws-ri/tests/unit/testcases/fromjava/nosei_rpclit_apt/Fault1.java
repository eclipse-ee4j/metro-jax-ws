/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.nosei_rpclit_apt;


@javax.xml.ws.WebFault(name="fault1",
    targetNamespace="urn:test:types")
public class Fault1 extends Exception {
    private FooException faultInfo;
    
    
    public Fault1(String message, FooException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }
    
    public Fault1(String message, FooException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }
    
    public FooException getFaultInfo() {
        return faultInfo;
    }
}
