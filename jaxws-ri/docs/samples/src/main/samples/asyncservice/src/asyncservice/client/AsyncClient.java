/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package asyncservice.client;
import java.lang.Exception;
import java.util.concurrent.*;

public class AsyncClient {
    private int noReqs = 0;
    private int noResps = 0;

    public static void main (String[] args) throws Exception {
        new AsyncClient().run();
    }

    public void run() throws Exception {
        Hello_Service service = new Hello_Service();
        final Hello proxy = service.getHelloAsyncPort();

        ExecutorService executor = Executors.newCachedThreadPool();
        synchronized(this) {
            noReqs = 4; noResps = 0;
        }
        for(int i=0; i < noReqs; i++) {
            final int j = i;
            executor.execute(new Runnable() {
                public void run() {
                    try {
                        test(proxy, "Hello", "Duke"+j);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executor.shutdown();
        while(!executor.awaitTermination(20L, TimeUnit.SECONDS));
        synchronized(this) {
            if(noReqs!= noResps) {
                throw new Exception("No. of requests and responses did not match");
            }
        }
    }


	private void test(Hello proxy,String arg, String extra) throws Exception {
    	Hello_Type req = new Hello_Type();
        req.setArgument(arg);
        req.setExtra(extra);
        System.out.println("Invoking Web Service with = " + arg + "," + extra);
        HelloResponse response = proxy.hello(req, req);
        System.out.println("arg=" + response.getArgument() + " extra=" + response.getExtra());

        if(!arg.equals(response.getArgument()) || !extra.equals(response.getExtra())) {
            throw new Exception("Mismatch in comparision");
        }

        synchronized (this) {
            ++noResps;
        }
	}
}
