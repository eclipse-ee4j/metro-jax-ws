/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.provider.xmlbind_datasource.server;

import java.io.ByteArrayInputStream;
import java.util.Map;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.ws.LogicalMessage;
import jakarta.xml.ws.handler.*;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.soap.*;
import org.w3c.dom.Node;

public class MyHandler
    implements LogicalHandler<LogicalMessageContext> {

    private static final JAXBContext jaxbContext = createJAXBContext ();
    
    private static jakarta.xml.bind.JAXBContext createJAXBContext (){
        try{
            return JAXBContext.newInstance (ObjectFactory.class);
        }catch(jakarta.xml.bind.JAXBException e){
            throw new WebServiceException (e.getMessage (), e);
        }
    }
    
    public boolean handleMessage(LogicalMessageContext messageContext) {
    	Boolean bool = (Boolean)messageContext.get(
    			MessageContext.MESSAGE_OUTBOUND_PROPERTY);
    	if (!bool.booleanValue()) {
    		// Do this only for Request

            // Get values from primary part and verify them
	    	LogicalMessage msg = messageContext.getMessage();
            Hello hello = getHello(msg);
            if (!"Dispatch".equals(hello.getArgument())) {
            	throw new WebServiceException("MyHandler - hello.getArgument(): expected \"Dispatch\", got \"" + hello.getArgument() + "\"");
        	}
        	if (!"Test".equals(hello.getExtra())) {
            	throw new WebServiceException("MyHandler - hello.getArgument(): expected \"Test\", got \"" + hello.getExtra() + "\"");
            }

            // Set new values for primary part
            hello.setArgument("ArgSetByHandler");
            hello.setExtra("ExtraSetByHandler");

            // Set new payload
            setHello(hello, msg);
            messageContext.put("foo", "bar");
            messageContext.setScope("foo", MessageContext.Scope.APPLICATION);
    	} else {
	    	LogicalMessage msg = messageContext.getMessage();
            msg.setPayload(msg.getPayload());
			String value = (String)messageContext.get("foo");
			if (value == null || !value.equals("return-bar")) {
            	throw new IllegalArgumentException(
                	"Got foo property: expected=return-bar Got="+value);
			}
			value = (String)messageContext.get("return-foo");
			if (value == null || !value.equals("return-bar")) {
            	throw new IllegalArgumentException(
                	"Got return-foo property: expected=return-bar Got="+value);
			}
		}
		return true;
    }
    
    public void close(MessageContext messageContext) {
    }    
    
    public boolean handleFault(LogicalMessageContext messageContext) {
		return true;
    }
	    	
	// Gets Hello from Payload(which is SOAPEnvelope)
    private Hello getHello(LogicalMessage msg) {
		try {
			Source source = msg.getPayload();
			SOAPMessage soap = MessageFactory.newInstance ().createMessage ();
			soap.getSOAPPart().setContent (source);
			Node node = soap.getSOAPBody ().getFirstChild ();
			Source helloSource = new DOMSource(node);
			Hello hello = (Hello)jaxbContext.createUnmarshaller().unmarshal(
				helloSource);
			return hello;
		} catch(Exception e) {
			throw new WebServiceException("getHello failed", e);
		}
	}

	// Sets Payload(which is SOAPEnvelope) in logical msg where its body is
	// Hello
    private void setHello(Hello hello, LogicalMessage msg) {
		try {
			Source source = msg.getPayload();
			SOAPMessage soap = MessageFactory.newInstance ().createMessage ();
			jaxbContext.createMarshaller().marshal(hello, soap.getSOAPBody());
            soap.saveChanges();
			msg.setPayload(soap.getSOAPPart().getContent());
		} catch(Exception e) {
			throw new WebServiceException("setHello failed", e);
		}
	}
 
}
