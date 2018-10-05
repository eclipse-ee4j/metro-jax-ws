/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.soap_fault.client;

import junit.framework.TestCase;

import javax.xml.ws.soap.SOAPFaultException;
import javax.xml.soap.SOAPFault;


/**
 * @author Rama Pulavarthi
 */
public class SOAPFaultTest extends TestCase {
    private TestEndpoint createProxy() {
        return new TestEndpointService().getTestEndpointPort();
    }
    public void testFault1() {
        TestEndpoint proxy = createProxy();
        try {
        proxy.echo("throw fault");
        } catch(SOAPFaultException e) {
            SOAPFault sf = e.getFault();
            assertEquals("http://testNode",sf.getFaultNode());
        }
    }
    
}
