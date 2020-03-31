/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
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
    public @interface Property {
        /**
         * Name of the property.
         */
        String[] value();
    }

    public boolean containsKey(Object key);

    /**
     * Gets the name of the property.
     *
     * @param key
     *      This field is typed as {@link Object} to follow the {@link Map#get(Object)}
     *      convention, but if anything but {@link String} is passed, this method
     *      just returns null.
     */
    public Object get(Object key);

    /**
     * Sets a property.
     *
     * <h3>Implementation Note</h3>
     * This method is slow. Code inside JAX-WS should define strongly-typed
     * fields in this class and access them directly, instead of using this.
     *
     * @see Property
     */
    public Object put(String key, Object value);

    /**
     * Checks if this {@link PropertySet} supports a property of the given name.
     */
    public boolean supports(Object key);

    public Object remove(Object key);

    /**
     * Creates a {@link Map} view of this {@link PropertySet}.
     *
     * <p>
     * This map is partially live, in the sense that values you set to it
     * will be reflected to {@link PropertySet}.
     *
     * <p>
     * However, this map may not pick up changes made
     * to {@link PropertySet} after the view is created.
     *
     * @deprecated use newer implementation {@link com.sun.xml.ws.api.PropertySet#asMap()} which produces
     * readwrite {@link Map}
     *
     * @return
     *      always non-null valid instance.
     */
    @Deprecated
    public Map<String,Object> createMapView();
    
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
    public Map<String, Object> asMap();
}
