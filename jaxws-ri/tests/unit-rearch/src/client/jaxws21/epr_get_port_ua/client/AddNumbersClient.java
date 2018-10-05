/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.jaxws21.epr_get_port_ua.client;

import com.sun.xml.ws.addressing.W3CAddressingConstants;
import com.sun.xml.ws.developer.MemberSubmissionAddressingFeature;
import com.sun.xml.ws.developer.MemberSubmissionEndpointReference;
import testutil.ClientServerTestUtil;
import testutil.XMLTestCase;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.*;
import javax.xml.ws.soap.AddressingFeature;
import javax.xml.ws.soap.SOAPFaultException;
import javax.xml.ws.wsaddressing.W3CEndpointReference;


/**
 * @author Arun Gupta
 *         Kathy walsh
 */
public class AddNumbersClient extends XMLTestCase {
    //may be used for verification
    private static final QName SERVICE_QNAME = new QName("http://example.com/", "AddNumbersService");
    private static final QName PORT_QNAME = new QName("http://example.com/", "AddNumbersPort");
    private static final String ENDPOINT_ADDRESS = "http://localhost:8080/jaxrpc-client_jaxws21_epr_get_port_na/hello";
    //maybe used for firther tests
    // private static final String CORRECT_ACTION = "http://example.com/AddNumbersPortType/addNumbersRequest";

    public AddNumbersClient(String name) {
        super(name);
    }

    private Dispatch<SOAPMessage> createDispatchWithWSDL() throws Exception {
        AddNumbersService service = new AddNumbersService();
        return service.createDispatch(PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE);
    }

    private AddNumbersPortType createStub() throws Exception {
        AddNumbersService service = new AddNumbersService();
        return service.getAddNumbersPort();
    }

    //wsdl has no adding extension specified
    public void testEPRGetPort() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }
        AddNumbersPortType proxy = createStub();
        //EndpointReference epr = ((BindingProvider) proxy).getEndpointReference();
        EndpointReference epr = ((BindingProvider) proxy).getEndpointReference(MemberSubmissionEndpointReference.class);
        AddNumbersPortType port = epr.getPort(AddNumbersPortType.class);

        assertTrue(port != null);

        System.out.println("Adding numbers 2 and 4");
        int result = port.addNumbers(2, 4);
        assert(result == 6);
        System.out.println("Addinion of 2 and 4 successful");
    }


    public void testEPRGetPortII() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }
        AddNumbersPortType proxy = createStub();
        EndpointReference epr = ((BindingProvider) proxy).getEndpointReference(MemberSubmissionEndpointReference.class);

        AddNumbersPortType port = epr.getPort(AddNumbersPortType.class);

        assertTrue(port != null);

        System.out.println("Adding numbers 2 and 4");
        int result = port.addNumbers(2, 4);
        assert(result == 6);
        System.out.println("Addinion of 2 and 4 successful");
    }



    public void testEPRGetPortIII() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }

        AddNumbersPortType proxy = createStub();
        //EndpointReference epr = ((BindingProvider) proxy).getEndpointReference();
        EndpointReference epr = ((BindingProvider) proxy).getEndpointReference(MemberSubmissionEndpointReference.class);
        RespectBindingFeature feature = new RespectBindingFeature(false);
        WebServiceFeature[] features = new WebServiceFeature[]{feature};
        AddNumbersPortType port = epr.getPort(AddNumbersPortType.class, features);

        assertTrue(port != null);

        System.out.println("Adding numbers 2 and 4");
        int result = port.addNumbers(2, 4);
        assert(result == 6);
        System.out.println("Addinion of 2 and 4 successful");
    }


    public void testEPRGetPortIV() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }

        AddNumbersPortType proxy = createStub();
        EndpointReference epr = ((BindingProvider) proxy).getEndpointReference(MemberSubmissionEndpointReference.class);

        //force addressing off
        AddressingFeature feature = new AddressingFeature(false, false);
        WebServiceFeature[] features = new WebServiceFeature[]{feature};


        AddNumbersPortType port = epr.getPort(AddNumbersPortType.class, features);
        assertTrue(port != null);

        //expectation is that port is not configured for addressing and the invocation will fail
        try {
            System.out.println("Adding numbers 2 and 4");
            int result = port.addNumbers(2, 4);
        } catch (Exception ex) {
            assertTrue(ex instanceof SOAPFaultException);
            System.out.println(((SOAPFaultException) ex).getFault().getFaultString());

        }
    }



    public void testEPRGetPortV() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }

        AddNumbersPortType proxy = createStub();
        //EndpointReference epr = ((BindingProvider) proxy).getEndpointReference(W3CEndpointReference.class);
        EndpointReference epr = ((BindingProvider) proxy).getEndpointReference(MemberSubmissionEndpointReference.class);
        //force addressing off
        AddressingFeature feature = new AddressingFeature(false, false);
        WebServiceFeature[] features = new WebServiceFeature[]{feature};


        AddNumbersPortType port = epr.getPort(AddNumbersPortType.class, features);
        assertTrue(port != null);

        //expectation is that port is not configured for addressing and the invocation will fail
        try {
            System.out.println("Adding numbers 2 and 4");
            int result = port.addNumbers(2, 4);
        } catch (Exception ex) {
            assertTrue(ex instanceof SOAPFaultException);
            System.out.println(((SOAPFaultException) ex).getFault().getFaultString());
        }

    }


    public void testEPRGetPortVI() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }

        AddNumbersPortType proxy = createStub();
        EndpointReference epr = ((BindingProvider) proxy).getEndpointReference(MemberSubmissionEndpointReference.class);

        RespectBindingFeature feature = new RespectBindingFeature(true);
        MemberSubmissionAddressingFeature addr = new MemberSubmissionAddressingFeature(false, false);
        WebServiceFeature[] features = new WebServiceFeature[]{feature, addr};


        AddNumbersPortType port = epr.getPort(AddNumbersPortType.class, features);
        assertTrue(port != null);

//expectation is that port is not configured for addressing and the invocation will fail
        try {
            System.out.println("Adding numbers 2 and 4");
            int result = port.addNumbers(2, 4);
        } catch (Exception ex) {
            assertTrue(ex instanceof SOAPFaultException);
            System.out.println(((SOAPFaultException) ex).getFault().getFaultString());
        }
    }


    //UsingAddressing wsdl:required=true
    //AddressingFeature Disabled expect Exception
    //Expect no valid port created, so exception thrown
    public void testEPRGetPortVII() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }

        AddNumbersPortType proxy = createStub();
        //EndpointReference epr = ((BindingProvider) proxy).getEndpointReference(W3CEndpointReference.class);
        EndpointReference epr = ((BindingProvider) proxy).getEndpointReference(MemberSubmissionEndpointReference.class);
        RespectBindingFeature feature = new RespectBindingFeature(true);
        AddressingFeature addr = new AddressingFeature(false, false);
        WebServiceFeature[] features = new WebServiceFeature[]{feature, addr};


        AddNumbersPortType port = epr.getPort(AddNumbersPortType.class, features);
        assertTrue(port != null);

//expectation is that port is not configured for addressing and the invocation will fail
        try {
            System.out.println("Adding numbers 2 and 4");
            int result = port.addNumbers(2, 4);
        } catch (Exception ex) {
            assertTrue(ex instanceof SOAPFaultException);
            System.out.println(((SOAPFaultException) ex).getFault().getFaultString());
        }

    }



    public void xxtestEPRGetPortVIII() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }

        AddNumbersPortType proxy = createStub();
        EndpointReference epr = ((BindingProvider) proxy).getEndpointReference(MemberSubmissionEndpointReference.class);

        RespectBindingFeature feature = new RespectBindingFeature(true);
        MemberSubmissionAddressingFeature addr = new MemberSubmissionAddressingFeature(true, false);
        WebServiceFeature[] features = new WebServiceFeature[]{feature, addr};


        AddNumbersPortType port = epr.getPort(AddNumbersPortType.class, features);
        assertTrue(port != null);


        System.out.println("Adding numbers 2 and 4");
        int result = port.addNumbers(2, 4);
        assert(result == 6);
        System.out.println("Addinion of 2 and 4 successful");


    }



    public void testEPRGetPortVIIII() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }

        AddNumbersPortType proxy = createStub();
        //EndpointReference epr = ((BindingProvider) proxy).getEndpointReference(W3CEndpointReference.class);
        EndpointReference epr = ((BindingProvider) proxy).getEndpointReference(MemberSubmissionEndpointReference.class);
        RespectBindingFeature feature = new RespectBindingFeature(true);
        AddressingFeature addr = new AddressingFeature(true, false);
        WebServiceFeature[] features = new WebServiceFeature[]{feature, addr};


        AddNumbersPortType port = epr.getPort(AddNumbersPortType.class, features);
        assertTrue(port != null);

        //expectation is that port is not configured for addressing and the invocation will fail
        try {
            System.out.println("Adding numbers 2 and 4");
            int result = port.addNumbers(2, 4);
        } catch (Exception ex) {
            assertTrue(ex instanceof SOAPFaultException);
            System.out.println(((SOAPFaultException) ex).getFault().getFaultString());
        }

    }


    public void testEPRGetPortVIIIII() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }

        AddNumbersPortType proxy = createStub();
        EndpointReference epr = ((BindingProvider) proxy).getEndpointReference(MemberSubmissionEndpointReference.class);

        RespectBindingFeature feature = new RespectBindingFeature(true);
        MemberSubmissionAddressingFeature addr = new MemberSubmissionAddressingFeature(true, true);
        WebServiceFeature[] features = new WebServiceFeature[]{feature, addr};


        AddNumbersPortType port = epr.getPort(AddNumbersPortType.class, features);
        assertTrue(port != null);

        System.out.println("Adding numbers 2 and 4");
        int result = port.addNumbers(2, 4);
        assert(result == 6);
        System.out.println("Addinion of 2 and 4 successful");

    }



    public void testEPRGetPortVIIIIII() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }

        AddNumbersPortType proxy = createStub();
        //EndpointReference epr = ((BindingProvider) proxy).getEndpointReference(W3CEndpointReference.class);
        EndpointReference epr = ((BindingProvider) proxy).getEndpointReference(MemberSubmissionEndpointReference.class);
        RespectBindingFeature feature = new RespectBindingFeature(true);
        AddressingFeature addr = new AddressingFeature(true, true);
        WebServiceFeature[] features = new WebServiceFeature[]{feature, addr};

        AddNumbersPortType port = epr.getPort(AddNumbersPortType.class, features);
        assertTrue(port != null);

        //expectation is that port is not configured for addressing and the invocation will fail

        System.out.println("Adding numbers 2 and 4");
        int result = port.addNumbers(2, 4);
        assert(result == 6);
        System.out.println("Addinion of 2 and 4 successful");

    }


    public void testDispatchEPRGetPort() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }
        Dispatch<SOAPMessage> dispatch = createDispatchWithWSDL();
        EndpointReference epr = dispatch.getEndpointReference(MemberSubmissionEndpointReference.class);

        //wsdl has addressing required
        AddNumbersPortType port = epr.getPort(AddNumbersPortType.class);

        assertTrue(port != null);

        System.out.println("Adding numbers 2 and 4");
        int result = port.addNumbers(2, 4);
        assert(result == 6);
        System.out.println("Addinion of 2 and 4 successful");
    }
}
