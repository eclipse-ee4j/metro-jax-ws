/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.endpoint_features.client;

import junit.framework.TestCase;

import jakarta.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import jakarta.xml.ws.Endpoint;
import jakarta.xml.ws.soap.AddressingFeature;
import jakarta.xml.ws.soap.SOAPBinding;
import java.io.InputStream;
import java.net.URL;
import testutil.PortAllocator;


/**
 * Tests Endpoint API with web service features
 *
 * @author Jitendra Kotamraju
 */
public class EndpointFeaturesTest extends TestCase {

    private static final QName ANONYMOUS =
            new QName("http://www.w3.org/2007/05/addressing/metadata", "AnonymousResponses");

    // Tests Endpoint.create(impl, features)
    public void testCreateImplFeatures() throws Exception {
        int port = PortAllocator.getFreePort();
        String address = "http://localhost:" + port + "/hello";
        Endpoint endpoint = Endpoint.create(new FeaturesEndpoint(),
                new AddressingFeature(true, true, AddressingFeature.Responses.ANONYMOUS));
        publishVerifyStop(address, endpoint);
    }

    // Tests Endpoint.publish(address, impl, features)
    public void testPublish() throws Exception {
        int port = PortAllocator.getFreePort();
        String address = "http://localhost:" + port + "/hello";
        Endpoint endpoint = Endpoint.publish(address, new FeaturesEndpoint(),
                new AddressingFeature(true, true, AddressingFeature.Responses.ANONYMOUS));
        assertTrue(endpoint.isPublished());
        publishVerifyStop(address, endpoint);
    }

    // Tests Endpoint.create(bindingId, impl, features)
    public void testCreateBindingImplFeatures() throws Exception {
        int port = PortAllocator.getFreePort();
        String address = "http://localhost:" + port + "/hello";
        Endpoint endpoint = Endpoint.create(SOAPBinding.SOAP11HTTP_BINDING, new FeaturesEndpoint(),
                new AddressingFeature(true, true, AddressingFeature.Responses.ANONYMOUS));
        publishVerifyStop(address, endpoint);
    }

    // Publishes the endpoint, if it is not published.
    // Checks if the generated WSDL contains wsam:AnonymousResponses policy
    // Stops the endpoint
    private void publishVerifyStop(String address, Endpoint endpoint) throws Exception {
        if (!endpoint.isPublished()) {
            endpoint.publish(address);
        }

        URL pubUrl = new URL(address + "?wsdl");
        boolean anon = isAnonymousResponses(pubUrl);
        assertTrue(anon);

        endpoint.stop();
    }


    // Does the generated WSDL have wsam:AnonymousResponses policy ?
    private boolean isAnonymousResponses(URL url) throws Exception {
        InputStream in = url.openStream();
        try {
            XMLInputFactory xif = XMLInputFactory.newInstance();
            XMLStreamReader rdr = xif.createXMLStreamReader(in);
            while(rdr.hasNext()) {
                rdr.next();
                if (rdr.getEventType() == XMLStreamReader.START_ELEMENT) {
                    QName name = rdr.getName();
                    if (name.equals(ANONYMOUS)) {
                        return true;
                    }
                }
            }
            rdr.close();
        } finally {
            in.close();
        }
        return false;
    }


    // DO NOT set @Addressing since is enabled via Endpoint.create()
    @WebService
    public static final class FeaturesEndpoint {
        public String echo(String name) {
            return name;
        }
    }
}
