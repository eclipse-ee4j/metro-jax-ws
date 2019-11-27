/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.addressing.v200408;

import com.sun.istack.NotNull;
import com.sun.xml.ws.addressing.WsaServerTube;
import com.sun.xml.ws.addressing.model.MissingAddressingHeaderException;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.pipe.TubeCloner;
import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.developer.MemberSubmissionAddressing;
import com.sun.xml.ws.developer.MemberSubmissionAddressingFeature;

/**
 * @author Rama Pulavarthi
 */
public class MemberSubmissionWsaServerTube extends WsaServerTube {
    private final MemberSubmissionAddressing.Validation validation;
    public MemberSubmissionWsaServerTube(WSEndpoint endpoint, @NotNull WSDLPort wsdlPort, WSBinding binding, Tube next) {
        super(endpoint, wsdlPort, binding, next);
        validation = binding.getFeature(MemberSubmissionAddressingFeature.class).getValidation();
    }

    public MemberSubmissionWsaServerTube(MemberSubmissionWsaServerTube that, TubeCloner cloner) {
        super(that, cloner);
        this.validation = that.validation;
    }

    @Override
    public MemberSubmissionWsaServerTube copy(TubeCloner cloner) {
        return new MemberSubmissionWsaServerTube(this, cloner);
    }

    @Override
    protected void checkMandatoryHeaders(Packet packet, boolean foundAction, boolean foundTo, boolean foundReplyTo,
            boolean foundFaultTo, boolean foundMessageId, boolean foundRelatesTo) {

        super.checkMandatoryHeaders(packet, foundAction, foundTo, foundReplyTo,
                foundFaultTo, foundMessageId, foundRelatesTo);
        
        // if no wsa:To header is found
        if (!foundTo)
            throw new MissingAddressingHeaderException(addressingVersion.toTag,packet);

        //we can find Req/Response or Oneway only with WSDLModel
        if (wsdlPort != null) {
            WSDLBoundOperation wbo = getWSDLBoundOperation(packet);
            // if two-way, must contain wsa:ReplyTo
            // Unlike W3C version, we cannot assume default value as anonymous if not present.
            // For protocol messages, don't check as they do not have any corresponding wsdl operations
            if (wbo != null && !wbo.getOperation().isOneWay() && !foundReplyTo) {
                throw new MissingAddressingHeaderException(addressingVersion.replyToTag,packet);
            }
        }
        if (!validation.equals(MemberSubmissionAddressing.Validation.LAX)) {
            // wsa:MessageId is required if wsa:ReplyTo is present.
            if ((foundReplyTo || foundFaultTo) && !foundMessageId)
                throw new MissingAddressingHeaderException(addressingVersion.messageIDTag,packet);
        }
    }
}
