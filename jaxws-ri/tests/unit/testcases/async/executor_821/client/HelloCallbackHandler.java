/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package async.executor_821.client;

import junit.framework.TestCase;

import jakarta.xml.ws.AsyncHandler;
import jakarta.xml.ws.Response;
import java.util.concurrent.ExecutionException;

/*
 * @author Jitendra Kotamraju
 */
public class HelloCallbackHandler extends TestCase implements AsyncHandler<HelloOutput> {
    public void handleResponse(Response<HelloOutput> response) {
        try {
            HelloOutput output = response.get();
            assertEquals("foo", output.getArgument());
            assertEquals("bar", output.getExtra());
            System.out.println("Callback Handler Completed-Test pass");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
