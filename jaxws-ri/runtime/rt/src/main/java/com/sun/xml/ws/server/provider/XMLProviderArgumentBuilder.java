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

import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.Messages;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.encoding.xml.XMLMessage;
import com.sun.xml.ws.resources.ServerMessages;

import jakarta.activation.DataSource;
import javax.xml.transform.Source;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.http.HTTPException;

/**
 * @author Jitendra Kotamraju
 */
abstract class XMLProviderArgumentBuilder<T> extends ProviderArgumentsBuilder<T> {

    @Override
    protected Packet getResponse(Packet request, Exception e, WSDLPort port, WSBinding binding) {
        Packet response = super.getResponse(request, e, port, binding);
        if (e instanceof HTTPException) {
            if (response.supports(MessageContext.HTTP_RESPONSE_CODE)) {
                response.put(MessageContext.HTTP_RESPONSE_CODE, ((HTTPException)e).getStatusCode());
            }
        }
        return response;
    }

    static XMLProviderArgumentBuilder createBuilder(ProviderEndpointModel model, WSBinding binding) {
        if (model.mode == Service.Mode.PAYLOAD) {
            return new PayloadSource();
        } else {
            if(model.datatype==Source.class)
                return new PayloadSource();
            if(model.datatype== DataSource.class)
                return new DataSourceParameter(binding);
            throw new WebServiceException(ServerMessages.PROVIDER_INVALID_PARAMETER_TYPE(model.implClass,model.datatype));
        }
    }

    private static final class PayloadSource extends XMLProviderArgumentBuilder<Source> {
        @Override
        public Source getParameter(Packet packet) {
            return packet.getMessage().readPayloadAsSource();
        }

        @Override
        public Message getResponseMessage(Source source) {
            return Messages.createUsingPayload(source, SOAPVersion.SOAP_11);
        }

        @Override
        protected Message getResponseMessage(Exception e) {
            return XMLMessage.create(e);
        }
    }

    private static final class DataSourceParameter extends XMLProviderArgumentBuilder<DataSource> {
        private final WSBinding binding;

        DataSourceParameter(WSBinding binding) {
            this.binding = binding;
        }
        @Override
        public DataSource getParameter(Packet packet) {
            Message msg = packet.getInternalMessage();
            return (msg instanceof XMLMessage.MessageDataSource)
                    ? ((XMLMessage.MessageDataSource) msg).getDataSource()
                    : XMLMessage.getDataSource(msg, binding.getFeatures());
        }

        @Override
        public Message getResponseMessage(DataSource ds) {
            return XMLMessage.create(ds, binding.getFeatures());
        }

        @Override
        protected Message getResponseMessage(Exception e) {
            return XMLMessage.create(e);
        }
    }

}
