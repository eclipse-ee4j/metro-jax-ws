/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.server.provider;

import com.sun.istack.Nullable;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.Messages;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.fault.SOAPFaultBuilder;
import com.sun.xml.ws.resources.ServerMessages;

import jakarta.xml.soap.MimeHeader;
import jakarta.xml.soap.MimeHeaders;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Jitendra Kotamraju
 */
abstract class SOAPProviderArgumentBuilder<T> extends ProviderArgumentsBuilder<T> {
    protected final SOAPVersion soapVersion;

    private SOAPProviderArgumentBuilder(SOAPVersion soapVersion) {
        this.soapVersion = soapVersion;
    }

    static ProviderArgumentsBuilder create(ProviderEndpointModel model, SOAPVersion soapVersion) {
        if (model.mode == Service.Mode.PAYLOAD) {
            return new PayloadSource(soapVersion);
        } else {
            if(model.datatype==Source.class)
                return new MessageSource(soapVersion);
            if(model.datatype==SOAPMessage.class)
                return new SOAPMessageParameter(soapVersion);
            if(model.datatype==Message.class)
                return new MessageProviderArgumentBuilder(soapVersion);
            throw new WebServiceException(ServerMessages.PROVIDER_INVALID_PARAMETER_TYPE(model.implClass,model.datatype));
        }
    }

    private static final class PayloadSource extends SOAPProviderArgumentBuilder<Source> {
        PayloadSource(SOAPVersion soapVersion) {
            super(soapVersion);
        }

        /*protected*/ public Source getParameter(Packet packet) {
            return packet.getMessage().readPayloadAsSource();
        }

        protected Message getResponseMessage(Source source) {
            return Messages.createUsingPayload(source, soapVersion);
        }

        protected Message getResponseMessage(Exception e) {
            return SOAPFaultBuilder.createSOAPFaultMessage(soapVersion, null, e);
        }

    }

    private static final class MessageSource extends SOAPProviderArgumentBuilder<Source> {
        MessageSource(SOAPVersion soapVersion) {
            super(soapVersion);
        }

        /*protected*/ public Source getParameter(Packet packet) {
            return packet.getMessage().readEnvelopeAsSource();
        }

        protected Message getResponseMessage(Source source) {
            return Messages.create(source, soapVersion);
        }

        protected Message getResponseMessage(Exception e) {
            return SOAPFaultBuilder.createSOAPFaultMessage(soapVersion, null, e);
        }
    }

    private static final class SOAPMessageParameter extends SOAPProviderArgumentBuilder<SOAPMessage> {
        SOAPMessageParameter(SOAPVersion soapVersion) {
            super(soapVersion);
        }

        /*protected*/ public SOAPMessage getParameter(Packet packet) {
            try {
                return packet.getMessage().readAsSOAPMessage(packet, true);
            } catch (SOAPException se) {
                throw new WebServiceException(se);
            }
        }

        protected Message getResponseMessage(SOAPMessage soapMsg) {
            return Messages.create(soapMsg);
        }

        protected Message getResponseMessage(Exception e) {
            return SOAPFaultBuilder.createSOAPFaultMessage(soapVersion, null, e);
        }

        @Override
        protected Packet getResponse(Packet request, @Nullable SOAPMessage returnValue, WSDLPort port, WSBinding binding) {
            Packet response = super.getResponse(request, returnValue, port, binding);
            // Populate SOAPMessage's transport headers
            if (returnValue != null && response.supports(Packet.OUTBOUND_TRANSPORT_HEADERS)) {
                MimeHeaders hdrs = returnValue.getMimeHeaders();
                Map<String, List<String>> headers = new HashMap<>();
                Iterator i = hdrs.getAllHeaders();
                while(i.hasNext()) {
                    MimeHeader header = (MimeHeader)i.next();
                    if(header.getName().equalsIgnoreCase("SOAPAction"))
                        // SAAJ sets this header automatically, but it interferes with the correct operation of JAX-WS.
                        // so ignore this header.
                        continue;

                    List<String> list = headers.computeIfAbsent(header.getName(), k -> new ArrayList<>());
                    list.add(header.getValue());
                }
                response.put(Packet.OUTBOUND_TRANSPORT_HEADERS, headers);
            }
            return response;
        }

    }

}
