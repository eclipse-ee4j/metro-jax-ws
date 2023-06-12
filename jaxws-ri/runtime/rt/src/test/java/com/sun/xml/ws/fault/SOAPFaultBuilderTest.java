/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.fault;

import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.message.saaj.SAAJMessage;
import com.sun.xml.ws.streaming.XMLStreamReaderUtil;
import java.io.ByteArrayInputStream;
import junit.framework.TestCase;
import org.w3c.dom.Node;

import javax.xml.namespace.QName;
import jakarta.xml.soap.Detail;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPConstants;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPFactory;
import jakarta.xml.soap.SOAPFault;
import jakarta.xml.soap.SOAPMessage;

import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import jakarta.xml.ws.soap.SOAPFaultException;
import java.io.StringWriter;

/**
 * @author Jitendra Kotamraju
 */
public class SOAPFaultBuilderTest extends TestCase {

    private static final QName DETAIL1_QNAME =  new QName("http://www.example1.com/faults", "myFirstDetail");
    private static final QName DETAIL2_QNAME =  new QName("http://www.example2.com/faults", "mySecondDetail");
    private static final SOAPFault FAULT_11;
    private static final SOAPFault FAULT_12;

    private static final String NPE_FAULT =
            "<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">"
            + "<env:Header/>"
            + "<env:Body>"
            + "<env:Fault>"
            + "<faultcode>env:Server</faultcode>"
            + "<faultstring>String index out of range: -1</faultstring>"
            + "<faultactor/>"
            + "<detail>"
            + "<exception>oracle.j2ee.ws.client.jaxws.JRFSOAPFaultException: Client received SOAP Fault from server : String index out of range: -1</exception>"
            + "</detail>"
            + "</env:Fault>"
            + "</env:Body>"
            + "</env:Envelope>";

    static {
        SOAPFault fault11 = null;
        SOAPFault fault12 = null;
        SOAPFaultBuilder.setCaptureExceptionMessage(true);
        try {
            fault11 = createFault(SOAPVersion.SOAP_11);
            fault12 = createFault(SOAPVersion.SOAP_12);
        } catch(Exception e) {
            // falls through
        }
        FAULT_11 = fault11;
        FAULT_12 = fault12;
    }

    public SOAPFaultBuilderTest(String testName) {
        super(testName);
    }

    private void printInfoset(Node domNode) throws Exception {
        StringWriter sw = new StringWriter(4096);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(domNode), new StreamResult(sw));
        System.out.println(sw);
    }

    private static SOAPFault createFault(SOAPVersion soapVersion) throws Exception {
        SOAPFactory fac = soapVersion.getSOAPFactory();
        SOAPFault sf = fac.createFault("This is a fault.", soapVersion.faultCodeClient);
        Detail d = sf.addDetail();
        SOAPElement de = d.addChildElement(DETAIL1_QNAME);
        de.addAttribute(new QName("", "msg1"), "This is the first detail message.");
        de = d.addChildElement(DETAIL2_QNAME);
        de.addAttribute(new QName("", "msg2"), "This is the second detail message.");
        return sf;
    }

    public void testCreate11FaultFromSFE() throws Exception {
        SOAPFaultException sfe = new SOAPFaultException(FAULT_11);
        Message msg = SOAPFaultBuilder.createSOAPFaultMessage(SOAPVersion.SOAP_11, sfe, SOAPVersion.SOAP_11.faultCodeMustUnderstand);
        verifyDetail(msg);
    }

    public void testCreate11FaultFromFault() throws Exception {
        Message msg = SOAPFaultBuilder.createSOAPFaultMessage(SOAPVersion.SOAP_11, FAULT_11);
        verifyDetail(msg);
    }

    public void testCreate12FaultFromSFE() throws Exception {
        SOAPFaultException sfe = new SOAPFaultException(FAULT_12);
        Message msg = SOAPFaultBuilder.createSOAPFaultMessage(SOAPVersion.SOAP_12, sfe, SOAPVersion.SOAP_12.faultCodeMustUnderstand);
        verifyDetail(msg);
    }

    public void testCreate12FaultFromFault() throws Exception {
        Message msg = SOAPFaultBuilder.createSOAPFaultMessage(SOAPVersion.SOAP_12, FAULT_12);
        verifyDetail(msg);
    }

    public void testCreate11FaultFromRE() {
        RuntimeException re =  new RuntimeException("XML reader error: com.ctc.wstx.exc.WstxParsingException: Unexpected < character in element");
        boolean captureExceptionMessageOldValue = SOAPFaultBuilder.isCaptureExceptionMessage();
        try {
            SOAPFaultBuilder.setCaptureExceptionMessage(false);
            Message faultMsg = SOAPFaultBuilder.createSOAPFaultMessage(SOAPVersion.SOAP_11, null, re);
            verifyFaultStringForSoap11(faultMsg);
        } catch(Exception ex) {
             ex.printStackTrace();
             fail(ex.getMessage());
        } finally{
             SOAPFaultBuilder.setCaptureExceptionMessage(captureExceptionMessageOldValue);
        }
    }

    public void testCreate12FaultFromRE() {
        RuntimeException re = new RuntimeException(
                "XML reader error: com.ctc.wstx.exc.WstxParsingException: Unexpected < character in element");

        boolean captureExceptionMessageOldValue = SOAPFaultBuilder.isCaptureExceptionMessage();
        try {
            SOAPFaultBuilder.setCaptureExceptionMessage(false);
            Message faultMsg = SOAPFaultBuilder.createSOAPFaultMessage(SOAPVersion.SOAP_12, null, re);
            verifyFaultStringForSoap12(faultMsg);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        } finally {
            SOAPFaultBuilder.setCaptureExceptionMessage(captureExceptionMessageOldValue);
        }
    }

    public void testCreateSOAPFaultMessageWithSOAPFaultArgumentForSOAP11() {

        boolean captureExceptionMessageOldValue = SOAPFaultBuilder.isCaptureExceptionMessage();
        try {
            SOAPFaultBuilder.setCaptureExceptionMessage(false);
            Message faultMsg = SOAPFaultBuilder.createSOAPFaultMessage(SOAPVersion.SOAP_11, FAULT_11);
            verifyFaultStringForSoap11(faultMsg);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        } finally {
            SOAPFaultBuilder.setCaptureExceptionMessage(captureExceptionMessageOldValue);
        }
    }

    public void testCreateSOAPFaultMessageWithFaultStringArgumentForSOAP11() {

        String faultString = "Couldn't create SOAP message due to exception: XML reader error: "
                + "com.ctc.wstx.exc.WstxParsingException: Unexpected '&lt;' character in element "
                + "(missing closing '>'?)";

        boolean captureExceptionMessageOldValue = SOAPFaultBuilder.isCaptureExceptionMessage();
        try {
            SOAPFaultBuilder.setCaptureExceptionMessage(false);
            Message faultMsg = SOAPFaultBuilder.createSOAPFaultMessage(SOAPVersion.SOAP_11, faultString, DETAIL1_QNAME);
            verifyFaultStringForSoap11(faultMsg);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        } finally {
            SOAPFaultBuilder.setCaptureExceptionMessage(captureExceptionMessageOldValue);
        }
    }

    public void testCreateSOAPFaultMessageWithSOAPFaultArgumentForSOAP12() {

        boolean captureExceptionMessageOldValue = SOAPFaultBuilder.isCaptureExceptionMessage();
        try {
            SOAPFaultBuilder.setCaptureExceptionMessage(false);
            Message faultMsg = SOAPFaultBuilder.createSOAPFaultMessage(SOAPVersion.SOAP_12, FAULT_12);
            verifyFaultStringForSoap12(faultMsg);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        } finally {
            SOAPFaultBuilder.setCaptureExceptionMessage(captureExceptionMessageOldValue);
        }
    }

    public void testCreateSOAPFaultMessageWithFaultStringArgumentForSOAP12() {

        String faultString = "Couldn't create SOAP message due to exception: XML reader error: "
                + "com.ctc.wstx.exc.WstxParsingException: Unexpected '&lt;' character in element "
                + "(missing closing '>'?)";

        boolean captureExceptionMessageOldValue = SOAPFaultBuilder.isCaptureExceptionMessage();
        try {
            SOAPFaultBuilder.setCaptureExceptionMessage(false);
            Message faultMsg = SOAPFaultBuilder.createSOAPFaultMessage(SOAPVersion.SOAP_12, faultString,
                    SOAPConstants.SOAP_RECEIVER_FAULT);
            verifyFaultStringForSoap12(faultMsg);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        } finally {
            SOAPFaultBuilder.setCaptureExceptionMessage(captureExceptionMessageOldValue);
        }
    }

    public void testCreateException_14504957() throws Exception {
        MessageFactory f = MessageFactory.newInstance();
        SOAPMessage soapMsg = f.createMessage(null, new ByteArrayInputStream(NPE_FAULT.getBytes()));
        Message m = new SAAJMessage(soapMsg);
        SOAPFaultBuilder builder = SOAPFaultBuilder.create(m);
        try {
            SOAPFaultException sex = (SOAPFaultException)builder.createException(null);
        } catch (Throwable t) {
            t.printStackTrace(System.out);
            fail("Got unexpected exception: " + t.getClass().getName());
        }
    }

    private void verifyDetail(Message message) throws Exception {
        boolean detail = false;
        XMLStreamReader rdr = message.readPayload();
        while(rdr.hasNext()) {
            int event = rdr.next();
            if (event == XMLStreamReader.START_ELEMENT) {
                if (rdr.getName().getLocalPart().equals("detail") || rdr.getName().getLocalPart().equals("Detail")) {
                    detail = true;
                    XMLStreamReaderUtil.nextElementContent(rdr);    // <myFirstDetail>
                    assertEquals(DETAIL1_QNAME, rdr.getName());
                    XMLStreamReaderUtil.nextElementContent(rdr);    // </myFirstDetail>
                    XMLStreamReaderUtil.nextElementContent(rdr);    // <mySecondDetail>
                    assertEquals(DETAIL2_QNAME, rdr.getName());
                }
            }
        }
        if (!detail) {
            fail("There is no detail element in the fault");
        }
    }

    private void verifyFaultStringForSoap11(Message faultMsg) throws Exception {
        boolean isfaultStringPresent = false;
        XMLStreamReader rdr = faultMsg.readPayload();
        while (rdr.hasNext()) {
            int event = rdr.next();
            if (event == XMLStreamReader.START_ELEMENT) {
                if (rdr.getName().getLocalPart().equals("faultstring")) {
                    isfaultStringPresent = true;
                    event = rdr.next();
                    assertEquals("Server Error", rdr.getText());
                }
            }
        }
        if(!isfaultStringPresent) {
            fail("There is no faultstring in the response");
        }
    }

    private void verifyFaultStringForSoap12(Message faultMsg) throws Exception {
        SOAPFaultBuilder sfb = SOAPFaultBuilder.create(faultMsg);
        Throwable ex = sfb.createException(null);
        assertTrue(ex instanceof SOAPFaultException);
        SOAPFaultException sfe = (SOAPFaultException) ex;
        SOAPFault sf = sfe.getFault();
        assertEquals(sf.getFaultString(),"Server Error");
    }
}
