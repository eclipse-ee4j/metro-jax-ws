/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.wsit1533.server;

import com.sun.xml.ws.developer.StreamingDataHandler;

import javax.activation.DataHandler;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * TODO: Write some description here ...
 *
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
@MTOM(threshold = 512)
@WebService(serviceName = "UploadService", endpointInterface = "bugs.wsit1533.server.Upload", portName = "Upload", targetNamespace = UploadImpl.NAMESPACE)
public class UploadImpl implements Upload {

    public static final String NAMESPACE = "http://api.example.com/ws";

    /**
     * test the metro data upload
     * @param data the data to upload
     * @return the first line of the provided data
     */
    public String uploadDataTest(DataHandler data) {
        if (data == null) throw new NullPointerException("DataHandler is null");

        StreamingDataHandler dataStream = (StreamingDataHandler) data;

        try {
            //wrap the data input stream into a reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(dataStream.readOnce()));
            //return the first line
            return reader.readLine();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    //other methods
}
