/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.test.processor;

import com.sun.istack.localization.Localizer;
import com.sun.tools.ws.processor.ProcessorException;
import junit.framework.TestCase;

/**
 * @author Rama Pulavarthi
 */
public class ProcessorExceptionTest extends TestCase {

    public void testLocalizability() {
        String msg = "Try localizing me";
        ProcessorException pe = new ProcessorException(msg);
        Localizer localizer = new Localizer();
        assertEquals(msg, localizer.localize(pe));
    }
}
