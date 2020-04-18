/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.nosei_default.server;

import jakarta.jws.*;

@WebService
public class EchoImpl {

    private void foo(Param p) {
    }

    public Bar echoBar(Bar bar) {
        return bar;
    }

    public String echoString(String str) {
        return str;
    }
    
    public String[] echoStringArray(String[] str) {
        return str;
    }
    
    public Bar[] echoBarArray(Bar[] bar) {
        return bar;
    }

    public Bar[] echoTwoBar(Bar bar, Bar bar2) {
        return new Bar[] { bar, bar2 };
    }
    
    public CLASS2 echoClass2(CLASS2 cls) {
        return cls;
    }
    
    @Oneway
    public void oneway() {
    }

    @WebMethod(exclude=true)
    public void test() {}
}
