/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client.sei;

import com.oracle.webservices.api.databinding.JavaCallInfo;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.client.RequestContext;
import com.sun.xml.ws.client.ResponseContextReceiver;
import com.sun.xml.ws.encoding.soap.DeserializationException;
import com.sun.xml.ws.message.jaxb.JAXBMessage;
import com.sun.xml.ws.model.JavaMethodImpl;
import com.sun.xml.ws.resources.DispatchMessages;

import jakarta.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.WebServiceException;


/**
 * {@link MethodHandler} that handles synchronous method invocations.
 *
 * <p>
 * This class mainly performs the following two tasks:
 * <ol>
 *  <li>Accepts Object[] that represents arguments for a Java method,
 *      and creates {@link JAXBMessage} that represents a request message.
 *  <li>Takes a {@link Message} that represents a response,
 *      and extracts the return value (and updates {@link Holder}s.)
 * </ol>
 *
 * <h2>Creating {@link JAXBMessage}</h2>
 * <p>
 * At the construction time, we prepare {@link BodyBuilder} and {@link MessageFiller}s
 * that know how to move arguments into a {@link Message}.
 * Some arguments go to the payload, some go to headers, still others go to attachments.
 *
 * @author Kohsuke Kawaguchi
 */
final class SyncMethodHandler extends MethodHandler {
    final boolean isVoid;
    final boolean isOneway;
    final JavaMethodImpl javaMethod;
    SyncMethodHandler(SEIStub owner, JavaMethodImpl jm) {
        super(owner, jm.getMethod());
        javaMethod = jm;
        isVoid = void.class.equals(jm.getMethod().getReturnType());
        isOneway = jm.getMEP().isOneWay();
    }

    @Override
    Object invoke(Object proxy, Object[] args) throws Throwable {
        return invoke(proxy,args,owner.requestContext,owner);
    }

    /**
     * Invokes synchronously, but with the given {@link RequestContext}
     * and {@link ResponseContextReceiver}.
     *
     * @param rc
     *      This {@link RequestContext} is used for invoking this method.
     *      We take this as a separate parameter because of the async invocation
     *      handling, which requires a separate copy.
     */
    Object invoke(Object proxy, Object[] args, RequestContext rc, ResponseContextReceiver receiver) throws Throwable {
        JavaCallInfo call = owner.databinding.createJavaCallInfo(method, args);
        Packet req = (Packet) owner.databinding.serializeRequest(call);
        // process the message
        Packet reply = owner.doProcess(req,rc,receiver);

        Message msg = reply.getMessage();
        if(msg == null) {
            if (!isOneway || !isVoid) {
                throw new WebServiceException(DispatchMessages.INVALID_RESPONSE());
            }
            return null;
        }

        try {
            call = owner.databinding.deserializeResponse(reply, call);
            if (call.getException() != null) {
                throw call.getException();
            } else {
                return call.getReturnValue();
            }
        } catch (JAXBException e) {
            throw new DeserializationException(DispatchMessages.INVALID_RESPONSE_DESERIALIZATION(), e);
        } catch (XMLStreamException e) {
            throw new DeserializationException(DispatchMessages.INVALID_RESPONSE_DESERIALIZATION(),e);
        } finally {
            if (reply.transportBackChannel != null)
                reply.transportBackChannel.close();
        }
    }

    ValueGetterFactory getValueGetterFactory() {
        return ValueGetterFactory.SYNC;
    }

}
