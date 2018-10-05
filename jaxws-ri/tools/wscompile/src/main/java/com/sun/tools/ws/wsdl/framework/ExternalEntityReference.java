/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.framework;

import javax.xml.namespace.QName;

/**
 * A reference to a globally known entity in a document.
 *
 * @author WS Development Team
 */
public class ExternalEntityReference {

    public ExternalEntityReference(
        AbstractDocument document,
        Kind kind,
        QName name) {
        _document = document;
        _kind = kind;
        _name = name;
    }

    public AbstractDocument getDocument() {
        return _document;
    }

    public Kind getKind() {
        return _kind;
    }

    public QName getName() {
        return _name;
    }

    public GloballyKnown resolve() {
        return _document.find(_kind, _name);
    }

    private AbstractDocument _document;
    private Kind _kind;
    private QName _name;
}
