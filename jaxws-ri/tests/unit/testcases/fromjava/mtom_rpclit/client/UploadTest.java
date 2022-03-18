/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.mtom_rpclit.client;

import java.io.*;
import java.util.*;
import jakarta.xml.ws.*;
import jakarta.xml.ws.soap.*;
import jakarta.activation.*;

import com.sun.xml.ws.developer.JAXWSProperties;
import com.sun.xml.ws.developer.StreamingDataHandler;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Jitendra Kotamraju
 */
public class UploadTest extends TestCase {

    public void testUpload() throws IOException {
        MTOMFeature feature = new MTOMFeature();
        UploadImplService service = new UploadImplService();
        UploadImpl proxy = service.getUploadImplPort(feature);        
        Map<String, Object> ctxt = ((BindingProvider)proxy).getRequestContext();
        ctxt.put(JAXWSProperties.HTTP_CLIENT_STREAMING_CHUNK_SIZE, 8192); 
        for(int i=0; i < 1000; i++) {
            File file = getFile();
            proxy.fileUpload("file.bin", new DataHandler(new FileDataSource(file)));
            file.delete();
            System.out.println("Uploaded "+i+" times");
        }
    }

    public static File getFile() throws IOException {
        File file = File.createTempFile("jaxws", ".bin");
        OutputStream out = new FileOutputStream(file);
        byte buf[] = new byte[8192];
        for(int i=0; i < buf.length; i++) {
            buf[i] = (byte)i;
        }
        for(int i=0; i < 100000; i++) {
            out.write(buf);
        } 
        out.close();
        return file;
    }

}
