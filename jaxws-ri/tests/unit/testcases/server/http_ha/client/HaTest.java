/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.http_ha.client;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.Service;
import java.util.Collections;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * HTTP HA test
 *
 * @author Jitendra Kotamraju
 */
public class HaTest extends TestCase {

    public HaTest(String name) {
        super(name);
    }

    public void test1() throws Exception {
        Hello proxy = new HelloService().getHelloPort();
        Map<String, List<String>> hdrs = new HashMap<String, List<String>>();
        hdrs.put("proxy-jroute", Collections.singletonList("instance2"));
        hdrs.put("Cookie", Collections.singletonList("METRO_KEY=key1;JREPLICA=replica1;JROUTE=instance1"));
        ((BindingProvider)proxy).getRequestContext().put(
            MessageContext.HTTP_REQUEST_HEADERS, hdrs);
        proxy.testHa();
    }
    
}
