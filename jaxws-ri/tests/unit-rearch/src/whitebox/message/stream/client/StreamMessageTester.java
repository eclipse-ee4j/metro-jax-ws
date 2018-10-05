/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.message.stream.client;

import com.sun.xml.ws.fault.SOAPFaultBuilder;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.streaming.XMLStreamWriterFactory;
import com.sun.xml.ws.api.message.*;
import com.sun.xml.ws.api.pipe.Codec;
import com.sun.xml.ws.api.pipe.Codecs;
import com.sun.xml.ws.util.*;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
import javax.xml.stream.*;


/**
 * @author Jitendra Kotamraju
 */
public class StreamMessageTester extends TestCase {

    public void testCopyStreamMessage1() throws IOException {
    	String soap11Msg = 
"<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema'><Header xmlns='http://schemas.xmlsoap.org/soap/envelope/'> <SubscriptionInfo xmlns='http://ws.strikeiron.com'> <LicenseStatusCode>0</LicenseStatusCode> </SubscriptionInfo> </Header> <soap:Body> <GetCountryCodesResponse xmlns='http://www.strikeiron.com'> <GetCountryCodesResult/></GetCountryCodesResponse></soap:Body></soap:Envelope>";
        Message message = useStreamCodec(soap11Msg);
        message.copy();
    }

    public void testCopyWithSpaces() throws IOException {
    	String soap12Msg = "<?xml version='1.0'?><S:Envelope xmlns:S='http://schemas.xmlsoap.org/soap/envelope/'> <S:Header> <m:ut xmlns:m='a'> <u xmlns='' id='a'>user</u> </m:ut> <b> hello </b> </S:Header> <S:Body> <ns2:c xmlns:ns2='local'> <clientName>Test</clientName> <ns2:year>2007</ns2:year> </ns2:c> </S:Body> </S:Envelope>";
        Message message = useStreamCodec(soap12Msg);
        message.copy();
    }

    public void testCopy13() throws IOException {
    	String soap13Msg = "<S:Envelope xmlns:S='http://schemas.xmlsoap.org/soap/envelope/'><S:Body/></S:Envelope>";
        Message message = useStreamCodec(soap13Msg);
        message.copy();
    }

    public void testCopy14() throws IOException {
    	String soap14Msg = "<S:Envelope xmlns:S='http://schemas.xmlsoap.org/soap/envelope/'><S:Body> </S:Body></S:Envelope>";
        Message message = useStreamCodec(soap14Msg);
        message.copy();
    }

    public void testCopy15() throws IOException {
    	String soap15Msg = "<S:Envelope xmlns:S='http://schemas.xmlsoap.org/soap/envelope/'> <S:Body> </S:Body> </S:Envelope>";
        Message message = useStreamCodec(soap15Msg);
        message.copy();
    }

    public void testCopy16() throws IOException {
    	String soap16Msg = "<S:Envelope xmlns:S='http://schemas.xmlsoap.org/soap/envelope/'> <S:Body> <a> </a><b> </b> <c/> </S:Body> </S:Envelope>";
        Message message = useStreamCodec(soap16Msg);
        message.copy();
    }

    public void testCopy17() throws IOException {
    	String soap17Msg = "<S:Envelope xmlns:S='http://schemas.xmlsoap.org/soap/envelope/'> <S:Header> <a> </a> <b> </b> </S:Header> <S:Body> <a> </a><b> </b> <c/> </S:Body> </S:Envelope>";
        Message message = useStreamCodec(soap17Msg);
        message.copy();
    }

    /**
     * Test for the following exception. Bug in StreamMessage.copy() code
     * java.lang.IllegalArgumentException: faultCode argument for createFault was passed NULL
     *   at com.sun.xml.messaging.saaj.soap.ver1_1.SOAPFactory1_1Impl.createFault(SOAPFactory1_1Impl.java:87)
     */
    public void testCopy18() throws Exception {
    	String soap18Msg = 
"<S:Envelope xmlns:S='http://schemas.xmlsoap.org/soap/envelope/'>" +
  "<S:Body>" +
    "<S:Fault xmlns:ns4='http://www.w3.org/2003/05/soap-envelope'>" +
      "<faultcode>S:Server</faultcode>" +
      "<faultstring>com.sun.istack.XMLStreamException2</faultstring>" +
    "</S:Fault>" +
  "</S:Body>" +
"</S:Envelope>";
        Message message = useStreamCodec(soap18Msg);
        message.copy();
        SOAPFaultBuilder.create(message).createException(null);
    }

    /**
     * ns4 is declared on Envelope and is used in faultcode. So making sure
     * it is picked up after copy()
     */
    public void testCopy19() throws Exception {
    	String soap18Msg = 
"<S:Envelope xmlns:S='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ns4='http://schemas.xmlsoap.org/soap/envelope/'>" +
  "<S:Body>" +
    "<S:Fault>" +
      "<faultcode>ns4:Server</faultcode>" +
      "<faultstring>com.sun.istack.XMLStreamException2</faultstring>" +
    "</S:Fault>" +
  "</S:Body>" +
"</S:Envelope>";
        Message message = useStreamCodec(soap18Msg);
        message.copy();
        SOAPFaultBuilder.create(message).createException(null);
    }

    /**
     * ns4 is declared on Body and is used in faultcode. So making sure
     * it is picked up after copy()
     */
    public void testCopy20() throws Exception {
    	String soap18Msg = 
"<S:Envelope xmlns:S='http://schemas.xmlsoap.org/soap/envelope/'>" +
  "<S:Body xmlns:ns4='http://schemas.xmlsoap.org/soap/envelope/'>" +
    "<S:Fault>" +
      "<faultcode>ns4:Server</faultcode>" +
      "<faultstring>com.sun.istack.XMLStreamException2</faultstring>" +
    "</S:Fault>" +
  "</S:Body>" +
"</S:Envelope>";
        Message message = useStreamCodec(soap18Msg);
        message.copy();
        SOAPFaultBuilder.create(message).createException(null);
    }

    /**
     * ns4 is declared on Envelope and is used in faultcode. So making sure
     * it is picked up for payload source
     */
    public void testPayloadSource() throws Exception {
    	String soap18Msg = 
"<S:Envelope xmlns:S='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ns4='http://schemas.xmlsoap.org/soap/envelope/'>" +
  "<S:Body>" +
    "<S:Fault>" +
      "<faultcode>ns4:Server</faultcode>" +
      "<faultstring>com.sun.istack.XMLStreamException2</faultstring>" +
    "</S:Fault>" +
  "</S:Body>" +
"</S:Envelope>";
        Message message = useStreamCodec(soap18Msg);
        Source source = message.readPayloadAsSource();
        InputStream is = getInputStream(source);
        XMLStreamReader xsr = XMLInputFactory.newInstance().createXMLStreamReader(is);
        xsr.next();
        xsr.next();
        assertEquals("http://schemas.xmlsoap.org/soap/envelope/", xsr.getNamespaceURI("ns4"));
    }

    /**
     * ns4 is declared on Envelope and Body and is used in faultcode.
     * So making sure the correct ns4 is picked up for payload source
     */
    public void testPayloadSource1() throws Exception {
    	String soap18Msg = 
"<S:Envelope xmlns:S='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ns4='A'>" +
  "<S:Body xmlns:ns4='http://schemas.xmlsoap.org/soap/envelope/'>" +
    "<S:Fault>" +
      "<faultcode>ns4:Server</faultcode>" +
      "<faultstring>com.sun.istack.XMLStreamException2</faultstring>" +
    "</S:Fault>" +
  "</S:Body>" +
"</S:Envelope>";
        Message message = useStreamCodec(soap18Msg);
        Source source = message.readPayloadAsSource();
        InputStream is = getInputStream(source);
        XMLStreamReader xsr = XMLInputFactory.newInstance().createXMLStreamReader(is);
        xsr.next();
        xsr.next();
        assertEquals("http://schemas.xmlsoap.org/soap/envelope/", xsr.getNamespaceURI("ns4"));
    }

    /**
     * Test to see if ns declarations on soap envelope are captured on to the headers after parsing.
     * 
     * @throws Exception
     */

    /* This test has been moved to jaxws-unit harness to use tag 2.1.5

    public void testHeadersInStreamMessage() throws Exception {
    	String requestStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                "xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2004/08/addressing\" xmlns:user=\"http://foo.bar\">" +
                "<S:Header>" +
                "<user:foo>bar</user:foo>" +
                "</S:Header>" +
                "<S:Body>" +
                "<addNumbers xmlns=\"http://example.com/\">" +
                "<number1>10</number1>" +
                "<number2>10</number2>" +
                "</addNumbers>" +
                "</S:Body></S:Envelope>";
        Message message = useStreamCodec(requestStr);
        HeaderList hl = message.getHeaders();
        ByteArrayBuffer baos = new ByteArrayBuffer();
        XMLStreamWriter writer = XMLStreamWriterFactory.create(baos);
        writer.writeStartDocument();
        for(Header h: hl) {
            h.writeTo(writer);
        }
        writer.writeEndDocument();
        writer.flush();
        //baos.writeTo(System.out);

        XMLInputFactory readerFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader = readerFactory.createXMLStreamReader(baos.newInputStream());
        reader.next();// go to start element
        Header h = Headers.create(SOAPVersion.SOAP_11,reader);
        assertEquals(h.getNamespaceURI(),"http://foo.bar");
    }
    
    */
    Message useStreamCodec(String msg) throws IOException {
        Codec codec = Codecs.createSOAPEnvelopeXmlCodec(SOAPVersion.SOAP_11);
        Packet packet = new Packet();
        ByteArrayInputStream in = new ByteArrayInputStream(msg.getBytes());
        codec.decode(in, "text/xml", packet);
        return packet.getMessage();
    }

    private DOMSource toDOMSource(Source source) throws Exception {
        if (source instanceof DOMSource) {
            return (DOMSource)source;
        }
        Transformer trans = TransformerFactory.newInstance().newTransformer();
        DOMResult result = new DOMResult();
        trans.transform(source, result);
        return new DOMSource(result.getNode());
    }

    private InputStream getInputStream(Source source) throws Exception {
        Transformer trans = TransformerFactory.newInstance().newTransformer();
        ByteArrayBuffer bab = new ByteArrayBuffer();
        StreamResult result = new StreamResult(bab);
        trans.transform(source, result);
        return bab.newInputStream();
    }

}
