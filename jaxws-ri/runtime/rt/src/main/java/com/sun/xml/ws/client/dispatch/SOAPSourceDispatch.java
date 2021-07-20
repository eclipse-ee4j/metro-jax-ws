/*
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client.dispatch;

import com.sun.xml.ws.api.addressing.WSEndpointReference;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.Messages;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.client.WSPortInfo;
import com.sun.xml.ws.binding.BindingImpl;
import com.sun.xml.ws.client.WSServiceDelegate;
import com.sun.xml.ws.message.source.PayloadSourceMessage;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import jakarta.xml.ws.Service.Mode;
import jakarta.xml.ws.WebServiceException;


/**
 * The <code>SOAPSourceDispatch</code> class provides support
 * for the dynamic invocation of a service endpoint operation using XML
 * constructs. The <code>jakarta.xml.ws.Service</code>
 * interface acts as a factory for the creation of <code>SOAPSourceDispatch</code>
 * instances.
 *
 * @author WS Development Team
 * @version 1.0
 * @see RESTSourceDispatch
 */
final class SOAPSourceDispatch extends DispatchImpl<Source> {
    @Deprecated
    public SOAPSourceDispatch(QName port, Mode mode, WSServiceDelegate owner, Tube pipe, BindingImpl binding, WSEndpointReference epr) {
        super(port, mode, owner, pipe, binding, epr);
        assert !isXMLHttp(binding);
    }

    public SOAPSourceDispatch(WSPortInfo portInfo, Mode mode, BindingImpl binding, WSEndpointReference epr) {
            super(portInfo, mode, binding, epr);
            assert !isXMLHttp(binding);
    }


    Source toReturnValue(Packet response) {
        Message msg = response.getMessage();

        switch (mode) {
        case PAYLOAD:
            return msg.readPayloadAsSource();
        case MESSAGE:
            return msg.readEnvelopeAsSource();
        default:
            throw new WebServiceException("Unrecognized dispatch mode");
        }
    }

    @Override
    Packet createPacket(Source msg) {

        final Message message;

        if (msg == null)
            message = Messages.createEmpty(soapVersion);
        else {
            switch (mode) {
            case PAYLOAD:
                message = new PayloadSourceMessage(null, msg, setOutboundAttachments(), soapVersion);
                break;
            case MESSAGE:
                message = Messages.create(msg, soapVersion);
                break;
            default:
                throw new WebServiceException("Unrecognized message mode");
            }
        }

        return new Packet(message);
    }


}
