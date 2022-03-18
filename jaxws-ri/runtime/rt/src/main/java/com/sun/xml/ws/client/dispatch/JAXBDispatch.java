/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client.dispatch;

import org.glassfish.jaxb.runtime.api.JAXBRIContext;
import com.sun.xml.ws.api.addressing.WSEndpointReference;
import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.api.message.Headers;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.Messages;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.client.WSPortInfo;
import com.sun.xml.ws.binding.BindingImpl;
import com.sun.xml.ws.client.WSServiceDelegate;
import com.sun.xml.ws.message.jaxb.JAXBDispatchMessage;
import com.sun.xml.ws.spi.db.BindingContextFactory;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceException;

/**
 * The <code>JAXBDispatch</code> class provides support
 * for the dynamic invocation of a service endpoint operation using
 * JAXB objects. The <code>jakarta.xml.ws.Service</code>
 * interface acts as a factory for the creation of <code>JAXBDispatch</code>
 * instances.
 *
 * @author WS Development Team
 * @version 1.0
 */
public class JAXBDispatch extends DispatchImpl<Object> {

    private final JAXBContext jaxbcontext;

    // We will support a JAXBContext parameter from an unknown JAXB
    // implementation by marshaling and unmarshaling directly from the
    // context object, as there is no Bond available.
    private final boolean isContextSupported;

    @Deprecated
    public JAXBDispatch(QName port, JAXBContext jc, Service.Mode mode, WSServiceDelegate service, Tube pipe, BindingImpl binding, WSEndpointReference epr) {
        super(port, mode, service, pipe, binding, epr);
        this.jaxbcontext = jc;
        this.isContextSupported = BindingContextFactory.isContextSupported(jc);
    }

    public JAXBDispatch(WSPortInfo portInfo, JAXBContext jc, Service.Mode mode, BindingImpl binding, WSEndpointReference epr) {
        super(portInfo, mode, binding, epr);
        this.jaxbcontext = jc;
        this.isContextSupported = BindingContextFactory.isContextSupported(jc);
    }

    @Override
    Object toReturnValue(Packet response) {
        try {
            Unmarshaller unmarshaller = jaxbcontext.createUnmarshaller();
            Message msg = response.getMessage();
            switch (mode) {
                case PAYLOAD:
                    return msg.<Object>readPayloadAsJAXB(unmarshaller);
                case MESSAGE:
                    Source result = msg.readEnvelopeAsSource();
                    return unmarshaller.unmarshal(result);
                default:
                    throw new WebServiceException("Unrecognized dispatch mode");
            }
        } catch (JAXBException e) {
            throw new WebServiceException(e);
        }
    }


    @Override
    Packet createPacket(Object msg) {
        assert jaxbcontext != null;

        Message message;
        if (mode == Service.Mode.MESSAGE) {
            message = isContextSupported ?
                    new JAXBDispatchMessage(BindingContextFactory.create(jaxbcontext), msg, soapVersion) :
                    new JAXBDispatchMessage(jaxbcontext, msg, soapVersion);
        } else {
            if (msg == null) {
                message = Messages.createEmpty(soapVersion);
            } else {
                message = isContextSupported ?
                        Messages.create(jaxbcontext, msg, soapVersion) :
                        Messages.createRaw(jaxbcontext, msg, soapVersion);
            }
        }

        return new Packet(message);

    }

    @Override
    public void setOutboundHeaders(Object... headers) {
        if (headers == null)
            throw new IllegalArgumentException();
        Header[] hl = new Header[headers.length];
        for (int i = 0; i < hl.length; i++) {
            if (headers[i] == null)
                throw new IllegalArgumentException();
            // TODO: handle any JAXBContext.
            hl[i] = Headers.create(jaxbcontext, headers[i]);
        }
        super.setOutboundHeaders(hl);
    }
}
