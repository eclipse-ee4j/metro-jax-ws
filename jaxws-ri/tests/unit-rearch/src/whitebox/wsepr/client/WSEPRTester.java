/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.wsepr.client;

import com.sun.xml.ws.api.addressing.WSEndpointReference;
import junit.framework.TestCase;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.EndpointReference;
import javax.xml.namespace.QName;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Rama Pulavarthi
 */

public class WSEPRTester extends TestCase {
    private final XMLOutputFactory staxOut;

    public WSEPRTester(String name) {
        super(name);
        this.staxOut = XMLOutputFactory.newInstance();
        staxOut.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
    }

    public void testEPRs() throws Exception {
        URL res = getClass().getResource("../epr/ms_epr_metadata.xml");
        File folder = new File(res.getFile()).getParentFile();   // assuming that this is a file:// URL.

        for (File f : folder.listFiles()) {
            System.out.println("***********"+ f.getName() + "***********");
            if(!f.getName().endsWith(".xml"))
                continue;
            InputStream is = new FileInputStream(f);
            StreamSource s = new StreamSource(is);
            EndpointReference epr = EndpointReference.readFrom(s);
            WSEndpointReference wsepr = new WSEndpointReference(epr);
            WSEndpointReference.Metadata metadata = wsepr.getMetaData();
            if (f.getName().equals("w3c_epr_identity.xml")) {
                QName q = new QName("http://schemas.xmlsoap.org/ws/2006/02/addressingidentity", "Identity");
                WSEndpointReference.EPRExtension eprExtn = wsepr.getEPRExtension(q);
                XMLStreamReader xsr = eprExtn.readAsXMLStreamReader();
                if (xsr.getEventType() == XMLStreamConstants.START_DOCUMENT)
                    xsr.next();
                assertEquals(q.getNamespaceURI(), xsr.getNamespaceURI());
                assertEquals(q.getLocalPart(),xsr.getLocalName());
            }
        }
    }
}
