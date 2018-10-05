/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.document;

/**
 * Entity corresponding to the "documentation" WSDL element.
 *
 * @author WS Development Team
 */
public class Documentation {

    public Documentation(String s) {
        content = s;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String s) {
        content = s;
    }

    public void accept(WSDLDocumentVisitor visitor) throws Exception {
        visitor.visit(this);
    }

    private String content;
}
