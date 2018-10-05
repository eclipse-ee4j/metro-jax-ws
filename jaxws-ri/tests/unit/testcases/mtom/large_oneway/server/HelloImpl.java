/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package mtom.large_oneway.server;

import javax.jws.WebService;
import javax.jws.*;
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
@WebService (endpointInterface = "mtom.large_oneway.server.Hello")
public class HelloImpl implements Hello {
    volatile boolean got;

    @Oneway
    public synchronized void upload(int total, String name, DataHandler dh) {
        System.out.println("Got name="+name);
        try {
    	    validateDataHandler(total, dh);
        } catch(IOException ioe) {
            throw new WebServiceException(ioe);
        }
        got = true;
        System.out.println("All the data received correctly");
    }

    @Oneway
    public synchronized boolean verify(VerifyType verifyType) {
        return got;
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
