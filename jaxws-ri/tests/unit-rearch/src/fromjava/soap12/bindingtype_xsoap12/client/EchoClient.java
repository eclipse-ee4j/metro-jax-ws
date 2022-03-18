/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.soap12.bindingtype_xsoap12.client;

import jakarta.xml.ws.soap.SOAPFaultException;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.soap.SOAPBinding;
import jakarta.xml.soap.SOAPFactory;
import jakarta.xml.soap.SOAPFault;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import testutil.ClientServerTestUtil;

import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.Holder;
import java.util.ArrayList;
import java.util.List;


/**
 * @author JAX-RPC Development Team
 */
public class EchoClient extends TestCase {
    private QName portQName = new QName("http://echo.org/", "Echo");;
    private static Echo stub;

    public EchoClient(String name) throws Exception{
        super(name);
        if (stub == null) {
            EchoService service = new EchoService();
            stub = service.getEchoPort();      
            ClientServerTestUtil.setTransport(stub);   
            BindingProvider bp = (BindingProvider)stub;
            System.out.println("Binding for port is: "+bp.getBinding());
            assertTrue(bp.getBinding() instanceof SOAPBinding);
            SOAPBinding sb = (SOAPBinding)bp.getBinding();
	    SOAPFactory factory = sb.getSOAPFactory();
	    System.out.println("SOAPFactory for port is: "+factory);
	    if(!(factory instanceof SOAPFactory)) {
	        System.out.println("SOAPFactory for port is not an instance of SOAPFactory");
	    } else
	        System.out.println("SOAPFactory for port is an instance of SOAPFactory");

	    System.out.println("Make sure that the SOAPFactory is based on SOAP1.2 protocol");
	    SOAPFault soapfault = factory.createFault();
	    try {
		soapfault.setFaultRole("http://myfault.org");
		System.out.println("SOAPFactory is a based on SOAP1.2 protocol (Expected)");
            } catch (UnsupportedOperationException e) {
		System.out.println("SOAPFactory is a based on SOAP1.1 protocol (Unexpected)");
                assertTrue(false);
            }
        }
    }


    public void testSimple() throws Exception {
        Holder<String> strHolder = new Holder<String>();
        strHolder.value = "fred";

        assertTrue(stub.echoString("test").equals("test"));
        assertTrue(stub.echoString("Mary & Paul").equals("Mary & Paul"));
        assertTrue(stub.echoLong(33L) == 33L);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(EchoClient.class);
        return suite;
    }

    /*
     * for debugging
     */
    public static void main(String [] args) {
        try {
            System.setProperty("uselocal", "true");
            EchoClient testor = new EchoClient("TestClient");
//            testor.testHeadersDynamic();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

