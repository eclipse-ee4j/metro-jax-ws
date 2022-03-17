/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.parser;

import com.sun.tools.ws.api.wsdl.TWSDLExtensible;
import com.sun.tools.ws.api.wsdl.TWSDLParserContext;
import com.sun.tools.ws.resources.ModelerMessages;
import com.sun.tools.ws.resources.WsdlMessages;
import com.sun.tools.ws.util.xml.XmlUtil;
import com.sun.tools.ws.wscompile.ErrorReceiver;
import com.sun.tools.ws.wsdl.document.Fault;
import com.sun.tools.ws.wsdl.document.Input;
import com.sun.tools.ws.wsdl.document.Output;
import com.sun.xml.ws.addressing.W3CAddressingMetadataConstants;
import com.sun.xml.ws.addressing.v200408.MemberSubmissionAddressingConstants;
import com.sun.xml.ws.api.addressing.AddressingVersion;
import org.w3c.dom.Element;
import org.xml.sax.Locator;

import javax.xml.namespace.QName;
import java.util.Map;

/**
 * @author Arun Gupta
 */
public class MemberSubmissionAddressingExtensionHandler extends W3CAddressingExtensionHandler {

    private ErrorReceiver errReceiver;
    private boolean extensionModeOn;

    public MemberSubmissionAddressingExtensionHandler(Map<String, AbstractExtensionHandler> extensionHandlerMap, ErrorReceiver env, boolean extensionModeOn) {
        super(extensionHandlerMap, env);
        this.errReceiver = env;
        this.extensionModeOn = extensionModeOn;
    }

    @Override
    public String getNamespaceURI() {
        return AddressingVersion.MEMBER.wsdlNsUri;
    }

    @Override
    protected QName getWSDLExtensionQName() {
        return AddressingVersion.MEMBER.wsdlExtensionTag;
    }

    @Override
    public boolean handlePortExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        // ignore any extension elements
        return false;
    }

    @Override
    public boolean handleInputExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        if (extensionModeOn) {
            warn(context.getLocation(e));
            String actionValue = XmlUtil.getAttributeNSOrNull(e, MemberSubmissionAddressingConstants.WSA_ACTION_QNAME);
            if (actionValue == null || actionValue.equals("")) {
                return warnEmptyAction(parent, context.getLocation(e));
            }
            ((Input) parent).setAction(actionValue);
            return true;
        } else {
            return fail(context.getLocation(e));
        }
    }

    private boolean fail(Locator location) {
        errReceiver.warning(location,
                ModelerMessages.WSDLMODELER_INVALID_IGNORING_MEMBER_SUBMISSION_ADDRESSING(
                        AddressingVersion.MEMBER.nsUri, W3CAddressingMetadataConstants.WSAM_NAMESPACE_NAME));
        return false;
    }

    private void warn(Locator location) {
        errReceiver.warning(location,
                ModelerMessages.WSDLMODELER_WARNING_MEMBER_SUBMISSION_ADDRESSING_USED(
                        AddressingVersion.MEMBER.nsUri, W3CAddressingMetadataConstants.WSAM_NAMESPACE_NAME));
    }

    @Override
    public boolean handleOutputExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        if (extensionModeOn) {
            warn(context.getLocation(e));
            String actionValue = XmlUtil.getAttributeNSOrNull(e, MemberSubmissionAddressingConstants.WSA_ACTION_QNAME);
            if (actionValue == null || actionValue.equals("")) {
                return warnEmptyAction(parent, context.getLocation(e));
            }
            ((Output) parent).setAction(actionValue);
            return true;
        } else {
            return fail(context.getLocation(e));
        }
    }

    @Override
    public boolean handleFaultExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        if (extensionModeOn) {
            warn(context.getLocation(e));
            String actionValue = XmlUtil.getAttributeNSOrNull(e, MemberSubmissionAddressingConstants.WSA_ACTION_QNAME);
            if (actionValue == null || actionValue.equals("")) {
                errReceiver.warning(context.getLocation(e), WsdlMessages.WARNING_FAULT_EMPTY_ACTION(parent.getNameValue(), parent.getWSDLElementName().getLocalPart(), parent.getParent().getNameValue()));
                return false; // keep compiler happy
            }
            ((Fault) parent).setAction(actionValue);
            return true;
        } else {
            return fail(context.getLocation(e));
        }
    }

    private boolean warnEmptyAction(TWSDLExtensible parent, Locator pos) {
        errReceiver.warning(pos, WsdlMessages.WARNING_INPUT_OUTPUT_EMPTY_ACTION(parent.getWSDLElementName().getLocalPart(), parent.getParent().getNameValue()));
        return false;
    }
}
