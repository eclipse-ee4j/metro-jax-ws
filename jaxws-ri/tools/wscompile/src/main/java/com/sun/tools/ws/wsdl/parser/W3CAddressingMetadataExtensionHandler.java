/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.parser;

import com.sun.tools.ws.wscompile.ErrorReceiver;
import com.sun.tools.ws.api.wsdl.TWSDLParserContext;
import com.sun.tools.ws.api.wsdl.TWSDLExtensible;
import com.sun.tools.ws.util.xml.XmlUtil;
import com.sun.tools.ws.wsdl.document.Input;
import com.sun.tools.ws.wsdl.document.Output;
import com.sun.tools.ws.wsdl.document.Fault;
import com.sun.tools.ws.resources.WsdlMessages;
import com.sun.xml.ws.api.addressing.AddressingVersion;
import static com.sun.xml.ws.addressing.W3CAddressingMetadataConstants.*;

import java.util.Map;

import org.w3c.dom.Element;
import org.xml.sax.Locator;

/**
 * This extension parses the WSDL Metadata extensibility elements in the wsdl definitions.
 *
 * This class looks for wsam:Action attribute on wsdl:input, wsdl:output, wsdl:fault elements and sets the action value
 * in the wsdl model so that it can be used to generate correpsonding annotations on SEI.
 *
 * @author Rama Pulavarthi
 */
public class W3CAddressingMetadataExtensionHandler extends AbstractExtensionHandler {
    private ErrorReceiver errReceiver;
    public W3CAddressingMetadataExtensionHandler(Map<String, AbstractExtensionHandler> extensionHandlerMap, ErrorReceiver errReceiver) {
        super(extensionHandlerMap);
        this.errReceiver = errReceiver;
    }

    @Override
    public String getNamespaceURI() {
        return WSAM_NAMESPACE_NAME;
    }

    @Override
    public boolean handleInputExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        String actionValue = XmlUtil.getAttributeNSOrNull(e, WSAM_ACTION_QNAME);
        if (actionValue == null || actionValue.equals("")) {
            return warnEmptyAction(parent, context.getLocation(e));
        }
        ((Input)parent).setAction(actionValue);
        return true;
    }

    @Override
    public boolean handleOutputExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        String actionValue = XmlUtil.getAttributeNSOrNull(e, WSAM_ACTION_QNAME);
        if (actionValue == null || actionValue.equals("")) {
            return warnEmptyAction(parent,context.getLocation(e));
        }
        ((Output)parent).setAction(actionValue);
        return true;
    }

    @Override
    public boolean handleFaultExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        String actionValue = XmlUtil.getAttributeNSOrNull(e, WSAM_ACTION_QNAME);
        if (actionValue == null || actionValue.equals("")) {
            errReceiver.warning(context.getLocation(e), WsdlMessages.WARNING_FAULT_EMPTY_ACTION(parent.getNameValue(), parent.getWSDLElementName().getLocalPart(), parent.getParent().getNameValue()));
            return false; // keep compiler happy
        }
        ((Fault)parent).setAction(actionValue);
        return true;
    }

    private boolean warnEmptyAction(TWSDLExtensible parent, Locator pos) {
        errReceiver.warning(pos, WsdlMessages.WARNING_INPUT_OUTPUT_EMPTY_ACTION(parent.getWSDLElementName().getLocalPart(), parent.getParent().getNameValue()));
        return false; 
    }
}
