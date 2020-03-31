/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.soap_fault_963.client;

//import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import com.sun.xml.messaging.saaj.soap.ver1_1.Detail1_1Impl;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.fault.SOAPFaultBuilder;
import com.sun.xml.ws.streaming.XMLStreamReaderUtil;
import junit.framework.TestCase;
import org.w3c.dom.Node;

import javax.xml.namespace.QName;
import jakarta.xml.soap.*;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import jakarta.xml.ws.soap.SOAPFaultException;
import java.io.StringWriter;
import java.lang.reflect.Field;


/**
 * @author Adam Lee
 */
public class SOAPFaultBuilderTest extends TestCase {

  private static final QName DETAIL1_QNAME = new QName("http://www.example1.com/faults", "myFirstDetail");
  private static final SOAPFault FAULT_11;

  static {
    SOAPFault fault11 = null;
    try {
      fault11 = createFault(SOAPVersion.SOAP_11);
    } catch (Exception e) {
      // falls through
    }
    FAULT_11 = fault11;
  }

  private static SOAPFault createFault(SOAPVersion soapVersion) throws Exception {
    SOAPFactory fac = soapVersion.saajSoapFactory;
    SOAPFault sf = fac.createFault("This is a fault.", soapVersion.faultCodeClient);
    Detail d = sf.addDetail();
    // value of underlied element/ElementNSImpl is null
    SOAPElement de = d.addChildElement(DETAIL1_QNAME);
    de.addAttribute(new QName("", "msg1"), "This is the first detail message.");
    return sf;
  }

  public void testCreate11FaultFromSFE() throws Exception {
    SOAPFaultException sfe = new SOAPFaultException(FAULT_11);
    Message msg = SOAPFaultBuilder.createSOAPFaultMessage(SOAPVersion.SOAP_11, sfe, SOAPVersion.SOAP_11.faultCodeMustUnderstand);
  }

}
