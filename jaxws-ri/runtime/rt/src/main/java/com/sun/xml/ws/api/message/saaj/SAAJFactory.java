/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.message.saaj;

import java.util.Iterator;

import jakarta.xml.soap.AttachmentPart;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SAAJMetaFactory;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPFactory;
import jakarta.xml.soap.SOAPMessage;
import javax.xml.stream.XMLStreamException;

import org.xml.sax.SAXException;

import org.glassfish.jaxb.core.marshaller.SAX2DOMEx;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.message.Attachment;
import com.sun.xml.ws.api.message.AttachmentEx;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.message.saaj.SAAJMessage;
import com.sun.xml.ws.util.ServiceFinder;
import com.sun.xml.ws.util.xml.XmlUtil;

/**
 * Factory SPI for SAAJ implementations
 * 
 * @since 2.2.6
 */
public class SAAJFactory {
	private static final SAAJFactory instance = new SAAJFactory();

    /**
     * Default constructor.
     */
    public SAAJFactory() {}

    /**
     * Creates a new <code>MessageFactory</code> object that is an instance
     * of the specified implementation.  May be a dynamic message factory,
     * a SOAP 1.1 message factory, or a SOAP 1.2 message factory. A dynamic
     * message factory creates messages based on the MIME headers specified
     * as arguments to the <code>createMessage</code> method.
     *
     * This method uses the SAAJMetaFactory to locate the implementation class 
     * and create the MessageFactory instance.
     * 
     * @return a new instance of a <code>MessageFactory</code>
     *
     * @param protocol  a string constant representing the class of the
     *                   specified message factory implementation. May be
     *                   either <code>DYNAMIC_SOAP_PROTOCOL</code>,
     *                   <code>DEFAULT_SOAP_PROTOCOL</code> (which is the same
     *                   as) <code>SOAP_1_1_PROTOCOL</code>, or
     *                   <code>SOAP_1_2_PROTOCOL</code>.
     *
     * @exception SOAPException if there was an error in creating the
     *            specified implementation of  <code>MessageFactory</code>.
     * @see SAAJMetaFactory
     */
	public static MessageFactory getMessageFactory(String protocol) throws SOAPException {
		for (SAAJFactory s : ServiceFinder.find(SAAJFactory.class)) {
			MessageFactory mf = s.createMessageFactory(protocol);
			if (mf != null)
				return mf;
		}
    	
    	return instance.createMessageFactory(protocol);
	}

    /**
     * Creates a new <code>SOAPFactory</code> object that is an instance of
     * the specified implementation, this method uses the SAAJMetaFactory to 
     * locate the implementation class and create the SOAPFactory instance.
     *
     * @return a new instance of a <code>SOAPFactory</code>
     *
     * @param protocol  a string constant representing the protocol of the
     *                   specified SOAP factory implementation. May be
     *                   either <code>DYNAMIC_SOAP_PROTOCOL</code>,
     *                   <code>DEFAULT_SOAP_PROTOCOL</code> (which is the same
     *                   as) <code>SOAP_1_1_PROTOCOL</code>, or
     *                   <code>SOAP_1_2_PROTOCOL</code>.
     *
     * @exception SOAPException if there was an error creating the
     *            specified <code>SOAPFactory</code>
     * @see SAAJMetaFactory
     */
	public static SOAPFactory getSOAPFactory(String protocol) throws SOAPException {
		for (SAAJFactory s : ServiceFinder.find(SAAJFactory.class)) {
			SOAPFactory sf = s.createSOAPFactory(protocol);
			if (sf != null)
				return sf;
		}
    	
    	return instance.createSOAPFactory(protocol);
	}
	
	/**
	 * Creates Message from SOAPMessage
	 * @param saaj SOAPMessage
	 * @return created Message
	 */
	public static Message create(SOAPMessage saaj) {
		for (SAAJFactory s : ServiceFinder.find(SAAJFactory.class)) {
			Message m = s.createMessage(saaj);
			if (m != null)
				return m;
		}
    	
    	return instance.createMessage(saaj);
	}
	
	/**
	 * Reads Message as SOAPMessage.  After this call message is consumed.
	 * @param soapVersion SOAP version
	 * @param message Message
	 * @return Created SOAPMessage
	 * @throws SOAPException if SAAJ processing fails
	 */
	public static SOAPMessage read(SOAPVersion soapVersion, Message message) throws SOAPException {
		for (SAAJFactory s : ServiceFinder.find(SAAJFactory.class)) {
			SOAPMessage msg = s.readAsSOAPMessage(soapVersion, message);
			if (msg != null)
				return msg;
		}
    	
    	return instance.readAsSOAPMessage(soapVersion, message);
	}
	
	/**
     * Reads Message as SOAPMessage.  After this call message is consumed.
     * @param soapVersion SOAP version
     * @param message Message
     * @param packet The packet that owns the Message
     * @return Created SOAPMessage
     * @throws SOAPException if SAAJ processing fails
     */
    public static SOAPMessage read(SOAPVersion soapVersion, Message message, Packet packet) throws SOAPException {
        SAAJFactory saajfac = packet.getSAAJFactory();
        if (saajfac != null) {
            SOAPMessage msg = saajfac.readAsSOAPMessage(soapVersion, message, packet);
            if (msg != null) return msg;
        }
        for (SAAJFactory s : ServiceFinder.find(SAAJFactory.class)) {
            SOAPMessage msg = s.readAsSOAPMessage(soapVersion, message, packet);
            if (msg != null)
                return msg;
        }
        
        return instance.readAsSOAPMessage(soapVersion, message, packet);
    }

    /**
     * Reads the message within the Packet to a SAAJMessage.  After this call message is consumed.
     * @param packet Packet
     * @return Created SAAJPMessage
     * @throws SOAPException if SAAJ processing fails
     */
    public static SAAJMessage read(Packet packet) throws SOAPException {
        SAAJFactory saajfac = packet.getSAAJFactory();
        if (saajfac != null) {
            SAAJMessage msg = saajfac.readAsSAAJ(packet);
            if (msg != null) return msg;
        }
        // Use the Component from the Packet if it exists.  Note the logic
        // in the ServiceFinder is such that find(Class) is not equivalent
        // to find (Class, null), so the ternary operator is needed.
        ServiceFinder<SAAJFactory> factories = (packet.component != null ?
                ServiceFinder.find(SAAJFactory.class, packet.component) :
                ServiceFinder.find(SAAJFactory.class));
        for (SAAJFactory s : factories) {
            SAAJMessage msg = s.readAsSAAJ(packet);
            if (msg != null) return msg;
        }        
        return instance.readAsSAAJ(packet);
    }

    /**
     * Reads the message within the Packet to a SAAJMessage.  After this call message is consumed.
     * @param packet Packet
     * @return Created SAAJPMessage
     * @throws SOAPException if SAAJ processing fails
     */
    public SAAJMessage readAsSAAJ(Packet packet) throws SOAPException {
        SOAPVersion v = packet.getMessage().getSOAPVersion();
        SOAPMessage msg = readAsSOAPMessage(v, packet.getMessage());
        return new SAAJMessage(msg);
    }
    
    /**
     * Creates a new <code>MessageFactory</code> object that is an instance
     * of the specified implementation.  May be a dynamic message factory,
     * a SOAP 1.1 message factory, or a SOAP 1.2 message factory. A dynamic
     * message factory creates messages based on the MIME headers specified
     * as arguments to the <code>createMessage</code> method.
     *
     * This method uses the SAAJMetaFactory to locate the implementation class 
     * and create the MessageFactory instance.
     * 
     * @return a new instance of a <code>MessageFactory</code>
     *
     * @param protocol  a string constant representing the class of the
     *                   specified message factory implementation. May be
     *                   either <code>DYNAMIC_SOAP_PROTOCOL</code>,
     *                   <code>DEFAULT_SOAP_PROTOCOL</code> (which is the same
     *                   as) <code>SOAP_1_1_PROTOCOL</code>, or
     *                   <code>SOAP_1_2_PROTOCOL</code>.
     *
     * @exception SOAPException if there was an error in creating the
     *            specified implementation of  <code>MessageFactory</code>.
     * @see SAAJMetaFactory
     */
	public MessageFactory createMessageFactory(String protocol) throws SOAPException {
		return MessageFactory.newInstance(protocol);
	}
	
    /**
     * Creates a new <code>SOAPFactory</code> object that is an instance of
     * the specified implementation, this method uses the SAAJMetaFactory to 
     * locate the implementation class and create the SOAPFactory instance.
     *
     * @return a new instance of a <code>SOAPFactory</code>
     *
     * @param protocol  a string constant representing the protocol of the
     *                   specified SOAP factory implementation. May be
     *                   either <code>DYNAMIC_SOAP_PROTOCOL</code>,
     *                   <code>DEFAULT_SOAP_PROTOCOL</code> (which is the same
     *                   as) <code>SOAP_1_1_PROTOCOL</code>, or
     *                   <code>SOAP_1_2_PROTOCOL</code>.
     *
     * @exception SOAPException if there was an error creating the
     *            specified <code>SOAPFactory</code>
     * @see SAAJMetaFactory
     */
	public SOAPFactory createSOAPFactory(String protocol) throws SOAPException {
		return SOAPFactory.newInstance(protocol);
	}
	
	/**
	 * Creates Message from SOAPMessage
	 * @param saaj SOAPMessage
	 * @return created Message
	 */
	public Message createMessage(SOAPMessage saaj) {
		return new SAAJMessage(saaj);
	}
	
	/**
	 * Reads Message as SOAPMessage.  After this call message is consumed.
	 * @param soapVersion SOAP version
	 * @param message Message
	 * @return Created SOAPMessage
	 * @throws SOAPException if SAAJ processing fails
	 */
	public SOAPMessage readAsSOAPMessage(final SOAPVersion soapVersion, final Message message) throws SOAPException {
        SOAPMessage msg = soapVersion.getMessageFactory().createMessage();
        SaajStaxWriter writer = new SaajStaxWriter(msg, soapVersion.nsUri);
        try {
            message.writeTo(writer);
        } catch (XMLStreamException e) {
            throw (e.getCause() instanceof SOAPException) ? (SOAPException) e.getCause() : new SOAPException(e);
        }
        msg = writer.getSOAPMessage();
        addAttachmentsToSOAPMessage(msg, message);        
        if (msg.saveRequired())
        	msg.saveChanges();
        return msg;
	}
	
    public SOAPMessage readAsSOAPMessageSax2Dom(final SOAPVersion soapVersion, final Message message) throws SOAPException {
        SOAPMessage msg = soapVersion.getMessageFactory().createMessage();
        SAX2DOMEx s2d = new SAX2DOMEx(msg.getSOAPPart());
        try {
            message.writeTo(s2d, XmlUtil.DRACONIAN_ERROR_HANDLER);
        } catch (SAXException e) {
            throw new SOAPException(e);
        }
        addAttachmentsToSOAPMessage(msg, message);        
        if (msg.saveRequired())
            msg.saveChanges();
        return msg;
    }
	
    static protected void addAttachmentsToSOAPMessage(SOAPMessage msg, Message message) {
        for(Attachment att : message.getAttachments()) {
            AttachmentPart part = msg.createAttachmentPart();
            part.setDataHandler(att.asDataHandler());
            
            // Be safe and avoid double angle-brackets.
            String cid = att.getContentId();
            if (cid != null) {
                if (cid.startsWith("<") && cid.endsWith(">"))
                    part.setContentId(cid);
                else
                    part.setContentId('<' + cid + '>');
            }
            
            // Add any MIME headers beside Content-ID, which is already
            // accounted for above, and Content-Type, which is provided
            // by the DataHandler above.
            if (att instanceof AttachmentEx) {
                AttachmentEx ax = (AttachmentEx) att;
                Iterator<AttachmentEx.MimeHeader> imh = ax.getMimeHeaders();
                while (imh.hasNext()) {
                    AttachmentEx.MimeHeader ame = imh.next();
                    if ((!"Content-ID".equalsIgnoreCase(ame.getName()))
                            && (!"Content-Type".equalsIgnoreCase(ame.getName())))
                        part.addMimeHeader(ame.getName(), ame.getValue());
                }
            }
            msg.addAttachmentPart(part);
        }    
    }

    /**
     * Reads Message as SOAPMessage.  After this call message is consumed.
     * The implementation in this class simply calls readAsSOAPMessage(SOAPVersion, Message),
     * and ignores the other parameters
     * Subclasses can override and choose to base SOAPMessage creation on Packet properties if needed 
     * @param soapVersion SOAP version
     * @param message Message
     * @return Created SOAPMessage
     * @throws SOAPException if SAAJ processing fails
     */
	public SOAPMessage readAsSOAPMessage(SOAPVersion soapVersion, Message message, Packet packet) throws SOAPException {
	    return readAsSOAPMessage(soapVersion, message);
	}
}
