/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.assembler;

import com.sun.istack.logging.Logger;
import com.sun.xml.ws.api.pipe.Pipe;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.pipe.helper.PipeAdapter;
import com.sun.xml.ws.assembler.dev.TubelineAssemblyContext;
import java.text.MessageFormat;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

/**
 * A base tubeline assembly context class providing common methods for both
 * client and server assembly context classes.
 *
 * @author Marek Potociar (marek.potociar at sun.com)
 */
class TubelineAssemblyContextImpl implements TubelineAssemblyContext {
    private static final Logger LOGGER = Logger.getLogger(TubelineAssemblyContextImpl.class);

    private Tube head;
    private Pipe adaptedHead;
    private List<Tube> tubes = new LinkedList<Tube>();

    @Override
    public Tube getTubelineHead() {
        return head;
    }

    @Override
    public Pipe getAdaptedTubelineHead() {
        if (adaptedHead == null) {
            adaptedHead = PipeAdapter.adapt(head);
        }
        return adaptedHead;
    }

    boolean setTubelineHead(Tube newHead) {
        if (newHead == head || newHead == adaptedHead) {
            return false;
        }

        head = newHead;
        tubes.add(head);
        adaptedHead = null;
        
        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.finer(MessageFormat.format("Added '{0}' tube instance to the tubeline.", (newHead == null) ? null : newHead.getClass().getName()));
        }

        return true;
    }

    @Override
    public <T> T getImplementation(Class<T> type) {
        for (Tube tube : tubes) {
            if (type.isInstance(tube)) {
                return type.cast(tube);
            }
        }
        return null;
    }
}
