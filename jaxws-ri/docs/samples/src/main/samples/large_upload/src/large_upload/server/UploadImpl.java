/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package large_upload.server;

import java.io.*;
import javax.jws.WebService;
import javax.activation.*;
import javax.jws.*;
import javax.xml.bind.annotation.*;
import javax.xml.ws.soap.*;
import javax.xml.ws.*;

import com.sun.xml.ws.developer.StreamingDataHandler;

/**
 * @author Jitendra Kotamraju
 */

@MTOM
@WebService
public class UploadImpl {
    
    public void fileUpload(String name, @XmlMimeType("application/octet-stream") DataHandler data) {
        try {
             StreamingDataHandler dh = (StreamingDataHandler)data;
             File file = File.createTempFile(name, "");
             System.out.println("Creating file = "+file);
             dh.moveTo(file);
             dh.close();
             System.out.println("Verifying file = "+file);
             verifyFile(file);
             System.out.println("Verified file = "+file);
             file.delete();
             System.out.println("Deleted file = "+file);
        } catch(Exception e) {
            throw new WebServiceException(e);
        }
    }

    private void verifyFile(File file) throws IOException {
        FileInputStream fin = new FileInputStream(file);
        try {
             byte buf[] = new byte[8192];
             
             for(int i=0; i < 100000; i++) {
                 int len = 0;
                 while(len < buf.length) {
                     int cur = fin.read(buf, len, buf.length-len);
                     if (cur == -1) {
                         throw new WebServiceException("EOF. Didn't receive all the file");
                     }
                     len += cur;
                 }
                 for(int j=0; j < len; j++) {
                     if (buf[j] != (byte)j) {
                         throw new WebServiceException();
                     }
                 }
             }
        } finally {
            fin.close();
        }
    }

}
