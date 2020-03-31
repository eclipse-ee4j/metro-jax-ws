/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.pkg_customization.server;
import fromjava.pkg_customization.server.types.*;
import jakarta.jws.*;
import jakarta.xml.ws.*;
import javax.annotation.Resource;

@WebService(name ="EchoIF", portName="EchoImplPort",targetNamespace = "http://example.org/")
public class EchoImpl  {
    public Bar echoBar(Bar bar) {
        return bar;
    }

    public String echoString(String str) {
        return str;
    }

    public void echoIntHolder(Holder<Integer> age) {
        age.value = age.value*2;
    }

    @Resource
    private WebServiceContext wsContext;
}
