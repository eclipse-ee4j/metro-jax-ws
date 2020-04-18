/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.soap12.hello.server;

import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;
import java.util.List;

/**
 * @author Vivek Pandey
 */
/*
 * Copyright 2004 Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

@jakarta.jws.WebService(endpointInterface="fromwsdl.soap12.hello.server.Hello")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class HelloImpl implements Hello {
    public HelloResponse hello(Hello_Type req)  {
        System.out.println("Hello_PortType_Impl received: " + req.getArgument() +
            ", " + req.getExtra());
        HelloResponse resp = new HelloResponse();
        resp.setName("vivek");
        resp.setArgument(req.getArgument());
        resp.setExtra(req.getExtra());
        return resp;
    }

    public VoidType voidTest(VoidType req)  {
        if(req == null)
            return null;
            return new VoidType();
    }

    public void echoArray(jakarta.xml.ws.Holder<NameType> name) {
    }

    public void echoArray1(jakarta.xml.ws.Holder<NameType> name) {
        NameType resp = name.value;
        resp.getName().add("EA");
    }

    public void echoArray2(jakarta.xml.ws.Holder<NameType> name) {
    }

    public void echoArray3(jakarta.xml.ws.Holder<List<String>> name) {

    }
    public NameType1 echoArray4(NameType1 request) {
        NameType1 resp = new NameType1();
        HelloType ht = new HelloType();
        ht.setArgument("arg1");
        ht.setExtra("extra1");


        HelloType ht1 = new HelloType();
        ht1.setArgument("arg2");
        ht1.setExtra("extra2");
        resp.getName().add(ht);
        resp.getName().add(ht1);
        return resp;
    }
}
