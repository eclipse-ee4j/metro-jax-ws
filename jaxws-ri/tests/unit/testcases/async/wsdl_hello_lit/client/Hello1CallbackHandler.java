/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package async.wsdl_hello_lit.client;

import junit.framework.TestCase;

import jakarta.xml.ws.AsyncHandler;
import jakarta.xml.ws.Response;
import java.util.concurrent.ExecutionException;

public class Hello1CallbackHandler extends TestCase implements AsyncHandler<Hello1WorldResponse> {
    public void handleResponse(Response<Hello1WorldResponse> response) {
        try {
            Hello1WorldResponse resp = response.get();
            HelloOutput out = resp.getHelloOutput();
            HelloType outH = resp.getHeader();
            assertEquals("foo", out.getArgument());
            assertEquals("bar", out.getExtra());
            assertEquals("header arg", outH.getArgument());
            assertEquals("header extra", outH.getExtra());
            System.out.println("should fail next line");
            assertTrue(false); // this should cause failure, but doesn't
//        assertEquals("Hello World!", resp.getExtraHeader());
            System.out.println("Callback Handler Completed-Test pass");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
