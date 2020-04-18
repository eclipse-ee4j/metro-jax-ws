/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.developer;

import com.sun.istack.NotNull;
import com.sun.xml.ws.addressing.v200408.MemberSubmissionAddressingConstants;
import com.sun.xml.ws.wsdl.parser.WSDLConstants;
import org.w3c.dom.Element;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAnyAttribute;
import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
import javax.xml.namespace.QName;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import jakarta.xml.ws.EndpointReference;
import jakarta.xml.ws.WebServiceException;
import java.util.List;
import java.util.Map;

/**
 * Data model for Member Submission WS-Addressing specification. This is modeled after the
 * member submission schema at:
 *
 *  http://schemas.xmlsoap.org/ws/2004/08/addressing/
 *
 * @author Kathy Walsh
 * @author Vivek Pandey
 */

@XmlRootElement(name = "EndpointReference", namespace = MemberSubmissionEndpointReference.MSNS)
@XmlType(name = "EndpointReferenceType", namespace = MemberSubmissionEndpointReference.MSNS)
public final class MemberSubmissionEndpointReference extends EndpointReference implements MemberSubmissionAddressingConstants {

    private final static ContextClassloaderLocal<JAXBContext> msjc = new ContextClassloaderLocal<JAXBContext>() {
        @Override
        protected JAXBContext initialValue() throws Exception {
            return MemberSubmissionEndpointReference.getMSJaxbContext();
        }
    };

    public MemberSubmissionEndpointReference() {
    }

    /**
     * construct an EPR from infoset representation
     *
     * @param source A source object containing valid XmlInfoset
     *               instance consistent with the Member Submission WS-Addressing
     * @throws jakarta.xml.ws.WebServiceException
     *                              if the source does not contain a valid W3C WS-Addressing
     *                              EndpointReference.
     * @throws WebServiceException if the <code>null</code> <code>source</code> value is given
     */
    public MemberSubmissionEndpointReference(@NotNull Source source) {

        if (source == null) {
            throw new WebServiceException("Source parameter can not be null on constructor");
        }

        try {
            Unmarshaller unmarshaller = MemberSubmissionEndpointReference.msjc.get().createUnmarshaller();
            MemberSubmissionEndpointReference epr = unmarshaller.unmarshal(source,MemberSubmissionEndpointReference.class).getValue();

            this.addr = epr.addr;
            this.referenceProperties = epr.referenceProperties;
            this.referenceParameters = epr.referenceParameters;
            this.portTypeName = epr.portTypeName;
            this.serviceName = epr.serviceName;
            this.attributes = epr.attributes;
            this.elements = epr.elements;
        } catch (JAXBException e) {
            throw new WebServiceException("Error unmarshalling MemberSubmissionEndpointReference ", e);
        } catch (ClassCastException e) {
            throw new WebServiceException("Source did not contain MemberSubmissionEndpointReference", e);
        }
    }

    @Override
    public void writeTo(Result result) {
        try {
            Marshaller marshaller = MemberSubmissionEndpointReference.msjc.get().createMarshaller();
            //marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.marshal(this, result);
        } catch (JAXBException e) {
            throw new WebServiceException("Error marshalling W3CEndpointReference. ", e);
        }
    }

    /**
     * Constructs a Source containing the wsdl from the MemberSubmissionEndpointReference
     *
     * @return Source A source object containing the wsdl in the MemeberSubmissionEndpointReference, if present.
     */
    public Source toWSDLSource() {        
        Element wsdlElement = null;

        for (Element elem : elements) {
            if (elem.getNamespaceURI().equals(WSDLConstants.NS_WSDL) &&
                    elem.getLocalName().equals(WSDLConstants.QNAME_DEFINITIONS.getLocalPart())) {
                wsdlElement = elem;
            }
        }

        return new DOMSource(wsdlElement);
    }


    private static JAXBContext getMSJaxbContext() {
        try {
            return JAXBContext.newInstance(MemberSubmissionEndpointReference.class);
        } catch (JAXBException e) {
            throw new WebServiceException("Error creating JAXBContext for MemberSubmissionEndpointReference. ", e);
        }
    }

    @XmlElement(name = "Address", namespace = MemberSubmissionEndpointReference.MSNS)
    public Address addr;

    @XmlElement(name = "ReferenceProperties", namespace = MemberSubmissionEndpointReference.MSNS)
    public Elements referenceProperties;

    @XmlElement(name = "ReferenceParameters", namespace = MemberSubmissionEndpointReference.MSNS)
    public Elements referenceParameters;

    @XmlElement(name = "PortType", namespace = MemberSubmissionEndpointReference.MSNS)
    public AttributedQName portTypeName;

    @XmlElement(name = "ServiceName", namespace = MemberSubmissionEndpointReference.MSNS)
    public ServiceNameType serviceName;

    @XmlAnyAttribute
    public Map<QName,String> attributes;

    @XmlAnyElement
    public List<Element> elements;
    
    @XmlType(name="address", namespace=MemberSubmissionEndpointReference.MSNS)
    public static class Address {
        public Address() {
        }

        @XmlValue
        public String uri;
        @XmlAnyAttribute
        public Map<QName, String> attributes;
    }

    @XmlType(name="elements", namespace=MemberSubmissionEndpointReference.MSNS)
    public static class Elements {
        public Elements() {}

        @XmlAnyElement
        public List<Element> elements;
    }


    public static class AttributedQName {
        public AttributedQName() {
        }

        @XmlValue
        public QName name;
        @XmlAnyAttribute
        public Map<QName, String> attributes;
    }

    public static class ServiceNameType extends AttributedQName{
        public ServiceNameType() {
        }

        @XmlAttribute(name="PortName")
        public String portName;
    }

    protected static final String MSNS = "http://schemas.xmlsoap.org/ws/2004/08/addressing";
}
