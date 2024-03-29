/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.async_client_transport;

import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
import com.sun.xml.ws.api.pipe.*;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.streaming.XMLStreamWriterFactory;
import com.sun.xml.ws.api.message.AddressingUtils;
import com.sun.xml.ws.api.message.MessageHeaders;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.api.addressing.AddressingVersion;
import com.sun.xml.ws.api.addressing.WSEndpointReference;
import com.sun.xml.ws.util.ByteArrayBuffer;
import com.sun.xml.ws.binding.BindingImpl;
import com.sun.istack.NotNull;

import javax.xml.stream.XMLStreamWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Rama.Pulavarthi@sun.com
 */
public class AsyncClientTransportTube extends AbstractFilterTubeImpl {
    WSBinding binding;
    NonAnonymousResponsesReceiver<Message> responseReceiver;
    RINonAnonymousResponseHandler nonAnonHandler;
    RequestSender requestSender;
    AddressingVersion addrVersion;
    WSEndpointReference nonAnonymousEPR;
    Header nonAnonymousHeader;


    protected AsyncClientTransportTube(ClientTubeAssemblerContext context) {
        super(TransportTubeFactory.create(Thread.currentThread().getContextClassLoader(), recreateClientContext(context)));
        this.binding = context.getBinding();
        addrVersion = binding.getAddressingVersion();
        AsyncClientTransportFeature nonanonftr = binding.getFeature(AsyncClientTransportFeature.class);
        if (addrVersion != null && nonanonftr.isEnabled()) {
            if (nonanonftr.getReceiver() == null) {
                responseReceiver = new DefaultNonAnonymousResponseReceiver(nonanonftr.getNonanonAddress(), binding.getBindingID());

            } else {
                responseReceiver = nonanonftr.getReceiver();
            }
        }
        nonAnonHandler = new RINonAnonymousResponseHandler();
        responseReceiver.register(nonAnonHandler);
        requestSender = new RequestSender(toString(), next);
        nonAnonymousEPR = new WSEndpointReference(responseReceiver.getAddress(), binding.getAddressingVersion());
        nonAnonymousHeader = nonAnonymousEPR.createHeader(binding.getAddressingVersion().replyToTag);

    }

    protected AsyncClientTransportTube(AsyncClientTransportTube that, TubeCloner cloner) {
        super(that, cloner);
        this.binding = that.binding;
        this.nonAnonHandler = that.nonAnonHandler;
        this.responseReceiver = that.responseReceiver;
        this.requestSender = that.requestSender;
        this.nonAnonymousEPR = that.nonAnonymousEPR;
        this.nonAnonymousHeader = that.nonAnonymousHeader;
    }


    private static ClientTubeAssemblerContext recreateClientContext(ClientTubeAssemblerContext context) {
        return new ClientTubeAssemblerContext(
                context.getAddress(), context.getWsdlModel(), context.getBindingProvider(),
                recreateBinding(context.getBinding()),
                context.getContainer(), context.getCodec(), context.getSEIModel(), context.getSEI());
    }

    private static WSBinding recreateBinding(WSBinding binding) {
        //return new FeatureSupressingWSBinding(AsyncClientTransportFeature.class, binding);
        return BindingImpl.create(binding.getBindingId(), new FeatureSupressingWSBinding(AsyncClientTransportFeature.class,
                binding).getFeatures().toArray());
    }

    @Override
    public AbstractTubeImpl copy(TubeCloner cloner) {
        return new AsyncClientTransportTube(this, cloner);
    }

    public
    @NotNull
    @Override
    NextAction processRequest(Packet request) {
        if (request.expectReply) {
            setNonAnnonymousReplyTo(request.getMessage(), binding.getAddressingVersion(), nonAnonymousHeader);
            String msgId = getMessageId(request.getMessage());
            nonAnonHandler.addNonAnonymousResponseHandler(msgId, new ClientResponseHandler(request));
            LOGGER.log(Level.FINE, "Sending request with message id{0}", msgId);
            //requestSender.sendAsync(request, new SyncResponseHandler(msgId, nonAnonHandler));
            requestSender.send(request);
            return doSuspend();
        } else {
            //oneway, continue as usual
            return doInvoke(next, request);
        }

    }

    public
    @NotNull
    @Override
    NextAction processResponse(Packet response) {
        return doReturnWith(response);
    }

    public
    @NotNull
    @Override
    NextAction processException(Throwable t) {
        return doThrow(t);
    }

    @Override
    public void preDestroy() {
        responseReceiver.unregister(nonAnonHandler);
        requestSender.close();
        nonAnonHandler.cleanUp();
        nonAnonHandler = null;
        responseReceiver = null;
        requestSender = null;

    }

    String getMessageId(Message m) {
        return AddressingUtils.getMessageID(m.getHeaders(), binding.getAddressingVersion(), binding.getSOAPVersion());
    }

    String getRelatesTo(Message m) {
        return AddressingUtils.getRelatesTo(m.getHeaders(), binding.getAddressingVersion(), binding.getSOAPVersion());
    }

    public class ClientResponseHandler implements NonAnonymousResponseHandler<Message> {
        final Fiber fiber;
        final Packet request;

        public ClientResponseHandler(Packet request) {
            this.request = request;
            this.fiber = Fiber.current();

        }

        @Override
        public void onReceive(@NotNull Message msg) {
            LOGGER.log(Level.INFO, "Client being resumed for processing message with id{0}", getRelatesTo(msg));
            try {
                if (dump) {
                    System.out.println("Received message: ");
                    ByteArrayBuffer baos = new ByteArrayBuffer();
                    XMLStreamWriter writer = XMLStreamWriterFactory.create(baos);
                    msg.copy().writeTo(writer);
                    writer.close();
                    baos.writeTo(System.out);
                    System.out.flush();
                }
            } catch (Exception e) {
                onError(e);
            }
            Packet reply = request.createClientResponse(msg);
            fiber.resume(reply);
        }

        @Override
        public void onError(@NotNull Throwable t) {
            fiber.resume(t);
        }

    }

    class RINonAnonymousResponseHandler implements NonAnonymousResponseHandler<Message> {
        Map<String, NonAnonymousResponseHandler> waiting = Collections.synchronizedMap(
                new HashMap<>());

        public void addNonAnonymousResponseHandler(String msgId, NonAnonymousResponseHandler handler) {
            waiting.put(msgId, handler);
        }

        public NonAnonymousResponseHandler remove(String msgId) {
            return waiting.remove(msgId);
        }

        public void cleanUp() {
            waiting.clear();
        }

        @Override
        public void onReceive(final @NotNull Message response) {
            String msgId = getRelatesTo(response);
            LOGGER.log(Level.FINE, "Received message with id{0}", msgId);
            if (msgId != null) {
                final NonAnonymousResponseHandler handler = waiting.remove(msgId);
                if (handler == null) {
                    LOGGER.log(Level.WARNING, "Received unexpected message with realtesTo id = {0}", msgId);
                } else {
                    handler.onReceive(response);
                    /*
                    Thread clientResponseHandlerThread = new Thread(new Runnable() {

                        public void run() {
                            handler.onReceive(response);
                        }
                    }, "Client ResponseHadler Thread for handling response for message id" + msgId);

                    clientResponseHandlerThread.start();
                    */
                }
            } else {
                LOGGER.warning("Received unexpected message - cannot find key");
            }
        }

        @Override
        public void onError(@NotNull Throwable t) {
            // no op
        }

    }


    class SyncResponseHandler implements Fiber.CompletionCallback {
        private final String msgId;
        private RINonAnonymousResponseHandler nonAnonResponseTracker;

        SyncResponseHandler(String msgId, RINonAnonymousResponseHandler nonAnonResponseTracker) {
            this.msgId = msgId;
            this.nonAnonResponseTracker = nonAnonResponseTracker;
        }

        @Override
        public void onCompletion(@NotNull Packet response) {
            Message responseMessage = response.getMessage();
            if (responseMessage != null) {
                if (responseMessage.hasPayload()) {
                    String relatesToId = getRelatesTo(responseMessage);
                    if (!msgId.equals(relatesToId)) {
                        LOGGER.log(Level.WARNING, "Received unexpected message for id = {0}with id = {1}", new Object[]{msgId, getMessageId(responseMessage)});
                    }
                    NonAnonymousResponseHandler responseHandler = nonAnonResponseTracker.remove(msgId);
                    if (responseHandler != null) {
                        responseHandler.onReceive(responseMessage);
                    }

                }
            }
        }

        @Override
        public void onCompletion(@NotNull Throwable error) {
            LOGGER.log(Level.WARNING, "Received unexpected error for request with id = {0}", msgId);

            NonAnonymousResponseHandler responseHandler = nonAnonResponseTracker.remove(msgId);
            if (responseHandler != null) {
                responseHandler.onError(error);
            }
        }
    }

    void setNonAnnonymousReplyTo(Message m, AddressingVersion av, Header nonAnonymousHeader) {
        MessageHeaders headers = m.getHeaders();
        headers.remove(av.replyToTag);
        headers.add(nonAnonymousHeader);

        if (headers.remove(av.faultToTag) != null) {
            headers.add(nonAnonymousHeader);
        }

    }

    /**
     * Dumps what goes across NonAnonymousResponseTube.
     */
    public static final boolean dump;

    static {
        boolean b;
        try {
            b = Boolean.getBoolean(AsyncClientTransportTube.class.getName() + ".dump");
        } catch (Throwable t) {
            b = false;
        }
        dump = b;
    }

    private static final Logger LOGGER = Logger.getLogger(AsyncClientTransportTube.class.getName());
}
