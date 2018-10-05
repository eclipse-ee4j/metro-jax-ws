/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.wsdl_fibonacci.server;

import java.util.List;
import javax.jws.WebService;
import javax.xml.ws.BindingProvider;

/**
 * calls itself recursively using its own proxy
 *
 * @author Jitendra Kotamraju
 */
@WebService(endpointInterface = "fromwsdl.wsdl_fibonacci.server.Fib")

public class TestEndpointImpl implements Fib {
    private volatile Fib proxy;
    private volatile int oneway = -100;

    public int getFib(String address, int num) {
        return get(address, num);
    }

    public void getFibOneway(String address, int num) {
        if (num == 0) {		// If it reaches 0, most likely it works
            oneway = num;
        }
        getOneway(address, num);
    }

    public int getFibVerifyOneway() {
        return oneway;		
    }

    private int get(String address, int num) {
        setUpProxy(address);
        System.out.println("num="+num);
    	if (num <= 1) {
            return 1;
        } else {
            return proxy.getFib(address, num-1)+proxy.getFib(address, num-2);
        }
    }

    private void getOneway(String address, int num) {
        setUpProxy(address);
        System.out.println("oneway num="+num);
    	if (num > 1) {
            proxy.getFibOneway(address, num-1);
            proxy.getFibOneway(address, num-2);
        }
    }

    private void setUpProxy(String address) {
        if (proxy == null ) {
            proxy = new Fib_Service().getFibPort();
            ((BindingProvider)proxy).getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
        }
    }

}
