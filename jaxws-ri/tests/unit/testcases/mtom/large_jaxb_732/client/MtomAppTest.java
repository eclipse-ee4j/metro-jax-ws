/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package mtom.large_jaxb_732.client;

import junit.framework.TestCase;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.soap.MTOMFeature;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Closeable;
import java.util.Map;

/**
 * @author Jitendra Kotamraju
 */
public class MtomAppTest extends TestCase {

    public void testUpload() throws Exception {
        int size = 123*1000 *1000;
        Hello port = new HelloService().getHelloPort(new MTOMFeature());
        Map<String, Object> ctxt = ((BindingProvider)port).getRequestContext();
        // At present, JDK internal property - not supported
        ctxt.put("com.sun.xml.internal.ws.transport.http.client.streaming.chunk.size", 8192); 
        // Add this one to run with standalone RI bits
        ctxt.put("com.sun.xml.ws.transport.http.client.streaming.chunk.size", 8192); 

        Holder<Integer> total = new Holder<Integer>(size);
        Holder<String> name = new Holder<String>("huge");
        Holder<DataHandler> dh = new Holder<DataHandler>(getDataHandler(total.value));
        port.upload(total, name, dh);
        if (!"hugehuge".equals(name.value)) {
           fail("FAIL: Expecting: hugehuge Got: "+name.value);
        }
        if (!total.value.equals(size+1)) {
           fail("FAIL: Expecting size: "+(size+1)+" Got: "+total.value);
        }
        System.out.println("SUCCESS: Got: "+name.value);
        System.out.println("Going to verify DataHandler. This would take some time");
        validateDataHandler(total.value, dh.value);
        System.out.println("SUCCESS: DataHandler is verified");
        if (dh.value instanceof Closeable) {
            System.out.println("Client:Received DH is closeable");
            ((Closeable)dh.value).close();
        }
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
        InputStream in = dh.getInputStream();
        int ch;
        int total = 0;
        while((ch=in.read()) != -1) {
            if (total++%256 != ch) {
                fail("Client:FAIL: DataHandler data is different");
            }
            if (total%(10*1000*1000) == 0) {
                System.out.println("Client: Received="+total); 
            }
        }
        in.close();
        if (total != expTotal) {
            fail("Client:FAIL: DataHandler data is different. Expecting "+expTotal+" but got "+total+" bytes");
        }
    }

}
