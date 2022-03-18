/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.assembler;

import com.sun.istack.NotNull;
import com.sun.istack.logging.Logger;
import com.sun.xml.ws.api.BindingID;
import com.sun.xml.ws.api.pipe.ClientTubeAssemblerContext;
import com.sun.xml.ws.api.pipe.ServerTubeAssemblerContext;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.pipe.TubelineAssembler;
import com.sun.xml.ws.assembler.dev.ClientTubelineAssemblyContext;
import com.sun.xml.ws.assembler.dev.ServerTubelineAssemblyContext;
import com.sun.xml.ws.assembler.dev.TubelineAssemblyDecorator;
import com.sun.xml.ws.dump.LoggingDumpTube;
import com.sun.xml.ws.resources.TubelineassemblyMessages;
import com.sun.xml.ws.util.ServiceFinder;

import java.util.Collection;
import java.util.logging.Level;

/**
* TODO: Write some description here ...
*
* @author Miroslav Kos (miroslav.kos at oracle.com)
*/
public class MetroTubelineAssembler implements TubelineAssembler {

    private static final String COMMON_MESSAGE_DUMP_SYSTEM_PROPERTY_BASE = "com.sun.metro.soap.dump";
    public static final MetroConfigName JAXWS_TUBES_CONFIG_NAMES = new MetroConfigNameImpl("jaxws-tubes-default.xml", "jaxws-tubes.xml");

    private static enum Side {

        Client("client"),
        Endpoint("endpoint");
        private final String name;

        private Side(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private static class MessageDumpingInfo {

        final boolean dumpBefore;
        final boolean dumpAfter;
        final Level logLevel;

        MessageDumpingInfo(boolean dumpBefore, boolean dumpAfter, Level logLevel) {
            this.dumpBefore = dumpBefore;
            this.dumpAfter = dumpAfter;
            this.logLevel = logLevel;
        }
    }

    private static final Logger LOGGER = Logger.getLogger(MetroTubelineAssembler.class);
    private final BindingID bindingId;
    private final TubelineAssemblyController tubelineAssemblyController;

    public MetroTubelineAssembler(final BindingID bindingId, MetroConfigName metroConfigName) {
        this.bindingId = bindingId;
        this.tubelineAssemblyController = new TubelineAssemblyController(metroConfigName);
    }

    TubelineAssemblyController getTubelineAssemblyController() {
        return tubelineAssemblyController;
    }

    @Override
    @NotNull
    public Tube createClient(@NotNull ClientTubeAssemblerContext jaxwsContext) {
        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.finer("Assembling client-side tubeline for WS endpoint: " + jaxwsContext.getAddress().getURI().toString());
        }

        ClientTubelineAssemblyContext context = createClientContext(jaxwsContext);

        Collection<TubeCreator> tubeCreators = tubelineAssemblyController.getTubeCreators(context);

        for (TubeCreator tubeCreator : tubeCreators) {
            tubeCreator.updateContext(context);
        }

        TubelineAssemblyDecorator decorator = TubelineAssemblyDecorator.composite(
                ServiceFinder.find(TubelineAssemblyDecorator.class, context.getContainer()));
        
        boolean first = true;
        TubelineAssemblyContextImpl contextImpl = (TubelineAssemblyContextImpl) context;
        for (TubeCreator tubeCreator : tubeCreators) {
            final MessageDumpingInfo msgDumpInfo = setupMessageDumping(tubeCreator.getMessageDumpPropertyBase(), Side.Client);

            final Tube oldTubelineHead = context.getTubelineHead();
            LoggingDumpTube afterDumpTube = null;
            if (msgDumpInfo.dumpAfter) {
                afterDumpTube = new LoggingDumpTube(msgDumpInfo.logLevel, LoggingDumpTube.Position.After, context.getTubelineHead());
                contextImpl.setTubelineHead(afterDumpTube);
            }

            if (!contextImpl.setTubelineHead(decorator.decorateClient(tubeCreator.createTube(context), context))) { // no new tube has been created
                if (afterDumpTube != null) {
                    contextImpl.setTubelineHead(oldTubelineHead); // removing possible "after" message dumping tube
                }
            } else {
                final String loggedTubeName = context.getTubelineHead().getClass().getName();
                if (afterDumpTube != null) {
                    afterDumpTube.setLoggedTubeName(loggedTubeName);
                }

                if (msgDumpInfo.dumpBefore) {
                    final LoggingDumpTube beforeDumpTube = new LoggingDumpTube(msgDumpInfo.logLevel, LoggingDumpTube.Position.Before, context.getTubelineHead());
                    beforeDumpTube.setLoggedTubeName(loggedTubeName);
                    contextImpl.setTubelineHead(beforeDumpTube);
                }
            }
            
            if (first) {
                contextImpl.setTubelineHead(decorator.decorateClientTail(context.getTubelineHead(), context));
                first = false;
            }
        }

        return decorator.decorateClientHead(context.getTubelineHead(), context);
    }

    @Override
    @NotNull
    public Tube createServer(@NotNull ServerTubeAssemblerContext jaxwsContext) {
        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.finer("Assembling endpoint tubeline for WS endpoint: " + jaxwsContext.getEndpoint().getServiceName() + "::" + jaxwsContext.getEndpoint().getPortName());
        }

        ServerTubelineAssemblyContext context = createServerContext(jaxwsContext);

        // FIXME endpoint URI for provider case
        Collection<TubeCreator> tubeCreators = tubelineAssemblyController.getTubeCreators(context);
        for (TubeCreator tubeCreator : tubeCreators) {
            tubeCreator.updateContext(context);
        }

        TubelineAssemblyDecorator decorator = TubelineAssemblyDecorator.composite(
                ServiceFinder.find(TubelineAssemblyDecorator.class, context.getEndpoint().getContainer()));
        
        boolean first = true;
        TubelineAssemblyContextImpl contextImpl = (TubelineAssemblyContextImpl) context;
        for (TubeCreator tubeCreator : tubeCreators) {
            final MessageDumpingInfo msgDumpInfo = setupMessageDumping(tubeCreator.getMessageDumpPropertyBase(), Side.Endpoint);

            final Tube oldTubelineHead = context.getTubelineHead();
            LoggingDumpTube afterDumpTube = null;
            if (msgDumpInfo.dumpAfter) {
                afterDumpTube = new LoggingDumpTube(msgDumpInfo.logLevel, LoggingDumpTube.Position.After, context.getTubelineHead());
                contextImpl.setTubelineHead(afterDumpTube);
            }

            if (!contextImpl.setTubelineHead(decorator.decorateServer(tubeCreator.createTube(context), context))) { // no new tube has been created
                if (afterDumpTube != null) {
                    contextImpl.setTubelineHead(oldTubelineHead); // removing possible "after" message dumping tube
                }
            } else {
                final String loggedTubeName = context.getTubelineHead().getClass().getName();
                if (afterDumpTube != null) {
                    afterDumpTube.setLoggedTubeName(loggedTubeName);
                }

                if (msgDumpInfo.dumpBefore) {
                    final LoggingDumpTube beforeDumpTube = new LoggingDumpTube(msgDumpInfo.logLevel, LoggingDumpTube.Position.Before, context.getTubelineHead());
                    beforeDumpTube.setLoggedTubeName(loggedTubeName);
                    contextImpl.setTubelineHead(beforeDumpTube);
                }
            }
            
            if (first) {
                contextImpl.setTubelineHead(decorator.decorateServerTail(context.getTubelineHead(), context));
                first = false;
            }
        }

        return decorator.decorateServerHead(context.getTubelineHead(), context);
    }

    private MessageDumpingInfo setupMessageDumping(String msgDumpSystemPropertyBase, Side side) {
        boolean dumpBefore = false;
        boolean dumpAfter = false;
        Level logLevel = Level.INFO;

        // checking common properties
        Boolean value = getBooleanValue(COMMON_MESSAGE_DUMP_SYSTEM_PROPERTY_BASE);
        if (value != null) {
            dumpBefore = value;
            dumpAfter = value;
        }

        value = getBooleanValue(COMMON_MESSAGE_DUMP_SYSTEM_PROPERTY_BASE + ".before");
        dumpBefore = (value != null) ? value : dumpBefore;

        value = getBooleanValue(COMMON_MESSAGE_DUMP_SYSTEM_PROPERTY_BASE + ".after");
        dumpAfter = (value != null) ? value : dumpAfter;

        Level levelValue = getLevelValue(COMMON_MESSAGE_DUMP_SYSTEM_PROPERTY_BASE + ".level");
        if (levelValue != null) {
            logLevel = levelValue;
        }

        // narrowing to proper communication side on common properties
        value = getBooleanValue(COMMON_MESSAGE_DUMP_SYSTEM_PROPERTY_BASE + "." + side.toString());
        if (value != null) {
            dumpBefore = value;
            dumpAfter = value;
        }

        value = getBooleanValue(COMMON_MESSAGE_DUMP_SYSTEM_PROPERTY_BASE + "." + side + ".before");
        dumpBefore = (value != null) ? value : dumpBefore;

        value = getBooleanValue(COMMON_MESSAGE_DUMP_SYSTEM_PROPERTY_BASE + "." + side + ".after");
        dumpAfter = (value != null) ? value : dumpAfter;

        levelValue = getLevelValue(COMMON_MESSAGE_DUMP_SYSTEM_PROPERTY_BASE + "." + side + ".level");
        if (levelValue != null) {
            logLevel = levelValue;
        }


        // checking general tube-specific properties
        value = getBooleanValue(msgDumpSystemPropertyBase);
        if (value != null) {
            dumpBefore = value;
            dumpAfter = value;
        }

        value = getBooleanValue(msgDumpSystemPropertyBase + ".before");
        dumpBefore = (value != null) ? value : dumpBefore;

        value = getBooleanValue(msgDumpSystemPropertyBase + ".after");
        dumpAfter = (value != null) ? value : dumpAfter;

        levelValue = getLevelValue(msgDumpSystemPropertyBase + ".level");
        if (levelValue != null) {
            logLevel = levelValue;
        }

        // narrowing to proper communication side on tube-specific properties
        msgDumpSystemPropertyBase += "." + side;

        value = getBooleanValue(msgDumpSystemPropertyBase);
        if (value != null) {
            dumpBefore = value;
            dumpAfter = value;
        }

        value = getBooleanValue(msgDumpSystemPropertyBase + ".before");
        dumpBefore = (value != null) ? value : dumpBefore;

        value = getBooleanValue(msgDumpSystemPropertyBase + ".after");
        dumpAfter = (value != null) ? value : dumpAfter;

        levelValue = getLevelValue(msgDumpSystemPropertyBase + ".level");
        if (levelValue != null) {
            logLevel = levelValue;
        }

        return new MessageDumpingInfo(dumpBefore, dumpAfter, logLevel);
    }

    private Boolean getBooleanValue(String propertyName) {
        Boolean retVal = null;

        String stringValue = System.getProperty(propertyName);
        if (stringValue != null) {
            retVal = Boolean.valueOf(stringValue);
            LOGGER.fine(TubelineassemblyMessages.MASM_0018_MSG_LOGGING_SYSTEM_PROPERTY_SET_TO_VALUE(propertyName, retVal));
        }

        return retVal;
    }

    private Level getLevelValue(String propertyName) {
        Level retVal = null;

        String stringValue = System.getProperty(propertyName);
        if (stringValue != null) {
            // if value is not null => property is set, we will try to override the default logging level
            LOGGER.fine(TubelineassemblyMessages.MASM_0018_MSG_LOGGING_SYSTEM_PROPERTY_SET_TO_VALUE(propertyName, stringValue));
            try {
                retVal = Level.parse(stringValue);
            } catch (IllegalArgumentException ex) {
                LOGGER.warning(TubelineassemblyMessages.MASM_0019_MSG_LOGGING_SYSTEM_PROPERTY_ILLEGAL_VALUE(propertyName, stringValue), ex);
            }
        }

        return retVal;
    }

    // Extension point to change Tubeline Assembly behaviour: override if necessary ...
    protected ServerTubelineAssemblyContext createServerContext(ServerTubeAssemblerContext jaxwsContext) {
        return new DefaultServerTubelineAssemblyContext(jaxwsContext);
    }

    // Extension point to change Tubeline Assembly behaviour: override if necessary ...
    protected ClientTubelineAssemblyContext createClientContext(ClientTubeAssemblerContext jaxwsContext) {
        return new DefaultClientTubelineAssemblyContext(jaxwsContext);
    }

}
