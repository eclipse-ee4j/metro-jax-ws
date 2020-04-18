/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package mtom.large_jaxb_732.server;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.jws.WebService;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.soap.MTOM;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Closeable;

/**
 * @author Jitendra Kotamraju
 */
@MTOM
@WebService (endpointInterface = "mtom.large_jaxb_732.server.Hello")
public class HelloImpl implements Hello {

    public void upload(Holder<Integer> total, Holder<String> name, Holder<DataHandler> dh) {
        System.out.println("Server:Got name="+name.value);
        name.value = "hugehuge";
        try {
    	    validateDataHandler(total.value, dh.value);
        } catch(IOException ioe) {
            throw new WebServiceException(ioe);
        }
        System.out.println("Server:All the data received correctly");
        if (dh.value instanceof Closeable) {
            System.out.println("Server:Received DH is closeable");
            try {
                ((Closeable)dh.value).close();
        	} catch(IOException ioe) {
            	throw new WebServiceException(ioe);
        	}
        }
        total.value = total.value + 1;
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
        InputStream in = dh.getInputStream();
        int ch;
        int total = 0;
        while((ch=in.read()) != -1) {
            if (total++%256 != ch) {
                System.out.println("Server:FAIL: DataHandler data is different");
            }
            if (total%(10*1000*1000) == 0) {
                System.out.println("Server: Received="+total); 
            }
        }
        in.close();
        if (total != expTotal) {
           throw new WebServiceException("Server:DataHandler data size is different. Expecting "+expTotal+" but got "+total+" bytes");
        }
    }
}
