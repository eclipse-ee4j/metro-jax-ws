/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.response_context.client;

import junit.framework.TestCase;
import testutil.ClientServerTestUtil;

import javax.xml.namespace.QName;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.handler.MessageContext;
import java.util.Map;
import java.util.Set;


/**
 * @author JAX-RPC RI Development Team
 */
public class HelloLiteralTest extends TestCase {

    private QName serviceQName = new QName("urn:test", "Hello");
    private QName portQName = new QName("urn:test", "HelloPort");
    private String bindingIdString = "http://schemas.xmlsoap.org/wsdl/soap/http";
    private String endpointWSDL = "http://localhost:8080/jaxrpc-client_response_context/hello?WSDL";
    private String endpointAddress = "http://localhost:8080/jaxrpc-client_response_context/heh";  //bogus endpointAddress


    private Hello_Service service;

    public HelloLiteralTest(String name) {
        super(name);
    }


    private void createService() {

        try {

            service = new Hello_Service();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Hello createStub() {
        return (Hello) ((Hello_Service) service).getHelloPort();
    }

    private Hello getStub() throws Exception {
        createService();
        return createStub();
    }

    public void testHelloResponseContext() throws Exception {
        if (ClientServerTestUtil.useLocal()) {
            System.out.println("Need to run only in http transport");
            return;
        }
        doTestHelloErrorCode();
        doTestResponseContext();
    }

    private void doTestHelloErrorCode() {
        Hello stub = null;
        try {
            stub = getStub();
            String arg = "foo";
            String extra = "bar";
            Hello_Type req = new Hello_Type();
            req.setArgument(arg);
            req.setExtra(extra);

            ((BindingProvider) stub).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);

            HelloResponse response = stub.hello(req);
            assertTrue(response == null);
        } catch (Exception e) {
            //e.printStackTrace();
            Map<String, Object> rc = ((BindingProvider) stub).getResponseContext();
            assertTrue(rc != null);
            Integer status_code = (Integer) rc.get(MessageContext.HTTP_RESPONSE_CODE);
            assertTrue(status_code != null);
            assertTrue(404 == status_code.intValue());
            /*java.util.Map<java.lang.String, java.util.List<java.lang.String>> responseHeaders =
                    (java.util.Map<java.lang.String, java.util.List<java.lang.String>>) rc.get(MessageContext.HTTP_RESPONSE_HEADERS);
            assertTrue(responseHeaders != null);
            assertFalse(responseHeaders.isEmpty());
            */
           // Set<String> keys = responseHeaders.keySet();
            //for (String key : keys) {

            //    java.util.List<java.lang.String> values = responseHeaders.get(key);
             //   System.out.print("Key : " + key + "       ");
              //  System.out.println("Values : ");
              //  for (String value : values) {
              //      System.out.println(value);
             //   }
            //}
        }
    }

    private void doTestResponseContext() throws Exception {
        Hello stub = getStub();
        String arg = "foo";
        String extra = "bar";
        Hello_Type req = new Hello_Type();
        req.setArgument(arg);
        req.setExtra(extra);

        Map<String, Object> rc = ((BindingProvider) stub).getResponseContext();

        assertTrue(rc == null);
    }

}
