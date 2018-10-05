/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.model;

import com.sun.tools.ws.wsdl.framework.Entity;

import javax.xml.namespace.QName;

public class HeaderFault extends Fault {

    public HeaderFault(Entity entity) {
        super(entity);
    }

    public HeaderFault(String name, Entity entity) {
        super(name, entity);
    }

    public QName getMessage() {
        return _message;
    }

    public void setMessage(QName message) {
        _message = message;
    }

    public String getPart() {
        return _part;
    }

    public void setPart(String part) {
        _part = part;
    }

    private QName _message;
    private String _part;
}

