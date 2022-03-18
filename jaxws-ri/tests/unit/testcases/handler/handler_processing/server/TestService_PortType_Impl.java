/*
 * Copyright (c) 2005, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package handler.handler_processing.server;

import handler.handler_processing.common.HandlerTracker;
import handler.handler_processing.common.TestConstants;

/**
 * @author Rama Pulvarthi
 */
@jakarta.jws.WebService(serviceName="TestService", portName="TestServicePort", targetNamespace="urn:test", endpointInterface="handler.handler_processing.server.TestService")
public class TestService_PortType_Impl implements TestService, TestConstants {

    /*
     * Simple echo int method used for testing. 
     */
    public int testInt(int theInt) throws MyFaultException {
        if (theInt == SERVER_THROW_RUNTIME_EXCEPTION) {
            System.err.println(
                "service throwing runtime exception as instructed");
            throw new RuntimeException("test exception");
        }
        if (theInt == SERVER_THROW_MYFAULT_EXCEPTION) {
            System.err.println(
                "service throwing service exception as instructed");
            MyFaultInfo faultInfo = new MyFaultInfo();
            faultInfo.setVarString("element string");
            throw new MyFaultException("test fault", faultInfo);
        }
        if (HandlerTracker.VERBOSE_HANDLERS) {
            System.out.println("service received (and is returning) " + theInt);
        }
        return theInt;
    }
    
    /*
     * One-way version of the testInt method. Just outputs
     * a message. This method isn't called nearly as often as
     * testInt(), so the amount of output should be small.
     */
    public void testIntOneWay(int theInt) {
        System.out.println("service received " + theInt + " in one-way method");
    }
    
}
