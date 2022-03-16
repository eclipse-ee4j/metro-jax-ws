/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.dump;

import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.pipe.Fiber;
import com.sun.xml.ws.api.pipe.NextAction;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.pipe.TubeCloner;
import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
import com.sun.xml.ws.commons.xmlutil.Converter;
import com.sun.xml.ws.dump.MessageDumper.MessageType;
import com.sun.xml.ws.dump.MessageDumper.ProcessingState;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Marek Potociar (marek.potociar at sun.com)
 */
final class MessageDumpingTube extends AbstractFilterTubeImpl {
    static final String DEFAULT_MSGDUMP_LOGGING_ROOT = com.sun.xml.ws.util.Constants.LoggingDomain + ".messagedump";
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);
    //
    private final MessageDumper messageDumper;
    private final int tubeId;
    //
    private final MessageDumpingFeature messageDumpingFeature;
    /**
     * @param name
     *      Specify the name that identifies this {@link MessageDumpingTube}
     *      instance. This string will be printed when this pipe
     *      dumps messages, and allows people to distinguish which
     *      pipe instance is dumping a message when multiple
     *      {@link com.sun.xml.ws.util.pipe.DumpTube}s print messages out.
     * @param out
     *      The output to send dumps to.
     * @param next
     *      The next {@link com.sun.xml.ws.api.pipe.Tube} in the pipeline.
     */
    MessageDumpingTube(Tube next, MessageDumpingFeature feature) {
        super(next);

        this.messageDumpingFeature = feature;
        this.tubeId = ID_GENERATOR.incrementAndGet();
        this.messageDumper = new MessageDumper(
                "MesageDumpingTube",
                java.util.logging.Logger.getLogger(feature.getMessageLoggingRoot()),
                feature.getMessageLoggingLevel());
    }

    /**
     * Copy constructor.
     */
    MessageDumpingTube(MessageDumpingTube that, TubeCloner cloner) {
        super(that, cloner);


        this.messageDumpingFeature = that.messageDumpingFeature;
        this.tubeId = ID_GENERATOR.incrementAndGet();
        this.messageDumper = that.messageDumper;
    }

    public MessageDumpingTube copy(TubeCloner cloner) {
        return new MessageDumpingTube(this, cloner);
    }

    @Override
    public NextAction processRequest(Packet request) {
        dump(MessageType.Request, Converter.toString(request), Fiber.current().owner.id);
        return super.processRequest(request);
    }

    @Override
    public NextAction processResponse(Packet response) {
        dump(MessageType.Response, Converter.toString(response), Fiber.current().owner.id);
        return super.processResponse(response);
    }

    @Override
    public NextAction processException(Throwable t) {
        dump(MessageType.Exception, Converter.toString(t), Fiber.current().owner.id);

        return super.processException(t);
    }

    protected void dump(MessageType messageType, String message, String engineId) {
        String logMessage;
        if (messageDumpingFeature.getMessageLoggingStatus()) {
            messageDumper.setLoggingLevel(messageDumpingFeature.getMessageLoggingLevel());
            logMessage = messageDumper.dump(messageType, ProcessingState.Received, message, tubeId, engineId);
        } else {
            logMessage = messageDumper.createLogMessage(messageType, ProcessingState.Received, tubeId, engineId, message);
        }
        messageDumpingFeature.offerMessage(logMessage);
    }
}

