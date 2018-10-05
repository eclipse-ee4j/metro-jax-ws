/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.endpoint.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import testutil.ClientServerTestUtil;

import javax.xml.ws.Endpoint;
import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.Provider;
import javax.activation.DataSource;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.Service;
import javax.xml.ws.Service.Mode;
import javax.xml.ws.BindingType;
import java.util.List;
import java.util.ArrayList;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.net.URL;
import javax.xml.stream.*;

/**
 * Tests xs:redefine schemaLocation patching
 *
 * @author Jitendra Kotamraju
 */
public class SchemaRedefineTester extends TestCase {

    public void testSchemaRedefine() throws Exception {
        int port = Util.getFreePort();
port=1666;
        String address = "http://localhost:"+port+"/redefine";
        Endpoint e = Endpoint.create(new RedefineProvider());

        List<Source> metadata = new ArrayList<Source>();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String[] docs = {
                "WEB-INF/wsdl/SchemaRedefine.wsdl",
                "WEB-INF/wsdl/SchemaRedefine.xsd"
        };
        for (String doc : docs) {
            URL url = cl.getResource(doc);
            metadata.add(new StreamSource(url.openStream(), url.toExternalForm()));
        }
        e.setMetadata(metadata);

        e.publish(address);

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(
            new URL(address+"?wsdl").openStream());
        while(reader.hasNext()) {
            reader.next();
            if (reader.getEventType() == XMLStreamConstants.START_ELEMENT &&
                reader.getLocalName().equals("redefine")) {
                String loc = reader.getAttributeValue(null, "schemaLocation");
                assertEquals(address+"?xsd=1", loc);
            }
        }

        e.stop();
    }


    @WebServiceProvider(serviceName="SchemaRedefineService", portName="SchemaRedefinePort", targetNamespace="http://SchemaRedefine/")
    public static class RedefineProvider implements Provider<Source> {
        public Source invoke(Source ds) {
            return null;
        }
    }

}

