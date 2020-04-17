/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.swaref_large.client;

import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Holder;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.Source;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import java.io.*;
import java.util.*;
import junit.framework.*;

import com.sun.xml.ws.developer.*;

/**
 * Tests {@link StreamingAttachmentFeature}, {@link StreamingDataHandler}
 *
 * @author Jitendra Kotamraju
 */
public class SwarefTest extends TestCase {

    public SwarefTest(String name) throws Exception {
        super(name);
    }

    public void testSwaref () throws Exception {

        StreamingAttachmentFeature saf = new StreamingAttachmentFeature(null, true, 4000000);

        Hello port = new HelloService().getHelloPort (saf);
        Map<String, Object> ctxt = ((BindingProvider)port).getRequestContext();
        ctxt.put(JAXWSProperties.HTTP_CLIENT_STREAMING_CHUNK_SIZE, 8192);

        int total = 120000000;
        ClaimFormTypeRequest req = new ClaimFormTypeRequest();
        req.setRequest(getDataHandler(total));
        req.setTotal(total);

        ClaimFormTypeResponse resp = port.claimForm (req);
        validateDataHandler(resp.getTotal(), resp.getResponse());
    }

    private static DataHandler getDataHandler(final int total)  {
        return new DataHandler(new DataSource() {
            public InputStream getInputStream() throws IOException {
                return new InputStream() {
                    int i;

                    @Override
                    public int read() throws IOException {
                        return i<total ? 'A'+(i++%26) : -1;
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

    private static void validateDataHandler(int expTotal, DataHandler dh)
		throws IOException {

        // readOnce() doesn't store attachment on the disk in some cases
        // for e.g when only one attachment is in the message
        StreamingDataHandler sdh = (StreamingDataHandler)dh;
        InputStream in = sdh.readOnce();
        byte[] buf = new byte[8192];
        int total = 0;
        int len;
        while((len=in.read(buf, 0, buf.length)) != -1) {
            for(int i=0; i < len; i++) {
                if ((byte)('A'+(total+i)%26) != buf[i]) {
                    System.out.println("FAIL: DataHandler data is different");
                }
            }
            total += len;
            if (total%(8192*250) == 0) {
            	System.out.println("Total so far="+total);
            }
        }
        System.out.println();
        if (total != expTotal) {
           System.out.println("FAIL: DataHandler data size is different. Expected="+expTotal+" Got="+total);
        }
        in.close();
        sdh.close();
    }

}
