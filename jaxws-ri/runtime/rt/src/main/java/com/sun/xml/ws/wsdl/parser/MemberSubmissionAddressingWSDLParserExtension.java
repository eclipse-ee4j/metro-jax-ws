/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.wsdl.parser;

import com.sun.xml.ws.api.addressing.AddressingVersion;
import com.sun.xml.ws.developer.MemberSubmissionAddressingFeature;
import com.sun.xml.ws.api.model.wsdl.WSDLFeaturedObject;
import com.sun.xml.ws.api.model.wsdl.editable.*;
import com.sun.xml.ws.streaming.XMLStreamReaderUtil;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;

/**
 * Member Submission WS-Addressing Runtime WSDL parser extension
 *
 * @author Arun Gupta
 */
public class MemberSubmissionAddressingWSDLParserExtension extends W3CAddressingWSDLParserExtension {
    @Override
    public boolean bindingElements(EditableWSDLBoundPortType binding, XMLStreamReader reader) {
        return addressibleElement(reader, binding);
    }

    @Override
    public boolean portElements(EditableWSDLPort port, XMLStreamReader reader) {
        return addressibleElement(reader, port);
    }

    private boolean addressibleElement(XMLStreamReader reader, WSDLFeaturedObject binding) {
        QName ua = reader.getName();
        if (ua.equals(AddressingVersion.MEMBER.wsdlExtensionTag)) {
            String required = reader.getAttributeValue(WSDLConstants.NS_WSDL, "required");
            binding.addFeature(new MemberSubmissionAddressingFeature(Boolean.parseBoolean(required)));
            XMLStreamReaderUtil.skipElement(reader);
            return true;        // UsingAddressing is consumed
        }

        return false;
    }

    @Override
    public boolean bindingOperationElements(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
        return false;
    }

    @Override
    protected void patchAnonymousDefault(EditableWSDLBoundPortType binding) {
    }

    @Override
    protected String getNamespaceURI() {
        return AddressingVersion.MEMBER.wsdlNsUri;
    }

    @Override
    protected QName getWsdlActionTag() {
        return  AddressingVersion.MEMBER.wsdlActionTag;
    }
}
