/*
 * Copyright (c) 2012, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.db.sdo;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

import com.sun.xml.ws.spi.db.BindingContext;
import com.sun.xml.ws.spi.db.BindingContextFactory;
import com.sun.xml.ws.spi.db.BindingInfo;

public class SDOContextFactory extends BindingContextFactory {

    public static final String ECLIPSELINK_SDO = "eclipselink.sdo";

    /**
     * Default constructor.
     */
    public SDOContextFactory() {}

    @Override
    protected BindingContext newContext(JAXBContext context) {
        return null;
    }

    @Override
    protected BindingContext newContext(BindingInfo bi) {
        return new SDOContextWrapper(bi);
    }

    @Override
    protected boolean isFor(String str) {
        return (ECLIPSELINK_SDO.equals(str) ||
                str.equals("toplink.sdo") ||
                str.equals(this.getClass().getName())||
                str.equals("org.eclipse.persistence.sdo"));
    }

}
