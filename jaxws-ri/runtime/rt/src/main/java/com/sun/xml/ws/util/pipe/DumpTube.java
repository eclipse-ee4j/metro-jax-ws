/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util.pipe;

import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.pipe.NextAction;
import com.sun.xml.ws.api.pipe.Pipe;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.pipe.TubeCloner;
import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.PrintStream;
import java.lang.reflect.Constructor;

/**
 * {@link Pipe} that dumps messages that pass through.
 *
 * @author Kohsuke Kawaguchi
 */
public class DumpTube extends AbstractFilterTubeImpl {

    private final String name;

    private final PrintStream out;

    private final XMLOutputFactory staxOut;

    /**
     * @param name
     *      Specify the name that identifies this {@link DumpTube}
     *      instance. This string will be printed when this pipe
     *      dumps messages, and allows people to distinguish which
     *      pipe instance is dumping a message when multiple
     *      {@link DumpTube}s print messages out.
     * @param out
     *      The output to send dumps to.
     * @param next
     *      The next {@link Tube} in the pipeline.
     */
    public DumpTube(String name, PrintStream out, Tube next) {
        super(next);
        this.name = name;
        this.out = out;
        this.staxOut = XMLOutputFactory.newInstance();
        //staxOut.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES,true);
    }

    /**
     * Copy constructor.
     */
    protected DumpTube(DumpTube that, TubeCloner cloner) {
        super(that,cloner);
        this.name = that.name;
        this.out = that.out;
        this.staxOut = that.staxOut;
    }

    @Override
    public NextAction processRequest(Packet request) {
        dump("request",request);
        return super.processRequest(request);
    }

    @Override
    public NextAction processResponse(Packet response) {
        dump("response",response);
        return super.processResponse(response);
    }

    protected void dump(String header, Packet packet) {
        out.println("====["+name+":"+header+"]====");
        if(packet.getMessage()==null)
            out.println("(none)");
        else
            try {
                XMLStreamWriter writer = staxOut.createXMLStreamWriter(new PrintStream(out) {
                    @Override
                    public void close() {
                        // noop
                    }
                });
                writer = createIndenter(writer);
                packet.getMessage().copy().writeTo(writer);
                writer.close();
            } catch (XMLStreamException e) {
                e.printStackTrace(out);
            }
        out.println("============");
    }

    /**
     * Wraps {@link XMLStreamWriter} by an indentation engine if possible.
     *
     * <p>
     * We can do this only when we have {@code stax-utils.jar} in the classpath.
     */
    private XMLStreamWriter createIndenter(XMLStreamWriter writer) {
        try {
            Class clazz = getClass().getClassLoader().loadClass("javanet.staxutils.IndentingXMLStreamWriter");
            Constructor c = clazz.getConstructor(XMLStreamWriter.class);
            writer = (XMLStreamWriter)c.newInstance(writer);
        } catch (Exception e) {
            // if stax-utils.jar is not in the classpath, this will fail
            // so, we'll just have to do without indentation
            if(!warnStaxUtils) {
                warnStaxUtils = true;
                out.println("WARNING: put stax-utils.jar to the classpath to indent the dump output");
            }
        }
        return writer;
    }


    public AbstractTubeImpl copy(TubeCloner cloner) {
        return new DumpTube(this,cloner);
    }

    private static boolean warnStaxUtils;
}
