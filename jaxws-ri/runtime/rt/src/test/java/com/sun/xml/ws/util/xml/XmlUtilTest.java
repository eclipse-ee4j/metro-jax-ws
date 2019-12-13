/*
 * Copyright (c) 2018, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package com.sun.xml.ws.util.xml;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import junit.framework.TestCase;

public class XmlUtilTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testXmlSecurityDisabled() throws InstantiationException, IllegalAccessException, NoSuchFieldException,
                    NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Method method = com.sun.xml.ws.util.xml.XmlUtil.class.getDeclaredMethod("xmlSecurityDisabled", Boolean.TYPE, Boolean.TYPE);
        method.setAccessible(true);

        Field fieldDisabledBySetting = com.sun.xml.ws.util.xml.XmlUtil.class.getDeclaredField("XML_SECURITY_DISABLED");
        fieldDisabledBySetting.setAccessible(true);

        boolean disabledBySetting = ((Boolean)fieldDisabledBySetting.get(com.sun.xml.ws.util.xml.XmlUtil.class)).booleanValue();

        try {

          fieldDisabledBySetting.set(com.sun.xml.ws.util.xml.XmlUtil.class, true);
          assertFalse((Boolean)method.invoke(com.sun.xml.ws.util.xml.XmlUtil.class, true, true));
          assertFalse((Boolean)method.invoke(com.sun.xml.ws.util.xml.XmlUtil.class, true, false));

          fieldDisabledBySetting.set(com.sun.xml.ws.util.xml.XmlUtil.class, false);
          assertFalse((Boolean)method.invoke(com.sun.xml.ws.util.xml.XmlUtil.class, true, true));
          assertFalse((Boolean)method.invoke(com.sun.xml.ws.util.xml.XmlUtil.class, true, false));

          fieldDisabledBySetting.set(com.sun.xml.ws.util.xml.XmlUtil.class, true);
          assertTrue((Boolean)method.invoke(com.sun.xml.ws.util.xml.XmlUtil.class, false, true));
          assertTrue((Boolean)method.invoke(com.sun.xml.ws.util.xml.XmlUtil.class, false, false));

          fieldDisabledBySetting.set(com.sun.xml.ws.util.xml.XmlUtil.class, false);
          assertTrue((Boolean)method.invoke(com.sun.xml.ws.util.xml.XmlUtil.class, false, true));
          assertFalse((Boolean)method.invoke(com.sun.xml.ws.util.xml.XmlUtil.class, false, false));
       } finally {
          fieldDisabledBySetting.set(com.sun.xml.ws.util.xml.XmlUtil.class, disabledBySetting);
       }
    }
}

