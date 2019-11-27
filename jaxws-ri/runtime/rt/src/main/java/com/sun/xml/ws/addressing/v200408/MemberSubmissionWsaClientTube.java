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

import com.sun.xml.ws.addressing.WsaClientTube;
import com.sun.xml.ws.addressing.model.MissingAddressingHeaderException;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.message.AddressingUtils;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.pipe.TubeCloner;
import com.sun.xml.ws.developer.MemberSubmissionAddressing;
import com.sun.xml.ws.developer.MemberSubmissionAddressingFeature;

/**
 * @author Rama Pulavarthi
 */
public class MemberSubmissionWsaClientTube extends WsaClientTube {
    private final MemberSubmissionAddressing.Validation validation;

    public MemberSubmissionWsaClientTube(WSDLPort wsdlPort, WSBinding binding, Tube next) {
        super(wsdlPort, binding, next);
        validation = binding.getFeature(MemberSubmissionAddressingFeature.class).getValidation();

    }

    public MemberSubmissionWsaClientTube(MemberSubmissionWsaClientTube that, TubeCloner cloner) {
        super(that, cloner);
        this.validation = that.validation;

    }
    public MemberSubmissionWsaClientTube copy(TubeCloner cloner) {
        return new MemberSubmissionWsaClientTube(this, cloner);
    }

    @Override
    protected void checkMandatoryHeaders(Packet packet, boolean foundAction, boolean foundTo, boolean foundReplyTo,
                                         boolean foundFaultTo, boolean foundMessageID, boolean foundRelatesTo) {
        super.checkMandatoryHeaders(packet,foundAction,foundTo,foundReplyTo,foundFaultTo,foundMessageID,foundRelatesTo);
        
        // if no wsa:To header is found
        if (!foundTo) {
            throw new MissingAddressingHeaderException(addressingVersion.toTag,packet);
        }

        if (!validation.equals(MemberSubmissionAddressing.Validation.LAX)) {

            // if it is not one-way, response must contain wsa:RelatesTo
            // RelatesTo required as per
            // Table 5-3 of http://www.w3.org/TR/2006/WD-ws-addr-wsdl-20060216/#wsdl11requestresponse
            if (expectReply && (packet.getMessage() != null) && !foundRelatesTo) {
                String action = AddressingUtils.getAction(packet.getMessage().getHeaders(), addressingVersion, soapVersion);
                // Don't check for AddressingFaults as
                // Faults for requests with duplicate MessageId will have no wsa:RelatesTo
                if (!packet.getMessage().isFault() || !action.equals(addressingVersion.getDefaultFaultAction())) {
                    throw new MissingAddressingHeaderException(addressingVersion.relatesToTag,packet);
                }
            }
        }
    }
}
