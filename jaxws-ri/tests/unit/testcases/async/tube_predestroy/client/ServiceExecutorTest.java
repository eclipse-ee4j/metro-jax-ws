/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package async.tube_predestroy.client;

import junit.framework.TestCase;
import java.io.*;
import async.tube_predestroy.common.*;

/**
 * @author Martin Grebac
 */
public class ServiceExecutorTest extends TestCase {

    private Hello proxy;

    public ServiceExecutorTest(String name) throws Exception {
        super(name);
    }

    public void test() throws Exception {
        Hello_Service service = new Hello_Service();
        proxy = service.getHelloPort();
        System.gc();
        Thread.sleep(500);
        ((Closeable)proxy).close();
        assertEquals("Number of tube instances is different than expected. Either a "
                    + "tube was cloned or something went wrong sooner", 1, MyTube.instancesCreated);
    }

}
