/*
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util;

import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.pipe.Codec;
import com.sun.xml.ws.api.pipe.StreamSOAPCodec;
import com.sun.xml.ws.resources.StreamingMessages;
import com.sun.xml.ws.streaming.XMLReaderException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;

public class FastInfosetUtil {

    private static final FISupport fi;
    private static final Logger LOG = Logger.getLogger(FastInfosetUtil.class.getName());

    static {
        FISupport s = null;
        try {
            //check availability of FI codec
            if (Class.forName("com.sun.xml.ws.encoding.fastinfoset.FastInfosetCodec") != null) {
                s = (FISupport) Class.forName("com.sun.xml.ws.util.FISupportImpl").getDeclaredConstructor().newInstance();
                LOG.config(StreamingMessages.FASTINFOSET_ENABLED());
            }
        } catch (ReflectiveOperationException | SecurityException t) {
            if (!(t instanceof ClassNotFoundException) && LOG.isLoggable(Level.FINEST)) {
                LOG.log(Level.FINEST, t.getMessage(), t);
            }
        }
        if (s == null) {
            LOG.config(StreamingMessages.FASTINFOSET_NO_IMPLEMENTATION());
            s = new FISupport() {
                @Override
                public boolean isFastInfosetSource(Source o) {
                    return false;
                }

                @Override
                public XMLStreamReader createFIStreamReader(Source source) {
                    // no compatible implementation of FI was found
                    throw new XMLReaderException("fastinfoset.noImplementation");
                }

                @Override
                public Codec getFICodec(StreamSOAPCodec soapCodec, SOAPVersion version) {
                    return null;
                }

                @Override
                public Codec getFICodec() {
                    return null;
                }
            };
        }
        fi = s;
    }

    private FastInfosetUtil() {
    }

    public static Codec getFICodec(StreamSOAPCodec soapCodec, SOAPVersion version) {
        return fi.getFICodec(soapCodec, version);
    }

    public static Codec getFICodec() {
        return fi.getFICodec();
    }

    public static boolean isFastInfosetSource(Source o) {
        return fi.isFastInfosetSource(o);
    }

    public static XMLStreamReader createFIStreamReader(Source source) {
        return fi.createFIStreamReader(source);
    }

    static interface FISupport {

        boolean isFastInfosetSource(Source o);

        XMLStreamReader createFIStreamReader(Source source);

        Codec getFICodec(StreamSOAPCodec soapCodec, SOAPVersion version);

        Codec getFICodec();
    }

}
