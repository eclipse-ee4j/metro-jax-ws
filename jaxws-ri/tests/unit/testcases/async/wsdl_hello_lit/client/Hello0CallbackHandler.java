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

public class Hello0CallbackHandler extends TestCase implements AsyncHandler<Integer> {
    public void handleResponse(Response<Integer> response) {
        try {
            Integer output = response.get();
            assertEquals(54321, output.intValue());
            System.out.println("Callback Handler Completed-Test pass");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
