/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.jaxb.client;

import junit.framework.TestCase;
import org.glassfish.jaxb.runtime.api.JAXBRIContext;
import com.sun.xml.ws.streaming.XMLStreamReaderUtil;
import com.sun.xml.stream.buffer.XMLStreamBufferResult;
import com.sun.xml.stream.buffer.XMLStreamBuffer;


import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import javax.xml.stream.XMLStreamReader;
import static javax.xml.stream.XMLStreamConstants.START_DOCUMENT;
import javax.xml.namespace.QName;
import jakarta.xml.soap.SOAPFault;
import jakarta.xml.soap.SOAPFactory;
import jakarta.xml.soap.Detail;
import jakarta.xml.soap.SOAPElement;


/**
 * Tests for JAXB.
 *
 * @author Rama Pulavarthi
 */
public class JAXBTest extends TestCase {

    public void testJAXBElementMarshalling() throws Exception {
        JAXBRIContext jc = (JAXBRIContext) JAXBContext.newInstance(whitebox.jaxb.client.DetailType.class);
        DetailType dt = new DetailType();
        SOAPFault sf = createFault();
        dt.getDetails().add(sf.getDetail());


        Marshaller m = jc.createMarshaller();
        m.marshal(dt, System.out);
        XMLStreamBufferResult sbr = new XMLStreamBufferResult();
        m.setProperty(Marshaller.JAXB_FRAGMENT, true);
        m.marshal(dt, sbr);
        XMLStreamBuffer infoset = sbr.getXMLStreamBuffer();
        XMLStreamReader reader = infoset.readAsXMLStreamReader();
        if (reader.getEventType() == START_DOCUMENT)
            XMLStreamReaderUtil.nextElementContent(reader);
        verifyDetail(reader);

    }

    private void verifyDetail(XMLStreamReader rdr) throws Exception {
        boolean detail = false;
        while (rdr.hasNext()) {
            int event = rdr.next();
            if (event == XMLStreamReader.START_ELEMENT) {
                if (rdr.getName().getLocalPart().equals("detail") || rdr.getName().getLocalPart().equals("Detail")) {
                    detail = true;
                    XMLStreamReaderUtil.nextElementContent(rdr);    // <myFirstDetail>
                    assertEquals(DETAIL1_QNAME, rdr.getName());
                    XMLStreamReaderUtil.nextElementContent(rdr);    // </myFirstDetail>                
                }
            }
        }
        if (!detail) {
            fail("There is no detail element in the fault");
        }
    }

    private static final QName DETAIL1_QNAME = new QName("http://www.example1.com/faults", "myFirstDetail");

    private static SOAPFault createFault() throws Exception {
        SOAPFactory fac = SOAPFactory.newInstance();
        SOAPFault sf = fac.createFault("This is a fault.", new QName("http://schemas.xmlsoap.org/wsdl/soap/http", "Client"));
        Detail d = sf.addDetail();
        SOAPElement de = d.addChildElement(DETAIL1_QNAME);
        de.addAttribute(new QName("", "msg1"), "This is the first detail message.");
        return sf;
    }


}
