/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.framework;

import org.xml.sax.Locator;

import java.util.HashMap;
import java.util.Map;

import com.sun.tools.ws.wscompile.ErrorReceiver;

/**
 * An entity, typically corresponding to an XML element.
 *
 * @author WS Development Team
 */
public abstract class Entity implements Elemental {

    private final Locator locator;
    protected ErrorReceiver errorReceiver;
    public Entity(Locator locator) {
        this.locator = locator;
    }

    public void setErrorReceiver(ErrorReceiver errorReceiver) {
        this.errorReceiver = errorReceiver;
    }

    public Locator getLocator() {
        return locator;
    }

    public Object getProperty(String key) {
        if (_properties == null)
            return null;
        return _properties.get(key);
    }

    public void setProperty(String key, Object value) {
        if (value == null) {
            removeProperty(key);
            return;
        }

        if (_properties == null) {
            _properties = new HashMap();
        }
        _properties.put(key, value);
    }

    public void removeProperty(String key) {
        if (_properties != null) {
            _properties.remove(key);
        }
    }

    public void withAllSubEntitiesDo(EntityAction action) {
        // no-op by default
    }

    public void withAllQNamesDo(QNameAction action) {
        action.perform(getElementName());
    }

    public void withAllEntityReferencesDo(EntityReferenceAction action) {
        // no-op by default
    }

    public abstract void validateThis();

    protected void failValidation(String key) {
        throw new ValidationException(key, getElementName().getLocalPart());
    }

    protected void failValidation(String key, String arg) {
        throw new ValidationException(
            key,
                arg, getElementName().getLocalPart());
    }

    private Map _properties;
}
