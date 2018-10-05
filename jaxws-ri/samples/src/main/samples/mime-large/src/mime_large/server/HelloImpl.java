/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package mime_large.server;

import javax.jws.WebService;
import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceException;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import java.io.*;

import com.sun.xml.ws.developer.StreamingDataHandler;

/**
 * @author Jitendra Kotamraju
 */

@WebService (endpointInterface = "mime_large.server.Hello")
public class HelloImpl {

    public ClaimFormTypeResponse claimForm(ClaimFormTypeRequest data) {
        try {
    	    validateDataHandler(data.getTotal(), data.getRequest());
        } catch(IOException ioe) {
            throw new WebServiceException(ioe);
        }

        ClaimFormTypeResponse resp = new ClaimFormTypeResponse();
        resp.setTotal(data.getTotal()+1);
        resp.setResponse(getDataHandler(data.getTotal()+1));
        return resp;
    }

    private DataHandler getDataHandler(final int total)  {
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

    private void validateDataHandler(int expTotal, DataHandler dh) throws IOException {
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
                    throw new WebServiceException("DataHandler data is different");
                }
            }
            total += len;
            if (total%(8192*250) == 0) {
            	System.out.println("Total so far="+total);
            }
        }
        in.close();
        sdh.close();
        if (total != expTotal) {
           throw new WebServiceException("DataHandler data size is different");
        }
    }
}
