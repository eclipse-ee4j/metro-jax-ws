/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package handler.single_handlertube.common;

import static handler.single_handlertube.common.TestConstants.INBOUND;
import static handler.single_handlertube.common.TestConstants.OUTBOUND;
import org.w3c.dom.Node;

import javax.xml.namespace.QName;
import jakarta.xml.soap.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import jakarta.xml.ws.LogicalMessage;
import jakarta.xml.ws.ProtocolException;
import jakarta.xml.ws.handler.LogicalMessageContext;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import jakarta.xml.ws.soap.SOAPFaultException;

/**
 * @author Rama Pulavarthi
 */
public class HandlerUtil {

    // used to return false only in outbound case
    boolean returnFalseOutbound(MessageContext context, String name) {
        Boolean outbound =
            (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        return !outbound.booleanValue();
    }

    // used to return false only in inbound case
    boolean returnFalseInbound(MessageContext context, String name) {
        Boolean outbound =
            (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        return outbound.booleanValue();
    }

    boolean addIntToSOAPMessage(SOAPMessageContext context, int diff) {
        try {
            SOAPMessage message = context.getMessage();
            SOAPBody body = message.getSOAPBody();

            SOAPElement bodyParam =
                (SOAPElement) body.getChildElements().next();

            if (!bodyParam.getLocalName().startsWith("TestInt")) {

                // just a convenience to ignore report service calls
                return true;
                //todo: use ignoreReportSOAPMessage for this
            }

            SOAPElement valueParam =
                (SOAPElement) bodyParam.getChildElements().next();
            int orig = Integer.parseInt(valueParam.getValue());
            valueParam.setValue(String.valueOf(orig + diff));
        } catch (SOAPException soapException) {
            throw new RuntimeException(soapException);
        }
        return true;
    }

    boolean addIntToLogicalMessage(LogicalMessageContext context, int diff) {
        try {
            LogicalMessage message = context.getMessage();
            Source source = message.getPayload();
            Transformer xFormer =
                TransformerFactory.newInstance().newTransformer();
            xFormer.setOutputProperty("omit-xml-declaration", "yes");
            DOMResult result = new DOMResult();
            xFormer.transform(source, result);

            Node documentNode = result.getNode();
            Node requestResponseNode = documentNode.getFirstChild();

            if (!requestResponseNode.getLocalName().startsWith("TestInt")) {

                // just a convenience to ignore report service calls
                return true;
                // todo: use ignoreReportLogicalMessage for this
            }

            Node textNode = requestResponseNode.getFirstChild().getFirstChild();
            int orig = Integer.parseInt(textNode.getNodeValue());
            if (HandlerTracker.VERBOSE_HANDLERS) {
                System.out.print("\torig value = " + orig);
            }
            textNode.setNodeValue(String.valueOf(orig + diff));
            if (HandlerTracker.VERBOSE_HANDLERS) {
                System.out.println("\tnew value = " + textNode.getNodeValue());
            }
            source = new DOMSource(documentNode);
            message.setPayload(source);

        } catch (TransformerException te) {
            throw new RuntimeException(te);
        }
        return true;
    }

    boolean throwRuntimeException(MessageContext context, String name,
                                  String direction) {
        Boolean outbound = (Boolean)
            context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if ( (outbound == Boolean.TRUE && direction.equals(INBOUND)) ||
                (outbound == Boolean.FALSE && direction.equals(OUTBOUND)) ) {
            // not the direction we want
            return true;
        }
        throw new RuntimeException("handler " + name +
            " throwing runtime exception as instructed FOO");
    }

    boolean throwSimpleProtocolException(MessageContext context, String name,
                                         String direction) {
        Boolean outbound = (Boolean)
            context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if ( (outbound == Boolean.TRUE && direction.equals(INBOUND)) ||
                (outbound == Boolean.FALSE && direction.equals(OUTBOUND)) ) {
            // not the direction we want
            return true;
        }
        throw new ProtocolException(name +
            " throwing protocol exception as instructed");
    }

    boolean throwSOAPFaultException(MessageContext context, String name,
                                    String direction) {
        Boolean outbound = (Boolean)
            context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if ( (outbound == Boolean.TRUE && direction.equals(INBOUND)) ||
                (outbound == Boolean.FALSE && direction.equals(OUTBOUND)) ) {
            // not the direction we want
            return true;
        }
        // random info in soap fault
        try {
            QName faultCode = new QName("uri", "local", "prefix");
            String faultString = "fault";
            String faultActor = "faultActor";
            SOAPFault sf = SOAPFactory.newInstance().createFault(faultString, faultCode);
            sf.setFaultActor(faultActor);
            Detail detail = sf.addDetail();
            Name entryName = SOAPFactory.newInstance().createName("someFaultEntry");
            detail.addDetailEntry(entryName);
            throw new SOAPFaultException(sf);
        } catch (SOAPException e) {
            throw new RuntimeException("Couldn't create SOAPFaultException " + e);
        }
    }

    /*
     * This tests a namespace prefix issue when
     * faults from the endpoint go through logical and soap handlers.
     */
    boolean getFaultInMessage(LogicalMessageContext context) {
        context.getMessage().getPayload();
        return true;
    }

    /*
    * This tests a namespace prefix issue when
    * faults from the endpoint go through logical and soap handlers.
    */
    boolean getFaultInMessage(SOAPMessageContext context) {
        try {
            context.getMessage().getSOAPBody();
        } catch (SOAPException se) {
            throw new RuntimeException(se);
        }
        return true;
    }
}
