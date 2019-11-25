/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client.sei;

import com.sun.xml.ws.api.model.Parameter;
import com.sun.xml.ws.model.ParameterImpl;
import com.sun.xml.ws.spi.db.PropertyAccessor;

import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceException;
import javax.xml.namespace.QName;
import javax.xml.bind.JAXBException;

/**
 * Moves a Java value unmarshalled from a response message
 * to the right place.
 *
 * <p>
 * Sometimes values are returned as a return value, and
 * others are returned in the {@link Holder} value. Instances
 * of this interface abstracts this detail.
 *
 * <p>
 * {@link ValueSetter} is a stateless behavior encapsulation.
 *
 * @author Kohsuke Kawaguchi
 */
public abstract class ValueSetter {
    private ValueSetter() {}

    /**
     * Moves the value to the expected place.
     *
     * @param obj
     *      The unmarshalled object.
     * @param args
     *      The arguments given to the Java method invocation. If {@code obj}
     *      is supposed to be returned as a {@link Holder} value, a suitable
     *      {@link Holder} is obtained from this argument list and {@code obj}
     *      is set.
     *
     * @return
     *      if {@code obj} is supposed to be returned as a return value
     *      from the method, this method returns {@code obj}. Otherwise null.
     */
    abstract Object put(Object obj, Object[] args);

    /**
     * Singleton instance.
     */
    private static final ValueSetter RETURN_VALUE = new ReturnValue();
    /**
     * {@link Param}s with small index numbers are used often,
     * so we pool them to reduce the footprint.
     */
    private static final ValueSetter[] POOL = new ValueSetter[16];

    static {
        for( int i=0; i<POOL.length; i++ )
            POOL[i] = new Param(i);
    }

    /**
     * Returns a {@link ValueSetter} suitable for the given {@link Parameter}.
     */
    static ValueSetter getSync(ParameterImpl p) {
        int idx = p.getIndex();

        if(idx==-1)
            return RETURN_VALUE;
        if(idx<POOL.length)
            return POOL[idx];
        else
            return new Param(idx);
    }


    private static final class ReturnValue extends ValueSetter {
        Object put(Object obj, Object[] args) {
            return obj;
        }
    }

    static final class Param extends ValueSetter {
        /**
         * Index of the argument to put the value to.
         */
        private final int idx;

        public Param(int idx) {
            this.idx = idx;
        }

        Object put(Object obj, Object[] args) {
            Object arg = args[idx];
            if(arg!=null) {
                // we build model in such a way that this is guaranteed
                assert arg instanceof Holder;
                ((Holder)arg).value = obj;
            }
            // else {
            //   if null is given as a Holder, there's no place to return
            //   the value, so just ignore.
            // }

            // no value to return
            return null;
        }
    }

    /**
     * Singleton instance.
     */
    static final ValueSetter SINGLE_VALUE = new SingleValue();

    /**
     * Used in case of async invocation, where there is only one OUT parameter
     */
    private static final class SingleValue extends ValueSetter {
        /**
         * Set args[0] as the value
         */
        Object put(Object obj, Object[] args) {
            args[0] = obj;
            return null;
        }
    }

    /**
     * OUT parameters are set in async bean
     */
    static final class AsyncBeanValueSetter extends ValueSetter {

        private final PropertyAccessor accessor;

        AsyncBeanValueSetter(ParameterImpl p, Class wrapper) {
            QName name = p.getName();
            try {
                accessor = p.getOwner().getBindingContext().getElementPropertyAccessor(
                            wrapper, name.getNamespaceURI(), name.getLocalPart() );
            } catch (JAXBException e) {
                    throw new WebServiceException(  // TODO: i18n
                        wrapper+" do not have a property of the name "+name,e);
            }
        }

        /**
         * Sets the property in async bean instance
         *
         * @param obj property in async bean
         * @param args args[0] contains async bean instance
         * @return null always
         */
        Object put(Object obj, Object[] args) {
            assert args != null;
            assert args.length == 1;
            assert args[0] != null;

            Object bean = args[0];
            try {
                accessor.set(bean, obj);
            } catch (Exception e) {
                throw new WebServiceException(e);    // TODO:i18n
            }
            return null;
        }

    }
    
}
