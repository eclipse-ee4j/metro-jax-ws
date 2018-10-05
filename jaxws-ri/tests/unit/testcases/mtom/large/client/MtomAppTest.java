/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package mtom.large.client;

import com.sun.xml.ws.developer.JAXWSProperties;

import junit.framework.TestCase;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.ws.soap.MTOMFeature;
import javax.xml.transform.stream.StreamSource;
import javax.activation.DataHandler;
import javax.activation.DataSource;
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

        Holder<Integer> total = new Holder<Integer>(123456000);
        Holder<String> name = new Holder<String>("huge");
        Holder<DataHandler> dh = new Holder<DataHandler>(getDataHandler(total.value));
        port.upload(total, name, dh);
        if (!"hugehuge".equals(name.value)) {
           fail("FAIL: Expecting: hugehuge Got: "+name.value);
        }
        System.out.println("SUCCESS: Got: "+name.value);
        System.out.println("Going to verify DataHandler. This would take some time");
        validateDataHandler(total.value, dh.value);
        System.out.println("SUCCESS: DataHandler is verified");
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

    private void validateDataHandler(int expTotal, DataHandler dh)
		throws IOException {

        // readOnce() doesn't store attachment on the disk in some cases
        // for e.g when only one attachment is in the message
        StreamingDataHandler sdh = (StreamingDataHandler)dh;
        InputStream in = sdh.readOnce();
        int ch;
        int total = 0;
        while((ch=in.read()) != -1) {
            if (total++%256 != ch) {
                fail("FAIL: DataHandler data is different");
            }
        }
        in.close();
        sdh.close();
    }

}
