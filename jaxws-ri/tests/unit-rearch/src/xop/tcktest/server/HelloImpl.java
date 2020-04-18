/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package xop.tcktest.server;


import com.sun.xml.ws.developer.JAXWSProperties;

import jakarta.xml.ws.WebServiceException;
import jakarta.activation.DataHandler;
import jakarta.annotation.Resource;
import jakarta.jws.WebService;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

/**
 * @author Jitendra Kotamraju
 */
@WebService(endpointInterface = "xop.tcktest.server.Hello")

public class HelloImpl {

    public String mtomIn(DataType data) {
        Source source1 = data.getDoc1();
        try {
            getStringFromSource(source1);
        } catch(Exception e) {
            throw new WebServiceException("Incorrect Source source1", e);
        }

        Source source2 = data.getDoc2();
        try {
            getStringFromSource(source2);
        } catch(Exception e) {
            throw new WebServiceException("Incorrect Source source1", e);
        }

        DataHandler dh = data.getDoc3();

        Image image = data.getDoc4();
        if (image == null) {
            throw new WebServiceException("Received Image is null");
        }

        return "works";
    }

    private String getStringFromSource(Source source) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        StreamResult sr = new StreamResult(bos );
        Transformer trans = TransformerFactory.newInstance().newTransformer();
        Properties oprops = new Properties();
        oprops.put(OutputKeys.OMIT_XML_DECLARATION, "yes");
        trans.setOutputProperties(oprops);
        trans.transform(source, sr);
        bos.flush();
        return bos.toString();
    }

}
