/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.encoding.fastinfoset;

import com.sun.xml.fastinfoset.stax.StAXDocumentParser;
import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
import java.io.InputStream;

/**
 * @author Alexey Stashok
 */
public final class FastInfosetStreamReaderRecyclable extends StAXDocumentParser implements XMLStreamReaderFactory.RecycleAware {
    private static final FastInfosetStreamReaderFactory READER_FACTORY = FastInfosetStreamReaderFactory.getInstance();

    public FastInfosetStreamReaderRecyclable() {
        super();
    }
    
    public FastInfosetStreamReaderRecyclable(InputStream in) {
        super(in);
    }
    
    public void onRecycled() {
        READER_FACTORY.doRecycle(this);
    }
}
