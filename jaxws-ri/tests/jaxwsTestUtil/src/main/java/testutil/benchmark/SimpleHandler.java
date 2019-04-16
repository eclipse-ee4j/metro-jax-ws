/*
 * Copyright (c) 2018, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/*
 * $Id: SimpleHandler.java,v 1.1 2005/10/07 22:46:07 kk122374 Exp $
 */

package testutil.benchmark;

import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

/**
 * @author JAX-RPC RI Development Team
 */
public class SimpleHandler implements SOAPHandler<SOAPMessageContext> {
   
    private static final boolean dosomething = true;
    
    public SimpleHandler() {}

    public Set<QName> getHeaders() {
        return null;
    }

    public boolean handleMessage(SOAPMessageContext context) {
        doSomeWork(context);
        return true;
    }

    public boolean handleFault(SOAPMessageContext context) {
        doSomeWork(context);
        return true;
    }

    protected void doSomeWork(SOAPMessageContext context) {
        if (dosomething) {
            try {
                SOAPMessage message = context.getMessage();
                SOAPPart sp = message.getSOAPPart();
                SOAPEnvelope se = sp.getEnvelope();
                SOAPBody sb = se.getBody();
            } catch (SOAPException e) {
                e.printStackTrace();
            }
        }

    }

    public void close(MessageContext messageContext) {
    }

}
