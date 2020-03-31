/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.wsit1533.client;

import com.sun.xml.ws.developer.JAXWSProperties;
import com.sun.xml.ws.developer.StreamingDataHandler;
import com.sun.xml.ws.encoding.DataSourceStreamingDataHandler;
import junit.framework.TestCase;

import jakarta.activation.DataSource;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.soap.MTOMFeature;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Testcase created from issue WSIT-1533; Issue already fixed at the time of testing
 *
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
public class UploadTest extends TestCase {

     private UploadServicePortType upload;

     public void setUp() throws Exception {
          upload = new UploadService().getUpload(new MTOMFeature());
          Map<String, Object> ctxt = ((BindingProvider) upload).getRequestContext();
          // Enable HTTP chunking mode, otherwise HttpURLConnection buffers
          ctxt.put(JAXWSProperties.HTTP_CLIENT_STREAMING_CHUNK_SIZE, 8192);
     }

     public void doBigUpload() throws Exception {

         StringBuilder sb = new StringBuilder();
         for(int i = 0; i < 100000; i++) {
             sb.append("some HUGE data ");
         }

         doUpload(sb.toString());
     }

    private void doUpload(String singleLineMsg) {
        StreamingDataHandler data = wrapStringData(singleLineMsg);
        final String line = upload.uploadDataTest(data);
        assertEquals(line, singleLineMsg);
    }

    public void testUploads() throws Exception {

         //this should be under the MTOM-treshold of 512
        doUpload("Small");

        doBigUpload();

        doUpload("Small");
        doUpload("Small");

        doBigUpload();
    }

     private StreamingDataHandler wrapStringData(final String msg) {

          return new DataSourceStreamingDataHandler(
                  new DataSource() {

                       @Override
                       public OutputStream getOutputStream() throws IOException {
                            throw new IOException("no outputstream provided");
                       }

                       @Override
                       public String getName() {
                            return "testdata";
                       }

                       @Override
                       public InputStream getInputStream() throws IOException {
                            return new ByteArrayInputStream(msg.getBytes());
                       }

                       @Override
                       public String getContentType() {
                            return "text/plain; charset=utf-8";
                       }
                  }
          );
     }
}
