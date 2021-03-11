/*
 * Copyright (c) 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util;

import com.sun.xml.fastinfoset.stax.StAXDocumentParser;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.pipe.Codec;
import com.sun.xml.ws.api.pipe.StreamSOAPCodec;
import com.sun.xml.ws.resources.StreamingMessages;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import org.jvnet.fastinfoset.FastInfosetSource;

/**
 *
 * @author lukas
 */
class FISupportImpl implements FastInfosetUtil.FISupport {

    private static final Logger LOG = Logger.getLogger(FISupportImpl.class.getName());

    private MethodHandle codec, streamCodec;

    FISupportImpl() {
    }

    @Override
    public boolean isFastInfosetSource(Source o) {
        return o instanceof FastInfosetSource;
    }

    /**
     * Returns the FI parser allocated for this thread.
     */
    @Override
    public XMLStreamReader createFIStreamReader(Source source) {
        // Do not use StAX pluggable layer for FI
        StAXDocumentParser stAXDocumentParser = new StAXDocumentParser();
        stAXDocumentParser.setInputStream(((FastInfosetSource) source).getInputStream());
        stAXDocumentParser.setStringInterning(true);
        return stAXDocumentParser;
    }

    @Override
    public Codec getFICodec() {
        try {
            return (Codec) getCodecHandle().invoke();
        } catch (Throwable t) {
            LOG.fine(StreamingMessages.FASTINFOSET_EXCEPTION());
            if (LOG.isLoggable(Level.FINEST)) {
                LOG.log(Level.FINEST, t.getMessage(), t);
            }
        }
        return null;
    }

    @Override
    public Codec getFICodec(StreamSOAPCodec soapCodec, SOAPVersion version) {
        try {
            return (Codec) getStreamCodecHandle().invoke(soapCodec, version);
        } catch (Throwable t) {
            LOG.fine(StreamingMessages.FASTINFOSET_EXCEPTION());
            if (LOG.isLoggable(Level.FINEST)) {
                LOG.log(Level.FINEST, t.getMessage(), t);
            }
        }
        return null;
    }

    private MethodHandle getCodecHandle() throws ReflectiveOperationException {
        if (codec == null) {
            Class c = Class.forName("com.sun.xml.ws.encoding.fastinfoset.FastInfosetCodec");
            Method m = c.getMethod("create");
            codec = MethodHandles.publicLookup().unreflect(m);
        }
        return codec;
    }

    private MethodHandle getStreamCodecHandle() throws ReflectiveOperationException {
        if (streamCodec == null) {
            Class c = Class.forName("com.sun.xml.ws.encoding.fastinfoset.FastInfosetStreamSOAPCodec");
            Method m = c.getMethod("create", StreamSOAPCodec.class, SOAPVersion.class);
            streamCodec = MethodHandles.publicLookup().unreflect(m);
        }
        return streamCodec;
    }

}
