/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.endpoint.client;

import java.io.StringReader;
import java.net.URL;
 
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Endpoint;
import jakarta.xml.ws.Service;

import javax.xml.stream.*;
import java.io.*;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.Style;

import junit.framework.TestCase;

/**
 * @author Jitendra Kotamraju
 */

public class SourceTester extends TestCase
{
    private static final String NS = "http://echo.org/";

    public void testSource() throws Exception {
        int port = Util.getFreePort();
        String address = "http://localhost:"+port+"/source";
        Endpoint endpoint = Endpoint.create(new SourceEndpoint());
        endpoint.publish(address);

        Source response = invoke(address);

        ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.transform(response, new StreamResult(bos));
        bos.close();

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(
            new ByteArrayInputStream(bos.toByteArray()));
        while(reader.hasNext()) {
            reader.next();
        }
        
        endpoint.stop();
    }

    private Source invoke(String address) throws Exception {
        QName portName = new QName(NS, "RpcLitPort");
        QName serviceName = new QName(NS, "RpcLitEndpoint");
        Service service = Service.create(new URL(address+"?wsdl"), serviceName);
        Dispatch<Source> d = service.createDispatch(portName, Source.class,
            Service.Mode.PAYLOAD);
        String body  = "<ns0:echoInteger xmlns:ns0=\"http://echo.abstract.org/\"><arg0>2</arg0></ns0:echoInteger>";
        return d.invoke(new StreamSource(new StringReader(body)));
    }
   
    @WebService(name="RpcLit", serviceName="RpcLitEndpoint",
        portName="RpcLitPort", targetNamespace="http://echo.org/",
        endpointInterface="whitebox.endpoint.client.RpcLitEndpointIF")
    @SOAPBinding(style=Style.RPC)
    public static class SourceEndpoint {
        public int echoInteger(int arg0) {
            return arg0;
        }
    }
}
