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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marek Potociar
 */
final class MessageDumper {

    static enum MessageType {
        Request("Request message"),
        Response("Response message"),
        Exception("Response exception");

        private final String name;

        private MessageType(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    static enum ProcessingState {
        Received("received"),
        Processed("processed");

        private final String name;

        private ProcessingState(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    

    private final String tubeName;
    private final Logger logger;
    private Level loggingLevel;


    public MessageDumper(String tubeName, Logger logger, Level loggingLevel) {
        this.tubeName = tubeName;
        this.logger = logger;
        this.loggingLevel = loggingLevel;
    }

    boolean isLoggable() {
        return logger.isLoggable(loggingLevel);
    }

    void setLoggingLevel(Level level) {
        this.loggingLevel = level;
    }

    String createLogMessage(MessageType messageType, ProcessingState processingState, int tubeId, String engineId, String message) {
        return String.format("%s %s in Tube [ %s ] Instance [ %d ] Engine [ %s ] Thread [ %s ]:%n%s",
                messageType,
                processingState,
                tubeName,
                tubeId,
                engineId,
                Thread.currentThread().getName(),
                message);
    }

    String dump(MessageType messageType, ProcessingState processingState, String message, int tubeId, String engineId) {
        String logMessage = createLogMessage(messageType, processingState, tubeId, engineId, message);
        logger.log(loggingLevel, logMessage);

        return logMessage;
    }
}
