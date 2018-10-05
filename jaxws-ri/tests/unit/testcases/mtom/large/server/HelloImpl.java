/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package mtom.large.server;

import javax.jws.WebService;
import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.MTOM;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import java.awt.Image;
import java.io.*;

import com.sun.xml.ws.developer.StreamingDataHandler;

/**
 * @author Jitendra Kotamraju
 */
@MTOM
@WebService (endpointInterface = "mtom.large.server.Hello")
public class HelloImpl implements Hello {

    public void upload(Holder<Integer> total, Holder<String> name, Holder<DataHandler> dh) {
        System.out.println("Got name="+name.value);
        name.value = "hugehuge";
        try {
    	    validateDataHandler(total.value, dh.value);
        } catch(IOException ioe) {
            throw new WebServiceException(ioe);
        }
        System.out.println("All the data received correctly");
        total.value = (int)total.value + 1;
        dh.value = getDataHandler(total.value);
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

    private void validateDataHandler(int expTotal, DataHandler dh) throws IOException {
        // readOnce() doesn't store attachment on the disk in some cases
        // for e.g when only one attachment is in the message
        StreamingDataHandler sdh = (StreamingDataHandler)dh;
        InputStream in = sdh.readOnce();
        int ch;
        int total = 0;
        while((ch=in.read()) != -1) {
            if (total++%256 != ch) {
                System.out.println("FAIL: DataHandler data is different");
            }
        }
        in.close();
        sdh.close();
        if (total != expTotal) {
           throw new WebServiceException("DataHandler data size is different");
        }
    }
}
