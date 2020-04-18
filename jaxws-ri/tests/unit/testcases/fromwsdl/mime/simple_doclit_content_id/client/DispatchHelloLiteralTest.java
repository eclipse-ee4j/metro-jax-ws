/*
 * Copyright (c) 2006, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.mime.simple_doclit_content_id.client;

import junit.framework.TestCase;
import testutil.AttachmentHelper;

import jakarta.activation.DataHandler;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.handler.MessageContext;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JAX-RPC RI Development Team
 */
public class DispatchHelloLiteralTest extends TestCase {

    private static final String tns = "http://www.ws-i.org/SampleApplications/SupplyChainManagement/2003-07/Catalog.wsdl";
    private static final QName portQName = new QName(tns, "CatalogPort");
    private static final String testDHBodyPayload = "<ns1:testDataHandlerBody xmlns:ns1=\"http://www.ws-i.org/SampleApplications/SupplyChainManagement/2003-07/Catalog.xsd\" xmlns:ns2=\"http://www.ws-i.org/SampleApplications/SupplyChainManagement/2003-07/Catalog.wsdl\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">test</ns1:testDataHandlerBody>";

    public DispatchHelloLiteralTest(String name) throws Exception {
        super(name);
    }

    private Source makeStreamSource(String msg) {
        byte[] bytes = msg.getBytes();
        ByteArrayInputStream sinputStream = new ByteArrayInputStream(bytes);
        return new StreamSource(sinputStream);
    }

    public void testDataHandler() {

        CatalogService service = new CatalogService();
        Dispatch dispatch = service.createDispatch(portQName, Source.class, Service.Mode.PAYLOAD);

        Source src = makeStreamSource(testDHBodyPayload);

        DataHandler dh = new DataHandler(getSampleXML(), "text/xml");
        Map<String, DataHandler> attMap = new HashMap();
        //make content id the wsdl part name
        attMap.put("<attachIn=1234@example.org>", dh);
        Map<String, Object> requestContext = dispatch.getRequestContext();
        requestContext.put(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS, attMap);

        Object result = dispatch.invoke(src);
        assertTrue(result == null);

        HashMap<String, DataHandler> attresult = (HashMap<String, DataHandler>)
                dispatch.getResponseContext().get(MessageContext.INBOUND_MESSAGE_ATTACHMENTS);
        Object content = null;
        if (attresult != null) {
            for (Map.Entry<String, DataHandler> att : attresult.entrySet()) {
                System.out.println("the content id " + att.getKey());
                try {
                    content = att.getValue().getContent();
                    System.out.println("Content is " + content.getClass());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                assertTrue(AttachmentHelper.compareSource(getSampleXML(), (Source) content));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            fail("mime test with dispatch failed");
        }
    }


    private String getDataDir() {
        String userDir = System.getProperty("user.dir");
        String sepChar = System.getProperty("file.separator");
        return userDir + sepChar + "testcases/fromwsdl/mime/simple_doclit_content_id/resources/";
    }

    private StreamSource getSampleXML() {
        try {
            String location = getDataDir() + "sample.xml";
            File f = new File(location);
            FileInputStream is = new FileInputStream(f);
            return new StreamSource(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
