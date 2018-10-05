/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package async.executor_821.client;

import junit.framework.TestCase;

import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.*;

/*
 * @author Jitendra Kotamraju
 */
public class Hello1CallbackHandler extends TestCase implements AsyncHandler<Hello1Response> {
    final Exchanger<Hello1Response> exchanger;

    Hello1CallbackHandler(Exchanger<Hello1Response> exchanger) {
        this.exchanger = exchanger;
    }
    public void handleResponse(Response<Hello1Response> response) {
        Hello1Response resp = null;
        try {
            resp = response.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            exchanger.exchange(resp);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
