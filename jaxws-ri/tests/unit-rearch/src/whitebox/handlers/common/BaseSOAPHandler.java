/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.handlers.common;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import javax.xml.namespace.QName;
import jakarta.xml.ws.handler.*;
import jakarta.xml.ws.handler.soap.*;
import jakarta.xml.ws.ProtocolException;
import jakarta.xml.soap.SOAPMessage;

public class BaseSOAPHandler implements SOAPHandler<SOAPMessageContext> {
    
    String name;   
    public boolean handleMessage(SOAPMessageContext messageContext) {
        System.out.println("handler " + name);
        return true;
    } 
    
    public void close(MessageContext messageContext) {
        
    }
    
    public boolean handleFault(SOAPMessageContext messageContext) {
        return true;
    }
    
    public Set<QName> getHeaders() {
        Set<QName> headers = new HashSet<QName>();
        headers.add(new QName("http://example.com/someheader", "testheader1"));
        return headers;
    }
    
   
    
}
