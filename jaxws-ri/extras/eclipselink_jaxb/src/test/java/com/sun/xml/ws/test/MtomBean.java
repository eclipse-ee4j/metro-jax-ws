/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.test;

public class MtomBean {
    private byte[] binary1;
    private byte[] binary2;
    public byte[] getBinary1() {
        return binary1;
    }
    public void setBinary1(byte[] binary1) {
        this.binary1 = binary1;
    }
    public byte[] getBinary2() {
        return binary2;
    }
    public void setBinary2(byte[] binary2) {
        this.binary2 = binary2;
    }

}
