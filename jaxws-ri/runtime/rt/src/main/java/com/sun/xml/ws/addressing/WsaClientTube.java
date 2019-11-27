/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.addressing;

import com.sun.istack.NotNull;
import com.sun.xml.ws.addressing.model.ActionNotSupportedException;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.message.AddressingUtils;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.pipe.NextAction;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.pipe.TubeCloner;
import com.sun.xml.ws.resources.AddressingMessages;

import javax.xml.ws.WebServiceException;

/**
 * WsaClientTube appears in the Tubeline only if addressing is enabled.
 * This tube checks the validity of addressing headers in the incoming messages
 * based on the WSDL model.
 * @author Rama Pulavarthi
 * @author Arun Gupta
 */
public class WsaClientTube extends WsaTube {
    // capture if the request expects a reply so that it can be used to
    // determine if its oneway for response validation.
    protected boolean expectReply = true;
    public WsaClientTube(WSDLPort wsdlPort, WSBinding binding, Tube next) {
        super(wsdlPort, binding, next);
    }

    public WsaClientTube(WsaClientTube that, TubeCloner cloner) {
        super(that, cloner);
    }

    public WsaClientTube copy(TubeCloner cloner) {
        return new WsaClientTube(this, cloner);
    }

    @Override
    public @NotNull NextAction processRequest(Packet request) {
        expectReply = request.expectReply;
        return doInvoke(next,request);
   }

    @Override
    public @NotNull NextAction processResponse(Packet response) {
        // if one-way then, no validation
        if (response.getMessage() != null) {
            response = validateInboundHeaders(response);
            response.addSatellite(new WsaPropertyBag(addressingVersion,soapVersion,response));
            String msgId = AddressingUtils.
              getMessageID(response.getMessage().getHeaders(),
                      addressingVersion, soapVersion);
            response.put(WsaPropertyBag.WSA_MSGID_FROM_REQUEST, msgId);
        }

        return doReturnWith(response);
    }


    @Override
    protected void validateAction(Packet packet) {
        //There may not be a WSDL operation.  There may not even be a WSDL.
        //For instance this may be a RM CreateSequence message.
        WSDLBoundOperation wbo = getWSDLBoundOperation(packet);

        if (wbo == null)    return;

        String gotA = AddressingUtils.getAction(
                packet.getMessage().getHeaders(),
                addressingVersion, soapVersion);
        if (gotA == null)
            throw new WebServiceException(AddressingMessages.VALIDATION_CLIENT_NULL_ACTION());

        String expected = helper.getOutputAction(packet);

        if (expected != null && !gotA.equals(expected))
            throw new ActionNotSupportedException(gotA);
    }

}
