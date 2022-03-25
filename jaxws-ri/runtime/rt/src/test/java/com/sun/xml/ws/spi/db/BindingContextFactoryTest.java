/*
 * Copyright (c) 2015, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.spi.db;

import com.sun.istack.NotNull;
import junit.framework.TestCase;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.SchemaOutputResolver;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Test for BindingContextFactory.
 *
 * @author yaroska
 */
public class BindingContextFactoryTest extends TestCase {

    //<editor-fold desc="setUp">
    private Method getBindingContextFromSpi;

    public void setUp() throws Exception {
        Class<BindingContextFactory> bcf = BindingContextFactory.class;
        getBindingContextFromSpi = bcf.getDeclaredMethod("getBindingContextFromSpi", List.class, BindingInfo.class);
        getBindingContextFromSpi.setAccessible(true);
    }
    //</editor-fold>

//---------- Testing getBindingContextFromSpi --------------

    public void test_receivedOnlyBadImpls() throws Exception {
        BindingContextFactory[] bcf = {new Bad(), new Bad()};
        BindingContext bc = (BindingContext) getBindingContextFromSpi.invoke(null, Arrays.asList(bcf), null);
        // two exceptions should be caught
        assertNull("Null should be returned.", bc);
    }

    public void test_receivedJaxbImpl() throws Exception {
        BindingContextFactory[] bcf = {new Bad(), new Good(), new Jaxb()};
        BindingContext bc = (BindingContext) getBindingContextFromSpi.invoke(null, Arrays.asList(bcf), null);
        assertNotNull("Not null should be returned.", bc);
        assertEquals("BindingContext from JaxbBcf is expected", Jaxb.BC, bc);
    }

    public void test_receivedMoxyImpl() throws Exception {
        BindingContextFactory[] bcf = {new Bad(), new Good(), new Moxy()};
        BindingContext bc = (BindingContext) getBindingContextFromSpi.invoke(null, Arrays.asList(bcf), null);
        assertNotNull("Not null should be returned.", bc);
        assertEquals("BindingContext from MoxyBcf is expected", Moxy.BC, bc);
    }

    public void test_receivedUnexpectedGood() throws Exception {
        BindingContextFactory[] bcf = {new Bad(), new Good()};
        BindingContext bc = (BindingContext) getBindingContextFromSpi.invoke(null, Arrays.asList(bcf), null);
        // one exception should be caught
        assertNotNull("Not null should be returned.", bc);
        assertEquals("BindingContext from GoodBcf is expected", Good.BC, bc);
    }

    public void test_exceptionToBeThrown() {
        boolean expectedExceptionWasThrown = false;
        BindingContextFactory[] bcf = {new Bad(), new JaxbWithException()};
        try {
            getBindingContextFromSpi.invoke(null, Arrays.asList(bcf), null);
        } catch (InvocationTargetException e) {
            Throwable realCause = e.getCause();
            if (realCause instanceof IllegalStateException) {
                expectedExceptionWasThrown = true;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        assertTrue("Illegal state exception should be thrown", expectedExceptionWasThrown);
    }
//---------- end --------------

    //<editor-fold desc="Tools">

    /**
     * Dummy implementation for test.
     * Used to mark BindingContextFactory.
     */
    private static class Marker implements BindingContext {

        @Override
        public Marshaller createMarshaller() throws JAXBException {
            return null;
        }

        @Override
        public Unmarshaller createUnmarshaller() throws JAXBException {
            return null;
        }

        @Override
        public JAXBContext getJAXBContext() {
            return null;
        }

        @Override
        public Object newWrapperInstace(Class<?> wrapperType) throws ReflectiveOperationException {
            return null;
        }

        @Override
        public boolean hasSwaRef() {
            return false;
        }

        @Override
        public QName getElementName(@NotNull Object o) throws JAXBException {
            return null;
        }

        @Override
        public QName getElementName(@NotNull Class o) throws JAXBException {
            return null;
        }

        @Override
        public XMLBridge createBridge(@NotNull TypeInfo ref) {
            return null;
        }

        @Override
        public XMLBridge createFragmentBridge() {
            return null;
        }

        @Override
        public <B, V> PropertyAccessor<B, V> getElementPropertyAccessor(Class<B> wrapperBean, String nsUri, String localName) throws JAXBException {
            return null;
        }

        @Override
        public List<String> getKnownNamespaceURIs() {
            return null;
        }

        @Override
        public void generateSchema(@NotNull SchemaOutputResolver outputResolver) throws IOException {

        }

        @Override
        public QName getTypeName(@NotNull TypeInfo tr) {
            return null;
        }

        @Override
        public String getBuildId() {
            return null;
        }
    }

    private static class Bad extends BindingContextFactory {

        @Override
        protected BindingContext newContext(JAXBContext context) {
            throw new IllegalStateException();
        }

        @Override
        protected BindingContext newContext(BindingInfo bi) {
            throw new IllegalStateException();
        }

        @Override
        protected boolean isFor(String databinding) {
            return false;
        }

    }

    private static class JaxbWithException extends BindingContextFactory {
        private static final Marker BC = new Marker();

        @Override
        protected BindingContext newContext(JAXBContext context) {
            throw new IllegalStateException("Bad context");
        }

        @Override
        protected BindingContext newContext(BindingInfo bi) {
            throw new IllegalStateException("Bad context");
        }

        @Override
        protected boolean isFor(String s) {
            return "org.glassfish.jaxb.runtime.v2.runtime".equals(s);
        }
    }

    private static class Good extends BindingContextFactory {

        private static final Marker BC = new Marker();

        protected BindingContext get() {
            return BC;
        }

        @Override
        protected BindingContext newContext(JAXBContext context) {
            return get();
        }

        @Override
        protected BindingContext newContext(BindingInfo bi) {
            return get();
        }

        @Override
        protected boolean isFor(String s) {
            return false;
        }

    }

    private static class Jaxb extends Good {

        private static final Marker BC = new Marker();

        @Override
        protected BindingContext get() {
            return BC;
        }

        @Override
        protected boolean isFor(String s) {
            return "org.glassfish.jaxb.runtime.v2.runtime".equals(s);
        }
    }

    private static class Moxy extends Jaxb {

        private static final Marker BC = new Marker();

        @Override
        protected BindingContext get() {
            return BC;
        }

        @Override
        protected boolean isFor(String s) {
            return "org.eclipse.persistence.jaxb".equals(s);
        }
    }
    //</editor-fold>
}
