/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.handler_simple.client;

import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.namespace.QName;
import java.util.Set;

/**
 * This handler will be set on the soap 12 binding in the
 * customization file. It's used to test that bindings with multiple
 * ports actually use the correct ports. See bug 6353179 and
 * the HandlerClient tests cases.
 */
public class Port12Handler implements SOAPHandler<SOAPMessageContext> {

    private int called = 0;

    public void resetCalled() {
        called = 0;
    }

    public int getCalled() {
        return called;
    }

    public boolean handleMessage(SOAPMessageContext context) {
        called++;
        return true;
    }

    /**** empty methods below ****/
    public Set<QName> getHeaders() {
        return null;
    }

    public void close(MessageContext context) {}

    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

}
