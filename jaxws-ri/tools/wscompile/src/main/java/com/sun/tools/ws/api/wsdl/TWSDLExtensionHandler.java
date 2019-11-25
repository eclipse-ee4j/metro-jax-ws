/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.api.wsdl;

import com.sun.tools.ws.wsdl.document.WSDLConstants;
import org.w3c.dom.Element;

/**
 * JAXWS WSDL parser {@link com.sun.tools.ws.wsdl.parser.WSDLParser} will call an {@link TWSDLExtensionHandler} registered
 * with it for the WSDL extensibility elements thats not already defined in the WSDL 1.1 spec, such as SOAP or MIME.
 *
 * @author Vivek Pandey
 * @deprecated This class is deprecated, will be removed in JAX-WS 2.2 RI.
 */
public abstract class TWSDLExtensionHandler {
    /**
     * Gives the namespace of an extensibility element.
     * <br>
     * For example a soap 1.1 XXExtensionHandler would return <code>""http://schemas.xmlsoap.org/wsdl/soap/"</code>
     */
    public String getNamespaceURI() {
        return null;
    }

    /**
     * This interface is called during WSDL parsing on detecting any wsdl extension.
     * 
     * @param context Parser context that will be passed on by the wsdl parser
     * @param parent  The Parent element within which the extensibility element is defined
     * @param e       The extensibility elemenet
     * @return false if there was some error during the extension handling otherwise returns true. If returned false
     *         then the WSDL parser can abort if the wsdl extensibility element had <code>required</code> attribute set to true
     */
    public boolean doHandleExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        if (parent.getWSDLElementName().equals(WSDLConstants.QNAME_DEFINITIONS)) {
            return handleDefinitionsExtension(context, parent, e);
        } else if (parent.getWSDLElementName().equals(WSDLConstants.QNAME_TYPES)) {
            return handleTypesExtension(context, parent, e);
        } else if (parent.getWSDLElementName().equals(WSDLConstants.QNAME_PORT_TYPE)) {
            return handlePortTypeExtension(context, parent, e);
        } else if (
            parent.getWSDLElementName().equals(WSDLConstants.QNAME_BINDING)) {
            return handleBindingExtension(context, parent, e);
        } else if (
            parent.getWSDLElementName().equals(WSDLConstants.QNAME_OPERATION)) {
            return handleOperationExtension(context, parent, e);
        } else if (parent.getWSDLElementName().equals(WSDLConstants.QNAME_INPUT)) {
            return handleInputExtension(context, parent, e);
        } else if (
            parent.getWSDLElementName().equals(WSDLConstants.QNAME_OUTPUT)) {
            return handleOutputExtension(context, parent, e);
        } else if (parent.getWSDLElementName().equals(WSDLConstants.QNAME_FAULT)) {
            return handleFaultExtension(context, parent, e);
        } else if (
            parent.getWSDLElementName().equals(WSDLConstants.QNAME_SERVICE)) {
            return handleServiceExtension(context, parent, e);
        } else if (parent.getWSDLElementName().equals(WSDLConstants.QNAME_PORT)) {
            return handlePortExtension(context, parent, e);
        } else {
            return false;
        }
    }

    /**
     * Callback for <code>wsdl:portType</code>
     *
     * @param context Parser context that will be passed on by the wsdl parser
     * @param parent  The Parent element within which the extensibility element is defined
     * @param e       The extensibility elemenet
     * @return false if there was some error during the extension handling otherwise returns true. If returned false
     *         then the WSDL parser can abort if the wsdl extensibility element had <code>required</code> attribute set to true
     */
    public boolean handlePortTypeExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        return false;
    }

    /**
     * Callback for <code>wsdl:definitions</code>
     *
     * @param context Parser context that will be passed on by the wsdl parser
     * @param parent  The Parent element within which the extensibility element is defined
     * @param e       The extensibility elemenet
     * @return false if there was some error during the extension handling otherwise returns true. If returned false
     *         then the WSDL parser can abort if the wsdl extensibility element had <code>required</code> attribute set to true
     */
    public boolean handleDefinitionsExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        return false;
    }

    /**
     * Callback for <code>wsdl:type</code>
     *
     * @param context Parser context that will be passed on by the wsdl parser
     * @param parent  The Parent element within which the extensibility element is defined
     * @param e       The extensibility elemenet
     * @return false if there was some error during the extension handling otherwise returns true. If returned false
     *         then the WSDL parser can abort if the wsdl extensibility element had <code>required</code> attribute set to true
     */
    public boolean handleTypesExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        return false;
    }

    /**
     * Callback for <code>wsdl:binding</code>
     *
     * @param context Parser context that will be passed on by the wsdl parser
     * @param parent  The Parent element within which the extensibility element is defined
     * @param e       The extensibility elemenet
     * @return false if there was some error during the extension handling otherwise returns true. If returned false
     *         then the WSDL parser can abort if the wsdl extensibility element had <code>required</code> attribute set to true
     */
    public boolean handleBindingExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        return false;
    }

    /**
     * Callback for <code>wsdl:portType/wsdl:operation</code>.
     *
     * @param context Parser context that will be passed on by the wsdl parser
     * @param parent  The Parent element within which the extensibility element is defined
     * @param e       The extensibility elemenet
     * @return false if there was some error during the extension handling otherwise returns true. If returned false
     *         then the WSDL parser can abort if the wsdl extensibility element had <code>required</code> attribute set to true
     */
    public boolean handleOperationExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        return false;
    }

    /**
     * Callback for <code>wsdl:input</code>
     *
     * @param context Parser context that will be passed on by the wsdl parser
     * @param parent  The Parent element within which the extensibility element is defined
     * @param e       The extensibility elemenet
     * @return false if there was some error during the extension handling otherwise returns true. If returned false
     *         then the WSDL parser can abort if the wsdl extensibility element had <code>required</code> attribute set to true
     */
    public boolean handleInputExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        return false;
    }

    /**
     * Callback for <code>wsdl:output</code>
     *
     * @param context Parser context that will be passed on by the wsdl parser
     * @param parent  The Parent element within which the extensibility element is defined
     * @param e       The extensibility elemenet
     * @return false if there was some error during the extension handling otherwise returns true. If returned false
     *         then the WSDL parser can abort if the wsdl extensibility element had <code>required</code> attribute set to true
     */
    public boolean handleOutputExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        return false;
    }

    /**
     * Callback for <code>wsdl:fault</code>
     *
     * @param context Parser context that will be passed on by the wsdl parser
     * @param parent  The Parent element within which the extensibility element is defined
     * @param e       The extensibility elemenet
     * @return false if there was some error during the extension handling otherwise returns true. If returned false
     *         then the WSDL parser can abort if the wsdl extensibility element had <code>required</code> attribute set to true
     */
    public boolean handleFaultExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        return false;
    }

    /**
     * Callback for <code>wsdl:service</code>
     *
     * @param context Parser context that will be passed on by the wsdl parser
     * @param parent  The Parent element within which the extensibility element is defined
     * @param e       The extensibility elemenet
     * @return false if there was some error during the extension handling otherwise returns true. If returned false
     *         then the WSDL parser can abort if the wsdl extensibility element had <code>required</code> attribute set to true
     */
    public boolean handleServiceExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        return false;
    }

    /**
     * Callback for <code>wsdl:port</code>
     *
     * @param context Parser context that will be passed on by the wsdl parser
     * @param parent  The Parent element within which the extensibility element is defined
     * @param e       The extensibility elemenet
     * @return false if there was some error during the extension handling otherwise returns true. If returned false
     *         then the WSDL parser can abort if the wsdl extensibility element had <code>required</code> attribute set to true
     */
    public boolean handlePortExtension(TWSDLParserContext context, TWSDLExtensible parent, Element e) {
        return false;
    }
}
