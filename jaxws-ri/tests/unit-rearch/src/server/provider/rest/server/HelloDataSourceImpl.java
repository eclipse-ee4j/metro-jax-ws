/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.provider.rest.server;

import java.io.StringReader;
import jakarta.xml.ws.WebServiceContext;
import java.io.ByteArrayInputStream;
import jakarta.xml.ws.Provider;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.WebServiceProvider;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.ServiceMode;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.annotation.Resource;
import jakarta.xml.ws.http.HTTPException;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import jakarta.xml.ws.WebServiceException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import jakarta.activation.DataHandler;
import jakarta.jws.HandlerChain;
import jakarta.activation.DataSource;
import java.io.OutputStream;

@WebServiceProvider
@ServiceMode(value=Service.Mode.MESSAGE)
public class HelloDataSourceImpl implements Provider<DataSource> {
    private int bodyIndex = 0;
    private String[] body = {
        "<HelloResponse xmlns=\"urn:test:types\"><argument xmlns=\"\">foo</argument><extra xmlns=\"\">bar</extra></HelloResponse>",
        "<ans1:HelloResponse xmlns:ans1=\"urn:test:types\"><argument>foo</argument><extra>bar</extra></ans1:HelloResponse>"
    };

    @Resource(type=Object.class)
    protected WebServiceContext wsContext;

    private byte[] getSource() {
        int i = (++bodyIndex)%body.length;
        return body[i].getBytes();
    }

    public DataSource invoke(DataSource ds) {
        MessageContext mc = wsContext.getMessageContext();
        String method = (String)mc.get(MessageContext.HTTP_REQUEST_METHOD);
        if (method.equals("GET")) {
            return get(ds, mc);
		}
        HTTPException ex = new HTTPException(404);
        throw ex;
    }

    private DataSource get(DataSource source, MessageContext mc) {
        String query = (String)mc.get(MessageContext.QUERY_STRING);
        String path = (String)mc.get(MessageContext.PATH_INFO);
        System.out.println("Query String = "+query);
        System.out.println("PathInfo = "+path);
        if (path != null && path.equals("/java.jpg")) {
            return new DataSource() {
				public InputStream getInputStream() {
					return this.getClass().getClassLoader().getResourceAsStream(
						"java.jpg");
				}
				public OutputStream getOutputStream() {
					return null;
				}
				public String getContentType() {
					return "image/jpeg";
				}
				public String getName() {
					return "";
				}
			};
        } else {
            HTTPException ex = new HTTPException(404);
            throw ex;
        }
    }
}
