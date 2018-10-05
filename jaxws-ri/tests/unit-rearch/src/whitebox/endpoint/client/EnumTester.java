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
 
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;

import javax.xml.stream.*;
import java.io.*;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import junit.framework.TestCase;

/**
 * @author Jitendra Kotamraju
 */

public class EnumTester extends TestCase
{
    private static final String NS = "http://echo.org/";

    public void testEnum() throws Exception {
        int port = Util.getFreePort();
        String address = "http://localhost:"+port+"/enum";
        Endpoint endpoint = Endpoint.create(new EnumEndpoint());
        endpoint.publish(address);

        String response = invoke(address, 2);
        assertTrue(response.contains("Blue"));

        response = invoke(address, 5);
        assertTrue(response.contains("Red"));
        
        endpoint.stop();
    }

    private String invoke(String address, int no) throws Exception {
        QName portName = new QName(NS, "EnumPort");
        QName serviceName = new QName(NS, "EnumService");
        Service service = Service.create(new URL(address+"?wsdl"), serviceName);
        Dispatch<Source> d = service.createDispatch(portName, Source.class,
            Service.Mode.PAYLOAD);
        String body  = "<ns0:get xmlns:ns0='http://echo.org/'>"+no+"</ns0:get>";
        Source response = d.invoke(new StreamSource(new StringReader(body)));

        ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.transform(response, new StreamResult(bos));
        bos.close();

        return new String(bos.toByteArray());
    }
   
    @WebService(serviceName="EnumService", portName="EnumPort",
        targetNamespace=NS)
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public static class EnumEndpoint {
	public enum MyEnum { Red, Blue };

        public MyEnum get(int i) {
            return i%2 == 0 ? MyEnum.Blue : MyEnum.Red;
        }

    }
}
