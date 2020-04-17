/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/*
 * $Id: DispatchTest.java,v 1.1 2009-03-26 01:53:46 jitu Exp $
 */

/*
 * Copyright 2004 Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package provider.no_content_type_657.client;

import junit.framework.TestCase;

import jakarta.activation.DataSource;
import javax.xml.namespace.QName;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.http.HTTPBinding;
import java.util.Map;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Jitendra Kotamraju
 */
public class DispatchTest extends TestCase {

    public DispatchTest(String name) throws Exception {
        super(name);
    }

    /*
     * Check for service's response code. It shouldn't be 202 since service
     * sets a http status code even for oneway
     */
    public void testNoContentType() throws Exception {
        BindingProvider bp = (BindingProvider)new Hello_Service().getHelloPort();
        String address = (String) bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);

        Service service = Service.create(new QName("", ""));
        service.addPort(new QName("",""), HTTPBinding.HTTP_BINDING, address);
        Dispatch<DataSource> d = service.createDispatch(new QName("",""), DataSource.class, Service.Mode.MESSAGE);

        // Set HTTP operation to PUT
        Map<String, Object> requestContext = d.getRequestContext();
        requestContext.put(MessageContext.HTTP_REQUEST_METHOD, "PUT");

        d.invoke(new DataSource() {

            public InputStream getInputStream() throws IOException {
                return null;
            }

            public OutputStream getOutputStream() throws IOException {
                return null;
            }

            public String getContentType() {
                return null;
            }

            public String getName() {
                return null;
            }
        });
    }

}
