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
import com.sun.tools.ws.util.xml.XmlUtil;
import com.sun.tools.ws.wsdl.document.http.*;
import org.w3c.dom.Element;

import java.util.Map;

/**
 * The HTTP extension handler for WSDL.
 *
 * @author WS Development Team
 */
public class HTTPExtensionHandler extends AbstractExtensionHandler {


    public HTTPExtensionHandler(Map<String, AbstractExtensionHandler> extensionHandlerMap) {
        super(extensionHandlerMap);
    }

    @Override
    public String getNamespaceURI() {
        return Constants.NS_WSDL_HTTP;
    }

    @Override
    public boolean handleDefinitionsExtension(
        TWSDLParserContext context,
        TWSDLExtensible parent,
        Element e) {
        Util.fail(
            "parsing.invalidExtensionElement",
            e.getTagName(),
            e.getNamespaceURI());
        return false;
    }

    @Override
    public boolean handleTypesExtension(
        com.sun.tools.ws.api.wsdl.TWSDLParserContext context,
        TWSDLExtensible parent,
        Element e) {
        Util.fail(
            "parsing.invalidExtensionElement",
            e.getTagName(),
            e.getNamespaceURI());
        return false;
    }

    @Override
    public boolean handleBindingExtension(
        TWSDLParserContext context,
        TWSDLExtensible parent,
        Element e) {
        if (XmlUtil.matchesTagNS(e, HTTPConstants.QNAME_BINDING)) {
            context.push();
            context.registerNamespaces(e);

            HTTPBinding binding = new HTTPBinding(context.getLocation(e));

            String verb = Util.getRequiredAttribute(e, Constants.ATTR_VERB);
            binding.setVerb(verb);

            parent.addExtension(binding);
            context.pop();
//            context.fireDoneParsingEntity(HTTPConstants.QNAME_BINDING, binding);
            return true;
        } else {
            Util.fail(
                "parsing.invalidExtensionElement",
                e.getTagName(),
                e.getNamespaceURI());
            return false;
        }
    }

    @Override
    public boolean handleOperationExtension(
        TWSDLParserContext context,
        TWSDLExtensible parent,
        Element e) {
        if (XmlUtil.matchesTagNS(e, HTTPConstants.QNAME_OPERATION)) {
            context.push();
            context.registerNamespaces(e);

            HTTPOperation operation = new HTTPOperation(context.getLocation(e));

            String location =
                Util.getRequiredAttribute(e, Constants.ATTR_LOCATION);
            operation.setLocation(location);

            parent.addExtension(operation);
            context.pop();
//            context.fireDoneParsingEntity(
//                HTTPConstants.QNAME_OPERATION,
//                operation);
            return true;
        } else {
            Util.fail(
                "parsing.invalidExtensionElement",
                e.getTagName(),
                e.getNamespaceURI());
            return false;
        }
    }

    @Override
    public boolean handleInputExtension(
        TWSDLParserContext context,
        TWSDLExtensible parent,
        Element e) {
        if (XmlUtil.matchesTagNS(e, HTTPConstants.QNAME_URL_ENCODED)) {
            parent.addExtension(new HTTPUrlEncoded(context.getLocation(e)));
            return true;
        } else if (
            XmlUtil.matchesTagNS(e, HTTPConstants.QNAME_URL_REPLACEMENT)) {
            parent.addExtension(new HTTPUrlReplacement(context.getLocation(e)));
            return true;
        } else {
            Util.fail(
                "parsing.invalidExtensionElement",
                e.getTagName(),
                e.getNamespaceURI());
            return false;
        }
    }

    @Override
    public boolean handleOutputExtension(
        TWSDLParserContext context,
        TWSDLExtensible parent,
        Element e) {
        Util.fail(
            "parsing.invalidExtensionElement",
            e.getTagName(),
            e.getNamespaceURI());
        return false;
    }

    @Override
    public boolean handleFaultExtension(
        TWSDLParserContext context,
        TWSDLExtensible parent,
        Element e) {
        Util.fail(
            "parsing.invalidExtensionElement",
            e.getTagName(),
            e.getNamespaceURI());
        return false;
    }

    @Override
    public boolean handleServiceExtension(
        TWSDLParserContext context,
        TWSDLExtensible parent,
        Element e) {
        Util.fail(
            "parsing.invalidExtensionElement",
            e.getTagName(),
            e.getNamespaceURI());
        return false;
    }

    @Override
    public boolean handlePortExtension(
        TWSDLParserContext context,
        TWSDLExtensible parent,
        Element e) {
        if (XmlUtil.matchesTagNS(e, HTTPConstants.QNAME_ADDRESS)) {
            context.push();
            context.registerNamespaces(e);

            HTTPAddress address = new HTTPAddress(context.getLocation(e));

            String location =
                Util.getRequiredAttribute(e, Constants.ATTR_LOCATION);
            address.setLocation(location);

            parent.addExtension(address);
            context.pop();
//            context.fireDoneParsingEntity(HTTPConstants.QNAME_ADDRESS, address);
            return true;
        } else {
            Util.fail(
                "parsing.invalidExtensionElement",
                e.getTagName(),
                e.getNamespaceURI());
            return false;
        }
    }

    @Override
    public boolean handlePortTypeExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        Util.fail(
            "parsing.invalidExtensionElement",
            e.getTagName(),
            e.getNamespaceURI());
        return false;
    }
}
