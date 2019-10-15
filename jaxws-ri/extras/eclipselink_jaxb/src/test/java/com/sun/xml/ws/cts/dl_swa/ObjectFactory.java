/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.cts.dl_swa;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the oracle.j2ee.ws.jaxws.cts.dl_swa package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _InputRequest_QNAME = new QName("http://SwaTestService.org/xsd", "InputRequest");
    private final static QName _OutputResponseString_QNAME = new QName("http://SwaTestService.org/xsd", "OutputResponseString");
    private final static QName _InputRequestWithHeader_QNAME = new QName("http://SwaTestService.org/xsd", "InputRequestWithHeader");
    private final static QName _MyHeader_QNAME = new QName("http://SwaTestService.org/xsd", "MyHeader");
    private final static QName _InputRequestGet_QNAME = new QName("http://SwaTestService.org/xsd", "InputRequestGet");
    private final static QName _InputRequestThrowAFault_QNAME = new QName("http://SwaTestService.org/xsd", "InputRequestThrowAFault");
    private final static QName _OutputResponseAll_QNAME = new QName("http://SwaTestService.org/xsd", "OutputResponseAll");
    private final static QName _VoidRequest_QNAME = new QName("http://SwaTestService.org/xsd", "VoidRequest");
    private final static QName _InputRequestString_QNAME = new QName("http://SwaTestService.org/xsd", "InputRequestString");
    private final static QName _MyFaultReason_QNAME = new QName("http://SwaTestService.org/xsd", "MyFaultReason");
    private final static QName _OutputResponse_QNAME = new QName("http://SwaTestService.org/xsd", "OutputResponse");
    private final static QName _InputRequestPut_QNAME = new QName("http://SwaTestService.org/xsd", "InputRequestPut");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: oracle.j2ee.ws.jaxws.cts.dl_swa
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InputRequestGet }
     * 
     */
    public InputRequestGet createInputRequestGet() {
        return new InputRequestGet();
    }

    /**
     * Create an instance of {@link OutputResponseString }
     * 
     */
    public OutputResponseString createOutputResponseString() {
        return new OutputResponseString();
    }

    /**
     * Create an instance of {@link MyHeader }
     * 
     */
    public MyHeader createMyHeader() {
        return new MyHeader();
    }

    /**
     * Create an instance of {@link InputRequestWithHeader }
     * 
     */
    public InputRequestWithHeader createInputRequestWithHeader() {
        return new InputRequestWithHeader();
    }

    /**
     * Create an instance of {@link InputRequest }
     * 
     */
    public InputRequest createInputRequest() {
        return new InputRequest();
    }

    /**
     * Create an instance of {@link OutputResponse }
     * 
     */
    public OutputResponse createOutputResponse() {
        return new OutputResponse();
    }

    /**
     * Create an instance of {@link OutputResponseAll }
     * 
     */
    public OutputResponseAll createOutputResponseAll() {
        return new OutputResponseAll();
    }

    /**
     * Create an instance of {@link InputRequestString }
     * 
     */
    public InputRequestString createInputRequestString() {
        return new InputRequestString();
    }

    /**
     * Create an instance of {@link MyFaultType }
     * 
     */
    public MyFaultType createMyFaultType() {
        return new MyFaultType();
    }

    /**
     * Create an instance of {@link VoidRequest }
     * 
     */
    public VoidRequest createVoidRequest() {
        return new VoidRequest();
    }

    /**
     * Create an instance of {@link InputRequestThrowAFault }
     * 
     */
    public InputRequestThrowAFault createInputRequestThrowAFault() {
        return new InputRequestThrowAFault();
    }

    /**
     * Create an instance of {@link InputRequestPut }
     * 
     */
    public InputRequestPut createInputRequestPut() {
        return new InputRequestPut();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InputRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SwaTestService.org/xsd", name = "InputRequest")
    public JAXBElement<InputRequest> createInputRequest(InputRequest value) {
        return new JAXBElement<InputRequest>(_InputRequest_QNAME, InputRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OutputResponseString }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SwaTestService.org/xsd", name = "OutputResponseString")
    public JAXBElement<OutputResponseString> createOutputResponseString(OutputResponseString value) {
        return new JAXBElement<OutputResponseString>(_OutputResponseString_QNAME, OutputResponseString.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InputRequestWithHeader }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SwaTestService.org/xsd", name = "InputRequestWithHeader")
    public JAXBElement<InputRequestWithHeader> createInputRequestWithHeader(InputRequestWithHeader value) {
        return new JAXBElement<InputRequestWithHeader>(_InputRequestWithHeader_QNAME, InputRequestWithHeader.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MyHeader }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SwaTestService.org/xsd", name = "MyHeader")
    public JAXBElement<MyHeader> createMyHeader(MyHeader value) {
        return new JAXBElement<MyHeader>(_MyHeader_QNAME, MyHeader.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InputRequestGet }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SwaTestService.org/xsd", name = "InputRequestGet")
    public JAXBElement<InputRequestGet> createInputRequestGet(InputRequestGet value) {
        return new JAXBElement<InputRequestGet>(_InputRequestGet_QNAME, InputRequestGet.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InputRequestThrowAFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SwaTestService.org/xsd", name = "InputRequestThrowAFault")
    public JAXBElement<InputRequestThrowAFault> createInputRequestThrowAFault(InputRequestThrowAFault value) {
        return new JAXBElement<InputRequestThrowAFault>(_InputRequestThrowAFault_QNAME, InputRequestThrowAFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OutputResponseAll }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SwaTestService.org/xsd", name = "OutputResponseAll")
    public JAXBElement<OutputResponseAll> createOutputResponseAll(OutputResponseAll value) {
        return new JAXBElement<OutputResponseAll>(_OutputResponseAll_QNAME, OutputResponseAll.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VoidRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SwaTestService.org/xsd", name = "VoidRequest")
    public JAXBElement<VoidRequest> createVoidRequest(VoidRequest value) {
        return new JAXBElement<VoidRequest>(_VoidRequest_QNAME, VoidRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InputRequestString }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SwaTestService.org/xsd", name = "InputRequestString")
    public JAXBElement<InputRequestString> createInputRequestString(InputRequestString value) {
        return new JAXBElement<InputRequestString>(_InputRequestString_QNAME, InputRequestString.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MyFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SwaTestService.org/xsd", name = "MyFaultReason")
    public JAXBElement<MyFaultType> createMyFaultReason(MyFaultType value) {
        return new JAXBElement<MyFaultType>(_MyFaultReason_QNAME, MyFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OutputResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SwaTestService.org/xsd", name = "OutputResponse")
    public JAXBElement<OutputResponse> createOutputResponse(OutputResponse value) {
        return new JAXBElement<OutputResponse>(_OutputResponse_QNAME, OutputResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InputRequestPut }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SwaTestService.org/xsd", name = "InputRequestPut")
    public JAXBElement<InputRequestPut> createInputRequestPut(InputRequestPut value) {
        return new JAXBElement<InputRequestPut>(_InputRequestPut_QNAME, InputRequestPut.class, null, value);
    }

}
