/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromjava.epr.client;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import javax.xml.transform.stream.StreamResult;
import javax.xml.namespace.QName;

import testutil.EprUtil;
import testutil.ClientServerTestUtil;
import junit.framework.TestCase;

public class AddNumbersClient extends TestCase {
    private static final String endpointAddress = "http://localhost:8080/jaxrpc-wsa_fromjava_epr/hello";
    private static final QName serviceName = new QName("http://foobar.org/", "AddNumbersService");
    private static final QName portName = new QName("http://foobar.org/", "AddNumbersPort");
    private static final QName portTypeName = new QName("http://foobar.org/", "AddNumbers");

    public AddNumbersClient(String name) {
        super(name);
    }

    private AddNumbers createProxy() throws Exception {
        return new AddNumbersService().getAddNumbersPort();
    }

    public void testDefaultOutputAction() throws Exception {
        if (ClientServerTestUtil.useLocal()){
            return;
        }
        AddNumbers proxy = createProxy();
        W3CEndpointReference epr = proxy.getW3CEPR();
        System.out.println("---------------------------------------");
        epr.writeTo(new StreamResult(System.out));
        System.out.println("---------------------------------------");
        //EprUtil.validateEPR(epr, endpointAddress, serviceName, portName, portTypeName, Boolean.TRUE);
        EprUtil.validateEPR(epr, endpointAddress, null, null, null, false);

//        AddNumbers newProxy = epr.getPort(AddNumbers.class);
//        int result = newProxy.addNumbersNoAction(10, 10);
//        assertEquals(20, result);
    }


}
