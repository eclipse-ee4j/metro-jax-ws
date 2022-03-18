/*
 * Copyright (c) 2019, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package com.sun.xml.ws.api.streaming;

import java.lang.reflect.Field;
import junit.framework.TestCase;

public class ContextClassloaderLocalTest extends TestCase {

    public void testSynchronizedMap() throws Exception {
      ContextClassloaderLocal<XMLStreamReaderFactory> streamReader =
            new ContextClassloaderLocal<XMLStreamReaderFactory>() {
                @Override
                protected XMLStreamReaderFactory initialValue() {
                  return null;
                }
      };
      Field f = streamReader.getClass().getSuperclass().getDeclaredField("CACHE");
      f.setAccessible(true);
        assertEquals("java.util.Collections$SynchronizedMap", f.get(streamReader).getClass().getName());
    }
}

