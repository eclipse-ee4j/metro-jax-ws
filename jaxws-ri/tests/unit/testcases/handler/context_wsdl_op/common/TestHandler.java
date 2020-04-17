/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package handler.context_wsdl_op.common;

import javax.xml.namespace.QName;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.WebServiceException;
import java.util.Set;

/**
 * This handler verifies the WSDL_OPERATION property in MessageContext
 * @author Rama Pulavarthi
 */
public class TestHandler implements SOAPHandler<SOAPMessageContext> {
    private String runtime;
    private final QName expected_wsdl_op = new QName("urn:test", "sayHello");

    public TestHandler(String runtime) {
        this.runtime = runtime;
    }

    public TestHandler() {
        this.runtime = "SERVER-SIDE";
    }

    public Set<QName> getHeaders() {
        return null;
    }

    public boolean handleMessage(SOAPMessageContext context) {
        System.out.println(runtime + " Message Oubound: " + context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY));
        QName got_wsdl_op = (QName) context.get(MessageContext.WSDL_OPERATION);
        //System.out.println(got_wsdl_op);
        if (expected_wsdl_op.equals(got_wsdl_op))
            return true;
        else
            throw new WebServiceException("WSDL Operation property not available in "+runtime+" handler");        
    }

    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    public void close(MessageContext context) {
    }

}
