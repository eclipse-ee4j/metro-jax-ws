/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.jaxws21.service_dispatch_features.client;

import client.common.client.DispatchTestCase;
import com.sun.xml.ws.developer.MemberSubmissionEndpointReference;
import testutil.ClientServerTestUtil;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import jakarta.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMResult;
import jakarta.xml.ws.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.StringWriter;


/**
 * @author Arun Gupta
 *         Kathy walsh
 */
public class AddNumbersClient extends DispatchTestCase {
    //may be used for verification
    private static final QName SERVICE_QNAME = new QName("http://example.com/", "AddNumbersService");
    private static final QName PORT_QNAME = new QName("http://example.com/", "AddNumbersPort");
    private static final String ENDPOINT_ADDRESS = "http://localhost:8080/jaxrpc-client_jaxws21_service_dispatch_features/hello";
    //maybe used for firther tests
    private URL wsdl;

    public AddNumbersClient(String name) {
        super(name);
        try {
            wsdl = new URL("http://localhost:8080/jaxrpc-client_jaxws21_service_dispatch_features/hello?WSDL");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private Service createServiceWithWSDL() throws Exception {
        return Service.create(wsdl, SERVICE_QNAME);

    }

    private EndpointReference createEPRStubServiceWithWSDL(Service service) throws Exception {

        AddNumbersPortType port = service.getPort(PORT_QNAME, AddNumbersPortType.class);
        return ((BindingProvider) port).getEndpointReference();
    }

    private MemberSubmissionEndpointReference createMSEPRStubServiceWithWSDL(Service service) throws Exception {

        AddNumbersPortType port = service.getPort(PORT_QNAME, AddNumbersPortType.class);
        return ((BindingProvider) port).getEndpointReference(MemberSubmissionEndpointReference.class);
    }

     private EndpointReference createEPRDispatchService(Service service) throws Exception {

        AddNumbersPortType port = service.getPort(PORT_QNAME, AddNumbersPortType.class);
        return ((BindingProvider) port).getEndpointReference();
    }

    private MemberSubmissionEndpointReference createMSEPRDispatchService(Service service) throws Exception {

        AddNumbersPortType port = service.getPort(PORT_QNAME, AddNumbersPortType.class);
        return ((BindingProvider) port).getEndpointReference(MemberSubmissionEndpointReference.class);
    }

     private EndpointReference createEPRSDispatchServiceWithWSDL(Service service) throws Exception {

        AddNumbersPortType port = service.getPort(PORT_QNAME, AddNumbersPortType.class);
        return ((BindingProvider) port).getEndpointReference();
    }

    private MemberSubmissionEndpointReference createMSEPRDispatchServiceWithWSDL(Service service) throws Exception {

        AddNumbersPortType port = service.getPort(PORT_QNAME, AddNumbersPortType.class);
        return ((BindingProvider) port).getEndpointReference(MemberSubmissionEndpointReference.class);
    }


    private Service createService() throws Exception {
        return Service.create(SERVICE_QNAME);
    }


    JAXBContext createJAXBContext() {
        try {
            return JAXBContext.newInstance(client.jaxws21.service_dispatch_features.client.ObjectFactory.class);
        } catch (JAXBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }


    public void testProxyAddNumbers() throws AddNumbersFault_Exception {

        AddNumbersService service = new AddNumbersService();
        AddNumbersPortType port = service.getAddNumbersPort();
        int result = port.addNumbers(2, 4);

    }

    public void testCreateDispatchSMWsdl() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }
        RespectBindingFeature rbf = new RespectBindingFeature(false);
        WebServiceFeature[] wse = new WebServiceFeature[]{rbf};
        Service service = createServiceWithWSDL();
        Dispatch<SOAPMessage> dispatch = service.createDispatch(PORT_QNAME, SOAPMessage.class, Service.Mode.MESSAGE, wse);
        SOAPMessage result = dispatch.invoke(getSOAPMessage(makeStreamSource(SMMsg)));

        result.writeTo(System.out);
    }

    //UsingAddressing wsdl:required=true
    public void testCreateDispatchSource() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }
        RespectBindingFeature rbf = new RespectBindingFeature(true);
        WebServiceFeature[] wse = new WebServiceFeature[]{rbf};
        Service service = createServiceWithWSDL();
        Dispatch<Source> dispatch = service.createDispatch(PORT_QNAME, Source.class, Service.Mode.PAYLOAD, wse);
        Source result = dispatch.invoke(makeStreamSource(MSGSrc));
        JAXBElement<AddNumbersResponse> addNumberResponse =  (JAXBElement<AddNumbersResponse>) createJAXBContext().createUnmarshaller().unmarshal(result);
        AddNumbersResponse response = addNumberResponse.getValue();
        assertEquals(response.getReturn(), 2 + 4);

    }

    //UsingAddressing wsdl:required=true
    //RespectBindingFeature Disabled - no effect - behavior undefined by specification
    //for backward compatability

    public void testCreateDispatchJAXB() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }

        RespectBindingFeature rbf = new RespectBindingFeature(true);
        WebServiceFeature[] wse = new WebServiceFeature[]{rbf};
        Service service = createServiceWithWSDL();
        Dispatch<Object> dispatch = service.createDispatch(PORT_QNAME, createJAXBContext(), Service.Mode.PAYLOAD, wse);

        AddNumbers addNumbers = factory.createAddNumbers();
        addNumbers.setNumber1(2);
        addNumbers.setNumber2(4);
        JAXBElement<AddNumbers> num = factory.createAddNumbers(addNumbers);
        JAXBElement<AddNumbersResponse> addNumbersResponse = (JAXBElement<AddNumbersResponse>) dispatch.invoke(num);
        AddNumbersResponse response = addNumbersResponse.getValue();
        assertEquals(response.getReturn(), 2 + 4);

    }

    public void testCreateDispatchSMWsdlWEPR() throws Exception {
        String eprString = "<EndpointReference xmlns=\"http://www.w3.org/2005/08/addressing\"><Address>" +
                "http://localhost:8080/jaxrpc-client_jaxws21_service_dispatch_features/hello</Address>" +
                "<Metadata><wsaw:ServiceName xmlns:wsaw=\"http://www.w3.org/2006/05/addressing/wsdl\" xmlns:wsns=\"http://example.com/\" EndpointName=\"AddNumbersPort\">wsns:AddNumbersService</wsaw:ServiceName>" +
                "</Metadata></EndpointReference>";
        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }

        RespectBindingFeature rbf = new RespectBindingFeature(false);
        WebServiceFeature[] wse = new WebServiceFeature[]{rbf};
        Service service = createServiceWithWSDL();
        EndpointReference w3cEPR = createEPRStubServiceWithWSDL(service);
        //W3CEPRString = w3cEPR.toString();
        W3CEPRString = eprString;
        Dispatch<SOAPMessage> dispatch = service.createDispatch(EndpointReference.readFrom(makeStreamSource(W3CEPRString)), SOAPMessage.class, Service.Mode.MESSAGE);
        SOAPMessage sm = dispatch.invoke(getSOAPMessage(makeStreamSource(SMMsg)));
        sm.writeTo(System.out);
        //System.out.println("Adding numbers 2 and 4");
        // int result = dispatch.invoke(getSOAPMessage())
        // assert(result == 6);
        // System.out.println("Addinion of 2 and 4 successful");
    }

    //UsingAddressing wsdl:required=true
    public void testCreateDispatchSourceWEPR() throws Exception {
        String eprString = "<EndpointReference xmlns=\"http://www.w3.org/2005/08/addressing\"><Address>" +
                "http://localhost:8080/jaxrpc-client_jaxws21_service_dispatch_features/hello</Address>" +
                "<Metadata><wsaw:ServiceName xmlns:wsaw=\"http://www.w3.org/2006/05/addressing/wsdl\" xmlns:wsns=\"http://example.com/\" EndpointName=\"AddNumbersPort\">wsns:AddNumbersService</wsaw:ServiceName>" +
                "</Metadata></EndpointReference>";
        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }

        RespectBindingFeature rbf = new RespectBindingFeature(true);
        WebServiceFeature[] wse = new WebServiceFeature[]{rbf};
        Service service = createServiceWithWSDL();

        EndpointReference w3cEPR = createEPRStubServiceWithWSDL(service);
        //W3CEPRString = w3cEPR.toString();
        W3CEPRString = eprString;        
        Dispatch<Source> dispatch = service.createDispatch(EndpointReference.readFrom(makeStreamSource(W3CEPRString)), Source.class, Service.Mode.PAYLOAD, wse);
        Source result = dispatch.invoke(makeStreamSource(MSGSrc));
        JAXBElement<AddNumbersResponse> addNumberResponse =  (JAXBElement<AddNumbersResponse>) createJAXBContext().createUnmarshaller().unmarshal(result);
        AddNumbersResponse response = addNumberResponse.getValue();
        assertEquals(response.getReturn(), 2 + 4);


    }

    //UsingAddressing wsdl:required=true
    //RespectBindingFeature Disabled - no effect - behavior undefined by specification
    //for backward compatability

    public void testCreateDispatchJAXBWEPR() throws Exception {
        String eprString = "<EndpointReference xmlns=\"http://www.w3.org/2005/08/addressing\"><Address>" +
                "http://localhost:8080/jaxrpc-client_jaxws21_service_dispatch_features/hello</Address>" +
                "<Metadata><wsaw:ServiceName xmlns:wsaw=\"http://www.w3.org/2006/05/addressing/wsdl\" xmlns:wsns=\"http://example.com/\" EndpointName=\"AddNumbersPort\">wsns:AddNumbersService</wsaw:ServiceName>" +
                "</Metadata></EndpointReference>";
        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }

        RespectBindingFeature rbf = new RespectBindingFeature(true);
        WebServiceFeature[] wse = new WebServiceFeature[]{rbf};

        Service service = createServiceWithWSDL();

        EndpointReference w3cEPR = createEPRStubServiceWithWSDL(service);
        //W3CEPRString = w3cEPR.toString();
        W3CEPRString = eprString;
        Dispatch<Object> dispatch = service.createDispatch(EndpointReference.readFrom(makeStreamSource(W3CEPRString)), createJAXBContext(), Service.Mode.PAYLOAD, wse);
        AddNumbers addNumbers = factory.createAddNumbers();
        addNumbers.setNumber1(2);
        addNumbers.setNumber2(4);
        JAXBElement<AddNumbers> num = factory.createAddNumbers(addNumbers);
        JAXBElement<AddNumbersResponse> addNumbersResponse = (JAXBElement<AddNumbersResponse>) dispatch.invoke(num);
        AddNumbersResponse response = addNumbersResponse.getValue();
        assertEquals(response.getReturn(), 2 + 4);

    }


    public void testCreateDispatchSMWsdlMSEPR() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }

        RespectBindingFeature rbf = new RespectBindingFeature(false);
        WebServiceFeature[] wse = new WebServiceFeature[]{rbf};
        Service service = createServiceWithWSDL();
        EndpointReference msEPR = createMSEPRStubServiceWithWSDL(service);

        MSEPRString = msEPR.toString();

        Dispatch<SOAPMessage> dispatch = service.createDispatch(EndpointReference.readFrom(makeStreamSource(MSEPRString)), SOAPMessage.class, Service.Mode.MESSAGE, wse);
        SOAPMessage sm = dispatch.invoke(getSOAPMessage(makeStreamSource(SMMsg)));
        sm.writeTo(System.out);

        //System.out.println("Adding numbers 2 and 4");
        // int result = dispatch.invoke(getSOAPMessage())
        // assert(result == 6);
        // System.out.println("Addinion of 2 and 4 successful");
    }


    //UsingAddressing wsdl:required=true
    public void testCreateDispatchSourceMSEPR() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }

        RespectBindingFeature rbf = new RespectBindingFeature(true);
        WebServiceFeature[] wse = new WebServiceFeature[]{rbf};
        Service service = createServiceWithWSDL();

        EndpointReference msEPR = createMSEPRStubServiceWithWSDL(service);
        MSEPRString = msEPR.toString();
        Dispatch<Source> dispatch = service.createDispatch(EndpointReference.readFrom(makeStreamSource(MSEPRString)), Source.class, Service.Mode.PAYLOAD, wse);
        Source result = dispatch.invoke(makeStreamSource(MSGSrc));
        JAXBElement<AddNumbersResponse> addNumberResponse =  (JAXBElement<AddNumbersResponse>) createJAXBContext().createUnmarshaller().unmarshal(result);
        AddNumbersResponse response = addNumberResponse.getValue();
        assertEquals(response.getReturn(), 2 + 4);
      
    }

    //UsingAddressing wsdl:required=true
    //RespectBindingFeature Disabled - no effect - behavior undefined by specification
    //for backward compatability

    public void testCreateDispatchJAXBMSEPR() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }

        RespectBindingFeature rbf = new RespectBindingFeature(true);
        WebServiceFeature[] wse = new WebServiceFeature[]{rbf};
        Service service = createServiceWithWSDL();

        EndpointReference msEPR = createMSEPRStubServiceWithWSDL(service);
        MSEPRString = msEPR.toString();
        Dispatch<Object> dispatch = service.createDispatch(EndpointReference.readFrom(makeStreamSource(MSEPRString)), createJAXBContext(), Service.Mode.PAYLOAD, wse);
        AddNumbers addNumbers = factory.createAddNumbers();
        addNumbers.setNumber1(2);
        addNumbers.setNumber2(4);
        JAXBElement<AddNumbers> num = factory.createAddNumbers(addNumbers);
        JAXBElement<AddNumbersResponse> addNumbersResponse = (JAXBElement<AddNumbersResponse>) dispatch.invoke(num);
         AddNumbersResponse response = addNumbersResponse.getValue();
        assertEquals(response.getReturn(), 2 + 4);

    }

    public void testCreateDispatchSMWsdlMSEPRNoPortQName() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }

        RespectBindingFeature rbf = new RespectBindingFeature(false);
        WebServiceFeature[] wse = new WebServiceFeature[]{rbf};
        Service service = createServiceWithWSDL();
        MemberSubmissionEndpointReference msEPR = createMSEPRStubServiceWithWSDL(service);
        msEPR.portTypeName.name = null;
        MSEPRString = msEPR.toString();


        Dispatch<SOAPMessage> dispatch = service.createDispatch(EndpointReference.readFrom(makeStreamSource(MSEPRString)), SOAPMessage.class, Service.Mode.MESSAGE, wse);
        SOAPMessage sm = dispatch.invoke(getSOAPMessage(makeStreamSource(SMMsg)));
        sm.writeTo(System.out);

        //System.out.println("Adding numbers 2 and 4");
        // int result = dispatch.invoke(getSOAPMessage())
        // assert(result == 6);
        // System.out.println("Addinion of 2 and 4 successful");
    }


    //UsingAddressing wsdl:required=true
    public void testCreateDispatchSourceMSEPRNoPortQName() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }

        RespectBindingFeature rbf = new RespectBindingFeature(true);
        WebServiceFeature[] wse = new WebServiceFeature[]{rbf};
        Service service = createServiceWithWSDL();

        MemberSubmissionEndpointReference msEPR = createMSEPRStubServiceWithWSDL(service);
        //MemberSubmissionEndpointReference.AttributedQName portTypeName = msEPR.portTypeName;
        //QName portQName = portTypeName.name;
        msEPR.portTypeName.name = null;
        MSEPRString = msEPR.toString();
        Dispatch<Source> dispatch = service.createDispatch(EndpointReference.readFrom(makeStreamSource(MSEPRString)), Source.class, Service.Mode.PAYLOAD, wse);
        Source result = dispatch.invoke(makeStreamSource(MSGSrc));
        JAXBElement<AddNumbersResponse> addNumberResponse =  (JAXBElement<AddNumbersResponse>) createJAXBContext().createUnmarshaller().unmarshal(result);
        AddNumbersResponse response = addNumberResponse.getValue();
        assertEquals(response.getReturn(), 2 + 4);

    }

    //UsingAddressing wsdl:required=true
    //RespectBindingFeature Disabled - no effect - behavior undefined by specification
    //for backward compatability

    public void testCreateDispatchJAXBMSEPRNoPortQName() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }

        RespectBindingFeature rbf = new RespectBindingFeature(true);
        WebServiceFeature[] wse = new WebServiceFeature[]{rbf};
        Service service = createServiceWithWSDL();

        MemberSubmissionEndpointReference msEPR = createMSEPRStubServiceWithWSDL(service);        
        msEPR.portTypeName.name = null;

        MSEPRString = msEPR.toString();
        Dispatch<Object> dispatch = service.createDispatch(EndpointReference.readFrom(makeStreamSource(MSEPRString)), createJAXBContext(), Service.Mode.PAYLOAD, wse);
        AddNumbers addNumbers = factory.createAddNumbers();
        addNumbers.setNumber1(2);
        addNumbers.setNumber2(4);
        JAXBElement<AddNumbers> num = factory.createAddNumbers(addNumbers);
        JAXBElement<AddNumbersResponse> addNumbersResponse = (JAXBElement<AddNumbersResponse>) dispatch.invoke(num);
        AddNumbersResponse response = addNumbersResponse.getValue();
        assertEquals(response.getReturn(), 2 + 4);

    }


    public void xxxtestEPRGetPortIV() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }
    }


    //UsingAddressing wsdl:required=true
    //AddressingFeature Disabled expect Exception
    //Expect no valid addressingport created, so exception thrown
    public void xxxtestEPRGetPortV() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }


    }

    //UsingAddressing wsdl:required=true
    //AddressingFeature Disabled expect Exception
    //Expect no valid port created,
    public void xxxtestEPRGetPortVI() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }


    }


    //UsingAddressing wsdl:required=true
    //AddressingFeature Disabled expect Exception
    //Expect no valid port created, so exception thrown
    public void xxxtestEPRGetPortVII() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }


    }


    //UsingAddressing wsdl:required=true
    //AddressingFeature Disabled expect Exception
    //Expect no valid port created, so exception thrown
    public void xxtestEPRGetPortVIII() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }


    }

//UsingAddressing wsdl:required=true
//AddressingFeature Disabled expect Exception
//Expect no valid port created, so exception thrown

    public void xxxtestEPRGetPortVIIII() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }


    }

    //UsingAddressing wsdl:required=true
    //AddressingFeature Disabled expect Exception
    //Expect no valid port created, so exception thrown
    public void xxxtestEPRGetPortVIIIII() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }


    }

//UsingAddressing wsdl:required=true
//AddressingFeature Disabled expect Exception
//Expect no valid port created, so exception thrown

    public void xxxtestEPRGetPortVIIIIII() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }


    }

    //UsingAddressing wsdl:required=true
    public void xxxtestDispatchEPRGetPort() throws Exception {

        if (ClientServerTestUtil.useLocal()) {
            System.out.println("HTTP Transport Only Exiting");
            return;
        }

    }


    public static String W3CEPRString;
    public String MSEPRString;

    private ObjectFactory factory = new ObjectFactory();

    private String SMMsg = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Header><To xmlns=\"http://www.w3.org/2005/08/addressing\">http://localhost:8080/jaxrpc-client_jaxws21_service_dispatch_features/hello</To><Action xmlns=\"http://www.w3.org/2005/08/addressing\">http://example.com/AddNumbersPortType/addNumbersRequest</Action><ReplyTo xmlns=\"http://www.w3.org/2005/08/addressing\">" +
            "<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>" +
            "</ReplyTo><MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">uuid:a89abfcf-0b64-4f71-979e-9ee31ae75b6c</MessageID></S:Header><S:Body><addNumbers xmlns=\"http://example.com/\"><number1>2</number1><number2>4</number2></addNumbers></S:Body></S:Envelope>";

    private String SMMsgString = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Header><To xmlns=\"http://www.w3.org/2005/08/addressing\">http://localhost:8080/jaxrpc-client_jaxws21_service_dispatch_features/hello</To><Action xmlns=\"http://www.w3.org/2005/08/addressing\">http://example.com/AddNumbersPortType/addNumbersRequest</Action><ReplyTo xmlns=\"http://www.w3.org/2005/08/addressing\"><Address>http://www.w3.org/2005/08/addressing/anonymous></Address></ReplyTo><MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">uuid:b63b8097-6ac9-4c6e-83f9-ab9f5b108f5c</MessageID></S:Header><S:Body><addNumbers xmlns=\"http://example.com/\"><number1>2</number1><number2>4</number2></addNumbers></S:Body></S:Envelope>";
    private String MSGSrc = "<addNumbers xmlns=\"http://example.com/\"><number1>2</number1><number2>4</number2></addNumbers>";
}
