/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client.dispatch;

import com.sun.xml.ws.api.addressing.WSEndpointReference;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.message.saaj.SAAJFactory;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.client.WSPortInfo;
import com.sun.xml.ws.binding.BindingImpl;
import com.sun.xml.ws.client.WSServiceDelegate;
import com.sun.xml.ws.client.PortInfo;
import com.sun.xml.ws.message.saaj.SAAJMessage;
import com.sun.xml.ws.resources.DispatchMessages;
import com.sun.xml.ws.transport.Headers;

import javax.xml.namespace.QName;
import jakarta.xml.soap.MimeHeader;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.handler.MessageContext;

import java.util.Iterator;

/**
 * The <code>SOAPMessageDispatch</code> class provides support
 * for the dynamic invocation of a service endpoint operation using
 * the <code>SOAPMessage</code> class. The <code>jakarta.xml.ws.Service</code>
 * interface acts as a factory for the creation of <code>SOAPMessageDispatch</code>
 * instances.
 *
 * @author WS Development Team
 * @version 1.0
 */
public class SOAPMessageDispatch extends com.sun.xml.ws.client.dispatch.DispatchImpl<SOAPMessage> {
    @Deprecated
    public SOAPMessageDispatch(QName port, Service.Mode mode, WSServiceDelegate owner, Tube pipe, BindingImpl binding, WSEndpointReference epr) {
        super(port, mode, owner, pipe, binding, epr);
    }

    public SOAPMessageDispatch(WSPortInfo portInfo, Service.Mode mode, BindingImpl binding, WSEndpointReference epr) {
        super(portInfo, mode, binding, epr);
    }

    Packet createPacket(SOAPMessage arg) {
        Iterator iter = arg.getMimeHeaders().getAllHeaders();
        Headers ch = new Headers();
        while(iter.hasNext()) {
            MimeHeader mh = (MimeHeader) iter.next();
            ch.add(mh.getName(), mh.getValue());
        }
        Packet packet = new Packet(SAAJFactory.create(arg));
        packet.invocationProperties.put(MessageContext.HTTP_REQUEST_HEADERS, ch);
        return packet;
    }

    SOAPMessage toReturnValue(Packet response) {
        try {

            //not sure if this is the correct way to deal with this.
            if ( response ==null || response.getMessage() == null )
                     throw new WebServiceException(DispatchMessages.INVALID_RESPONSE());
            else
                return response.getMessage().readAsSOAPMessage();            
        } catch (SOAPException e) {
            throw new WebServiceException(e);
        }
    }
}
