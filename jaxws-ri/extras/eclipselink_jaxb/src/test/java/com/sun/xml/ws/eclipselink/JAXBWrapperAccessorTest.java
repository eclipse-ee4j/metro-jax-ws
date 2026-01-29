/*
 * Copyright (c) 2012, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.eclipselink;

import javax.xml.namespace.QName;

import com.sun.xml.ws.db.toplink.JAXBContextFactory;
import jakarta.xml.ws.WebServiceFeature;

import com.oracle.webservices.api.databinding.Databinding;
import com.oracle.webservices.api.databinding.DatabindingMode;
import com.oracle.webservices.api.databinding.DatabindingFactory;
import com.oracle.webservices.api.databinding.DatabindingModeFeature;

import com.sun.xml.ws.binding.WebServiceFeatureList;
import com.sun.xml.ws.spi.db.JAXBWrapperAccessor;
import com.sun.xml.ws.test.*;
import junit.framework.TestCase;

public class JAXBWrapperAccessorTest extends TestCase {
    public void testJAXBWrapperAccessorCreation() {
        JAXBWrapperAccessor jwa = null;
        jwa = new JAXBWrapperAccessor(com.sun.xml.ws.test.BaseStruct.class);
        assertNotNull(jwa.getPropertyAccessor("", "floatMessage"));
        assertNotNull(jwa.getPropertyAccessor("", "floatMessage"));

        jwa = new JAXBWrapperAccessor(com.sun.xml.ws.test.ExtendedStruct.class);
        assertNotNull(jwa.getPropertyAccessor("", "shortMessage"));
        assertNotNull(jwa.getPropertyAccessor("", "floatMessage"));
        assertNotNull(jwa.getPropertyAccessor("", "anotherIntMessage"));
        assertNotNull(jwa.getPropertyAccessor("", "intMessage"));
        assertNotNull(jwa.getPropertyAccessor("", "stringMessage"));

        jwa = new JAXBWrapperAccessor(
                com.sun.xml.ws.test.MoreExtendedStruct.class);
        assertNotNull(jwa.getPropertyAccessor("", "shortMessage"));
        assertNotNull(jwa.getPropertyAccessor("", "floatMessage"));
        assertNotNull(jwa.getPropertyAccessor("", "anotherIntMessage"));
        assertNotNull(jwa.getPropertyAccessor("", "intMessage"));
        assertNotNull(jwa.getPropertyAccessor("", "stringMessage"));
    }

    public void testDatabindingCreation() {
        Class<?> sei = DocServicePortType.class;
        DatabindingFactory fac = DatabindingFactory.newInstance();
        Databinding.Builder b = fac.createBuilder(sei, null);
        DatabindingModeFeature dbf = new DatabindingModeFeature(
                JAXBContextFactory.ECLIPSELINK_JAXB);
        WebServiceFeature[] f = { dbf };
        b.feature(f);
        String ns = "http://performance.bea.com";
        b.serviceName(new QName(ns, "DocService"));
        b.portName(new QName(ns, "DocServicePortTypePort"));
        assertNotNull(b.build());

    }    

    @DatabindingMode(JAXBContextFactory.ECLIPSELINK_JAXB)
    static class SEB {}       
    public void testDatabindingModeAnnotationToFeature() throws Exception {
        DatabindingMode a = SEB.class.getAnnotation(DatabindingMode.class); 
        DatabindingModeFeature f = (DatabindingModeFeature) WebServiceFeatureList.getFeature(a);
        assertEquals(f.getMode(), a.value());
    }
}
