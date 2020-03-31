/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.xmlbind_handler.common;

import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import jakarta.xml.soap.*;

/*
 * Trying to use this handler should result in an exception on the
 * server end. Client code uses soap binding.
 */
public class TestSOAPHandler implements SOAPHandler<SOAPMessageContext> {

    private boolean expectEmptyResponse = false;
    private Exception exception = null;
    
    public void setExpectEmptyResponse(boolean expectEmptyResponse) {
        this.expectEmptyResponse = expectEmptyResponse;
    }
    
    public Exception getException() {
        return exception;
    }
    
    public Set<QName> getHeaders() {
        return null;
    }
    
    public boolean handleMessage(SOAPMessageContext context) {
        if (isOutbound(context)) {
            return true;
        }
        exception = null;
        try {
            if (!expectEmptyResponse) {
                // make sure a message can be created
                SOAPBody body = context.getMessage().getSOAPBody();
                int dummyVarForDebugger = 0;
            } else {
                // make sure a soap message cannot be created
                try {
                    SOAPBody body = context.getMessage().getSOAPBody();
                    exception = new Exception(
                        "did not receive error while creating soap message");
                } catch (SOAPException se) {
                    // good
                    exception = null;
                }
            }
        } catch (Exception e) {
            exception = e;
        }
        return true;
    }
    
    private boolean isOutbound(MessageContext context) {
        return context.get(
            context.MESSAGE_OUTBOUND_PROPERTY).equals(Boolean.TRUE);
    }
    
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }
    
    public void close(MessageContext context) {}
    
}
