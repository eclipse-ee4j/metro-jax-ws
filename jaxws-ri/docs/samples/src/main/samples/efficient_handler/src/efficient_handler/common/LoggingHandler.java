/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package efficient_handler.common;


import com.sun.xml.ws.api.handler.MessageHandlerContext;
import com.sun.xml.ws.api.handler.MessageHandler;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.streaming.XMLStreamWriterFactory;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.XMLStreamException;
import java.io.PrintStream;
import java.util.Set;
import java.lang.reflect.Constructor;

/*
 * This simple LoggingHandler will log the contents of incoming
 * and outgoing messages. This is implemented as a MessageHandler
 * for better performance over SOAPHandler.
 *
 * @author Rama Pulavarthi
 */
public class LoggingHandler implements MessageHandler<MessageHandlerContext> {

    // change this to redirect output if desired
    private static PrintStream out = System.out;

    public Set<QName> getHeaders() {
        return null;
    }

    public boolean handleMessage(MessageHandlerContext mhc) {
        logToSystemOut(mhc);
        return true;
    }

    public boolean handleFault(MessageHandlerContext mhc) {
        logToSystemOut(mhc);
        return true;
    }

    // nothing to clean up
    public void close(MessageContext messageContext) {
    }

    /**
     * Check the MESSAGE_OUTBOUND_PROPERTY in the context
     * to see if this is an outgoing or incoming message.
     * Writes the message to the OutputStream.
     */
    private void logToSystemOut(MessageHandlerContext mhc) {
        Boolean outboundProperty = (Boolean)
                mhc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty.booleanValue()) {
            out.println("\nOutbound message:");
        } else {
            out.println("\nInbound message:");
        }

        Message m = mhc.getMessage().copy();
        XMLStreamWriter writer = XMLStreamWriterFactory.create(System.out);
        try {
            m.writeTo(createIndenter(writer));
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        out.println("");

    }
    /**
     * Wraps {@link XMLStreamWriter} by an indentation engine if possible.
     *
     * <p>
     * We can do this only when we have <tt>stax-utils.jar</tt> in the classpath.
     */
    private XMLStreamWriter createIndenter(XMLStreamWriter writer) {
        try {
            Class clazz = getClass().getClassLoader().loadClass("javanet.staxutils.IndentingXMLStreamWriter");
            Constructor c = clazz.getConstructor(XMLStreamWriter.class);
            writer = (XMLStreamWriter)c.newInstance(writer);
        } catch (Exception e) {
            // if stax-utils.jar is not in the classpath, this will fail
            // so, we'll just have to do without indentation
            out.println("WARNING: put stax-utils.jar to the classpath to indent the dump output");

        }
        return writer;
    }
}
