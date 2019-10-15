/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package asyncservice.server;

import com.sun.xml.ws.api.server.AsyncProvider;
import com.sun.xml.ws.api.server.AsyncProviderCallback;

import javax.xml.bind.JAXBContext;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceProvider;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@WebServiceProvider(
    wsdlLocation="WEB-INF/wsdl/hello_literal.wsdl",
    targetNamespace="urn:test",
    serviceName="Hello")

public class HelloAsyncImpl implements AsyncProvider<Source> {
    static String body  = "<HelloResponse xmlns=\"urn:test:types\"><argument xmlns=\"\">%s</argument><extra xmlns=\"\">%s</extra></HelloResponse>";
    private static final JAXBContext jaxbContext = createJAXBContext();
    private int bodyIndex;

    /*
    public javax.xml.bind.JAXBContext getJAXBContext(){
        return jaxbContext;
    } */

    private static javax.xml.bind.JAXBContext createJAXBContext(){
        try{
            return javax.xml.bind.JAXBContext.newInstance(ObjectFactory.class);
        }catch(javax.xml.bind.JAXBException e){
            throw new WebServiceException(e.getMessage(), e);
        }
    }

    private Hello_Type recvBean(Source source) throws Exception {
        System.out.println("**** recvBean ******");
        return (Hello_Type)jaxbContext.createUnmarshaller().unmarshal(source);
    }

    private Source sendSource(String arg, String extra) {
        System.out.println("**** sendSource ******");
        String response = String.format(body,arg,extra);
        return new StreamSource(
            new ByteArrayInputStream(response.getBytes()));
    }

    public void invoke(Source source, AsyncProviderCallback<Source> cbak, WebServiceContext ctxt) {
        System.out.println("**** Received in AsyncService Impl ******");
		try {
			Hello_Type hello = recvBean(source);
			String arg = hello.getArgument();
			new Thread(new RequestHandler(cbak, hello)).start();

		} catch(Exception e) {
            throw new WebServiceException("Endpoint failed", e);
		}
    }

	private class RequestHandler implements Runnable {
		final AsyncProviderCallback<Source> cbak;
		final Hello_Type hello;

		public RequestHandler(AsyncProviderCallback<Source> cbak, Hello_Type hello) {
			this.cbak = cbak;
			this.hello = hello;
		}

		public void run() {
			try {
				Thread.sleep(10000);
			} catch(InterruptedException ie) {
				cbak.sendError(new WebServiceException("Interrupted..."));
				return;
			}
			try {
				cbak.send(sendSource(hello.getArgument(),hello.getExtra()));
			} catch(Exception e) {
				cbak.sendError(new WebServiceException(e));
			}
		}
	}

}
