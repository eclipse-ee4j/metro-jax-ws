/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package mtom.large_oneway.client;

import com.sun.xml.ws.developer.JAXWSProperties;

import junit.framework.TestCase;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.soap.SOAPBinding;
import jakarta.xml.ws.soap.MTOMFeature;
import javax.xml.transform.stream.StreamSource;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.awt.*;
import java.util.Arrays;
import java.io.*;
import java.util.*;

import com.sun.xml.ws.developer.JAXWSProperties;
import com.sun.xml.ws.developer.StreamingDataHandler;

/**
 * @author Jitendra Kotamraju
 */
public class MtomAppTest extends TestCase {

    public void testUpload() throws Exception {
        Hello port = new HelloService().getHelloPort(new MTOMFeature());
        Map<String, Object> ctxt = ((BindingProvider)port).getRequestContext();
        ctxt.put(JAXWSProperties.HTTP_CLIENT_STREAMING_CHUNK_SIZE, 8192); 

        int total = 123456;
        String name = "huge_oneway";
        DataHandler dh = getDataHandler(total);
        port.upload(total, name, dh);
//        Thread.sleep(2000);
//        assertTrue(port.verify(new VerifyType()));
    }

    private DataHandler getDataHandler(final int total)  {
        return new DataHandler(new DataSource() {
            public InputStream getInputStream() throws IOException {
                return new InputStream() {
                    int i;

                    @Override
                    public int read() throws IOException {
                        return i<total ? i++%256 : -1;
                    }
                };
            }

            public OutputStream getOutputStream() throws IOException {
                return null;
            }

            public String getContentType() {
                return "application/octet-stream";
            }

            public String getName() {
                return "";
            }
        });
    }
}
