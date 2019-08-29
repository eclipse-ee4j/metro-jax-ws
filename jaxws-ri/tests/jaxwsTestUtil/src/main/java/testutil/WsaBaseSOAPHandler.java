/*
 * Copyright (c) 2006, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package testutil;

import java.util.Iterator;
import java.util.Set;

import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.WebServiceException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.namespace.QName;

import org.w3c.dom.Node;

/**
 * @author Arun Gupta
 */
public abstract class WsaBaseSOAPHandler implements SOAPHandler<SOAPMessageContext> {

    String name;
//    protected SOAPBody soapBody;

    public WsaBaseSOAPHandler() {
    }

    public boolean handleMessage(SOAPMessageContext context) {
        boolean outbound = (Boolean)context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        try {
            String oper = getOperationName(getSOAPBody(context));
            if (outbound) {
                context.put("op.name", oper);
            } else {
                if (getSOAPBody(context) != null && getSOAPBody(context).getFault() != null) {
                    String detailName = getSOAPBody(context).getFault().getDetail().getFirstChild().getLocalName();
                    checkFaultActions((String)context.get("op.name"), detailName, getAction(context));
                } else {
                    checkInboundActions(oper, getAction(context));
                }
            }
        } catch (SOAPException e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean handleFault(SOAPMessageContext context) {
        return handleMessage(context);
    }

    public Set<QName> getHeaders() {
        return null;
    }

    public void close(MessageContext messageContext) {
    }

    protected SOAPBody getSOAPBody(SOAPMessageContext context) throws SOAPException {
        return context.getMessage().getSOAPBody();
    }

    protected String getAction(SOAPMessageContext context) throws SOAPException {
        SOAPMessage message = context.getMessage();
        SOAPHeader header = message.getSOAPHeader();
        Iterator iter = header.getChildElements(getActionQName());
        if (!iter.hasNext())
            throw new WebServiceException("wsa:Action header is missing in the message");

        Node node = (Node)iter.next();
        String action = node.getFirstChild().getNodeValue();
        return action;
    }

    protected String getOperationName(SOAPBody soapBody) throws SOAPException {
        if (soapBody == null)
            return null;

        if (soapBody.getFirstChild() == null)
            return null;

        return soapBody.getFirstChild().getLocalName();
    }

    protected void checkFaultActions(String requestName, String detailName, String action) {
    }

    public QName getActionQName() {
        return W3CAddressingConstants.actionTag;
    }

    protected abstract void checkInboundActions(String oper, String action);
}
