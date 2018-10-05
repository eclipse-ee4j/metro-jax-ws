/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.client.WSPortInfo;
import com.sun.xml.ws.binding.BindingImpl;
import com.sun.xml.ws.client.WSServiceDelegate;
import com.sun.xml.ws.client.PortInfo;
import com.sun.xml.ws.encoding.xml.XMLMessage;
import com.sun.xml.ws.encoding.xml.XMLMessage.MessageDataSource;
import com.sun.xml.ws.message.source.PayloadSourceMessage;

import javax.activation.DataSource;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;

/**
 *
 * @author WS Development Team
 * @version 1.0
 */
public class DataSourceDispatch extends DispatchImpl<DataSource> {
    @Deprecated
    public DataSourceDispatch(QName port, Service.Mode mode, WSServiceDelegate service, Tube pipe, BindingImpl binding, WSEndpointReference epr) {
       super(port, mode, service, pipe, binding, epr );
    }

    public DataSourceDispatch(WSPortInfo portInfo, Service.Mode mode,BindingImpl binding, WSEndpointReference epr) {
       super(portInfo, mode, binding, epr );
    }

    Packet createPacket(DataSource arg) {

         switch (mode) {
            case PAYLOAD:
                throw new IllegalArgumentException("DataSource use is not allowed in Service.Mode.PAYLOAD\n");
            case MESSAGE:
                return new Packet(XMLMessage.create(arg, binding.getFeatures()));
            default:
                throw new WebServiceException("Unrecognized message mode");
        }
    }

    DataSource toReturnValue(Packet response) {
        Message message = response.getInternalMessage();
        return (message instanceof MessageDataSource)
                ? ((MessageDataSource)message).getDataSource()
                : XMLMessage.getDataSource(message, binding.getFeatures());
    }
}
