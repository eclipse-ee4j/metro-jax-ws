/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.wsdl.writer;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.server.PortAddressResolver;
import org.jvnet.staxex.util.XMLStreamReaderToXMLStreamWriter;
import com.sun.xml.ws.wsdl.parser.WSDLConstants;
import com.sun.xml.ws.addressing.W3CAddressingConstants;
import com.sun.xml.ws.addressing.v200408.MemberSubmissionAddressingConstants;
import com.sun.istack.Nullable;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import java.util.logging.Logger;

/**
 * Patches WSDL with the correct endpoint address and the relative paths
 * to other documents.
 *
 * @author Jitendra Kotamraju
 * @author Kohsuke Kawaguchi
 */
public final class WSDLPatcher extends XMLStreamReaderToXMLStreamWriter {
    
    private static final String NS_XSD = "http://www.w3.org/2001/XMLSchema";
    private static final QName SCHEMA_INCLUDE_QNAME = new QName(NS_XSD, "include");
    private static final QName SCHEMA_IMPORT_QNAME = new QName(NS_XSD, "import");
    private static final QName SCHEMA_REDEFINE_QNAME = new QName(NS_XSD, "redefine");

    private static final Logger logger = Logger.getLogger(
            com.sun.xml.ws.util.Constants.LoggingDomain + ".wsdl.patcher");

    private final DocumentLocationResolver docResolver;
    private final PortAddressResolver portAddressResolver;

    //
    // fields accumulated as we parse through documents
    //
    private String targetNamespace;
    private QName serviceName;
    private QName portName;
    private String portAddress;

    // true inside <wsdl:service>/<wsdl:part>/<wsa:EndpointReference>
    private boolean inEpr;
    // true inside <wsdl:service>/<wsdl:part>/<wsa:EndpointReference>/<wsa:Address>
    private boolean inEprAddress;

    /**
     * Creates a {@link WSDLPatcher} for patching WSDL.
     *
     * @param portAddressResolver
     *      address of the endpoint is resolved using this docResolver.
     * @param docResolver
     *      Consulted to get the import/include document locations.
     *      Must not be null.
     */
    public WSDLPatcher(@NotNull PortAddressResolver portAddressResolver,
            @NotNull DocumentLocationResolver docResolver) {
        this.portAddressResolver = portAddressResolver;
        this.docResolver = docResolver;
    }

    @Override
    protected void handleAttribute(int i) throws XMLStreamException {
        QName name = in.getName();
        String attLocalName = in.getAttributeLocalName(i);

        if((name.equals(SCHEMA_INCLUDE_QNAME) && attLocalName.equals("schemaLocation"))
        || (name.equals(SCHEMA_IMPORT_QNAME)  && attLocalName.equals("schemaLocation"))
        || (name.equals(SCHEMA_REDEFINE_QNAME)  && attLocalName.equals("schemaLocation"))
        || (name.equals(WSDLConstants.QNAME_IMPORT)  && attLocalName.equals("location"))) {
            // patch this attribute value.

            String relPath = in.getAttributeValue(i);
            String actualPath = getPatchedImportLocation(relPath);
            if (actualPath == null) {
                return; // skip this attribute to leave it up to "implicit reference".
            }

            logger.fine("Fixing the relative location:"+relPath
                    +" with absolute location:"+actualPath);
            writeAttribute(i, actualPath);
            return;
        }

        if (name.equals(WSDLConstants.NS_SOAP_BINDING_ADDRESS) ||
            name.equals(WSDLConstants.NS_SOAP12_BINDING_ADDRESS)) {

            if(attLocalName.equals("location")) {
                portAddress = in.getAttributeValue(i);
                String value = getAddressLocation();
                if (value != null) {
                    logger.fine("Service:"+serviceName+ " port:"+portName
                            + " current address "+portAddress+" Patching it with "+value);
                    writeAttribute(i, value);
                    return;
                }
            }
        }

        super.handleAttribute(i);
    }

    /**
     * Writes out an {@code i}-th attribute but with a different value.
     * @param i attribute index
     * @param value attribute value
     * @throws XMLStreamException when an error encountered while writing attribute
     */
    private void writeAttribute(int i, String value) throws XMLStreamException {
        String nsUri = in.getAttributeNamespace(i);
        if(nsUri!=null)
            out.writeAttribute( in.getAttributePrefix(i), nsUri, in.getAttributeLocalName(i), value );
        else
            out.writeAttribute( in.getAttributeLocalName(i), value );
    }

    @Override
    protected void handleStartElement() throws XMLStreamException {
        QName name = in.getName();

        if (name.equals(WSDLConstants.QNAME_DEFINITIONS)) {
            String value = in.getAttributeValue(null,"targetNamespace");
            if (value != null) {
                targetNamespace = value;
            }
        } else if (name.equals(WSDLConstants.QNAME_SERVICE)) {
            String value = in.getAttributeValue(null,"name");
            if (value != null) {
                serviceName = new QName(targetNamespace, value);
            }
        } else if (name.equals(WSDLConstants.QNAME_PORT)) {
            String value = in.getAttributeValue(null,"name");
            if (value != null) {
                portName = new QName(targetNamespace,value);
            }
        } else if (name.equals(W3CAddressingConstants.WSA_EPR_QNAME)
        		|| name.equals(MemberSubmissionAddressingConstants.WSA_EPR_QNAME)) {
            if (serviceName != null && portName != null) {
                inEpr = true;
            }
        } else if (name.equals(W3CAddressingConstants.WSA_ADDRESS_QNAME)
        		|| name.equals(MemberSubmissionAddressingConstants.WSA_ADDRESS_QNAME)) {
            if (inEpr) {
                inEprAddress = true;
            }
        }
        super.handleStartElement();
    }

    @Override
    protected void handleEndElement() throws XMLStreamException {
        QName name = in.getName();
        if (name.equals(WSDLConstants.QNAME_SERVICE)) {
            serviceName = null;
        } else if (name.equals(WSDLConstants.QNAME_PORT)) {
            portName = null;
        } else if (name.equals(W3CAddressingConstants.WSA_EPR_QNAME)
        		|| name.equals(MemberSubmissionAddressingConstants.WSA_EPR_QNAME)) {
            if (inEpr) {
                inEpr = false;
            }
		} else if (name.equals(W3CAddressingConstants.WSA_ADDRESS_QNAME)
				|| name.equals(MemberSubmissionAddressingConstants.WSA_ADDRESS_QNAME)) {
			if (inEprAddress) {
                String value = getAddressLocation();
                if (value != null) {
                    logger.fine("Fixing EPR Address for service:"+serviceName+ " port:"+portName
                                + " address with "+value);
                    out.writeCharacters(value);
                }
                inEprAddress = false;
            }
        }
        super.handleEndElement();
    }

    @Override
    protected void handleCharacters() throws XMLStreamException {
        // handleCharacters() may be called multiple times.
        if (inEprAddress) {
            String value = getAddressLocation();
            if (value != null) {
                // will write the address with <wsa:Address> end element
                return;
            }
        }
        super.handleCharacters();
    }

    /**
     * Returns the location to be placed into the generated document.
     *
     * @param relPath relative URI to be resolved
     * @return
     *      null to leave it to the "implicit reference".
     */
    private @Nullable String getPatchedImportLocation(String relPath) {
        return docResolver.getLocationFor(null, relPath);
    }

    /**
     * For the given service, port names it matches the correct endpoint and
     * reutrns its endpoint address
     *
     * @return returns the resolved endpoint address
     */
    private String getAddressLocation() {
        return (portAddressResolver == null || portName == null)
                ? null : portAddressResolver.getAddressFor(serviceName, portName.getLocalPart(), portAddress);
    }
}
    

