/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.endpoint.client;

import com.sun.xml.ws.client.WSServiceDelegate;
import java.io.StringReader;
import java.io.ByteArrayOutputStream;
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

import com.sun.xml.ws.client.WSServiceDelegate;

import junit.framework.TestCase;

import testutil.PortAllocator;

/**
 * @author Jitendra Kotamraju
 */

public class SourceTest extends TestCase
{
    private static final String NS = "http://echo.org/";

    public void testSource() throws Exception {
        int port = PortAllocator.getFreePort();
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

    /**
     * This test ensures that when a valid Source object is provided to the JAX-WS client for
     * the WSDL, it gets used instead of going over the wire to read from the WSDL URL
     * (Associated change is in RuntimeWSDLParser.resolveWSDL)
     * 
     * The test first reads the WSDL into an in-memory Source object and uses that 
     * to construct the Service delegate (using WSServiceDelegate constructor since 
     * no standard API is available). It then creates the service with this Source and makes
     * sure that the InputStream underlying the Source has been consumed 
     * 
     * @throws Exception
     */
    public void testServiceCreateUsingInMemorySource() throws Exception {
    	int port = PortAllocator.getFreePort();
        String address = "http://localhost:"+port+"/source2";
        Endpoint endpoint = Endpoint.create(new SourceEndpoint());
        endpoint.publish(address);
        QName portName = new QName(NS, "RpcLitPort");
        QName serviceName = new QName(NS, "RpcLitEndpoint");
        StreamSource wsdlSource = readWsdlToInMemorySource(address + "?wsdl");
        WSServiceDelegate svcDel = new WSServiceDelegate(wsdlSource, serviceName, Service.class);
        byte[] b = new byte[512];
        int len = wsdlSource.getInputStream().read(b);
        assertTrue("In-memory WSDL Source passed to client must be consumed!", len < 0);
    }
    private StreamSource readWsdlToInMemorySource(String wsdlAddr) throws Exception {
		URL wsdlUrl = new URL(wsdlAddr);
		InputStream wsdlStream = wsdlUrl.openStream();
		byte[] b = new byte[512];
		int len = wsdlStream.read(b);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while (len >= 0) {
			bos.write(b, 0, len);
			len = wsdlStream.read(b);
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		StreamSource wsdlSource = new StreamSource(bis, wsdlAddr);
		return wsdlSource;
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
        endpointInterface="server.endpoint.client.RpcLitEndpointIF")
    @SOAPBinding(style=Style.RPC)
    public static class SourceEndpoint {
        public int echoInteger(int arg0) {
            return arg0;
        }
    }
}
