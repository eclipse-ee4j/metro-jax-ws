/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.endpoint_6790700.client;

import javax.xml.ws.Provider;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.Service;
import javax.xml.ws.Service.Mode;
import javax.xml.ws.*;
import java.io.StringReader;
import java.io.*;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.*;
import org.w3c.dom.*;
import javax.xml.transform.stream.StreamResult;


/**
 * @author Jitendra Kotamraju
 */

@WebServiceProvider
@ServiceMode(value=Service.Mode.PAYLOAD)
public class MyProvider implements Provider<Source> {
    public Source invoke(Source source) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            StreamResult sr = new StreamResult(bos );
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            trans.transform(source, sr);

            source = new StreamSource(new ByteArrayInputStream(bos.toByteArray()));
            // Typically received source is StAXSource. When it is converted
            // to DOMSource, it seems to pullin inscope namespaces. Hence
            // it is converted to StreamSource above
            DOMResult dr = new DOMResult();
            trans = TransformerFactory.newInstance().newTransformer();
            trans.transform(source, dr);
            Node node = dr.getNode();
            node = node.getFirstChild().getFirstChild();
            node = node.getAttributes().getNamedItemNS("http://www.w3.org/2001/XMLSchema-instance" , "nil");
            if (node == null) {
                throw new WebServiceException("xsi:nil attribute is not found");
            }
        } catch(Exception e) {
            throw new WebServiceException(e);
        }

        String replyElement = new String("<p>hello world</p>");
        StreamSource reply = new StreamSource(new StringReader (replyElement));
        return reply;
    }
}
