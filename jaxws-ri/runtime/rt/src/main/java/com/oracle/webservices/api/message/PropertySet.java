/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.oracle.webservices.api.message;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import jakarta.xml.ws.handler.MessageContext;

/**
 * A set of "properties" that can be accessed via strongly-typed fields
 * as well as reflexibly through the property name.
 *
 * @author Kohsuke Kawaguchi
 */
public interface PropertySet {

    /**
     * Marks a field on {@link PropertySet} as a
     * property of {@link MessageContext}.
     *
     * <p>
     * To make the runtime processing easy, this annotation
     * must be on a public field (since the property name
     * can be set through {@link Map} anyway, you won't be
     * losing abstraction by doing so.)
     *
     * <p>
     * For similar reason, this annotation can be only placed
     * on a reference type, not primitive type.
     *
     * @author Kohsuke Kawaguchi
     */
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD,ElementType.METHOD})
    @interface Property {
        /**
         * Name of the property.
         */
        String[] value();
    }

    boolean containsKey(Object key);

    /**
     * Gets the name of the property.
     *
     * @param key
     *      This field is typed as {@link Object} to follow the {@link Map#get(Object)}
     *      convention, but if anything but {@link String} is passed, this method
     *      just returns null.
     */
    Object get(Object key);

    /**
     * Sets a property.
     *
     * <p>
     * <strong>Implementation Note</strong>
     * This method is slow. Code inside JAX-WS should define strongly-typed
     * fields in this class and access them directly, instead of using this.
     *
     * @see Property
     */
    Object put(String key, Object value);

    /**
     * Checks if this {@link PropertySet} supports a property of the given name.
     */
    boolean supports(Object key);

    Object remove(Object key);

    /**
     * Creates a modifiable {@link Map} view of this {@link PropertySet}.
     * <br>
     * Changes done on this {@link Map} or on {@link PropertySet} object work in both directions - values made to
     * {@link Map} are reflected to {@link PropertySet} and changes done using getters/setters on {@link PropertySet}
     * object are automatically reflected in this {@link Map}.
     * <br>
     * If necessary, it also can hold other values (not present on {@link PropertySet}) -
     * see {@link BasePropertySet#mapAllowsAdditionalProperties()}
     *
     * @return always non-null valid instance.
     */
    Map<String, Object> asMap();
}
