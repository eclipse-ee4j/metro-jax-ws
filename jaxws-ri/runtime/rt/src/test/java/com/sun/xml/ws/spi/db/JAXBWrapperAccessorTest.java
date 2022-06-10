/*
 * Copyright (c) 2013, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.spi.db;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.ws.WebServiceException;
import junit.framework.TestCase;

import javax.xml.namespace.QName;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class JAXBWrapperAccessorTest extends TestCase {

    public void testXmlElementWrapperWithUpperCase() {
        JAXBWrapperAccessor jaxbWrapperAccessor = new JAXBWrapperAccessor(UppercaseRequest.class);
        assertNotNull(jaxbWrapperAccessor.getPropertySetter(new QName("myNames")));
    }

    public void testXmlElementWrapper() {
        JAXBWrapperAccessor jaxbWrapperAccessor = new JAXBWrapperAccessor(HelloRequest.class);
        assertNotNull(jaxbWrapperAccessor.getPropertySetter(new QName("names")));
    }

    public void testAccessor() throws Exception {
        try {
            JAXBWrapperAccessor jaxbWrapperAccessor = new JAXBWrapperAccessor(System.class);
            fail();
        } catch (WebServiceException e) {
            e.printStackTrace(System.out);
        }

        for (Field f : System.class.getDeclaredFields()) {
//            System.out.println("try " + f);
            try {
                FieldGetter getter = new FieldGetter(f);
                fail();
            } catch (WebServiceException e) {
            }
            try {
                FieldSetter setter = new FieldSetter(f);
                fail();
            } catch (WebServiceException e) {
            }
        }
        for (Method m : System.class.getDeclaredMethods()) {
//            System.out.println("try " + m);
            try {
                MethodGetter getter = new MethodGetter(m);
                fail();
            } catch (WebServiceException e) {
            }
            try {
                MethodSetter setter = new MethodSetter(m);
                fail();
            } catch (WebServiceException e) {
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    class HelloRequest {
        @XmlElementWrapper(name = "names")
        @XmlElement(name = "name")
        private List<String> names;

        public List<String> getNames() {
            return this.names;
        }

        public void setNames(List<String> names) {
            this.names = names;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    class UppercaseRequest {
        @XmlElementWrapper(name = "myNames")
        @XmlElement(name = "myName")
        private List<String> names;

        public List<String> getMyNames() {
            return this.names;
        }

        public void setMyNames(List<String> names) {
            this.names = names;
        }
    }
}
