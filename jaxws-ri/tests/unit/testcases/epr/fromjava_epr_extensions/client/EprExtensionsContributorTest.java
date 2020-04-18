/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package epr.fromjava_epr_extensions.client;

import com.sun.xml.ws.developer.WSBindingProvider;
import com.sun.xml.ws.api.addressing.WSEndpointReference;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import junit.framework.TestCase;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.EndpointReference;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.wsaddressing.W3CEndpointReference;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Tests pluggability of EndpointReferenceExtensionContributor and runtime access of EPR extensions inside EPR specified
 *  under wsdl:port on server and client
 *
 * @author Rama.Pulavarthi@sun.com
 */
public class EprExtensionsContributorTest extends TestCase {
    public EprExtensionsContributorTest(String name) {
        super(name);
        Hello hello = new HelloService().getHelloPort();
        endpointAddress = (String) ((BindingProvider) hello).getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
    }

    private String endpointAddress = "http://helloservice.org/Hello";
    private static final QName serviceName = new QName("http://helloservice.org/wsdl", "HelloService");
    private static final QName portName = new QName("http://helloservice.org/wsdl", "HelloPort");
    private static final QName portTypeName = new QName("http://helloservice.org/wsdl", "Hello");

    /**
     * Tests client-side access to EPR extensions specified in WSDL
     *
     * @throws Exception
     */
    public void testEprWithDispatchWithoutWSDL() throws Exception {
        Service service = Service.create(serviceName);
        service.addPort(portName, jakarta.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING, endpointAddress);
        Dispatch dispatch = service.createDispatch(portName, Source.class, Service.Mode.PAYLOAD);
        WSEndpointReference wsepr = ((WSBindingProvider) dispatch).getWSEndpointReference();
        assertTrue(wsepr.getEPRExtensions().isEmpty());

    }

    /**
     * Tests client-side access to EPR extensions specified in WSDL
     *
     * @throws Exception
     */
    public void testEprWithDispatchWithWSDL() throws Exception {
        Service service = new HelloService();
        Dispatch dispatch = service.createDispatch(portName, Source.class, Service.Mode.PAYLOAD);
        WSEndpointReference wsepr = ((WSBindingProvider) dispatch).getWSEndpointReference();
        assertTrue(wsepr.getEPRExtensions().size() == 1);
        WSEndpointReference.EPRExtension idExtn = wsepr.getEPRExtension(new QName("http://example.com/addressingidentity", "Identity"));
        assertTrue(idExtn != null && idExtn.getQName().equals(new QName("http://example.com/addressingidentity", "Identity")));


    }

    /**
     * Tests client-side access to EPR extensions specified in WSDL
     *
     * @throws Exception
     */
    public void testEprWithSEI() throws Exception {
        HelloService service = new HelloService();
        Hello hello = service.getHelloPort();
        WSEndpointReference wsepr = ((WSBindingProvider) hello).getWSEndpointReference();

        assertTrue(wsepr.getEPRExtensions().size() == 1);
        WSEndpointReference.EPRExtension idExtn = wsepr.getEPRExtension(new QName("http://example.com/addressingidentity", "Identity"));
        assertTrue(idExtn != null && idExtn.getQName().equals(new QName("http://example.com/addressingidentity", "Identity")));

    }

    /**
     * Tests server-side access to EPR extensions specified in WSDL
     *
     * @throws Exception
     */

    public void testEprOnServerSide() throws Exception {
        HelloService service = new HelloService();
        Hello hello = service.getHelloPort();
        W3CEndpointReference serverEpr = hello.getW3CEPR();
        //  printEPR(serverEpr);

        WSEndpointReference wsepr = new WSEndpointReference(serverEpr);
        assertTrue(wsepr.getEPRExtensions().size() == 1);
        WSEndpointReference.EPRExtension idExtn = wsepr.getEPRExtension(new QName("http://example.com/addressingidentity", "Identity"));
        assertTrue(idExtn != null && idExtn.getQName().equals(new QName("http://example.com/addressingidentity", "Identity")));
    }

    /**
     * Tests the published wsdl for EPR extensions
     * @throws Exception
     */

    public void testEprInPublishedWSDL() throws Exception {
        HelloService service = new HelloService();
        Hello hello = service.getHelloPort();
        WSDLPort wsdlModel =((WSBindingProvider) hello).getPortInfo().getPort();
        WSEndpointReference wsdlepr = wsdlModel.getExtension(WSEndpointReference.class);
        assertTrue(wsdlepr.getEPRExtensions().size() == 1);
        WSEndpointReference.EPRExtension idExtn = wsdlepr.getEPRExtension(new QName("http://example.com/addressingidentity", "Identity"));
        assertTrue(idExtn != null && idExtn.getQName().equals(new QName("http://example.com/addressingidentity", "Identity")));
    }

    private static void printEPR(EndpointReference epr) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        StreamResult sr = new StreamResult(bos);
        epr.writeTo(sr);
        bos.flush();
        System.out.println(bos);
    }


}
