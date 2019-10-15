/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.protocol.soap;

import com.sun.xml.ws.api.SOAPVersion;
import static com.sun.xml.ws.api.SOAPVersion.SOAP_11;
import static com.sun.xml.ws.api.SOAPVersion.SOAP_12;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.MessageHeaders;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.pipe.TubeCloner;
import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
import com.sun.xml.ws.binding.SOAPBindingImpl;
import com.sun.xml.ws.message.DOMHeader;
import com.sun.xml.ws.fault.SOAPFaultBuilder;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.ws.soap.SOAPFaultException;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Rama Pulavarthi
 */

abstract class MUTube extends AbstractFilterTubeImpl {

    private static final String MU_FAULT_DETAIL_LOCALPART = "NotUnderstood";
    private final static QName MU_HEADER_DETAIL = new QName(SOAPVersion.SOAP_12.nsUri, MU_FAULT_DETAIL_LOCALPART);
    //TODO: change
    protected static final Logger logger = Logger.getLogger(
            com.sun.xml.ws.util.Constants.LoggingDomain + ".soap.decoder");
    private final static String MUST_UNDERSTAND_FAULT_MESSAGE_STRING =
            "One or more mandatory SOAP header blocks not understood";

    protected final SOAPVersion soapVersion;
    protected SOAPBindingImpl binding;

    protected MUTube(WSBinding binding, Tube next) {
        super(next);
        // MUPipe should n't be used for bindings other than SOAP.
        if (!(binding instanceof SOAPBinding)) {
            throw new WebServiceException(
                    "MUPipe should n't be used for bindings other than SOAP.");
        }
        this.binding = (SOAPBindingImpl) binding;
        this.soapVersion = binding.getSOAPVersion();
    }

    protected MUTube(MUTube that, TubeCloner cloner) {
        super(that, cloner);
        binding = that.binding;
        soapVersion = that.soapVersion;
    }

    /**
     * @param headers HeaderList that needs MU processing
     * @param roles        Roles configured on the Binding. Required Roles supposed to be assumbed a by a
     *                     SOAP Binding implementation are added.
     * @param handlerKnownHeaders Set of headers that the handlerchain associated with the binding understands
     * @return returns the headers that have mustUnderstand attribute and are not understood
     *         by the binding.
     */
    public final Set<QName> getMisUnderstoodHeaders(MessageHeaders headers, Set<String> roles,
                                                    Set<QName> handlerKnownHeaders) {
        return headers.getNotUnderstoodHeaders(roles, handlerKnownHeaders, binding);

    }

    /**
     * @param notUnderstoodHeaders
     * @return SOAPfaultException with SOAPFault representing the MustUnderstand SOAP Fault.
     *         notUnderstoodHeaders are added in the fault detail.
     */
    final SOAPFaultException createMUSOAPFaultException(Set<QName> notUnderstoodHeaders) {
        try {
            SOAPFault fault = soapVersion.getSOAPFactory().createFault(
                MUST_UNDERSTAND_FAULT_MESSAGE_STRING,
                soapVersion.faultCodeMustUnderstand);
            fault.setFaultString("MustUnderstand headers:" +
                notUnderstoodHeaders + " are not understood");
            return new SOAPFaultException(fault);
        } catch (SOAPException e) {
            throw new WebServiceException(e);
        }
    }

    /**
     * This should be used only in ServerMUPipe
     *
     * @param notUnderstoodHeaders
     * @return Message representing a SOAPFault
     *         In SOAP 1.1, notUnderstoodHeaders are added in the fault Detail
     *         in SOAP 1.2, notUnderstoodHeaders are added as the SOAP Headers
     */

    final Message createMUSOAPFaultMessage(Set<QName> notUnderstoodHeaders) {
        try {
            String faultString = MUST_UNDERSTAND_FAULT_MESSAGE_STRING;
            if (soapVersion == SOAP_11) {
                faultString = "MustUnderstand headers:" + notUnderstoodHeaders + " are not understood";
            }
            Message  muFaultMessage = SOAPFaultBuilder.createSOAPFaultMessage(
                    soapVersion,faultString,soapVersion.faultCodeMustUnderstand);

            if (soapVersion == SOAP_12) {
                addHeader(muFaultMessage, notUnderstoodHeaders);
            }
            return muFaultMessage;
        } catch (SOAPException e) {
            throw new WebServiceException(e);
        }
    }

    private static void addHeader(Message m, Set<QName> notUnderstoodHeaders) throws SOAPException {
        for (QName qname : notUnderstoodHeaders) {
            SOAPElement soapEl = SOAP_12.getSOAPFactory().createElement(MU_HEADER_DETAIL);
            soapEl.addNamespaceDeclaration("abc", qname.getNamespaceURI());
            soapEl.setAttribute("qname", "abc:" + qname.getLocalPart());
            Header header = new DOMHeader<Element>(soapEl);
            m.getHeaders().add(header);
        }
    }
}
