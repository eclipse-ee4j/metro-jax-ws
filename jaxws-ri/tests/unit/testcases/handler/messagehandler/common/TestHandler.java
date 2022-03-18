/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package handler.messagehandler.common;

import com.sun.xml.ws.api.handler.MessageHandler;
import com.sun.xml.ws.api.handler.MessageHandlerContext;
import com.sun.xml.ws.api.message.Message;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import jakarta.xml.ws.handler.MessageContext;
import java.util.Set;

public class TestHandler implements MessageHandler<MessageHandlerContext> {

    public Set<QName> getHeaders() {
        return null;
    }

    public boolean handleMessage(MessageHandlerContext context) {
        JAXBContext jc = ((com.sun.xml.ws.model.AbstractSEIModelImpl)(context.getSEIModel())).getBindingContext().getJAXBContext();
        Message in_message = context.getMessage();
       try {
            JAXBElement obj = in_message.readPayloadAsJAXB(jc.createUnmarshaller());
            //handler.messagehandler.client.Hello hello= obj.getValue();
            //System.out.println(hello); 
       } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean handleFault(MessageHandlerContext context) {
        return true;
    }

    public void close(MessageContext context) {}

}
