/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package async.wsdl_rpclit.client;

import junit.framework.TestCase;

import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;
import java.util.concurrent.ExecutionException;

public class Hello2CallbackHandler extends TestCase implements AsyncHandler<Hello2Response> {
    /*
    * (non-Javadoc)
    *
    * @see javax.xml.rpc.AsyncHandler#handleResponse(javax.xml.rpc.Response)
    */
    public void handleResponse(Response<Hello2Response> response) {
        try {
            Hello2Response resp = response.get();
            HelloType out = resp.getParam();
            int age = resp.getAge();
            assertEquals("foo", out.getArgument());
            assertEquals("bar", out.getExtra());
            assertEquals(age, 1234);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
