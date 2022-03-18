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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPFault;
import jakarta.xml.ws.WebServiceException;
import java.util.Iterator;

/**
 * SOAP 1.2 Fault class that can be marshalled/unmarshalled by JAXB
 * <br>
 * <pre>
 * Example:
 * &lt;env:Envelope xmlns:env="http://www.w3.org/2003/05/soap-envelope"
 *            xmlns:m="http://www.example.org/timeouts"
 *            xmlns:xml="http://www.w3.org/XML/1998/namespace">
 * &lt;env:Body>
 *     &lt;env:Fault>
 *         &lt;env:Code>
 *             &lt;env:Value>env:Sender* &lt;/env:Value>
 *             &lt;env:Subcode>
 *                 &lt;env:Value>m:MessageTimeout* &lt;/env:Value>
 *             &lt;/env:Subcode>
 *         &lt;/env:Code>
 *         &lt;env:Reason>
 *             &lt;env:Text xml:lang="en">Sender Timeout* &lt;/env:Text>
 *         &lt;/env:Reason>
 *         &lt;env:Detail>
 *             &lt;m:MaxTime>P5M* &lt;/m:MaxTime>
 *         &lt;/env:Detail>
 *     &lt;/env:Fault>
 * &lt;/env:Body>
 * &lt;/env:Envelope>
 * </pre>
 *
 * @author Vivek Pandey
 */
@XmlRootElement(name = "Fault", namespace = "http://www.w3.org/2003/05/soap-envelope")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "code",
    "reason",
    "node",
    "role",
    "detail"
})
class SOAP12Fault extends SOAPFaultBuilder {
    @XmlTransient
    private static final String ns = "http://www.w3.org/2003/05/soap-envelope";

    @XmlElement(namespace=ns, name="Code")
    private CodeType code;

    @XmlElement(namespace=ns, name="Reason")
    private ReasonType reason;

    @XmlElement(namespace=ns, name="Node")
    private String node;

    @XmlElement(namespace=ns, name="Role")
    private String role;

    @XmlElement(namespace=ns, name="Detail")
    private DetailType detail;

    SOAP12Fault() {
    }

    SOAP12Fault(CodeType code, ReasonType reason, String node, String role, DetailType detail) {
        this.code = code;
        this.reason = reason;
        this.node = node;
        this.role = role;
        this.detail = detail;
    }

    SOAP12Fault(CodeType code, ReasonType reason, String node, String role, Element detailObject) {
        this.code = code;
        this.reason = reason;
        this.node = node;
        this.role = role;
        if (detailObject != null) {
            if(detailObject.getNamespaceURI().equals(ns) && detailObject.getLocalName().equals("Detail")){
                detail = new DetailType();
                for(Element detailEntry : DOMUtil.getChildElements(detailObject)){
                    detail.getDetails().add(detailEntry);
                }
            }else{
                detail = new DetailType(detailObject);
            }
        }
    }

    SOAP12Fault(SOAPFault fault) {
        code = new CodeType(fault.getFaultCodeAsQName());
        try {
            fillFaultSubCodes(fault);
        } catch (SOAPException e) {
            throw new WebServiceException(e);
        }

        reason = new ReasonType(fault.getFaultString());
        role = fault.getFaultRole();
        node = fault.getFaultNode();
        if (fault.getDetail() != null) {
            detail = new DetailType();
            Iterator<DetailEntry> iter = fault.getDetail().getDetailEntries();
            while(iter.hasNext()){
                Element fd = iter.next();
                detail.getDetails().add(fd);
            }
        }
    }

    SOAP12Fault(QName code, String reason, Element detailObject) {
        this(new CodeType(code), new ReasonType(reason), null, null, detailObject);
    }

    CodeType getCode() {
        return code;
    }

    ReasonType getReason() {
        return reason;
    }

    String getNode() {
        return node;
    }

    String getRole() {
        return role;
    }

    @Override
    DetailType getDetail() {
        return detail;
    }

    @Override
    void setDetail(DetailType detail) {
        this.detail = detail;
    }

    @Override
    String getFaultString() {
        return reason.texts().get(0).getText();
    }

     @Override
     protected Throwable getProtocolException() {
        try {
            SOAPFault fault = SOAPVersion.SOAP_12.getSOAPFactory().createFault();
            if(reason != null){
                for(TextType tt : reason.texts()){
                    fault.setFaultString(tt.getText());
                }
            }

            if(code != null){
                fault.setFaultCode(code.getValue());
                fillFaultSubCodes(fault, code.getSubcode());
            }

            if(detail != null && detail.getDetail(0) != null){
                jakarta.xml.soap.Detail detail = fault.addDetail();
                for(Node obj: this.detail.getDetails()){
                    Node n = fault.getOwnerDocument().importNode(obj, true);
                    detail.appendChild(n);
                }
            }

            if(node != null) {
                fault.setFaultNode(node);
            }

            return new ServerSOAPFaultException(fault);
        } catch (SOAPException e) {
            throw new WebServiceException(e);
        }
    }

    /**
     * Recursively populate the Subcodes
     */
    private void fillFaultSubCodes(SOAPFault fault, SubcodeType subcode) throws SOAPException {
        if(subcode != null){
            fault.appendFaultSubcode(subcode.getValue());
            fillFaultSubCodes(fault, subcode.getSubcode());
        }
    }

    /**
     * Adds Fault subcodes from {@link SOAPFault} to {@link #code}
     */
    private void fillFaultSubCodes(SOAPFault fault) throws SOAPException {
        Iterator<QName> subcodes = fault.getFaultSubcodes();
        SubcodeType firstSct = null;
        while(subcodes.hasNext()){
            QName subcode = subcodes.next();
            if(firstSct == null){
                firstSct = new SubcodeType(subcode);
                code.setSubcode(firstSct);
                continue;
            }
            SubcodeType nextSct = new SubcodeType(subcode);
            firstSct.setSubcode(nextSct);
            firstSct = nextSct;
        }
    }

}

