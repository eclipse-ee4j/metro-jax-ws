/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.fault;

import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.util.DOMUtil;
import jakarta.xml.soap.DetailEntry;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import jakarta.xml.bind.annotation.*;
import javax.xml.namespace.QName;
import jakarta.xml.soap.Detail;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPFault;
import jakarta.xml.ws.WebServiceException;
import java.util.Iterator;

/**
 * This class represents SOAP1.1 Fault. This class will be used to marshall/unmarshall a soap fault using JAXB.
 * <br>
 * <pre>
 * Example:
 * <br>
 *     &lt;soap:Fault xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>
 *         &lt;faultcode>soap:Client&lt;/faultcode>
 *         &lt;faultstring>Invalid message format&lt;/faultstring>
 *         &lt;faultactor>http://example.org/someactor&lt;/faultactor>
 *         &lt;detail>
 *             &lt;m:msg xmlns:m='http://example.org/faults/exceptions'>
 *                 Test message
 *             &lt;/m:msg>
 *         &lt;/detail>
 *     &lt;/soap:Fault>
 * <br>
 * Above, m:msg, if a known fault (described in the WSDL), IOW, if m:msg is known by JAXBContext it should be unmarshalled into a
 * Java object otherwise it should be deserialized as {@link jakarta.xml.soap.Detail}
 * </pre>
 * <br>
 *
 * @author Vivek Pandey
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "faultcode",
        "faultstring",
        "faultactor",
        "detail"
        })
@XmlRootElement(name = "Fault", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
class SOAP11Fault extends SOAPFaultBuilder {
    @XmlElement(namespace = "")
    private QName faultcode;

    @XmlElement(namespace = "")
    private String faultstring;

    @XmlElement(namespace = "")
    private String faultactor;

    @XmlElement(namespace = "")
    private DetailType detail;

    SOAP11Fault() {
    }

    /**
     * This constructor takes soap fault detail among other things. The detail could represent {@link jakarta.xml.soap.Detail}
     * or a java object that can be marshalled/unmarshalled by JAXB.
     *
     */
    SOAP11Fault(QName code, String reason, String actor, Element detailObject) {
        this.faultcode = code;
        this.faultstring = reason;
        this.faultactor = actor;
        if (detailObject != null) {
            if ((detailObject.getNamespaceURI() == null ||
                 "".equals(detailObject.getNamespaceURI())) && "detail".equals(detailObject.getLocalName())) {
                detail = new DetailType();
                for(Element detailEntry : DOMUtil.getChildElements(detailObject)) {
                    detail.getDetails().add(detailEntry);
                }
            } else {
                detail = new DetailType(detailObject);
            }
        }
    }

    SOAP11Fault(SOAPFault fault) {
        this.faultcode = fault.getFaultCodeAsQName();
        this.faultstring = fault.getFaultString();
        this.faultactor = fault.getFaultActor();
        if (fault.getDetail() != null) {
            detail = new DetailType();
            Iterator<DetailEntry> iter = fault.getDetail().getDetailEntries();
            while(iter.hasNext()){
                Element fd = iter.next();
                detail.getDetails().add(fd);
            }
        }
    }

    QName getFaultcode() {
        return faultcode;
    }

    void setFaultcode(QName faultcode) {
        this.faultcode = faultcode;
    }

    @Override
    String getFaultString() {
        return faultstring;
    }

    void setFaultstring(String faultstring) {
        this.faultstring = faultstring;
    }

    String getFaultactor() {
        return faultactor;
    }

    void setFaultactor(String faultactor) {
        this.faultactor = faultactor;
    }

    /**
     * returns the object that represents detail.
     */
    @Override
    DetailType getDetail() {
        return detail;
    }

    @Override
    void setDetail(DetailType detail) {
        this.detail = detail;
    }

    @Override
    protected Throwable getProtocolException() {
        try {
            SOAPFault fault = SOAPVersion.SOAP_11.getSOAPFactory().createFault(faultstring, faultcode);
            fault.setFaultActor(faultactor);
            if(detail != null){
                Detail d = fault.addDetail();
                for(Element det : detail.getDetails()){
                    Node n = fault.getOwnerDocument().importNode(det, true);
                    d.appendChild(n);
                }
            }
            return new ServerSOAPFaultException(fault);
        } catch (SOAPException e) {
            throw new WebServiceException(e);
        }
    }
}
