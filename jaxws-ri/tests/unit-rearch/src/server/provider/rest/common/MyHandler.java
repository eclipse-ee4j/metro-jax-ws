/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.provider.rest.common;

import javax.xml.transform.Source;
import jakarta.xml.ws.LogicalMessage;
import jakarta.xml.ws.handler.*;
public class MyHandler
    implements LogicalHandler<LogicalMessageContext> {

    public boolean handleMessage(LogicalMessageContext lmc) {
        System.out.println(" MyHandler");
        LogicalMessage lm = lmc.getMessage();
        Source payload = lm.getPayload();
        // Doing nothing special, setting payload to null
        // This just tests get/setPayload with null
        if (payload == null) {
			System.out.println("Setting Payload as null");
            lm.setPayload(null);
        }
        return true;
    }
    
    public void close(MessageContext messageContext) {
    }    
    
    public boolean handleFault(LogicalMessageContext messageContext) {
		return true;
    }	    	

}
