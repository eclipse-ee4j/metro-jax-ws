/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package mtom_soap12.server;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.MTOM;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@MTOM
@WebService (endpointInterface = "mtom_soap12.server.MtomSample")

public class MtomSampleImpl implements MtomSample {

    public String upload(Image data) {
        if(data != null)
            return "Success";
        throw new WebServiceException("No image Received!");
    }

    /**
     * Send data as attachment in streaming fashion
     */
    public DataHandler download(int size) {
        return getDataHandler(size);
    }

    /**
     * Streaming data handler
     */
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
}
