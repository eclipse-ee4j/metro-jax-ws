/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws1040.fromwsdl.client;

import com.sun.xml.ws.developer.MemberSubmissionAddressingFeature;
import junit.framework.TestCase;

import javax.xml.ws.Action;
import java.lang.reflect.Method;

/**
 * Test verifying bugfix for JAX_WS-1040 - correct wsdl2java generation (annotation javax.xml.ws.Action)
 *
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
public class WSAActionTest extends TestCase {

    public void testWSGen() throws NoSuchMethodException {
        Class c = WSAAction.class;
        Method m = c.getMethod("myWebMethod");
        Action annotation = m.getAnnotation(Action.class);
        assertTrue("annotation must be null", annotation == null);
        // fix rolled back, for details see JAX_WS-1040
        // assertTrue("annotation must be not-null", annotation != null);
        // assertTrue("checking annotation.input", "myInputAction".equals(annotation.input()));
        // assertTrue("checking annotation.output", "myOutputAction".equals(annotation.output()));
    }
    
    // test of runtime configuration - check dump messages
    public void testMethodInvocation() {
        WSAAction port = new WSAActionService().getWSAActionPort(new MemberSubmissionAddressingFeature(true));
        port.myWebMethod();
    }

}
