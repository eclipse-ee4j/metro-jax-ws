/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package deployment.partial_webxml_multi.client;

import junit.framework.TestCase;
import deployment.partial_webxml_multi.client2.HelloImplService;

/**
 * @author Rama Pulavarthi
 */
public class SimpleTest extends TestCase{
    public void test1() {
        String resposne = new HelloImplService().getEchoPort().hello("Duke");
        assertEquals(resposne, "Hello Duke");

        int resp = new CalculatorImplService().getCalculatorPort().add(1,2);
        assertEquals(resp,3);
    }
}
