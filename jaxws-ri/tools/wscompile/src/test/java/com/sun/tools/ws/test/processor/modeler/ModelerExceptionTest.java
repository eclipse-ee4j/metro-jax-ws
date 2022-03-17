/*
 * Copyright (c) 2013, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.test.processor.modeler;

import com.sun.istack.localization.Localizer;
import com.sun.tools.ws.processor.modeler.ModelerException;
import junit.framework.TestCase;
import org.junit.Assert;

/**
 *
 * @author ljungman
 */
public class ModelerExceptionTest extends TestCase {
    
    public void testLocalizability() {
        String msg = "Try localizing me";
        ModelerException pe = new ModelerException(msg);
        Localizer localizer = new Localizer();
        Assert.assertEquals(msg, localizer.localize(pe));
    }
}
